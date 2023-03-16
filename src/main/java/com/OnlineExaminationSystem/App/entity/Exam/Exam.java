package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Exam", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "examName", unique = true, nullable = false)
    private String examName;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime endTime;

    @Column(name = "successRate")
    private double successRate;

    @Column(name = "state")
    private boolean state = false;


    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Course course;
    // getters and setters
}