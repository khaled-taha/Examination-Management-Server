package com.OnlineExaminationSystem.App.config.security;

import com.OnlineExaminationSystem.App.config.app.PrivilegeConstant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins (List.of("http://localhost:4200"));
                    config.setAllowedMethods (List.of("*"));
                    config.setAllowCredentials (true);
                    config.setAllowedHeaders (List.of("*"));
                    config.setMaxAge (2500L);
                    return config;
                }).and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/recovery/**").permitAll()
                .requestMatchers("/api-doc", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()

                .requestMatchers("/admins/add").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN)
                .requestMatchers("/admins/update").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN)
                .requestMatchers("/admins/getAll").hasAnyAuthority(PrivilegeConstant.SHOW_ADMINS_LIST_ROLE, PrivilegeConstant.MANAGE_ADMIN, PrivilegeConstant.MANAGE_COURSES, PrivilegeConstant.DASHBOARD_ROLE)
                .requestMatchers("/admins/get/**").hasAnyAuthority(PrivilegeConstant.SHOW_PROFILE, PrivilegeConstant.MANAGE_ADMIN) //

                .requestMatchers("/students/add").hasAnyAuthority(PrivilegeConstant.MANAGE_STUDENT)
                .requestMatchers("/students/update").hasAnyAuthority(PrivilegeConstant.MANAGE_STUDENT)
                .requestMatchers("/students/getAll").hasAnyAuthority(PrivilegeConstant.SHOW_ALL_STUDENTS, PrivilegeConstant.MANAGE_STUDENT, PrivilegeConstant.DASHBOARD_ROLE)
                .requestMatchers("/students/get/**").hasAnyAuthority(PrivilegeConstant.SHOW_PROFILE, PrivilegeConstant.MANAGE_STUDENT)
                .requestMatchers("/students/delete/**").hasAnyAuthority(PrivilegeConstant.MANAGE_STUDENT)


                .requestMatchers("/roles/add").hasAnyAuthority(PrivilegeConstant.MANAGE_ROLE)
                .requestMatchers("/roles/update").hasAnyAuthority(PrivilegeConstant.MANAGE_ROLE)
                .requestMatchers("/roles/getAll").hasAnyAuthority(PrivilegeConstant.MANAGE_ROLE, PrivilegeConstant.MANAGE_ADMIN, PrivilegeConstant.MANAGE_STUDENT) //
                .requestMatchers("/roles/get/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ROLE) //
                .requestMatchers("/roles/delete/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ROLE)
                .requestMatchers("/privilege").hasAnyAuthority(PrivilegeConstant.MANAGE_ROLE)

                .requestMatchers("/groups/getAll").hasAnyAuthority(PrivilegeConstant.MANAGE_COURSES, PrivilegeConstant.MANAGE_STUDENT, PrivilegeConstant.MANAGE_COURSES)
                .requestMatchers("/groups/StudentGroup/**").hasAnyAuthority(PrivilegeConstant.SHOW_COURSES_OF_GROUP)
                .requestMatchers("/courses/save").hasAnyAuthority(PrivilegeConstant.MANAGE_COURSES)
                .requestMatchers("/courses/delete/**").hasAnyAuthority(PrivilegeConstant.MANAGE_COURSES)
                .requestMatchers("/courses/get/**").hasAnyAuthority(PrivilegeConstant.MANAGE_COURSES, PrivilegeConstant.SHOW_COURSES_OF_ADMIN,
                        PrivilegeConstant.SHOW_COURSES_OF_GROUP, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/courses/getAll").hasAnyAuthority(PrivilegeConstant.MANAGE_COURSES, PrivilegeConstant.DASHBOARD_ROLE)
                .requestMatchers("/courses/groupCourses/**").hasAnyAuthority(PrivilegeConstant.SHOW_COURSES_OF_GROUP, PrivilegeConstant.MANAGE_COURSES, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/courses/getCoursesByAdminId/**").hasAnyAuthority(PrivilegeConstant.SHOW_COURSES_OF_ADMIN, PrivilegeConstant.MANAGE_COURSES)
                .requestMatchers("/courses/getStudentsByCourseId/**").hasAnyAuthority(PrivilegeConstant.SHOW_STUDENTS_COURSE_ROLE, PrivilegeConstant.MANAGE_COURSES)



                .requestMatchers("/exam/save").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/saveStandardQuestions/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/saveCompleteStudentAnswer/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/saveSelectedStudentAnswer/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/saveCodeQuestion/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/judgeCodeQuestion").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/endExam/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/createResult/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/attemptExam/**").hasAnyAuthority(PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/testExam/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)

                .requestMatchers("/exam/getExam/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/usersAttemptedExam/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SHOW_ATTEMPTS_ROLE, PrivilegeConstant.MANAGE_COURSES)
                .requestMatchers("/exam/renderExam/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/getStandardQuestions/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/getResult/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/getCodeQuestions/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/getAllExams").hasAnyAuthority(PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.DASHBOARD_ROLE)
                .requestMatchers("/exam/getAllStudentAnswers/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/getAllCourseExams/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.SHOW_COURSE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)
                .requestMatchers("/exam/attempts/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/getCodeStatus/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE, PrivilegeConstant.SOLVE_EXAM_ROLE)

                .requestMatchers("/exam/delete").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/deleteTestCases").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/deleteStandardQuestion").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/deleteStandardQuestionAnswer").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/deleteCodeQuestion/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)
                .requestMatchers("/exam/delete/**").hasAnyAuthority(PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE, PrivilegeConstant.MANAGE_EXAMS_ROLE)

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
/**
 * USER:
 * SHOW PROFILE (FOR ONE USER)
 * =============================================================
 * ADMIN:
 * Manage Admins: ADD, UPDATE, DELETE, SHOW ALL ADMINS
 * SHOW ALL ADMINS
 * =============================================================
 * STUDENT:
 * MANAGE STUDENT: ADD, UPDATE, DELETE, SHOW ALL STUDENTS
 * SHOW ALL STUDENTS
 * =============================================================
 * MANAGE ROLE: ADD, UPDATE, DELETE, SHOW ALL ROLES, SHOW ROLE, SHOW PRIVILEGES
 * =============================================================
 * MANAGE COURSES : SHOW ALL, SHOW_COURSE_DETAILS_ROLE, SAVE, DELETE, SHOW ALL GROUPS
 * SHOW COURSES OF ADMIN ===> ++ SHOW_COURSE_DETAILS_ROLE, SHOW EXAMS OF COURSE
 * SHOW_COURSE_OF_GROUP_ROLE ==> ++ SHOW_COURSE_DETAILS_ROLE, SHOW EXAMS OF COURSE (STUDENT)
 * =============================================================
 */