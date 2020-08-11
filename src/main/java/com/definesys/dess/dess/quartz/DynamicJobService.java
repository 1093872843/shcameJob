package com.definesys.dess.dess.quartz;

import com.alibaba.fastjson.JSONObject;
import com.definesys.dess.dess.DessInstance.bean.DinstVO;
import com.definesys.dess.dess.util.CornUtils;
import com.definesys.dess.dess.util.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;


@Service
public class DynamicJobService {

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(DynamicJob.class)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }




    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(DinstVO job) {
        JobDataMap map = new JobDataMap();
        map.put("JobNo", job.getJobNo());
        map.put("JobName", job.getJobName());
        map.put("businessId", job.getBusinessId());
        map.put("GroupName", job.getGroupName());
        map.put("cronExpression", job.getJobFrequency());
        map.put("JobDescription", job.getJobDescription());
        map.put("status", job.getJobStatus());
        map.put("sucessTimes", job.getSucessTimes());
        map.put("failTimes", job.getFailTimes());
        map.put("avgRunTime", job.getAvgRunTime());
        if(job.getAliveStart()!=null){
            map.put("aliveStart", job.getAliveStart().getTime());
        }
        if(job.getAliveEnd()!=null){
            map.put("aliveEnd", job.getAliveEnd().getTime());
        }


        return map;
    }
    //withMisfireHandlingInstructionIgnoreMisfires 立即执行超时任务
    //.withMisfireHandlingInstructionDoNothing()放弃超时任务。


    //对数据库获取的调度时间list进行触发器的创建
    public Set<Trigger> getTriggerList(DinstVO job) throws Exception {
        Set<Trigger> triggerList = new HashSet<>();
        String jobFrequency = job.getJobFrequency();
        String[] frequencyList ={};
        if(jobFrequency != null){
           frequencyList = job.getJobFrequency().split(";");
        }
        for(String frequency:frequencyList){
            //传入参数为yyyy-MM-dd hh:mm:ss
            if(StringUtils.isTime(frequency)){
                //获取yyyy-MM-dd hh:mm:ss转换的corn
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date dateFormatParse = sdf.parse(frequency);
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateFormatParse);
                String[] cornStr = CornUtils.parseDate(cal);
                //指定存活时间
                Date aliveStart = sdf.parse(cornStr[1]+"-01-01 00:00:00");
                Date aliveEnd = sdf.parse(cornStr[1]+"-12-31 23:59:59");
                triggerList.add(getTriggerAlive(job,cornStr[0],aliveStart,aliveEnd));
            }else if(StringUtils.isCorn(frequency)){
                triggerList.add(getTrigger(job,frequency));
            }else{
                throw new Exception("frequency调度字段不符合规范:"+frequency);
            }
        }
        return triggerList;
    }


    //获取JobKey,包含Name和Group
    public JobKey getJobKey(DinstVO job) {
        return JobKey.jobKey(job.getJobNo(), job.getGroupName());
    }



    public Trigger getTriggerAlive(DinstVO job,String corn,Date startDate,Date endDate) {
        TriggerBuilder<Trigger> TriggerBuilder = org.quartz.TriggerBuilder.newTrigger();
        return TriggerBuilder
                .startAt(startDate)
                .endAt(endDate)
                .withIdentity(job.getJobNo()+UUID.randomUUID().toString(), job.getGroupName())
                .withSchedule(CronScheduleBuilder.cronSchedule(corn).withMisfireHandlingInstructionDoNothing())
                .build();
    }

    public Trigger getTrigger(DinstVO job,String corn) {
        TriggerBuilder<Trigger> TriggerBuilder = org.quartz.TriggerBuilder.newTrigger();
        return TriggerBuilder
                .withIdentity(job.getJobNo()+UUID.randomUUID().toString(), job.getGroupName())
                .withSchedule(CronScheduleBuilder.cronSchedule(corn).withMisfireHandlingInstructionIgnoreMisfires())
                .build();
    }



}