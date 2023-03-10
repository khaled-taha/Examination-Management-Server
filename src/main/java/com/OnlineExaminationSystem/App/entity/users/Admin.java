package com.OnlineExaminationSystem.App.entity.users;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToMany
    @NotNull
    @JoinTable(name = "admin_groups",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "groups_id"))
    private Set<Group> groups = new HashSet<>();

}