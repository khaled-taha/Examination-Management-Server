package com.OnlineExaminationSystem.App.entity.users;

import com.OnlineExaminationSystem.App.entity.validation.Year;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student", schema = "examinationsystem")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@DiscriminatorValue("student")
public class Student extends User implements Serializable {

    @Range(min = 1, max = 4)
    @Column(name = "year")
    @Year
    private byte year;


}
