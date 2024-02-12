package com.example.springbootposts.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String massage){
        super(massage);
    }
}
