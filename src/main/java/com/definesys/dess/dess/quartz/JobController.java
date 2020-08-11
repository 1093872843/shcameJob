package com.definesys.dess.dess.quartz;


import com.definesys.dess.dess.DessInstance.DInsDao;
import com.definesys.dess.dess.DessInstance.bean.DinstVO;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import java.util.*;

/**

 */
@RestController
@RequestMapping("/dess")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private DynamicJobService jobService;
    @Autowired
    private DInsDao dInsDao;


//    @PostConstruct
//    public void initialize() {
//        try {
//            reStartAllJobs();
//            logger.info("INIT SUCCESS");
//        } catch (Exception e) {
//            logger.info("INIT EXCEPTION : " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    //重启数据库中所有的Job
    @GetMapping("/refresh/all")
    public Map<String,Object> refreshAll() {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            reStartAllJobs();
            result.put("status","1");
            result.put("message","sucess");
        } catch (Exception e) {
            result.put("status","0");
            result.put("message",e.getMessage());
        }
        return result;
    }



//    //停止指定的Job
//    @GetMapping("/stop")
//    public Map<String,Object> stop(@RequestParam String servNo) throws SchedulerException {
//        Map<String,Object> result = new HashMap<String,Object>();
//        DinstVO entity = dInsDao.getInstanceByNo(servNo);
//        if (entity == null) {
//            result.put("status","0");
//            result.put("message","error: id is not exist");
//        }
//        synchronized (logger) {
//            JobKey jobKey = jobService.getJobKey(entity);
//            Scheduler scheduler = schedulerFactoryBean.getScheduler();
//            scheduler.pauseJob(jobKey);
//            //终止单一触发器
//          //  scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
//            scheduler.deleteJob(jobKey);
//            JobDataMap map = jobService.getJobDataMap1(entity);
//            JobDetail jobDetail = jobService.geJobDetail(jobKey, entity.getJobDescription(), map);
//            if (entity.getJobStatus().equals("OPEN")) {
//                scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
//                result.put("status","1");
//                result.put("message","Refresh Job : " + entity.getJobNo() + "\t InvokeUrl: " + entity.getInvokeUrl() + " success !");
//            } else {
//                result.put("status","0");
//                result.put("message","Refresh Job : " + entity.getJobNo() + "\t InvokeUrl: " + entity.getInvokeUrl() + " failed ! , " +
//                        "Because the Job status is " + entity.getJobStatus());
//            }
//        }
//        return result;
//    }



    //add定时调度
    @PostMapping("/add")
    public Map<String,Object> addJob(@RequestBody DinstVO dinstVO) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        JobKey jobKey=new JobKey(dinstVO.getJobNo(),dinstVO.getGroupName());
        synchronized (logger) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDataMap map = jobService.getJobDataMap(dinstVO);
            JobDetail jobDetail = jobService.geJobDetail(jobKey, dinstVO.getJobDescription(), map);
            if (dinstVO.getJobStatus().equals("1")) {
                Set<Trigger> triggerList = jobService.getTriggerList(dinstVO);
                scheduler.scheduleJob(jobDetail, triggerList,true);
                result.put("status","1");
                result.put("message", "add Job : " + dinstVO.getJobNo() + "\t InvokeUrl: " + dinstVO.getInvokeUrl() + " success !");
            } else {
                result.put("status","0");
                result.put("message", "add Job : " + dinstVO.getJobNo() + "\t InvokeUrl: " + dinstVO.getInvokeUrl()  + " failed ! , " +
                        "Because the Job status is " + dinstVO.getJobStatus());
            }
        }
        return result;
    }

    @PostMapping("/pauseJob")
    public Map<String,Object> pauseJob(@RequestBody DinstVO dinstVO) throws SchedulerException {
        Map<String,Object> result = new HashMap<String,Object>();
        if (dinstVO == null) {
            result.put("status","0");
            result.put("message","error: id is not exist");
        }
        synchronized (logger) {
            try{
                JobKey jobKey = jobService.getJobKey(dinstVO);
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                scheduler.pauseJob(jobKey);
                //终止单一触发器
                //  scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                //终止该任务关联的所有触发器
                scheduler.deleteJob(jobKey);
                result.put("status","1");
                result.put("message", "pause Job : " + dinstVO.getJobNo() + "\t InvokeUrl: " + dinstVO.getInvokeUrl() + " success !");
            }catch (Exception e){
                result.put("status","0");
                result.put("message", "pause Job : " + dinstVO.getJobNo() + "\t InvokeUrl: " + dinstVO.getInvokeUrl()  + " failed ! "+e.getMessage());
            }
        }
        return result;
    }

    //根据ID重启某个Job，该操作会导致放弃追赶丢失的调度，直接从现在开始。
    @PostMapping("/refresh")
    public Map<String,Object> refresh(@RequestBody DinstVO dinstVO) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        if (dinstVO == null) {
            result.put("status","0");
            result.put("message","error: id is not exist");
        }
        synchronized (logger) {
            JobKey jobKey = jobService.getJobKey(dinstVO);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseJob(jobKey);
           // scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
            JobDataMap map = jobService.getJobDataMap(dinstVO);
            JobDetail jobDetail = jobService.geJobDetail(jobKey, dinstVO.getJobDescription(), map);
            if (dinstVO.getJobStatus().equals("1")) {
                Set<Trigger> triggerList = jobService.getTriggerList(dinstVO);
                scheduler.scheduleJob(jobDetail,triggerList,true);
                result.put("status","1");
                result.put("message", "Refresh Job : " + dinstVO.getJobNo() + "\t InvokeUrl: " + dinstVO.getInvokeUrl() + " success !");
            } else {
                result.put("status","0");
                result.put("message", "Refresh Job : " + dinstVO.getJobNo() + "\t InvokeUrl: " + dinstVO.getInvokeUrl()  + " failed ! ");
            }
        }
        return result;
    }

    private void reStartAllJobs() throws Exception {
        synchronized (logger) {                                                         //只允许一个线程进入操作
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
//          以下操作会重建所有调度，将导致丢失调度的追赶执行失效
            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
            scheduler.pauseJobs(GroupMatcher.anyGroup());                               //暂停所有JOB
            for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                scheduler.deleteJob(jobKey);
            }
            for (DinstVO job : dInsDao.findAll()) {                               //从数据库中注册的所有JOB
                logger.info("Job register name : {} , group : {} , cron : {}", job.getJobNo(), job.getGroupName(), job.getJobFrequency());
                JobDataMap map = jobService.getJobDataMap(job);
                JobKey jobKey = jobService.getJobKey(job);
                JobDetail jobDetail = jobService.geJobDetail(jobKey, job.getJobDescription(), map);
                if (job.getJobStatus().equals("1")) {
                    Set<Trigger> triggerList = jobService.getTriggerList(job);
                    scheduler.scheduleJob(jobDetail, triggerList, true);
                } else {
                    logger.info("Job jump name : {} , Because {} status is {}", job.getJobNo(), job.getJobStatus());
                }
            }
        }
    }
}