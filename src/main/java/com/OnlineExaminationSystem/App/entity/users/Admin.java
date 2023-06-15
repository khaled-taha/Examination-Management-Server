package com.OnlineExaminationSystem.App.entity.users;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.io.Serializable;

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