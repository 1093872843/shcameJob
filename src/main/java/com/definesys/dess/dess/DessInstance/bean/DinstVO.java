package com.definesys.dess.dess.DessInstance.bean;

import com.alibaba.fastjson.JSONObject;
import com.definesys.dess.dess.DessBusiness.bean.DessBusiness;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DinstVO
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-7-28 18:20
 * @Version 1.0
 **/
public class DinstVO {
    //DinstBean
    private String logId;
    private String jobNo;
    private String businessId;
    private String jobName;
    private String jobStatus;
    private String jobFrequency; //json字符串，接收corn表达式
    private Date nextDoTime;
    private Date aliveStart;
    private Date aliveEnd;
    private String groupName;
    private Integer sucessTimes;
    private Integer failTimes;
    private Double avgRunTime;
    private String jobDescription;
    //DessBusiness
    private String InvokeUrl;
    private String businessName;
    private String headerPayload;
    private String businessDesc;
    private String webServiceType;
    private String serviceName;
    private String portType;
    private String invokeOperation;
    private String bodyPayload;
    private List<Map<String,String>> dateList;
    private String jobRate; // 频率类型方便前端展示定义时间
    private String businessType;

    public DinstVO(){}

    public DinstVO(DinstBean dinstBean, DessBusiness dessBusiness){
        //DinstBean
        businessId=dinstBean.getBusinessId();
        jobName=dinstBean.getJobName();
        //等待使用corn工具类转换
        //frequency=dinstBean.getFrequency();
        nextDoTime=dinstBean.getNextDoTime();
        aliveStart=dinstBean.getAliveStart();
        aliveEnd=dinstBean.getAliveEnd();
        groupName=dinstBean.getGroupName();
        sucessTimes=dinstBean.getSucessTimes();
        failTimes=dinstBean.getFailTimes();
        avgRunTime=dinstBean.getAvgRunTime();
        jobDescription=dinstBean.getJobDescription();
        //DessBusiness
        InvokeUrl=dessBusiness.getInvokeUrl();
        webServiceType=dessBusiness.getWebserviceType();
        serviceName=dessBusiness.getWebserviceType();
        portType=dessBusiness.getPortType();
        invokeOperation=dessBusiness.getInvokeOperation();
        bodyPayload=dessBusiness.getBodyPayload();
        businessDesc=dessBusiness.getBusinessDesc();
        businessName=dessBusiness.getBusinessName();
        headerPayload=dessBusiness.getHeaderPayload();
        businessType=dessBusiness.getBusinessType();
    }

    public  DinstBean getDinstBean(){
        DinstBean dinstBean =new DinstBean();
        dinstBean.setAliveEnd(aliveEnd);
        dinstBean.setAliveStart(aliveStart);
        dinstBean.setAvgRunTime(avgRunTime);
        dinstBean.setBusinessId(businessId);
        dinstBean.setJobDescription(jobDescription);
        dinstBean.setFailTimes(failTimes);
        //等待corn工具转换
        //dinstBean.setFrequency(frequency);
        dinstBean.setGroupName(groupName);
        dinstBean.setJobName(jobName);
        dinstBean.setJobNo(jobNo);
        dinstBean.setNextDoTime(nextDoTime);
        return dinstBean;
    }

    public  DessBusiness getDessBusiness(){
        DessBusiness dessBusiness = new DessBusiness();
        dessBusiness.setBusinessId(businessId);
        dessBusiness.setInvokeOperation(invokeOperation);
        dessBusiness.setBodyPayload(bodyPayload);
        dessBusiness.setPortType(portType);
        dessBusiness.setServiceName(serviceName);
        dessBusiness.setWebserviceType(webServiceType);
        dessBusiness.setInvokeUrl(InvokeUrl);
        dessBusiness.setBusinessDesc(businessDesc);
        dessBusiness.setBusinessName(businessName);
        dessBusiness.setHeaderPayload(headerPayload);
        dessBusiness.setBusinessType(businessType);
        return dessBusiness;
    }



    public String getJobNo() { return jobNo; }

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

    public String getInvokeUrl() {
        return InvokeUrl;
    }

    public void setInvokeUrl(String invokeUrl) {
        InvokeUrl = invokeUrl;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getWebServiceType() {
        return webServiceType;
    }

    public void setWebServiceType(String webServiceType) {
        this.webServiceType = webServiceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    public String getInvokeOperation() {
        return invokeOperation;
    }

    public void setInvokeOperation(String invokeOperation) {
        this.invokeOperation = invokeOperation;
    }

    public String getBodyPayload() {
        return bodyPayload;
    }

    public void setBodyPayload(String bodyPayload) {
        this.bodyPayload = bodyPayload;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public List<Map<String, String>> getDateList() {
        return dateList;
    }

    public void setDateList(List<Map<String, String>> dateList) {
        this.dateList = dateList;
    }

    public String getJobRate() {
        return jobRate;
    }

    public void setJobRate(String jobRate) {
        this.jobRate = jobRate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getHeaderPayload() {
        return headerPayload;
    }

    public void setHeaderPayload(String headerPayload) {
        this.headerPayload = headerPayload;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

}
