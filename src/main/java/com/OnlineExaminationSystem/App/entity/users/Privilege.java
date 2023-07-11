package com.OnlineExaminationSystem.App.entity.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "privilege", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class Privilege implements Serializable {

    @Id
    @SequenceGenerator(name = "privilege_sequence", sequenceName = "privilege_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilege_sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

}
