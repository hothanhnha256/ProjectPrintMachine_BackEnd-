package com.learingspring.demo_spring.PrintMachine.dto.request;

import com.learingspring.demo_spring.enums.TypeOfPage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrintingImplementRequest {
    String idUser;
    String idPrinter;
    String idFile;
    int copiesNum;
    boolean sideOfPage;  //false is 1 side of page, true is 2 sides of page.
    TypeOfPage typeOfPage;
    boolean printColor; // true means print color, false means don't print color
}
