package com.definesys.dess.dess.DessBusiness.bean;

import com.definesys.mpaas.query.annotation.*;

/**
 * @ClassName DessBusiness
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-7-28 18:19
 * @Version 1.0
 **/
@Table(value = "DESS_BUSINESS")
public class DessBusiness {

    @RowID(sequence = "dess_business_s",type= RowIDType.AUTO)
    private String businessId;
    private String businessName;
    private String invokeUrl;
    private String webserviceType;
    private String serviceName;
    private String portType;
    private String invokeOperation;
    private String bodyPayload;
    private String headerPayload;
    private String businessDesc;
    private String businessType;




    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    public String getWebserviceType() {
        return webserviceType;
    }

    public void setWebserviceType(String webserviceType) {
        this.webserviceType = webserviceType;
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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getInvokeUrl() {
        return invokeUrl;
    }

    public void setInvokeUrl(String invokeUrl) {
        this.invokeUrl = invokeUrl;
    }

    @Override
    public String toString() {
        return "DessBusiness{" +
                "businessId='" + businessId + '\'' +
                ", businessName='" + businessName + '\'' +
                ", invokeUrl='" + invokeUrl + '\'' +
                ", webserviceType='" + webserviceType + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", portType='" + portType + '\'' +
                ", invokeOperation='" + invokeOperation + '\'' +
                ", bodyPayload='" + bodyPayload + '\'' +
                ", headerPayload='" + headerPayload + '\'' +
                ", businessDesc='" + businessDesc + '\'' +
                ", businessType='" + businessType + '\'' +
                '}';
    }
}
