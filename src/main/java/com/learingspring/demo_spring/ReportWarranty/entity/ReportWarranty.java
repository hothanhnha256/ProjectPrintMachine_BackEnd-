package com.learingspring.demo_spring.ReportWarranty.entity;

import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
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
public class ReportWarranty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name="id_machine", referencedColumnName = "id", nullable = false)
    PrintMachine printMachine;

    String description;
    LocalDate createDate;
}
