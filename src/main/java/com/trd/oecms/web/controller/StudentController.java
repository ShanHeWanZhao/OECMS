package com.trd.oecms.web.controller;

import com.trd.oecms.dao.StudentDao;
import com.trd.oecms.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-03-31 12:59
 */
@RestController
public class StudentController {

    private StudentDao studentDao;

    @Autowired
    public StudentController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping("/getAll")
    public List<Student> findAll(){
        return studentDao.findAll();
    }

    @GetMapping("/get/{id}")
    public Student findOne(@PathVariable("id") Integer id){
        return studentDao.findByPrimaryKey(id);
    }

    @PostMapping("/insert")
    public int insert(Student student){
        return studentDao.insertOne(student);
    }

    @PutMapping("/update")
    public Student update(Student student){
        int i = studentDao.updateByPrimaryKey(student);
        System.out.println(i);
        return studentDao.findByPrimaryKey(student.getId());
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        int i = studentDao.deleteByPrimaryKey(id);
        System.out.println(i);
        Student student = studentDao.findByPrimaryKey(id);
        return student == null;
    }

}
