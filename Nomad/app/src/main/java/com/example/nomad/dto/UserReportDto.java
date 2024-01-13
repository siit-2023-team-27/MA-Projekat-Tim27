package com.example.nomad.dto;


public class UserReportDto {

    private Long reportingUser;
    private Long reportedUser;
    private String reason;
    private int reportStatus;

    public UserReportDto() {}

    public UserReportDto(Long reportingUser, Long reportedUser, String reason, int reportStatus) {
        this.reportingUser = reportingUser;
        this.reportedUser = reportedUser;
        this.reason = reason;
        this.reportStatus = reportStatus;
    }

    public Long getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(Long reportingUser) {
        this.reportingUser = reportingUser;
    }

    public Long getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(Long reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(int reportStatus) {
        this.reportStatus = reportStatus;
    }
}
