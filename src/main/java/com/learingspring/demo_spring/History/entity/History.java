package com.learingspring.demo_spring.History.entity;

import com.learingspring.demo_spring.enums.PageType;
import com.learingspring.demo_spring.enums.Process;
import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
    @JoinColumn(name = "mssv", referencedColumnName = "mssv", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false)
    File file;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "printer_id", referencedColumnName = "id", nullable = true)
    PrintMachine printMachine;

    @Column(name = "copies_num")
    int copiesNum;
    @Column(name = "side_of_page")
    boolean sideOfPage;  //false is 1 side of page, true is 2 sides of page.
    @Column(name = "type_of_page")
    @Enumerated(EnumType.STRING)
    PageType typeOfPage;

    @Column(name = "print_color")
    boolean printColor; //false is no, true is yes

    @Column(name = "date")
    LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    Process process;

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", file=" + file +
                ", printMachine=" + printMachine +
                ", copiesNum=" + copiesNum +
                ", sideOfPage=" + sideOfPage +
                ", typeOfPage=" + typeOfPage +
                ", printColor=" + printColor +
                ", date=" + date +
                ", process=" + process +
                '}';
    }
}
