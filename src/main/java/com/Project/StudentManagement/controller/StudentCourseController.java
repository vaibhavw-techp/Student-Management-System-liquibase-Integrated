package com.Project.StudentManagement.controller;


import com.Project.StudentManagement.RequestForPost.CourseRequest;
import com.Project.StudentManagement.RequestForPost.StudentRequest;
import com.Project.StudentManagement.entity.Course;
import com.Project.StudentManagement.entity.Student;
import com.Project.StudentManagement.repository.CourseRepository;
import com.Project.StudentManagement.repository.StudentRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/studentcourse")
public class StudentCourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/createStudent")
    public ResponseEntity<String> createNewStudent(@RequestBody @NotNull StudentRequest studentRequest) {
        String studentName = studentRequest.getName();
        String studentYear = studentRequest.getYear();
        String studentDept = studentRequest.getDept();
        List<CourseRequest> courseRequests = studentRequest.getCourses();

        Student student = new Student();
        student.setName(studentName);
        student.setYear(studentYear);
        student.setDept(studentDept);

        // Save the student to the database
        studentRepository.save(student);

        for (CourseRequest courseRequest : courseRequests) {
            Course course = new Course();
            course.setTitle(courseRequest.getTitle());
            course.setCourseCode(courseRequest.getCourseCode());
            course.setCredits(courseRequest.getCredits());
            course.setFee(courseRequest.getFee());

            // Save the course
            course = courseRepository.save(course);

            // Update bidirectional relationship
            if (student.getCourses() == null) {
                student.setCourses(new HashSet<>());
            }
            student.getCourses().add(course);
            course.getStudents().add(student);

            // Save changes once after modifications
            courseRepository.save(course);
            studentRepository.save(student);
        }

        return ResponseEntity.ok("Student created successfully");
    }
}
