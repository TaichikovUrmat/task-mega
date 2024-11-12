package dev.urmat.taskmega.task;

import dev.urmat.taskmega.SimpleResponseMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    // create task
    @PostMapping()
    public Task create(@RequestBody TaskRequest taskRequest) {
        logger.info("Received request to create task: {}", taskRequest.title());
        return taskService.create(taskRequest);
    }

    // get all tasks
    @GetMapping
    public Page<Task> getAll(@RequestParam int page,
                             @RequestParam int size) {
        return taskService.getAll(PageRequest.of(page, size));
    }

    // update task by id
    @PutMapping("/{taskId}")
    public Task updateById(@PathVariable Long taskId,
                           @RequestBody TaskRequest taskRequest) {
        return taskService.updateById(taskId, taskRequest);
    }

    // delete task by id
    @DeleteMapping("/{taskId}")
    public SimpleResponseMessage deleteById(@PathVariable Long taskId) {
        taskService.deleteById(taskId);
        return new SimpleResponseMessage("Task successfully deleted");
    }

    // get task by id
    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        return taskService.getById(taskId);
    }
}