package com.kiet.student.model;

import java.util.Objects;

/**
 * Model class representing a Student entity.
 * Contains student academic information: studentId, name, email, branch, year, cgpa.
 */
public class Student {
    private Integer studentId;
    private String name;
    private String email;
    private String branch;
    private Integer year;
    private Double cgpa;

    // Default no-argument constructor (required for JSON deserialization)
    public Student() {
    }

    // Parameterized constructor with all fields
    public Student(Integer studentId, String name, String email, String branch, Integer year, Double cgpa) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.cgpa = cgpa;
    }

    // Getters and Setters
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", branch='" + branch + '\'' +
                ", year=" + year +
                ", cgpa=" + cgpa +
                '}';
    }
}
