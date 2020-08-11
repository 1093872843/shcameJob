package com.definesys.dess.dess.DessBusiness;

import com.definesys.dess.dess.DessBusiness.bean.DessBusiness;
import com.definesys.mpaas.query.MpaasQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @ClassName DBusDao
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-3 13:28
 * @Version 1.0
 **/
@Repository
public class DBusDao {

    @Autowired
    private MpaasQueryFactory sw;
    @Value("${database.type}")
    private String dbType;


    public DessBusiness getBusinessById(String businessId){
       return sw.buildQuery()
               .eq("BUSINESS_ID",businessId)
               .doQueryFirst(DessBusiness.class);
    }
}
