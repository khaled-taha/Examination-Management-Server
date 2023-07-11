package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.entity.dto.user.student.StudentDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.OnlineExaminationSystem.App.entity.users.Student;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.ExamAttemptRepository;
import com.OnlineExaminationSystem.App.repository.StudentRepository;
import com.OnlineExaminationSystem.App.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public StudentDto saveStudent(StudentDto student) {

        if(this.userRepository.findUserByEmailAndIdNot(student.getEmail(), student.getId()).isPresent())
            throw new ApiException("Duplicate Email");
        else if(this.studentRepository.findStudentByUniversityIdAndIdNot(student.getUniversityId(), student.getId()).isPresent())
            throw new ApiException("Duplicate UniversityId");

        Student savedStudent = new Student();
        savedStudent.setId(student.getId());
        savedStudent.setFirstName(student.getFirstName());
        savedStudent.setLastName(student.getLastName());
        savedStudent.setEmail(student.getEmail());
        savedStudent.setPassword(student.getPassword());
        savedStudent.setGroup(student.getGroup());
        savedStudent.setUniversityId(student.getUniversityId());
        savedStudent.setRoles(student.getRoles());
        savedStudent.setEnable(student.isEnable());
        savedStudent.setLocked(student.isLocked());

        this.studentRepository.save(savedStudent);

        return student;
    }

    public void deleteById(Long studentId) {
        this.studentRepository.findStudentById(studentId)
                .orElseThrow(() -> new ApiException("Student not found"));
        this.studentRepository.deleteById(studentId);
    }

    public List<StudentDto> getAllStudents() {

        List<Student> students = this.studentRepository.findAll();
        List<StudentDto> savedStudents = new ArrayList<>();

        students.stream().forEach(student -> {

            StudentDto savedStudent = new StudentDto();
            savedStudent.setId(student.getId());
            savedStudent.setFirstName(student.getFirstName());
            savedStudent.setLastName(student.getLastName());
            savedStudent.setEmail(student.getEmail());
            savedStudent.setPassword(student.getPassword());
            savedStudent.setGroup(student.getGroup());
            savedStudent.setUniversityId(student.getUniversityId());
            savedStudent.setRoles(student.getRoles());
            savedStudent.setEnable(student.isEnable());
            savedStudent.setLocked(student.isLocked());

            savedStudents.add(savedStudent);

        });

        return savedStudents;
    }

    public StudentDto getStudentById(Long studentId) {
        Student student = this.studentRepository.findStudentById(studentId)
                .orElseThrow(() -> new ApiException("Student not found"));

        StudentDto savedStudent = new StudentDto();
        savedStudent.setId(student.getId());
        savedStudent.setFirstName(student.getFirstName());
        savedStudent.setLastName(student.getLastName());
        savedStudent.setEmail(student.getEmail());
        savedStudent.setPassword(student.getPassword());
        savedStudent.setGroup(student.getGroup());
        savedStudent.setUniversityId(student.getUniversityId());
        savedStudent.setRoles(student.getRoles());
        savedStudent.setEnable(student.isEnable());
        savedStudent.setLocked(student.isLocked());

        return savedStudent;
    }

    public long getGroupOfStudent(long id){
        Group group = this.studentRepository.findGroupByStudentId(id);
        if(group != null){
            return group.getId();
        }
        throw new ApiException("There is no group for this student");
    }



}