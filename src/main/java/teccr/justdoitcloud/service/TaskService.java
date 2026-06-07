package teccr.justdoitcloud.service;

import org.springframework.stereotype.Service;
import teccr.justdoitcloud.data.Task;
import teccr.justdoitcloud.data.User;
import teccr.justdoitcloud.repository.TaskRepository;

import java.util.List;

public void advanceTaskStatus (Long taskId) {
    Task task = taskRepository.findbyId(taskId);


    Task.Status newStatus = switch (task.getStatus()){
        case PENDING -> Task.Status.INPROGRESS;
        case INPROGRESS -> Task.Status.DONE;
        default -> task.getStatus();
    };
    task.setStatus(newStatus);
    taskRepository.save(task);
}

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksForUser(User user) {
        return taskRepository.findByUserId(user.getId());
    }

    public void addTaskToUser(User user, Task task) {
        task.setUserId(user.getId());
        taskRepository.save(task);
    }
}
