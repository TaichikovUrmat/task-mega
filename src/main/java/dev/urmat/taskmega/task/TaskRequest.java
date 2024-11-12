package dev.urmat.taskmega.task;

public record TaskRequest(
        String title,
        String description,
        boolean completed
) {

    public Task setValuesTo(Task task) {
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        return task;
    }
}