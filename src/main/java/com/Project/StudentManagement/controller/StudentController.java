package com.Project.StudentManagement.controller;

import com.Project.StudentManagement.dto.StudentDTO;
import com.Project.StudentManagement.exceptions.ResourceNotFoundException;
import com.Project.StudentManagement.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable(value = "id") Integer studentId) throws ResourceNotFoundException {
        StudentDTO student = studentService.getStudentById(studentId);
        return ResponseEntity.ok().body(student);
    }

    @GetMapping("/name/{name}")
    public List<StudentDTO> findByName(@PathVariable String name) {
        return studentService.getStudentsByName(name);
    }

    @PostMapping("/student")
    public StudentDTO createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PostMapping
    public List<StudentDTO> createMultipleStudents(@RequestBody List<StudentDTO> studentDTOs) {
        return studentService.createMultipleStudents(studentDTOs);
    }

    @PutMapping("/{sid}/course/{cid}")
    public ResponseEntity<StudentDTO> assignStudentToCourse(@PathVariable(value = "sid") Integer studentId,
                                                            @PathVariable(value = "cid") Integer courseId) throws ResourceNotFoundException {
        return studentService.assignStudentToCourse(studentId, courseId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable(value = "id") Integer studentId,
                                                    @Validated @RequestBody StudentDTO studentDTO) throws ResourceNotFoundException {
        return studentService.updateStudent(studentId, studentDTO);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Integer studentId) throws ResourceNotFoundException {
        return studentService.deleteStudent(studentId);
    }

    @DeleteMapping
    public Map<String, Boolean> deleteAllStudents() {
        return studentService.deleteAllStudents();
    }
}
