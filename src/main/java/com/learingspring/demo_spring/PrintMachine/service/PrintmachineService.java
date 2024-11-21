package com.learingspring.demo_spring.PrintMachine.service;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.File.service.FileService;
import com.learingspring.demo_spring.History.entity.History;
import com.learingspring.demo_spring.History.services.HistoryService;
import com.learingspring.demo_spring.PriceSetting.service.PriceSettingService;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.services.UserService;
import com.learingspring.demo_spring.Wallet.service.WalletService;
import com.learingspring.demo_spring.enums.*;
import com.learingspring.demo_spring.Location.services.LocationService;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintingImplementRequest;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintmachineCreationRequest;
import com.learingspring.demo_spring.Location.Entity.Location;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.PrintMachine.repository.PrintmachineRepository;
import com.learingspring.demo_spring.enums.Process;
import com.learingspring.demo_spring.exception.ApiResponse;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RequiredArgsConstructor
@Service
public class PrintmachineService {

    private final PrintmachineRepository printmachineRepository;
    private final LocationService locationService;
    private final UserService userService;
    private final FileService fileService;
    private final HistoryService historyService;
    private final PriceSettingService priceSettingService;
    private final WalletService walletService;

    // Khởi tạo map để lưu trữ thread của từng máy in
    private final Map<String, Thread> printerThreads = new HashMap<>();

    // Khởi tạo map để lưu trữ hàng đợi của từng máy in
    private final Map<String, BlockingQueue<History>> printQueues = new HashMap<>();

    public PrintMachine createPrintMachine(PrintmachineCreationRequest request) {
        PrintMachine printer = new PrintMachine();
        BaseEnum base = convertStringToBaseEnum(request.getBase());
        BuildingEnum building = convertStringToBuildingEnum(request.getBuilding());
        int floor = request.getFloor();

        Location location = locationService.findLocationByBaseBuildingFloor(base, building, floor);

        printer.setName(request.getName());
        printer.setManufacturer(request.getManufacturer());
        printer.setModel(request.getModel());
        printer.setDescription(request.getDescription());
        printer.setAddress(location);

        printer.setInkStatus(100);
        printer.setPaperStatus(request.getPaperStatus());
        printer.setCapacity(request.getCapacity());
        printer.setPrintWaiting(0);

        printer.setWarrantyDate(request.getWarrantyDate());
        printer.setStatus(true);

        printer.setCreateDate(LocalDate.now());
        printer.setUpdateDate(LocalDate.now());

        PrintMachine savedPrinter = printmachineRepository.save(printer);

        // Khởi tạo own queue cho máy in này
        BlockingQueue<History> printQueue = new LinkedBlockingQueue<>();
        printQueues.put(savedPrinter.getId(), printQueue);

        // Khởi tạo own thread cho máy in này
        Thread printerThread = new Thread(() -> processPrintJobs(savedPrinter.getId()));
        printerThread.start();

        // store thread into map
        printerThreads.put(savedPrinter.getId(), printerThread);

        return savedPrinter;
    }

    @Transactional
    public boolean updateStatus(Boolean status, String ID) {
        int record = printmachineRepository.updatePrinterStatus(status, ID);
        if (record <= 0) throw new AppException(ErrorCode.INVALID_KEY);

        // check thread of printer now
        Thread currentThread = printerThreads.get(ID);

        // if status true but there is no thread running, create thread to do job
        if (status && (currentThread == null || !currentThread.isAlive())) {
            // create thread
            Thread newThread = new Thread(() -> processPrintJobs(ID));
            printerThreads.put(ID, newThread);  // Store new thread into Map
            newThread.start();
        }
        return true;
    }

