package com.OnlineExaminationSystem.App.config.app;

import org.springframework.stereotype.Component;

@Component
public class PrivilegeLogExecutor {
    private PrivilegeLog privilegeLog;

    public PrivilegeLogExecutor(PrivilegeLog privilegeLog) {
        this.privilegeLog = privilegeLog;
        execute();
    }

    public void execute() {
        privilegeLog.addPrivilege();
    }
}
