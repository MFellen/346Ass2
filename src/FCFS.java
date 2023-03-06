import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
//import java.time.*;

import static java.lang.System.currentTimeMillis;

public class FCFS implements Algorithm {
    private List<Task> tasks;
    private static int completionTime = 0;
    private int avgWaiting;
    private int avgTurnAround;
//    private static int arrivalTime = 0;
    private static int startCPU = 0;

    public FCFS(List<Task> queue) {
        tasks = queue;
    }

    @Override
    public void schedule() { //Scheduler, will implement the algorithm

        int waiting;
        int turnAround;
        int size = tasks.size();

        while(!tasks.isEmpty()) {

            Task current = pickNextTask(); // assigning next task as current one

            tasks.remove(current);         // removing it from the list, indicating that it is no longer in ready state


            CPU.run(current, current.getBurst()); //  This method will run the current chosen Task
//
            //get turnaround and waiting
            waiting = startCPU;
//                System.out.print("waiting : " + waiting );  // used for testing
            turnAround = current.getBurst() + waiting;
//                System.out.println(", turnaround : " + turnAround ); // used for testing
            completionTime += current.getBurst();

            avgWaiting += waiting;
            avgTurnAround += turnAround;

            System.out.println("Task " + current.getName() + " finished\n");
            startCPU = completionTime; // start of next process is the end of current process
        }
        avgTurnAround = avgTurnAround / size;
        avgWaiting = avgWaiting / size;
        System.out.println("Average times: waiting " + avgWaiting + ", turnaround: " +  avgTurnAround);



    }

    @Override
    public Task pickNextTask() { //Will pick tasks to be executed by CPU, specifically the first one in the list

        Task nextTask = null;

            if (!tasks.isEmpty()) {

                nextTask = tasks.get(0);
            } else {

                return null;

            }


        return nextTask;
    }
}
