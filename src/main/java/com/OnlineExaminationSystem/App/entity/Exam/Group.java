package com.OnlineExaminationSystem.App.entity.Exam;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "group", schema = "public")
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
