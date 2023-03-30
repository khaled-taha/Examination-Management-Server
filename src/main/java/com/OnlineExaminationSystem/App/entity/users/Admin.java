package com.OnlineExaminationSystem.App.entity.users;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admin", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@DiscriminatorValue("admin")
public class Admin extends User implements Serializable {

    @Column(name = "specialization")
    @NotBlank
    private String specialization;



}