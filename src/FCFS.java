import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
//import java.time.*;

import static java.lang.System.currentTimeMillis;

public class FCFS implements Algorithm {
    List<Task> tasks = new ArrayList<>();
    private int completionTime;
    private int avgWaiting;
    private int avgTurnAround;
    private static int arrivalTime = 0;
    private static int startCPU = 0;
    private static int waiting = 0;


    public FCFS(List<Task> queue) {
        tasks = queue;

    }
    // while iterating through queue..
    // check queue-> run first available task
    // take time values...
    // pick next task-> the one right after in the queue..
    //

    @Override
    public void schedule() { //Scheduler, will implement the algorithm

        CPU cpu = new CPU(); // this is a mistake delete this line
        int turnAround;

        Task current = pickNextTask(); // this needs to be in the while loop because then it wont change current while in the loop
        int burst = current.getBurst();

        while(!tasks.isEmpty()) {
            //Will output the info about current running task
            tasks.remove(current);
            cpu.run(current, burst); //  CPU.run("") after deleting the other line
            current.toString();

            //get turnaround and waiting
            waiting = startCPU-arrivalTime;
            turnAround = burst + waiting;

            avgWaiting += waiting; //this is not an average its a total
            avgTurnAround += turnAround; //^
        }


    }

    @Override
    public Task pickNextTask() { //Will pick tasks to be executed by CPU

        Task nextTask = null;
        if(tasks.isEmpty())
        {
            return null;
        }
        for(Task tk : tasks)
        {
            nextTask = tk;
        }

        return nextTask;
    }
}
