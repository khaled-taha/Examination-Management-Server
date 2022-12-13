package com.OnlineExaminationSystem.App.entity.exam;

import com.OnlineExaminationSystem.App.entity.validation.SemestNum;
import com.OnlineExaminationSystem.App.entity.validation.Year;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "subject", schema = "examinationsystem")
@AllArgsConstructor
@Setter
@Getter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    @NotBlank
    String name;

    @Column(name = "semest_num")
    @SemestNum
    int semest_num;

    @Year
    @Column(name = "year")
    private byte year;
}
