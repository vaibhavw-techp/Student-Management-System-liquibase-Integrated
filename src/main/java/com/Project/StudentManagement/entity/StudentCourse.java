//package com.Project.StudentManagement.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "student_course_table")
//public class StudentCourse {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "student_id", nullable = false)
//    private Student student;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "course_id", nullable = false)
//    private Course course;
//
//
//    // Other fields specific to StudentCourse entity...
//
//    public StudentCourse() {
//        // Default constructor
//    }
//
//    // Getters and setters for fields...
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public Course getCourse() {
//        return course;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
//    }
//
//    // Other methods...
//}
//
