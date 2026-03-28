package com.bugboard26.model;

public class Issue {

    private int id;
    private String title;
    private String type; // bug, feature, question, documentation
    private String priority; // es. low, medium, high
    private String status;
    private String createdBy;
    private String description;
    private String assignedTo;
    private String createdAt;
    private String commento;

    public Issue(int id, String title, String type, String status, String description, String assignedTo) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.description = description;
        this.assignedTo = assignedTo;
    }

    public Issue() {}

    // --- Getter & Setter ---
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    // --- Opzionale: metodo per debug ---
    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy+ '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
