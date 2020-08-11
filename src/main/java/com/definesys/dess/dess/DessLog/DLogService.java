package com.definesys.dess.dess.DessLog;


import com.definesys.dess.dess.DessLog.bean.DessLog;
import com.definesys.dess.dess.DessLog.bean.DessLogPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName DLogService
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-3 13:30
 * @Version 1.0
 **/
@Service
public class DLogService {

    @Autowired
    private DLogDao dLogDao;

    public void insertLog(DessLog dessLog){
        dLogDao.insertLog(dessLog);
    }

    public void insertPayload(DessLogPayload dessLogPayload){
        dLogDao.insertPayload(dessLogPayload);
    }



}
