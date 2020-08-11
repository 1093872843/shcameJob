package com.definesys.dess.dess.quartz;


  import com.alibaba.fastjson.JSON;
  import com.alibaba.fastjson.JSONObject;
  import com.definesys.dess.dess.DessBusiness.DBusService;
  import com.definesys.dess.dess.DessBusiness.bean.DessBusiness;
  import com.definesys.dess.dess.DessInstance.DInsService;
  import com.definesys.dess.dess.DessInstance.bean.DinstBean;
  import com.definesys.dess.dess.DessLog.DLogService;
  import com.definesys.dess.dess.DessLog.bean.DessLog;
  import com.definesys.dess.dess.DessLog.bean.DessLogPayload;
  import com.definesys.dess.dess.invokeurl.invokeWebService;
  import com.definesys.dess.dess.util.CommonUtils;
  import com.definesys.dess.dess.util.StringUtils;
  import org.quartz.*;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.scheduling.quartz.SchedulerFactoryBean;
  import org.springframework.stereotype.Component;
  import java.io.*;
  import java.text.SimpleDateFormat;
  import java.util.*;
  import java.util.Calendar;


/**

 12  * :@DisallowConcurrentExecution : 此标记用在实现Job的类上面,意思是不允许并发执行.
 13  * :注意org.quartz.threadPool.threadCount线程池中线程的数量至少要多个,否则@DisallowConcurrentExecution不生效
 14  * :假如Job的设置时间间隔为3秒,但Job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 15  */
   @DisallowConcurrentExecution
 @Component
 public class DynamicJob implements Job {
     private Logger logger = LoggerFactory.getLogger(DynamicJob.class);
     @Autowired
     DLogService dLogService;
     @Autowired
     DInsService dInsService;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    DBusService dBusService;
     /**
       * 核心方法,Quartz Job真正的执行逻辑.
       * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
       * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
       */
     @Override
     public void execute(JobExecutionContext executorContext) throws JobExecutionException {
         JobDataMap map = executorContext.getMergedJobDataMap();
         DessBusiness dessBusiness=dBusService.getBusinessById( map.getString("businessId"));
         logger.info("业务信息如下[{}]",dessBusiness);
         DinstBean dinstBean = getDinstBean(map);
         dinstBean.setNextDoTime( executorContext.getNextFireTime());
         if(!checkAlive(dinstBean)){
             return;
         }
         logger.info("实例信息如下[{}]",dinstBean);
         long startTime = System.currentTimeMillis();
         Date startDate = Calendar.getInstance().getTime();
         String logId = UUID.randomUUID().toString();
         String jobStatus ="0";
         DessLog dessLog =getDinstLog(map,logId);
         dessLog.setDoTime(startDate);
         DessLogPayload reqPayload = new DessLogPayload();
         reqPayload.setLogId(logId+"req");
         reqPayload.setBodyPayload(dessBusiness.getBodyPayload());
         reqPayload.setHeaderPayload(dessBusiness.getHeaderPayload());
         DessLogPayload resPayload = new DessLogPayload();
         resPayload.setLogId(logId+"res");
         if (!StringUtils.getStringUtil.isEmpty(dessBusiness.getInvokeUrl())) {
             String result="";
             try{
                    switch (dessBusiness.getBusinessType()){
                        case "REST":
                            result = invokeREST(dessBusiness);
                            break;
                        case "SOAP":
                            result = invokeWSDL(dessBusiness);
                            break;
                        default:
                            logger.error("本次业务未指定业务类型，不予处理");
                            break;
                    }
                     jobStatus="1";
                     resPayload.setBodyPayload(result);
                     dinstBean.setSucessTimes(dinstBean.getSucessTimes()+1);
                 }catch (Exception e){
                     result = CommonUtils.getExceptionTrackDetailInfo(e);
                     jobStatus="0";
                     dinstBean.setFailTimes(dinstBean.getFailTimes()+1);
                     resPayload.setErrPayload(result);
                 }finally {
                     logger.info("日志响应如下[{}]",result);
                     long endTime = System.currentTimeMillis();
                     Date endDate = Calendar.getInstance().getTime();
                     long costTime = endTime - startTime;
                     int runTime = dinstBean.getFailTimes()+dinstBean.getSucessTimes();
                     dinstBean.setAvgRunTime((dinstBean.getAvgRunTime()*(runTime-1)+costTime)/runTime);
                     dInsService.updateStatis(dinstBean);
                     dessLog.setRunTime(endTime - startTime);
                     dessLog.setLogStatus(jobStatus);
                     dLogService.insertLog(dessLog);
                     dLogService.insertPayload(reqPayload);
                     dLogService.insertPayload(resPayload);
                     logger.info(">>>>>>>>>>>>> Running Job has been completed[{}][{}]",dinstBean.getJobNo(),Calendar.getInstance().getTime() );
                     logger.info(dinstBean.toString());
                 }
             }
     }


     public String invokeWSDL(DessBusiness dessBusiness) throws IOException {
         invokeWebService invokeWebService =new invokeWebService();
         invokeWebService.setContentType(invokeWebService.HTTP_CONTENT_TYPE_XML);
         invokeWebService.setHttpUrl(dessBusiness.getInvokeUrl());
         invokeWebService.setRequestConent(dessBusiness.getBodyPayload());
         String result = invokeWebService.doPost();
         return result;

     }

    public String invokeREST(DessBusiness dessBusiness) throws Exception {
        invokeWebService invokeWebService =new invokeWebService();
        invokeWebService.setContentType(invokeWebService.HTTP_CONTENT_TYPE_JSON);
        invokeWebService.setHttpUrl(dessBusiness.getInvokeUrl());
        if(dessBusiness.getHeaderPayload()!= null){
            JSONObject headerJson =JSONObject.parseObject(dessBusiness.getHeaderPayload());
            Set<String> keys =  headerJson.keySet();
            Map<String,String> header =new HashMap<>();
            for(String key:keys){
                header.put(key,headerJson.getString(key));
            }
            invokeWebService.setHeaderPayload(header);
        }
        String result = "";
        if(dessBusiness.getInvokeOperation().equals("GET")){
            result=invokeWebService.doGet();
        }else{
            invokeWebService.setRequestConent(dessBusiness.getBodyPayload());
            result=invokeWebService.doPost();
        }
        return result;
    }

    public boolean checkAlive(DinstBean dinstBean){
         Date endAlive = dinstBean.getAliveEnd();
         if(endAlive!=null&&endAlive.before( Calendar.getInstance().getTime())){
             logger.warn("[{}]存活到期[{}]，暂停该调度",dinstBean.getAliveEnd(),dinstBean.getJobNo());
             dinstBean.setJobStatus("0");
             JobKey jobKey = JobKey.jobKey(dinstBean.getJobNo(), dinstBean.getGroupName());
             Scheduler scheduler = schedulerFactoryBean.getScheduler();
             try {
                 scheduler.pauseJob(jobKey);
                 scheduler.deleteJob(jobKey);
                 dInsService.updateStatis(dinstBean);
             } catch (SchedulerException e) {
                 e.printStackTrace();
             }
                return false;
         }
         else{
             return true;
         }
    }

    public DinstBean getDinstBean(JobDataMap map){
        DinstBean dinstBean = new DinstBean();
        dinstBean.setJobNo( map.getString("JobNo"));
        dinstBean.setJobName( map.getString("JobName"));
        dinstBean.setBusinessId( map.getString("businessId"));
        dinstBean.setJobDescription( map.getString("JobDescription"));
        dinstBean.setGroupName(map.getString("GroupName"));
        dinstBean.setJobFrequency( map.getString("cronExpression"));
        dinstBean.setJobStatus(map.getString("status"));
        if(map.get("aliveStart")!=null){
            Date aliveStart = new Date(map.getLong("aliveStart"));
            dinstBean.setAliveStart(aliveStart);
        }
        if(map.get("aliveEnd")!=null){
            Date aliveEnd =new Date(map.getLong("aliveEnd"));
            dinstBean.setAliveEnd(aliveEnd);
        }
        if(map.get("sucessTimes")!=null){
            dinstBean.setSucessTimes(map.getInt("sucessTimes"));
        }else{
            dinstBean.setSucessTimes(0);
        }
        if(map.get("failTimes")!=null){
            dinstBean.setFailTimes(map.getInt("failTimes"));
        }else{
            dinstBean.setFailTimes(0);
        }
        if(map.get("avgRunTime")!=null){
            dinstBean.setAvgRunTime(map.getDouble("avgRunTime"));
        }else{
            dinstBean.setAvgRunTime(0.0);
        }
         return dinstBean;
    }

    public DessLog getDinstLog(JobDataMap map,String logId){
        DessLog dessLog =new DessLog();
        dessLog.setLogId(logId);
        dessLog.setGroupName(map.getString("GroupName"));
        dessLog.setJobName( map.getString("JobName"));
        dessLog.setJobNo( map.getString("JobNo"));
        dessLog.setRetryTimes(0);
        return dessLog;
    }

    public final static void main(String[] args){
        invokeWebService invokeWebService =new invokeWebService();
        invokeWebService.setContentType(invokeWebService.HTTP_CONTENT_TYPE_JSON);
        invokeWebService.setHttpUrl("");
        String result = "";



    }



 }