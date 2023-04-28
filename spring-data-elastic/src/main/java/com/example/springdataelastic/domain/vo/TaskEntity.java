package com.example.springdataelastic.domain.vo;
import java.io.Serializable;
import java.util.Map;

/**
 * @author xiaoxu123
 */
public class TaskEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 任务编号
     */
    private Long id;
    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务所属组
     */
    private String groupName;
    /**
     * 任务执行方法
     */
    private String jobClass;
    /**
     *  任务状态
     */
    private String jobStatus;
    /**
     * 任务调度时间表达式
     */
    private String cronExpression;
    /**
     * 任务描述
     */
    private String jobDescription;
    /**
     *时区
     */
    private String timeZoneId;
    /**
     * 任务执行时间
     */
    private Long startTime;
    /**
     * 任务结束时间
     */
    private Long endTime;
    /**
     *状态
     */
    private String state;

    /**
     * 附加参数
     */
    private Map<String,Object> param;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public
    void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public
    String getJobGroup() {
        return jobGroup;
    }

    public
    void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}
