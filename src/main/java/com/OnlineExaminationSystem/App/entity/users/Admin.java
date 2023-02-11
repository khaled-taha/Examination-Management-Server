package com.OnlineExaminationSystem.App.entity.users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "admin", schema = "examinationsystem")
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
