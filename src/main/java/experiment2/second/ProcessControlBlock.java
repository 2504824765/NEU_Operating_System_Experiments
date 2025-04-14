package experiment2.second;

public class ProcessControlBlock {
    private String name;
    private int requestTime;
    private int workedTime;
    private Status status;
    public ProcessControlBlock next;

    ProcessControlBlock(String name, int requestTime) {
        this.name = name;
        this.requestTime = requestTime;
        this.workedTime = 0;
        this.status = Status.Ready;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public int getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(int workedTime) {
        this.workedTime = workedTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ProcessControlBlock getNext() {
        return next;
    }

    public void setNext(ProcessControlBlock next) {
        this.next = next;
    }
}
