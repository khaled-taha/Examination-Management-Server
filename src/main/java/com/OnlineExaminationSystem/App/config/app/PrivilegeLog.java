package com.OnlineExaminationSystem.App.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeLog {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PrivilegeLog(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addPrivilege() {
        try {
            // Insert permissions
            insertPrivilege(1, PrivilegeConstant.MANAGE_ADMIN_EXAMS_ROLE);
            insertPrivilege(2, PrivilegeConstant.MANAGE_EXAMS_ROLE);
            insertPrivilege(3, PrivilegeConstant.SOLVE_EXAM_ROLE);
            insertPrivilege(4, PrivilegeConstant.SHOW_ATTEMPTS_ROLE);
            insertPrivilege(5, PrivilegeConstant.SHOW_COURSE_EXAMS_ROLE);
            insertPrivilege(6, PrivilegeConstant.SHOW_ADMINS_LIST_ROLE);
            insertPrivilege(7, PrivilegeConstant.SHOW_PROFILE);

            insertPrivilege(8, PrivilegeConstant.MANAGE_ADMIN);
            insertPrivilege(9, PrivilegeConstant.MANAGE_STUDENT);

            insertPrivilege(10, PrivilegeConstant.MANAGE_COURSES);
            insertPrivilege(11, PrivilegeConstant.SHOW_COURSES_OF_ADMIN);
            insertPrivilege(12, PrivilegeConstant.SHOW_COURSES_OF_GROUP);


            insertPrivilege(13, PrivilegeConstant.MANAGE_ROLE);
            insertPrivilege(14, PrivilegeConstant.SHOW_STUDENTS_COURSE_ROLE);
            insertPrivilege(15, PrivilegeConstant.SHOW_ALL_STUDENTS);
            insertPrivilege(16, PrivilegeConstant.DASHBOARD_ROLE);


        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            insertRole(1, "Super Admin");
        }catch (Exception e){ e.printStackTrace(); }

        try {
            insertRole_Privileges();
        }catch (Exception e){ e.printStackTrace();}

        try {
            insertSuperAdmin();
        }catch (Exception e){e.printStackTrace(); }

        try {
            insertAdmin_Role();
        }catch (Exception e){ e.printStackTrace();}

        try {
            insertGroups();
        }catch (Exception e){ e.printStackTrace();}
    }

    private void insertPrivilege(int id, String name) {
        String sql = "INSERT INTO privilege (id, name) VALUES (?,?)";
        jdbcTemplate.update(sql, id, name);
    }

    private void insertRole(int id, String name) {
        String sql = "INSERT INTO role (id, role) VALUES (?,?)";
        jdbcTemplate.update(sql, id, name);
    }

    private void insertRole_Privileges() {
        String sql = """
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 4);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 5);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 6);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 7);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 8);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 9);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 10);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 13);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 15);
                INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 16);
                """;
        jdbcTemplate.update(sql);
    }

    private void insertSuperAdmin() {
        String userSql = "INSERT INTO public.\"user\" (user_type, id, first_name, last_name, university_id, email, password, locked, enable) " +
                "VALUES ('admin', 1, 'Super', 'Admin', 123456789, 'admin@example.com', 'Superadmin@2023', false, true)";
        jdbcTemplate.update(userSql);

        String adminSql = "INSERT INTO admin (id, specialization) VALUES (1, 'DEVELOPER')";
        jdbcTemplate.update(adminSql);
    }

    private void insertAdmin_Role() {
        String sql = """
                INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
                """;
        jdbcTemplate.update(sql);
    }

    private void insertGroups() {
        String sql = """
                INSERT INTO public.groups (id, name) VALUES (1, 'Group 1');
                INSERT INTO public.groups (id, name) VALUES (2, 'Group 2');
                INSERT INTO public.groups (id, name) VALUES (3, 'Group 3');
                INSERT INTO public.groups (id, name) VALUES (4, 'Group 4');
                """;
        jdbcTemplate.update(sql);
    }


}
