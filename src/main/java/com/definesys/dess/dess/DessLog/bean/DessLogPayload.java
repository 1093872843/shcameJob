package com.definesys.dess.dess.DessLog.bean;

import com.definesys.mpaas.query.annotation.RowID;
import com.definesys.mpaas.query.annotation.RowIDType;
import com.definesys.mpaas.query.annotation.Table;

/**
 * @ClassName DessLogPayload
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-3 14:30
 * @Version 1.0
 **/
@Table("DESS_LOG_PAYLOAD")
public class DessLogPayload {

    private String logId  ;
    private String headerPayload ;
    private String bodyPayload ;
    private String errPayload ;
    private String creationDate ;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getHeaderPayload() {
        return headerPayload;
    }

    public void setHeaderPayload(String headerPayload) {
        this.headerPayload = headerPayload;
    }

    public String getBodyPayload() {
        return bodyPayload;
    }

    public void setBodyPayload(String bodyPayload) {
        this.bodyPayload = bodyPayload;
    }

    public String getErrPayload() {
        return errPayload;
    }

    public void setErrPayload(String errPayload) {
        this.errPayload = errPayload;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
