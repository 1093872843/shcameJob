package com.definesys.dess.dess.DessInstance;

import com.alibaba.fastjson.JSONObject;
import com.definesys.dess.dess.DessInstance.bean.DinstBean;
import com.definesys.dess.dess.DessInstance.bean.DinstVO;
import com.definesys.dsgc.service.dess.CommonReqBean;

import com.definesys.mpaas.query.db.PageQueryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName DInsService
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-3 13:29
 * @Version 1.0
 **/

@Service
public class DInsService {


    //服务调度程序的访问地址
    private  String dessServiceUrl;

    @Autowired
    private DInsDao dInsDao;

    public void updateStatis (DinstBean dinstBean){
        dInsDao.updateStatis(dinstBean);
    }




}
