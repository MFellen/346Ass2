import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Priority implements Algorithm{

    List<Task> tasks = new ArrayList<>();
    private int completionTime;
    private double avgWaiting;
    private double avgTurnAround;
    private static int arrivalTime = 0;


    public Priority(List<Task> queue) {
        tasks = queue;
    }
    @Override
    public void schedule() {
        int currentTime = arrivalTime;
        int totalWait = 0;
        int totalTurn= 0;
        int numberTasks = tasks.size();
        while (!tasks.isEmpty()){
            Task task = pickNextTask();
            tasks.remove(task);
            CPU.run(task, task.getBurst());
            int wait = currentTime;
            totalWait += wait;
            currentTime += task.getBurst();
            int turn = currentTime;
            totalTurn += turn;
            System.out.println("Wait: " + wait + " Turn: " + turn + "\n");
        }
        completionTime = currentTime;
        System.out.println("Completion Time: " + completionTime);
        avgWaiting = totalWait / numberTasks;
        avgTurnAround = totalTurn / numberTasks;
        System.out.println("avgTurn: " + avgTurnAround+ " avgWait: " + avgWaiting);
    }

    @Override
    public Task pickNextTask() {
        Task next = null;
        for (Task task : tasks){
            if (next != null) {
                if (task.getPriority() > next.getPriority()) {
                    next = task;
                }
            }
            else {
                next = task;
            }
        }
        return next;
    }
}
