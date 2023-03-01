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

        System.out.println("Average times: waiting [" + avrWaitingTime + "], turnaround [" + avrTurnaroundTime + "]");//Average times: waiting [X], turnaround: [Y]
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
        System.out.println("Decremented CPU Burst of " + task);

        if (isFinished(task)){
            queue.remove(task);
            System.out.println("Task " + task.getName() + " finished");
        }

        System.out.println("Moves on to next task");
        reachedEndQueue();
    }

    private boolean isFinished(Task task) {
        //Checks if task is done running
        return task.getBurst() <= 0;
    }

    private void reachedEndQueue() {
        if (taskIndex < queue.size()){
            taskIndex++;//Increments index once previous task is done running its quantum
        } else if (taskIndex >= queue.size() && !queue.isEmpty()) {
            taskIndex = 0;//Resets index to beginning of queue to run remaining tasks
        }
        decrementBurst(pickNextTask());
    }
}
