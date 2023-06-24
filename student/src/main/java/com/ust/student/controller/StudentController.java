package com.ust.student.controller;

import com.ust.student.domain.Student;
import com.ust.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(studentRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id){
        try{
            Student student = studentRepository.findById(id).orElseThrow(NoSuchElementException::new);
            return new ResponseEntity<>(student,HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentRepository.save(student),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id){
        try {
            Student student1 = studentRepository.findById(id).orElseThrow(NoSuchElementException::new);
            studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id,@RequestBody Student student){
        try
        {
            Student student1 = studentRepository.findById(id).orElseThrow(NoSuchElementException::new);
            student1.setName(student.getName());
            student1.setAge(student.getAge());
            student1.setAddress(student.getAddress());
            student1.setBranch(student.getBranch());
            studentRepository.save(student1);
            return new ResponseEntity<>(student1,HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
