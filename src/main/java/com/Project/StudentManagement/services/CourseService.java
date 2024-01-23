package com.Project.StudentManagement.services;

import com.Project.StudentManagement.dto.CourseDTO;
import com.Project.StudentManagement.dto.StudentDTO;
import com.Project.StudentManagement.entity.Course;
import com.Project.StudentManagement.entity.Student;
import com.Project.StudentManagement.exceptions.ResourceNotFoundException;
import com.Project.StudentManagement.repository.CourseRepository;
import com.Project.StudentManagement.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Integer courseId) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
        return convertToDTO(course);
    }

    public List<CourseDTO> getCoursesByFee(double fee) {
        List<Course> courses = courseRepository.findByFeeLessThan(fee);
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    public List<CourseDTO> createMultipleCourses(List<CourseDTO> courseDTOs) {
        List<Course> courses = courseDTOs.stream().map(this::convertToEntity).collect(Collectors.toList());
        List<Course> savedCourses = courseRepository.saveAll(courses);
        return savedCourses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CourseDTO updateCourse(Integer courseId, CourseDTO courseDTO) throws ResourceNotFoundException {
        Course existingCourse = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
        Course updatedCourse = convertToEntity(courseDTO);
        updatedCourse.setId(existingCourse.getId());
        Course savedCourse = courseRepository.save(updatedCourse);
        return convertToDTO(savedCourse);
    }

    public Map<String, Boolean> deleteCourse(Integer courseId) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
        courseRepository.delete(course);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Course with id " + courseId + " is deleted successfully", Boolean.TRUE);
        return response;
    }

    public Map<String, Boolean> deleteAllCourses() {
        courseRepository.deleteAll();
        Map<String, Boolean> response = new HashMap<>();
        response.put("All courses records deleted", Boolean.TRUE);
        return response;
    }

    public Map<String, Boolean> deleteStudentFromCourse(Integer courseId, Integer studentId) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(studentId));
        student.getCourses().remove(course);
        course.getStudents().remove(student);
        studentRepository.save(student);
        courseRepository.save(course);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Student is removed from course", Boolean.TRUE);
        return response;
    }

    public Set<StudentDTO> getStudentsByCourseId(Integer courseId) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId));
        Set<Student> students = course.getStudents();
        return students.stream().map(this::convertStudentToDTO).collect(Collectors.toSet());
    }

    private CourseDTO convertToDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, Course.class);
    }

    private StudentDTO convertStudentToDTO(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }
}
