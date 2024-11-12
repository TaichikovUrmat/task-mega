package dev.urmat.taskmega.task;

import dev.urmat.taskmega.SimpleResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        // Arrange
        TaskRequest taskRequest = new TaskRequest("Title", "Description", true);
        Task expectedTask = new Task();
        when(taskService.create(any(TaskRequest.class))).thenReturn(expectedTask);

        // Act
        Task result = taskController.create(taskRequest);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTask, result);
        verify(taskService).create(taskRequest);
    }

    @Test
    void getAllTasks_ShouldReturnPageOfTasks() {
        // Arrange
        int page = 0;
        int size = 10;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        Page<Task> expectedPage = new PageImpl<>(tasks);
        when(taskService.getAll(any(PageRequest.class))).thenReturn(expectedPage);

        // Act
        Page<Task> result = taskController.getAll(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(taskService).getAll(PageRequest.of(page, size));
    }

    @Test
    void updateTaskById_ShouldReturnUpdatedTask() {
        // Arrange
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest("Title", "Description", true);
        Task expectedTask = new Task();
        when(taskService.updateById(anyLong(), any(TaskRequest.class))).thenReturn(expectedTask);

        // Act
        Task result = taskController.updateById(taskId, taskRequest);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTask, result);
        verify(taskService).updateById(taskId, taskRequest);
    }

    @Test
    void deleteTaskById_ShouldReturnSuccessMessage() {
        // Arrange
        Long taskId = 1L;
        doNothing().when(taskService).deleteById(anyLong());

        // Act
        SimpleResponseMessage result = taskController.deleteById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals("Task successfully deleted", result.message());
        verify(taskService).deleteById(taskId);
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        // Arrange
        Long taskId = 1L;
        Task expectedTask = new Task();
        when(taskService.getById(anyLong())).thenReturn(expectedTask);

        // Act
        Task result = taskController.getTaskById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTask, result);
        verify(taskService).getById(taskId);
    }
}