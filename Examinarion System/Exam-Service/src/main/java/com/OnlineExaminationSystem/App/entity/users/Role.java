package com.OnlineExaminationSystem.App.entity.users;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;

@Entity
@Table(name = "role", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class Role implements Serializable {

    @Id
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    @NotBlank
    private String role;


}
