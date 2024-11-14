package com.learingspring.demo_spring.PrintMachine.service;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.File.service.FileService;
import com.learingspring.demo_spring.History.entity.History;
import com.learingspring.demo_spring.History.services.HistoryService;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.services.UserService;
import com.learingspring.demo_spring.enums.BaseEnum;
import com.learingspring.demo_spring.enums.BuildingEnum;
import com.learingspring.demo_spring.Location.services.LocationService;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintingImplementRequest;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintmachineCreationRequest;
import com.learingspring.demo_spring.Location.Entity.Location;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.PrintMachine.repository.PrintmachineRepository;
import com.learingspring.demo_spring.enums.Process;
import com.learingspring.demo_spring.enums.TypeOfPage;
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
        TypeOfPage typeOfPage = request.getTypeOfPage();
        boolean printColor = request.isPrintColor();

        History history = historyService.logPrinting(user, file, printMachine, copiesNum, sideOfPage, typeOfPage, printColor);

        ApiResponse<String> result = new ApiResponse<>();
        result.setCode(200);
        result.setMessage("Print registration successful");
        result.setResult("Request was Recorded");

        // Thêm job vào hàng đợi của máy in
        addPrintJobToQueue(request.getIdPrinter(), history);

        return result;
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
        return printmachineRepository.findInkStatusById(ID) > 0 && printmachineRepository.findPaperStatusById(ID) > 0;
    }

    // handle printing in a specified thread
    private void processPrintJobs(String printerId) {
        try {
            BlockingQueue<History> queue = printQueues.get(printerId);
            if (queue == null) {
                throw new AppException(ErrorCode.INVALID_KEY);
            }
            while (!Thread.currentThread().isInterrupted()) {
                if(!checkInkAndPaperStatus(printerId)){
                    System.out.println("Waiting for ink status");
                    printmachineRepository.updatePrinterStatus(false, printerId);
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

}



