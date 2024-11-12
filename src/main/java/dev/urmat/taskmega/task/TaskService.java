package dev.urmat.taskmega.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TaskService {

    Task create(TaskRequest taskRequest);

    Page<Task> getAll(PageRequest pageRequest);

    Task updateById(Long taskId, TaskRequest taskRequest);

    void deleteById(Long taskId);

    Task getById(Long taskId);

}