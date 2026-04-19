package com.sms.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

/**
 * OOP — ABSTRACTION & ENCAPSULATION
 *
 * BaseEntity is an abstract class that cannot be instantiated directly.
 * It provides a common id and audit timestamps to all entity subclasses.
 * All fields are private (encapsulation), accessed via getters/setters.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = BaseEntity.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Student.class,         name = "student"),
    @JsonSubTypes.Type(value = GraduateStudent.class, name = "graduate"),
    @JsonSubTypes.Type(value = Course.class,          name = "course")
})
public abstract class BaseEntity {

    // ENCAPSULATION: private fields, not directly accessible
    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public BaseEntity(int id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ABSTRACTION: Subclasses must define how they display their core info
    public abstract String getSummary();

    // --- Getters & Setters ---

    public int getId() {
        return id; }
    public void setId(int id) {
        this.id = id; }

    public LocalDateTime getCreatedAt() {
        return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() {
        return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt; }

    public void touch() {
        this.updatedAt = LocalDateTime.now(); }
}
