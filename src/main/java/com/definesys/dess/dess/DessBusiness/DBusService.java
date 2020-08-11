package com.definesys.dess.dess.DessBusiness;

import com.definesys.dess.dess.DessBusiness.bean.DessBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName DBusService
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-3 13:27
 * @Version 1.0
 **/
@Service
public class DBusService {

    @Autowired
    private DBusDao dBusDao;


    public DessBusiness getBusinessById(String businessId){
        return dBusDao.getBusinessById(businessId);
    }

}
