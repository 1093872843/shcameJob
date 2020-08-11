package com.definesys.dess.dess.DessInstance.bean;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.ColumnType;
import com.definesys.mpaas.query.annotation.Table;

import java.util.Date;

/**
 * @ClassName DinstBean
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-7-28 18:18
 * @Version 1.0
 **/
@Table(value = "DESS_INSTANCE")
public class DinstBean {
    private String jobNo;
    private String businessId;
    private String jobName;
    private String jobStatus;
    private String jobFrequency; //json字符串，接收corn表达式
    private Date nextDoTime;
    private Date aliveStart;
    private Date aliveEnd;
    private String groupName="default";
    private Integer sucessTimes;
    private Integer failTimes;
    private Double avgRunTime;
    private String jobDescription;
    @Column(type = ColumnType.JAVA)
    private String businessType;
    private String jobRate;


    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }


    public Date getNextDoTime() {
        return nextDoTime;
    }

    public void setNextDoTime(Date nextDoTime) {
        this.nextDoTime = nextDoTime;
    }

    public Date getAliveStart() {
        return aliveStart;
    }

    public void setAliveStart(Date aliveStart) {
        this.aliveStart = aliveStart;
    }

    public Date getAliveEnd() {
        return aliveEnd;
    }

    public void setAliveEnd(Date aliveEnd) {
        this.aliveEnd = aliveEnd;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getSucessTimes() {
        return sucessTimes;
    }

    public void setSucessTimes(Integer sucessTimes) {
        this.sucessTimes = sucessTimes;
    }

    public Integer getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }

    public Double getAvgRunTime() {
        return avgRunTime;
    }

    public void setAvgRunTime(Double avgRunTime) {
        this.avgRunTime = avgRunTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobFrequency() {
        return jobFrequency;
    }

    public void setJobFrequency(String jobFrequency) {
        this.jobFrequency = jobFrequency;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobRate() {
        return jobRate;
    }

    public void setJobRate(String jobRate) {
        this.jobRate = jobRate;
    }

    @Override
    public String toString() {
        return "DinstBean{" +
                "jobNo='" + jobNo + '\'' +
                ", businessId='" + businessId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", jobFrequency='" + jobFrequency + '\'' +
                ", nextDoTime=" + nextDoTime +
                ", aliveStart=" + aliveStart +
                ", aliveEnd=" + aliveEnd +
                ", groupName='" + groupName + '\'' +
                ", sucessTimes=" + sucessTimes +
                ", failTimes=" + failTimes +
                ", avgRunTime=" + avgRunTime +
                ", jobDescription='" + jobDescription + '\'' +
                ", businessType='" + businessType + '\'' +
                ", jobRate='" + jobRate + '\'' +
                '}';
    }
}
