package dev.urmat.taskmega.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DefaultTaskServiceTest {

    @Mock
    private TaskCrudRepo taskCrudRepo;

    @InjectMocks
    private DefaultTaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreatedTask() {
        // Arrange
        TaskRequest taskRequest = new TaskRequest("Test Title", "Test Description", true);
        Task expectedTask = taskRequest.setValuesTo(new Task());

        when(taskCrudRepo.save(any(Task.class))).thenReturn(expectedTask);

        // Act
        Task result = taskService.create(taskRequest);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTask.getTitle(), result.getTitle());
        assertEquals(expectedTask.getDescription(), result.getDescription());
        assertEquals(expectedTask.isCompleted(), result.isCompleted());
        verify(taskCrudRepo).save(any(Task.class));
    }

    @Test
    void getAll_ShouldReturnPageOfTasks() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Task> tasks = Arrays.asList(
                new Task(1L, Instant.now().getEpochSecond(), "Task 1", "Description 1", false),
                new Task(2L, Instant.now().getEpochSecond(), "Task 2", "Description 2", true)
        );
        Page<Task> expectedPage = new PageImpl<>(tasks);
        when(taskCrudRepo.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        // Act
        Page<Task> result = taskService.getAll(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(tasks.get(0).getTitle(), result.getContent().get(0).getTitle());
        assertEquals(tasks.get(1).getTitle(), result.getContent().get(1).getTitle());
        verify(taskCrudRepo).findAll(pageRequest);
    }

    @Test
    void updateById_ShouldReturnUpdatedTask() {
        // Arrange
        Long taskId = 1L;
        long now = Instant.now().getEpochSecond();

        TaskRequest taskRequest = new TaskRequest("Updated Title", "Updated Description", true);

        Task existingTask = new Task(taskId, now, "Old Title", "Old Description", false);
        Task updatedTask = taskRequest.setValuesTo(new Task());

        when(taskCrudRepo.findByIdOrElseThrow(anyLong())).thenReturn(existingTask);
        when(taskCrudRepo.save(any(Task.class))).thenReturn(updatedTask);

        // Act
        Task result = taskService.updateById(taskId, taskRequest);

        // Assert
        assertNotNull(result);
        assertEquals(updatedTask.getTitle(), result.getTitle());
        assertEquals(updatedTask.getDescription(), result.getDescription());
        assertEquals(updatedTask.isCompleted(), result.isCompleted());
        verify(taskCrudRepo).findByIdOrElseThrow(taskId);
        verify(taskCrudRepo).save(any(Task.class));
    }

    @Test
    void deleteById_ShouldDeleteTask() {
        // Arrange
        Long taskId = 1L;
        long now = Instant.now().getEpochSecond();
        Task task = new Task(taskId, now, "Test Task", "Test Description", false);

        when(taskCrudRepo.findByIdOrElseThrow(anyLong())).thenReturn(task);
        doNothing().when(taskCrudRepo).delete(any(Task.class));

        // Act
        taskService.deleteById(taskId);

        // Assert
        verify(taskCrudRepo).findByIdOrElseThrow(taskId);
        verify(taskCrudRepo).delete(task);
    }

    @Test
    void getById_ShouldReturnTask() {
        // Arrange
        Long taskId = 1L;
        long now = Instant.now().getEpochSecond();

        Task expectedTask = new Task(taskId, now, "Test Task", "Test Description", false);
        when(taskCrudRepo.findByIdOrElseThrow(anyLong())).thenReturn(expectedTask);

        // Act
        Task result = taskService.getById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTask.getTitle(), result.getTitle());
        assertEquals(expectedTask.getDescription(), result.getDescription());
        assertEquals(expectedTask.isCompleted(), result.isCompleted());
        verify(taskCrudRepo).findByIdOrElseThrow(taskId);
    }
}