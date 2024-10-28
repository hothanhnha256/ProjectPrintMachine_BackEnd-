package com.learingspring.demo_spring.History.entity;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(
//        indexes = {
//                @Index(name = "idx_mssv", columnList = "mssv"),
//                @Index(name = "idx_file_id", columnList = "file_id"),
//                @Index(name = "idx_print_id", columnList = "print_id"),
//                @Index(name = "idx_date", columnList = "date")
//        }
)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mssv",referencedColumnName = "mssv",nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="file_id",referencedColumnName ="id",nullable = false )
    File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "printer_id", referencedColumnName ="id", nullable = false )
    PrintMachine printMachine;

    Boolean status;
    Number pageNum;
    LocalDate date;

}
