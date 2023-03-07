import java.util.ArrayList;
import java.util.List;

public class FairShare implements Algorithm{
    List<Task> tasks = new ArrayList<>();
    private int completionTime;
    private int avgWaiting;
    private int avgTurnAround;
    private static int arrivalTime = 0;
    int quantum = 30;
    int userQuantum;
    List<Task> userTasks = new ArrayList<>();
    int user  = 0;
    public FairShare(List<Task> queue) {
        tasks = queue;
    }
    @Override
    public void schedule() {
        int currentTime = arrivalTime;
        int totalWait = 0;
        int totalTurn= 0;
        int numberTasks = tasks.size();
        do
        {
            Task task = pickNextTask();
            CPU.run(task, userQuantum);
            userTasks.remove(task);
            tasks.remove(task);
            if (task.getBurst() <= userQuantum){
                System.out.println(task.getName() + " Finished");
                currentTime += task.getBurst();
            }
            else {
                tasks.add(task);
                currentTime += userQuantum;
                task.setBurst(task.getBurst()-userQuantum);
            }
//            int wait = currentTime;
//            totalWait += wait;
//            int turn = currentTime;
//            totalTurn += turn;
//            System.out.println("Wait: " + wait + " Turn: " + turn + "\n");
        } while (!tasks.isEmpty());
        completionTime = currentTime;
        System.out.println("Completion Time: " + completionTime);
//        avgWaiting = totalWait / numberTasks;
//        avgTurnAround = totalTurn / numberTasks;
//        System.out.println("avgTurn: " + avgTurnAround+ " avgWait: " + avgWaiting);
    }

    @Override
    public Task pickNextTask() {
        Task next = null;
        while (userTasks.isEmpty() && !tasks.isEmpty()) {
            for (Task task : tasks) {
                if (userTasks.isEmpty()) {
                    user = task.getPriority();
                    userTasks.add(task);
                }
                else if (task.getPriority() == user) {
                    userTasks.add(task);
                }
            }
            if(!userTasks.isEmpty()) {
                userQuantum = quantum / userTasks.size();
            }
        }
        next = userTasks.get(0);
        return next;
    }
}
