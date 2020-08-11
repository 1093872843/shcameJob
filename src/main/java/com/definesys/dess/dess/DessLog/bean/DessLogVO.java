package com.definesys.dess.dess.DessLog.bean;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.ColumnType;

import java.util.Date;

public class DessLogVO {


    private String logId;
    private String jobNo;
    private String status;
    private String doTime;
    private String creationDate;
    private Integer retryTimes;
    private String jobName;
    private String jobType;
    private DessLogPayload reqPayload;
    private DessLogPayload resPayload;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoTime() {
        return doTime;
    }

    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public DessLogPayload getReqPayload() {
        return reqPayload;
    }

    public void setReqPayload(DessLogPayload reqPayload) {
        this.reqPayload = reqPayload;
    }

    public DessLogPayload getResPayload() {
        return resPayload;
    }

    public void setResPayload(DessLogPayload resPayload) {
        this.resPayload = resPayload;
    }
}
