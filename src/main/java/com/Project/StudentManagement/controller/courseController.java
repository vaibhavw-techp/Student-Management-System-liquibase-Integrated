package com.Project.StudentManagement.controller;

import com.Project.StudentManagement.dto.CourseDTO;
import com.Project.StudentManagement.dto.StudentDTO;
import com.Project.StudentManagement.exceptions.ResourceNotFoundException;
import com.Project.StudentManagement.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class courseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable(value = "id") Integer courseId) throws ResourceNotFoundException {
        CourseDTO course = courseService.getCourseById(courseId);
        return ResponseEntity.ok().body(course);
    }

    @GetMapping("/fees/{fee}")
    public List<CourseDTO> getCoursesByFees(@PathVariable double fee) {
        return courseService.getCoursesByFee(fee);
    }

    @PostMapping("/course")
    public CourseDTO createCourses(@RequestBody CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    @PostMapping
    public List<CourseDTO> createMultipleCourses(@RequestBody List<CourseDTO> courseDTOs) {
        return courseService.createMultipleCourses(courseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourses(@PathVariable(value = "id") Integer courseId,
                                                   @Validated @RequestBody CourseDTO courseDTO) throws ResourceNotFoundException {
        CourseDTO updatedCourse = courseService.updateCourse(courseId, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCourses(@PathVariable(value = "id") Integer courseId) throws ResourceNotFoundException {
        return courseService.deleteCourse(courseId);
    }

    @DeleteMapping
    public Map<String, Boolean> deleteAllCourses() {
        return courseService.deleteAllCourses();
    }

    @DeleteMapping("/{cid}/student/{sid}")
    public Map<String, Boolean> deleteStudentFromCourse(@PathVariable(value = "cid") Integer courseId,
                                                        @PathVariable(value = "sid") Integer studentId) throws ResourceNotFoundException {
        return courseService.deleteStudentFromCourse(courseId, studentId);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Set<StudentDTO>> getStudentsByCourseId(@PathVariable(value = "id") Integer courseId)
            throws ResourceNotFoundException {
        Set<StudentDTO> students = courseService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }
}
