package com.learingspring.demo_spring.History.dto.request;

import com.learingspring.demo_spring.enums.PageType;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;


@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreateHistoryDTO implements Serializable {

    @NotNull
    private Integer copiesNum;

    private LocalDate date;

    @NotNull
    private Boolean printColor;

    private String process;

    @NotNull
    private Boolean sideOfPage;

    private PageType typeOfPage;

//    private String mssv;

    private String printerId;

    private String fileId;

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

    public PageType getTypeOfPage() {
        return typeOfPage;
    }

    public void setTypeOfPage(PageType typeOfPage) {
        this.typeOfPage = typeOfPage;
    }

//    public String getMssv() {
//        return mssv;
//    }
//
//    public void setMssv(String mssv) {
//        this.mssv = mssv;
//    }

    public String getPrinterId() {
        return printerId;
    }

    public void setPrinterId(String printerId) {
        this.printerId = printerId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
