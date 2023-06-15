package com.OnlineExaminationSystem.App.entity.Exam;


import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "course", schema = "public")
public class Course {

    @Id
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "groups_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Group group;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Exam> exams;

    @ManyToMany
    @JoinTable(name = "admin_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id"))
    @JsonIgnore
    private List<Admin> admins;



}
