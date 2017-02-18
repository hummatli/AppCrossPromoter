package com.mobapphome.mahads.types;

/**
 * Created by settar on 2/18/17.
 */

public class Urls {

    private String urlForProgramVersion;
    private String urlForProgramList;
    private String urlRootOnServer;

    public Urls(String urlForProgramVersion, String urlForProgramList, String urlRootOnServer) {
        this.urlForProgramVersion = urlForProgramVersion;
        this.urlForProgramList = urlForProgramList;
        this.urlRootOnServer = urlRootOnServer;
    }

    public String getUrlForProgramVersion() {
        return urlForProgramVersion;
    }

    public void setUrlForProgramVersion(String urlForProgramVersion) {
        this.urlForProgramVersion = urlForProgramVersion;
    }

    public String getUrlForProgramList() {
        return urlForProgramList;
    }

    public void setUrlForProgramList(String urlForProgramList) {
        this.urlForProgramList = urlForProgramList;
    }

    public String getUrlRootOnServer() {
        return urlRootOnServer;
    }

    public void setUrlRootOnServer(String urlRootOnServer) {
        this.urlRootOnServer = urlRootOnServer;
    }
}
