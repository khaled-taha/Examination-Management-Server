package com.OnlineExaminationSystem.App.entity.Exam;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.List;

@Entity
@Table(name = "groups", schema = "public")
@Data
public class Group {

    @Id
    @SequenceGenerator(name = "groups_sequence", sequenceName = "groups_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> courses;
}
