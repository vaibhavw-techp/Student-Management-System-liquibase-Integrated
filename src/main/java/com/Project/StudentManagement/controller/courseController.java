package com.Project.StudentManagement.controller;


import com.Project.StudentManagement.entity.Course;
import com.Project.StudentManagement.entity.Student;
import com.Project.StudentManagement.exceptions.resourceNotFoundException;
import com.Project.StudentManagement.repository.CourseRepository;
import com.Project.StudentManagement.repository.StudentRepository;
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
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    // Get All Courses
    @GetMapping
    public List<Course> getAllSCourses(){
        return this.courseRepository.findAll();
    }

    // Get Courses by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable(value = "id")Integer courid) throws resourceNotFoundException {
        Course course=courseRepository.findById(courid).orElseThrow(()->new resourceNotFoundException(courid));
        return ResponseEntity.ok().body(course);
    }

    // Get Courses by fee
    @GetMapping("/find/{fee}")
    public List<Course> getCoursesByFees(@PathVariable double fee){
        return courseRepository.findByFeeLessThan(fee);
    }

    // Create Courses
    @PostMapping
    public Course createCourses(@RequestBody Course course){
        return this.courseRepository.save(course);
    }

    // Create Multiple Courses
    @PostMapping("/createMultiple")
    public List<Course> createMultipleCourses(@RequestBody List<Course> courses){
        return  this.courseRepository.saveAll(courses);
    }

    // Update Courses
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourses(@PathVariable(value = "id")Integer courid, @Validated @RequestBody Course courseDetails)throws resourceNotFoundException {
        Course course=courseRepository.findById(courid).orElseThrow(()->new resourceNotFoundException(courid));
        course.setCourseCode(courseDetails.getCourseCode());
        course.setStudents(courseDetails.getStudents());
        course.setFee(courseDetails.getFee());
        course.setTitle(courseDetails.getTitle());
        course.setCredits(courseDetails.getCredits());
        return ResponseEntity.ok(this.courseRepository.save(course));
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCourses(@PathVariable(value = "id") Integer courid)throws resourceNotFoundException {
        Course course=courseRepository.findById(courid).orElseThrow(()->new resourceNotFoundException(courid));
        this.courseRepository.delete(course);
        Map<String, Boolean> response=new HashMap<>();
        response.put("Course with id "+courid+" is deleted successfully",Boolean.TRUE);
        return response;
    }

    //Delete all courses
    @DeleteMapping("/deleteAll")
    public Map<String, Boolean> deleteAllCourses(){
        this.courseRepository.deleteAll();
        Map<String, Boolean> response=new HashMap<>();
        response.put("All courses records deleted",Boolean.TRUE);
        return response;
    }

    //Delete student from a course
    @DeleteMapping("/{cid}/student/{sid}")
    public  Map<String, Boolean> deleteStudentFromCourse(@PathVariable(value = "cid")Integer cid,@PathVariable(value = "sid")Integer sid) throws resourceNotFoundException{
        Course course=courseRepository.findById(cid).orElseThrow(()-> new resourceNotFoundException(cid));
        Student student=studentRepository.findById(sid).orElseThrow(()-> new resourceNotFoundException(sid));
        student.getCourses().remove(course);
        course.getStudents().remove(student);
        studentRepository.save(student);
        courseRepository.save(course);
        Map<String, Boolean> response=new HashMap<>();
        response.put("Student is removed from course",Boolean.TRUE);
        return response;
    }

    // Get all students by course id
    @GetMapping("/{id}/students")
    public ResponseEntity<Set<Student>> getStudentsByCourseId(@PathVariable(value = "id")Integer courid)throws resourceNotFoundException{
        Course course=courseRepository.findById(courid).orElseThrow(()->new resourceNotFoundException(courid));
        Set<Student> students=course.getStudents();
        return ResponseEntity.ok(students);
    }
}
