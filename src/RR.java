import java.util.ArrayList;
import java.util.List;

public class RR implements Algorithm{
    final private int quantum = 10;//We assume the CPU Bursts are also in milliseconds
    private double avrTurnaroundTime;
    private double avrWaitingTime;
    private static int taskIndex = 0;

    List<Task> queue;

    public RR(List<Task> queue){
        /*
        Round Robin is based of the FCFS scheduler, meaning it would prioritize the task that came in first
        Since they all came in at the same time, it will be scheduled based off the order the tasks were read from the .txt file
         */
        this.queue = queue;
    }
    @Override
    public void schedule() {
        System.out.println("Round Robin Scheduler");//Scheduler name

        decrementBurst(pickNextTask());
    }


    @Override
    public Task pickNextTask() {
        Task task;

        task = queue.get(taskIndex);
        System.out.println("Will run " + task.toString());

        return task;
    }

    private void decrementBurst(Task task) {
        //Decrement CPU Burst by q = 10ms
        task.setBurst(task.getBurst() - 10);

        if (isFinished(task)){
            queue.remove(task);
        }

        reachedEndQueue();
    }

    private boolean isFinished(Task task) {
        //Checks if task is done running
        return task.getBurst() <= 0;
    }

    private void reachedEndQueue() {//Checks if index has reached end of queue
        taskIndex++;//Increments index once previous task is done running its quantum


        if (taskIndex >= queue.size() && !queue.isEmpty()) {
            taskIndex = 0;//Resets index to beginning of queue to run remaining tasks
        } else if (queue.isEmpty()) {
            calculateAverages();//Average waiting time and average turnaround time gets calculated once the queue is empty
        }

        if (taskIndex < queue.size()){
            decrementBurst(pickNextTask());
        }
    }

    private void calculateAverages() {
        System.out.println("Average times: waiting [" + avrWaitingTime + "], turnaround [" + avrTurnaroundTime + "]");//Average times: waiting [X], turnaround: [Y]
    }
}
