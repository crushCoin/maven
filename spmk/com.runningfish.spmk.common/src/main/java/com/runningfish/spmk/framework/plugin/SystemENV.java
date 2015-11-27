package com.runningfish.spmk.framework.plugin;

public class SystemENV {
    public SystemENV(String systemNumber, String orgId){
        this.orgId = orgId;
        this.systemNumber = systemNumber;
    }
    
    private String systemNumber;

    private String orgId;

    public String getSystemNumber() {
        return systemNumber;
    }

    public String getOrgId() {
        return orgId;
    }
}
