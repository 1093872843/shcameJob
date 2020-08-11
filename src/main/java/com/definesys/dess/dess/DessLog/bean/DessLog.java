package com.definesys.dess.dess.DessLog.bean;

import com.definesys.mpaas.query.annotation.*;

import java.util.Date;

/**
 * @ClassName DessLog
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-7-28 18:21
 * @Version 1.0
 **/
@Table(value = "DESS_LOG")
public class DessLog {
    private String logId;
    private String jobNo;
    private String groupName;
    private String logStatus;
    //计划执行时间
    private Date doTime;
    //实际执行时间
    private Long runTime;
    //实际执行时间
    private Date creationDate;
    private Integer retryTimes;
    @Column(type = ColumnType.JAVA)
    private String jobName;
    @Column(type = ColumnType.JAVA)
    private String jobType;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }
}