    public ApiResponse<List<AvailablePrintersResponse>> availablePrinters(String base, String building, int floor) {
        BaseEnum temp_base = convertStringToBaseEnum(base);
        BuildingEnum temp_building = convertStringToBuildingEnum(building);
        List<AvailablePrintersResponse> printers = printmachineRepository.findAvailbalePrinters(temp_base, temp_building, floor);
        ApiResponse<List<AvailablePrintersResponse>> result = new ApiResponse<>();

        if (printers == null || printers.isEmpty()) {
            result.setMessage("No available printers found for the specified location");
            result.setResult(Collections.emptyList());
        } else {
            result.setMessage("Available printers found");
            result.setResult(printers);
        }
        return result;
    }

    public ApiResponse<String> implementPrint(PrintingImplementRequest request) {
        /*this place will handle wallet of User who has enough to pay later*/
        User user = userService.getUserById(request.getIdUser());
        File file = fileService.getFileById(request.getIdFile());
        PrintMachine printMachine = getPrintMachine(request.getIdPrinter());
        int copiesNum = request.getCopiesNum();
        boolean sideOfPage = request.isSideOfPage();
        PageType typeOfPage = request.getTypeOfPage();
        boolean printColor = request.isPrintColor();

        ApiResponse<String> result = new ApiResponse<>();

        if(!checkWallet(user,copiesNum,sideOfPage,typeOfPage,printColor)) {
            result.setCode(401);
            result.setMessage("User's balance is not enough");
            return result;
        }

        History history = historyService.logPrinting(user, file, printMachine, copiesNum, sideOfPage, typeOfPage, printColor);

        result.setCode(200);
        result.setMessage("Print registration successful");
        result.setResult("Request was Recorded");

        // Thêm job vào hàng đợi của máy in
        addPrintJobToQueue(request.getIdPrinter(), history);

        return result;
    }

    public ApiResponse<List<PrintMachine>> allPrintMachines() {
        ApiResponse<List<PrintMachine>> result = new ApiResponse<>();

        try {
            // Truy vấn tất cả các máy in
            List<PrintMachine> printMachines = printmachineRepository.findAll();

            // Kiểm tra nếu không có máy in nào
            if (printMachines.isEmpty()) {
                result.setCode(404);  // Có thể dùng 404 để chỉ ra rằng không có dữ liệu
                result.setMessage("No print machines found.");
            } else {
                result.setCode(200);  // Thành công
                result.setResult(printMachines);
                result.setMessage("Successfully fetched print machines.");
            }
        } catch (Exception e) {
            // Xử lý lỗi chung nếu có
            result.setCode(500);  // 500 cho lỗi server
            result.setMessage("An error occurred while fetching print machines: " + e.getMessage());
        }

        return result;
    }

    public ApiResponse<Void> deletePrintMachine(String id) {
        ApiResponse<Void> response = new ApiResponse<>();
        try{
            boolean exists = printmachineRepository.existsById(id);
            if(!exists){
                response.setCode(404);
                response.setMessage("Printer with ID " + id + " not found.");
                return response;
            }
            Integer printWaiting = printmachineRepository.findWaitingById(id);
            if(printWaiting > 0){
                response.setCode(402);
                response.setMessage("Delete fail! Printer is working");
                return response;
            }
            printmachineRepository.deleteById(id);
            response.setCode(200);
            response.setMessage("Print machine deleted successfully.");
        } catch (Exception e) {
            response.setCode(500); // Lỗi server
            response.setMessage("Error occurred while deleting print machine: " + e.getMessage());
        }
        return response;
    }

    //-------------------------------------------Utilization tool-------------------------------------------------------


    private BaseEnum convertStringToBaseEnum(String baseStr) {
        try {
            return BaseEnum.valueOf(baseStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.IVALID_BASE_ENUMCODE);
        }
    }

    private BuildingEnum convertStringToBuildingEnum(String buildingStr) {
        try {
            return BuildingEnum.valueOf(buildingStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.IVALID_BUILDING_ENUMCODE);
        }
    }

    public PrintMachine getPrintMachine(String ID) {
        return printmachineRepository.findById(ID).orElse(null);
    }

    private boolean getStatus(String ID) {
        return printmachineRepository.findStatusById(ID);
    }

