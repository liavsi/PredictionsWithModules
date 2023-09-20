package DTOManager.impl;

public class MyThreadInfo {

    private int queueSize;

    private int workingThreads;

    private int finishedThread;

    public MyThreadInfo(int queueSize, int workingThreads, int finishedThread) {
        this.queueSize = queueSize;
        this.workingThreads = workingThreads;
        this.finishedThread = finishedThread;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public int getWorkingThreads() {
        return workingThreads;
    }

    public int getFinishedThread() {
        return finishedThread;
    }
}
