package com.Project.StudentManagement.controller;


import com.Project.StudentManagement.entity.Course;
import com.Project.StudentManagement.entity.Student;
import com.Project.StudentManagement.repository.CourseRepository;
import com.Project.StudentManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.Project.StudentManagement.exceptions.resourceNotFoundException;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class studentController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    // Get All Students
    @GetMapping
    public List<Student> getAllStudents(){
        return this.studentRepository.findAll();
    }

    // Get Student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable(value = "id")Integer studid) throws resourceNotFoundException {
        Student student=studentRepository.findById(studid).orElseThrow(()->new resourceNotFoundException(studid));
        return ResponseEntity.ok().body(student);
    }

    // Get by Name
    @GetMapping("/find/{name}")
    public List<Student> findbyname(@PathVariable String name){
        return studentRepository.findByNameContaining(name);
    }

    // Create Student
    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return this.studentRepository.save(student);
    }

    // Create Multiple Students
    @PostMapping("/createMultiple")
    public List<Student> createMultipleStudents(@RequestBody List<Student> students){
        return  this.studentRepository.saveAll(students);
    }

    // Assign Student to a course
    @PutMapping("/{sid}/course/{cid}")
    public ResponseEntity<Student> assignStudentToCourse(@PathVariable(value = "sid")Integer sid, @PathVariable(value = "cid")Integer cid)throws resourceNotFoundException{
        Course course=courseRepository.findById(cid).orElseThrow(()-> new resourceNotFoundException(cid));
        Student student=studentRepository.findById(sid).orElseThrow(()-> new resourceNotFoundException(sid));
        student.getCourses().add(course);
        course.getStudents().add(student);
        courseRepository.save(course);
        return ResponseEntity.ok(this.studentRepository.save(student));
    }


    // Update Student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(value = "id")Integer studid, @Validated @RequestBody Student studentDetails)throws resourceNotFoundException {
        Student student=studentRepository.findById(studid).orElseThrow(()->new resourceNotFoundException(studid));
        student.setCourses(studentDetails.getCourses());
        student.setDept(studentDetails.getDept());
        student.setName(studentDetails.getName());
        student.setYear(studentDetails.getYear());
        return ResponseEntity.ok(this.studentRepository.save(student));
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Integer studid)throws resourceNotFoundException {
        Student student=studentRepository.findById(studid).orElseThrow(()->new resourceNotFoundException(studid));
        this.studentRepository.delete(student);
        Map<String, Boolean> response=new HashMap<>();
        response.put("Student with id "+studid+" is deleted successfully",Boolean.TRUE);
        return response;
    }

    // Delete all students
    @DeleteMapping("/deleteAll")
    public Map<String, Boolean> deleteAllStudents(){
        this.studentRepository.deleteAll();
        Map<String, Boolean> response=new HashMap<>();
        response.put("All Students records deleted",Boolean.TRUE);
        return response;
    }


}
