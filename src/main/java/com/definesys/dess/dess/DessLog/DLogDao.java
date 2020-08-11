package com.definesys.dess.dess.DessLog;


import com.definesys.dess.dess.DessLog.bean.DessLog;
import com.definesys.dess.dess.DessLog.bean.DessLogPayload;
import com.definesys.mpaas.query.MpaasQuery;
import com.definesys.mpaas.query.MpaasQueryFactory;
import com.definesys.mpaas.query.db.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @ClassName DLogDao
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-3 13:30
 * @Version 1.0
 **/
@Repository
public class DLogDao {

    @Autowired
    private MpaasQueryFactory sw;
    @Value("${database.type}")
    private String dbType;


    public void insertLog(DessLog dessLog){
        sw.buildQuery().doInsert(dessLog);
    }

    public void insertPayload(DessLogPayload dessLogPayload){
        sw.buildQuery().doInsert(dessLogPayload);
    }


}
