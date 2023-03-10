package com.OnlineExaminationSystem.App.entity.users;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.entity.validation.Year;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@DiscriminatorValue("student")
public class Student extends User implements Serializable {

    @ManyToOne
    @JoinColumn(name = "groups_id")
    private Group group;
}