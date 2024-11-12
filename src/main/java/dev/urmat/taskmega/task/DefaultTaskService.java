package dev.urmat.taskmega.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private final TaskCrudRepo taskCrudRepo;

    @Override
    @Transactional
    public Task create(TaskRequest taskRequest) {
        Task newTask = taskRequest.setValuesTo(new Task());
        return taskCrudRepo.save(newTask);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> getAll(PageRequest pageRequest) {
        return taskCrudRepo.findAll(pageRequest);
    }

    @Override
    @Transactional
    public Task updateById(Long taskId, TaskRequest taskRequest) {
        Task task = taskCrudRepo.findByIdOrElseThrow(taskId);

        Task updatedTask = taskRequest.setValuesTo(task);

        return taskCrudRepo.save(updatedTask);
    }

    @Override
    @Transactional
    public void deleteById(Long taskId) {
        Task task = taskCrudRepo.findByIdOrElseThrow(taskId);
        taskCrudRepo.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long taskId) {
        return taskCrudRepo.findByIdOrElseThrow(taskId);
    }
}