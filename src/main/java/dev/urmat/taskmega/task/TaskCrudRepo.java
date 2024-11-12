package dev.urmat.taskmega.task;

import dev.urmat.taskmega.NotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskCrudRepo extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

    default Task findByIdOrElseThrow(Long taskId) {
        return findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
    }
}