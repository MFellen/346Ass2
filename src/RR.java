import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RR implements Algorithm {
    private final int quantum = 10;
    private final int nbOfTasks;
    private final List<Task> queue;

    private int taskIndex = 0;
    private int currentQuantum = 0;
    private final Map<Integer, Integer> turnaroundTime = new HashMap<>();
    private final Map<Integer, Integer> waitingTime = new HashMap<>();

    public RR(List<Task> queue) {
        /*
        Round Robin is based of the FCFS scheduler, meaning it would prioritize the task that came in first
        Since they all came in at the same time, it will be scheduled based off the order the tasks were read from the .txt file
         */
        this.queue = queue;
        nbOfTasks = queue.size();
    }

    @Override
    public void schedule() {
        System.out.println("Round Robin Scheduler");//Scheduler name

        while (queue.size() > 0) decrementBurst(pickNextTask());

        calculateAverages();//Average waiting time and average turnaround time gets calculated once the queue is empty
    }

    @Override
    public Task pickNextTask() {
        Task task = queue.get(taskIndex);
        CPU.run(task, quantum);

        return task;
    }

    private void decrementBurst(Task task) {
        //Does the statistics before the burst time is changed
        addWaitingTime(task);
        var runTime = Math.min(task.getBurst(), quantum);
        addTurnAroundTime(task, runTime);

        //Decrement CPU Burst by q = 10ms
        //We assume the CPU Bursts are also in milliseconds
        task.setBurst(task.getBurst() - runTime);

        if (isFinished(task)) {
            queue.remove(task);
        }

        reachedEndQueue();
        currentQuantum++;
    }

    private void addTurnAroundTime(Task task, int runTime) {
        if (turnaroundTime.containsKey(task.getTid())) {
            //Checks if this isn't the first time it ran
            turnaroundTime.put(task.getTid(), turnaroundTime.get(task.getTid()) + runTime);
        } else {
            turnaroundTime.put(task.getTid(), runTime);
        }
    }

    private void addWaitingTime(Task task) {
        if (!waitingTime.containsKey(task.getTid())) {
            //Since waiting time only calculated once, we check if it exists then add how much time pass
            //If it exists, we skip this
            waitingTime.put(task.getTid(), quantum * currentQuantum);
        }
    }

    private boolean isFinished(Task task) {
        //Checks if task is done running
        return task.getBurst() <= 0;
    }

    private void reachedEndQueue() {//Checks if index has reached end of queue
        taskIndex++;//Increments index once previous task is done running its quantum

        if (!queue.isEmpty() && taskIndex >= queue.size()) {
            taskIndex = 0;//Resets index to beginning of queue to run remaining tasks
        }
    }

    private void calculateAverages() {
        var avrTurnaroundTime = turnaroundTime.values().stream().mapToInt(Integer::intValue).sum();
        var avrWaitingTime = waitingTime.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Average times: waiting [" + (double)avrWaitingTime / nbOfTasks + "], turnaround [" + (double)avrTurnaroundTime / nbOfTasks + "]");//Average times: waiting [X], turnaround: [Y]
    }
}