import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
//import java.time.*;

import static java.lang.System.currentTimeMillis;

public class FCFS implements Algorithm {
    private List<Task> tasks;
    private int completionTime;
    private int avgWaiting;
    private int avgTurnAround;
    private static int arrivalTime = 0;
    private static int startCPU = 0;
    private static int waiting = 0;


    public FCFS(List<Task> queue) {
        tasks = queue;
    }

    @Override
    public void schedule() { //Scheduler, will implement the algorithm

        int turnAround;
        int size = tasks.size();

        while(!tasks.isEmpty()) {
            Task current = pickNextTask();

            tasks.remove(current);
            CPU.run(current, current.getBurst()); //  CPU.run("") after deleting the other line
            current.toString();

            //get turnaround and waiting
            waiting = startCPU-arrivalTime;
                System.out.print("waiting : " + waiting );
            turnAround = current.getBurst() + waiting;
                System.out.println(", turnaround : " + turnAround );
            completionTime = arrivalTime + current.getBurst();

            avgWaiting += waiting;
            avgTurnAround += turnAround;

            System.out.println("Task " + current.getName() + " finished\n");
            startCPU = completionTime; // start of next process is the
        }
        avgTurnAround = avgTurnAround / size;
        avgWaiting = avgWaiting / size;
        System.out.println("Average times: waiting " + avgWaiting + ", turnaround: " +  avgTurnAround);



    }

    @Override
    public Task pickNextTask() { //Will pick tasks to be executed by CPU

        Task nextTask = null;

        for(Task tk : tasks) {
            if (!tasks.isEmpty()) {
                nextTask = tk;
            } else {
                return null;
            }
        }

        return nextTask;
    }
}
