/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.util.Date;

/**
 *
 * @author carlo
 */
public class taskData {
    
    private Integer taskID;
    private String task;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private String status;
    private String planner;
    
    public taskData(Integer taskID, String task, Date startDate, Date endDate,
            Date createdDate, String status, String planner){
        this.taskID = taskID;
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.status = status;
        this.planner = planner;
    }
    
    public Integer getTaskID(){
        return taskID;
    }
    
    public String getTask(){
        return task;
    }
    public Date getStartDate(){
        return startDate;
    }
    public Date getEndDate(){
        return endDate;
    }
    public Date getCreatedDate(){
        return createdDate;
    }
    public String getStatus(){
        return status;
    }
    public String getPlanner(){
        return planner;
    }

   
    
}
