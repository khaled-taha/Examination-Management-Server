package com.OnlineExaminationSystem.App.config.app;

public class PrivilegeConstant {

    // ExamController
    public static final String MANAGE_EXAMS_ROLE = "MANAGE_EXAMS_ROLE";

    public static final String MANAGE_ADMIN_EXAMS_ROLE = "MANAGE_ADMIN_EXAMS_ROLE";

    public static final String SOLVE_EXAM_ROLE = "SOLVE_EXAM_ROLE";
    public static final String SHOW_ATTEMPTS_ROLE = "SHOW_ATTEMPTS_ROLE";
    public static final String SHOW_COURSE_EXAMS_ROLE = "SHOW_COURSE_EXAMS_ROLE";

    // AdminController
    public static final String SHOW_ADMINS_LIST_ROLE = "SHOW_ADMINS_LIST_ROLE";
    public static final String SHOW_PROFILE = "SHOW_PROFILE_ROLE";
    public static final String MANAGE_ADMIN = "MANAGE_ADMIN_ROLE";
    public static final String MANAGE_STUDENT = "MANAGE_STUDENT_ROLE";

    // CourseController
    public static final String MANAGE_COURSES = "MANAGE_COURSES_ROLE";
    public static final String SHOW_COURSES_OF_ADMIN = "SHOW_COURSES_OF_ADMIN_ROLE";
    public static final String SHOW_COURSES_OF_GROUP = "SHOW_COURSE_OF_GROUP_ROLE";

    public static final String DASHBOARD_ROLE = "DASHBOARD_ROLE";

    // PrivilegeController
    public static final String MANAGE_ROLE = "MANAGE_ROLE";

    // StudentController
    public static final String SHOW_STUDENTS_COURSE_ROLE = "SHOW_STUDENTS_COURSE_ROLE";
    public static final String SHOW_ALL_STUDENTS = "SHOW_ALL_STUDENTS_ROLE";






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