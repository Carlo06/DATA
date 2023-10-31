package Controller; 


public class userTask {
    private int taskID;
    private String taskName;

    // Constructor that includes task ID
    public userTask(int taskID, String taskName) {
        this.taskID = taskID;
        this.taskName = taskName;
    }

    // Getter methods for task ID and task name
    public int getTaskID() {
        return taskID;
    }

    public String getTaskName() {
        return taskName;
    }

}