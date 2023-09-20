package component.queue.manager;
import DTOManager.impl.MyThreadInfo;
import DTOManager.impl.SimulationOutcomeDTO;
import engine.api.Engine;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class QueueManagementView {
    @FXML
    private Label queueSizeLabel;

    @FXML
    private Label workingThreadsLabel;

    @FXML
    private Label finishedThreadsLabel;

    Task<MyThreadInfo> myThreadInfoTask;


    public void setThreadInfo(Engine engine) {
        myThreadInfoTask = new Task<MyThreadInfo>() {

            @Override
            protected MyThreadInfo call()  {
                while (true) {
                    MyThreadInfo threadInfo = engine.getThreadPoolInfo();
                    Platform.runLater(()-> {
                        queueSizeLabel.setText("Queue Size: " + threadInfo.getQueueSize());
                        workingThreadsLabel.setText("Working Threads: " + threadInfo.getWorkingThreads());
                        finishedThreadsLabel.setText("Finished Threads: " + threadInfo.getFinishedThread());
                            });
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // Run your simulation here
                }
            }
        };
        Thread th = new Thread(myThreadInfoTask);
        th.setDaemon(true);
        th.start();


    }
}
