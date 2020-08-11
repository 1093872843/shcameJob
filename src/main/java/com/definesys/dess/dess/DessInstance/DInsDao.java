package com.definesys.dess.dess.DessInstance;

import com.definesys.dess.dess.DessInstance.bean.DinstBean;
import com.definesys.dess.dess.DessInstance.bean.DinstVO;
import com.definesys.dsgc.service.dess.CommonReqBean;

import com.definesys.mpaas.query.MpaasQuery;
import com.definesys.mpaas.query.MpaasQueryFactory;
import com.definesys.mpaas.query.db.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName DInsDao
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-3 13:29
 * @Version 1.0
 **/
@Repository
public class DInsDao {


    @Value("${database.type}")
    private String dbType;


    @Autowired
    private MpaasQueryFactory sw;

    public List<DinstVO> findAll (){
        return sw.buildQuery().sql("select * from DESS_INSTANCE i\n" +
                "left join dess_business b on i.business_id=b.business_id \n" +
                "where i.job_status ='1'")
                .doQuery(DinstVO.class);
    }

    public DinstVO getInstanceByNo (String servNo){
        return sw.buildQuery().sql("select * from DESS_INSTANCE i\n" +
                "left join dess_business b on i.business_id=b.business_id" +
                "and i.job_no =#servNo")
                .eq("servNo",servNo)
                .doQueryFirst(DinstVO.class);
    }

    public void updateStatis (DinstBean dinstBean){
         sw.buildQuery()
                 .eq("JOB_NO",dinstBean.getJobNo())
                 .eq("GROUP_NAME",dinstBean.getGroupName())
                 .update("SUCESS_TIMES",dinstBean.getSucessTimes())
                 .update("FAIL_TIMES",dinstBean.getFailTimes())
                 .update("AVG_RUN_TIME",dinstBean.getAvgRunTime())
                 .update("NEXT_DO_TIME",dinstBean.getNextDoTime())
                 .update("JOB_STATUS",dinstBean.getJobStatus())
                 .doUpdate(dinstBean);
    }


}
