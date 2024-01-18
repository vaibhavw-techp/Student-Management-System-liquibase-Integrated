package com.Project.StudentManagement.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class resourceNotFoundException extends Exception{
    public resourceNotFoundException(Integer id){
        super("404 not found with id: "+id);
    }
}
