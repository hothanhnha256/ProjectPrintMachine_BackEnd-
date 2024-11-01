package com.learingspring.demo_spring.File.entity;

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
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Lob
    @Column(name="file_size",columnDefinition = "LONGBLOB")
    byte[] fileSize;

    String filetype;

    String name;

    @ManyToOne
    @JoinColumn(name="mssv", referencedColumnName = "mssv", nullable = false)
    User user;

    LocalDate uploadDate;
}
