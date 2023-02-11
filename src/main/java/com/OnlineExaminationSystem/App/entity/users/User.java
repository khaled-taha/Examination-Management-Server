package com.OnlineExaminationSystem.App.entity.users;

import com.OnlineExaminationSystem.App.entity.users.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "examinationsystem")
@NoArgsConstructor
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "userType")
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "firstName")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "lastName")
    private String lastName;


    @Column(name = "universityId")
    @Positive
    @NotNull
    private Long universityId;

    @Column(name = "email", unique = true)
    @NotBlank
    private String email;

    @Column(name = "password")
    @NotBlank
    @Size(min = 6)
    private String password;

    @ManyToMany
    @NotNull
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "locked")
    private boolean locked = false;

    @Column(name = "enable")
    private boolean enable = true;

}
