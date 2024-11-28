package com.learingspring.demo_spring.History.dto.reponse;

import com.learingspring.demo_spring.File.dto.response.FileResponse;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.User.dto.response.UserResponse;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;


@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistoryDTO implements Serializable {

    @Size(max = 255)
    private String id;

    @NotNull
    private Integer copiesNum;

    private LocalDate date;

    @NotNull
    private Boolean printColor;

    private String process;

    @NotNull
    private Boolean sideOfPage;

    private String typeOfPage;

    private UserResponse user;

    private AvailablePrintersResponse printMachine;

    private FileResponse file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    public Integer getCopiesNum() {
        return copiesNum;
    }

    public void setCopiesNum(@NotNull Integer copiesNum) {
        this.copiesNum = copiesNum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @NotNull
    public Boolean getPrintColor() {
        return printColor;
    }

    public void setPrintColor(@NotNull Boolean printColor) {
        this.printColor = printColor;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @NotNull
    public Boolean getSideOfPage() {
        return sideOfPage;
    }

    public void setSideOfPage(@NotNull Boolean sideOfPage) {
        this.sideOfPage = sideOfPage;
    }

    public String getTypeOfPage() {
        return typeOfPage;
    }

    public void setTypeOfPage(String typeOfPage) {
        this.typeOfPage = typeOfPage;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public AvailablePrintersResponse getPrintMachine() {
        return printMachine;
    }

    public void setPrintMachine(AvailablePrintersResponse printMachine) {
        this.printMachine = printMachine;
    }

    public FileResponse getFile() {
        return file;
    }

    public void setFile(FileResponse file) {
        this.file = file;
    }
}
