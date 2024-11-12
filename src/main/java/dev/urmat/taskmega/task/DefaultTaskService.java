package dev.urmat.taskmega.task;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTaskService.class);
    private final TaskCrudRepo taskCrudRepo;

    @Override
    @Transactional
    public Task create(TaskRequest taskRequest) {
        logger.info("Creating new task with request: {}", taskRequest);
        Task newTask = taskRequest.setValuesTo(new Task());
        Task savedTask = taskCrudRepo.save(newTask);
        logger.info("Task created successfully with ID: {}", savedTask.getId());
        sendAnEmail("taskMega@gmail.com", newTask.getTitle(), newTask.getDescription());
        return savedTask;
    }

    @Override
    @Cacheable(value = "tasks", key = "'all_tasks_' + #pageRequest.pageNumber + '_' + #pageRequest.pageSize")
    @Transactional(readOnly = true)
    public Page<Task> getAll(PageRequest pageRequest) {
        logger.info("Fetching all tasks with pagination: {}", pageRequest);
        Page<Task> tasks = taskCrudRepo.findAll(pageRequest);
        logger.info("Fetched {} tasks", tasks.getTotalElements());
        return tasks;
    }

    @Override
    @CachePut(value = "tasks", key = "'task_' + #taskId")
    @Transactional
    public Task updateById(Long taskId, TaskRequest taskRequest) {
        logger.info("Updating task with ID: {} using request: {}", taskId, taskRequest);
        Task task = taskCrudRepo.findByIdOrElseThrow(taskId);
        Task updatedTask = taskRequest.setValuesTo(task);
        Task savedTask = taskCrudRepo.save(updatedTask);
        logger.info("Task with ID: {} updated successfully", savedTask.getId());
        return savedTask;
    }

    @Override
    @CacheEvict(value = "tasks", key = "'task_' + #taskId")
    @Transactional
    public void deleteById(Long taskId) {
        logger.info("Deleting task with ID: {}", taskId);
        Task task = taskCrudRepo.findByIdOrElseThrow(taskId);
        taskCrudRepo.delete(task);
        logger.info("Task with ID: {} deleted successfully", taskId);
    }

    @Override
    @Cacheable(value = "tasks", key = "'task_' + #taskId")
    @Transactional(readOnly = true)
    public Task getById(Long taskId) {
        logger.info("Fetching task with ID: {}", taskId);
        Task task = taskCrudRepo.findByIdOrElseThrow(taskId);
        logger.info("Fetched task: {}", task);
        return task;
    }


    private void sendAnEmail(String email, String title, String description) {
        CompletableFuture.runAsync(() -> {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("taskMega@gmail.com", "xomo wzns kbzz pess");
                }
            });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("taskMega@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("title  " + title);
                message.setSubject("Description задача: " + description);
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }


}