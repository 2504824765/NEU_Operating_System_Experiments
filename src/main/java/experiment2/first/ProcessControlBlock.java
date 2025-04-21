package experiment2.first;

public class ProcessControlBlock {
    private String processName;
    private int requestTime;
    private int priority;
    private Status status;
    private int cpuTime;
    private int roundTime;
    private int workingTime;
    public ProcessControlBlock next;

    ProcessControlBlock(String processName, int requestTime, int priority) {
        this.processName = processName;
        this.requestTime = requestTime;
        this.priority = priority;
        this.status = Status.READY;
        this.cpuTime = 0;
        this.roundTime = 0;
        this.workingTime = 0;
    }

    public void plusRoundTime() {
        this.roundTime++;
    }

    public void plusWorkingTime() {
        this.workingTime++;
    }

    public void subWorkingTime() {
        this.workingTime--;
    }

    public void runOnce() {
        this.requestTime--;
        this.priority--;
        this.cpuTime++;
        if (requestTime <= 0) {
            this.status = Status.Finish;
        }
    }

    @Override
    public String toString() {
        return processName + "\t" + cpuTime + "\t" + requestTime + "\t" + priority + "\t" + status.toString();
    }

    public void updateStatus() {
        if (requestTime <= 0) {
            status = Status.Finish;
        }
    }

    public int getRoundTime() {
        return roundTime;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getProcessName() {
        return processName;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public int getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }
}