    private boolean checkInkAndPaperStatus(String ID) {
        return printmachineRepository.findInkStatusById(ID) > 0 || printmachineRepository.findPaperStatusById(ID) > 0;
    }

    // handle printing in a specified thread
    private void processPrintJobs(String printerId) {
        try {
            BlockingQueue<History> queue = printQueues.get(printerId);
            if (queue == null) {
                throw new AppException(ErrorCode.INVALID_KEY);
            }
            while (!Thread.currentThread().isInterrupted()) {
                if(!checkInkAndPaperStatus(printerId)){     //Check if there is no enough ink and paper then auto turn off the printer
                    System.out.println("Waiting for ink or paper status");
                    printmachineRepository.updatePrinterStatus(false, printerId);
                    break;
                }
                if(!getStatus(printerId)) break;
                History request = queue.take();
                performPrinting(request, printerId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Printing job processing stopped for printer: " + printerId);
        }
    }

    // implement job
    private void performPrinting(History request, String printerId) {
        System.out.println("Printing job: " + request);
        String id = request.getId();
        try {
            historyService.updateProcess(id, Process.Implementing);
            // Giả lập time in: làm cho thread "stop" 10 seconds
            printmachineRepository.updatePrintMachine(1,1, 0, printerId);
            Thread.sleep(20000);  // 20000 milliseconds = 20 second
            printmachineRepository.updatePrintMachine(0,0, -1, printerId);
            historyService.updateProcess(id, Process.Completed);
            System.out.println("Printing job completed successfully.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Printing job was interrupted: " + request);
        }
    }

    // add job into queue
    private void addPrintJobToQueue(String printerId, History request) {
        BlockingQueue<History> queue = printQueues.get(printerId);
        if (queue != null) {
            // Check does thread of printer still working?
            Thread printerThread = printerThreads.get(printerId);
            if (printerThread == null || !printerThread.isAlive()) {
                // if there is no thread or that was over, initial new thread
                Thread newThread = new Thread(() -> processPrintJobs(printerId));
                printerThreads.put(printerId, newThread);
                newThread.start();
            }
            try {
                queue.put(request);  // add job into queue
                printmachineRepository.updatePrintMachine(0,0, 1, printerId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
    }

    @PostConstruct
    private void init(){
        List<String> idPrinters = printmachineRepository.findAllByStatus(true);

        for (String idPrinter : idPrinters ){
            // Khởi tạo own queue cho máy in này
            BlockingQueue<History> printQueue = new LinkedBlockingQueue<>();
            List<History> jobsNotDone = historyService.getNotDoneHistoryByNotProcess(Process.Completed, idPrinter);
            for(History jobNotDone : jobsNotDone ){
                try {
                    printQueue.put(jobNotDone);
                }catch (InterruptedException e) {
                    // handle thread when interrupt
                    Thread.currentThread().interrupt(); // ensure trạng thái gián đoạn của luồng được giữ lại
                }
            }
            printQueues.put(idPrinter, printQueue);

            // Khởi tạo own thread cho máy in này
            Thread printerThread = new Thread(() -> processPrintJobs(idPrinter));
            printerThread.start();

            // store thread into map
            printerThreads.put(idPrinter, printerThread);
        }
    }

    private boolean checkWallet(User user, int copiesNum, boolean sideOfPage, PageType typeOfPage, boolean printColor ){
        Number pricePerPage = priceSettingService.getPrice(typeOfPage, printColor);
        // Kiểm tra kiểu của pricePerPage và ép kiểu cho phù hợp
        double pricePerPageDouble = pricePerPage.doubleValue();

        // Giả sử balance của người dùng là số nguyên (hoặc bạn có thể dùng .doubleValue() nếu cần so sánh với double)
        double userBalance = user.getWallet().getBalance().doubleValue();

        if(userBalance >= pricePerPageDouble){
            user.setWallet(walletService.payment(pricePerPage));
            return true;
        }
        return false;
    }

}



