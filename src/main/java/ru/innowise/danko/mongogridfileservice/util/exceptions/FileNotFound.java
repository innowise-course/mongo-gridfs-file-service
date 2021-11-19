package ru.innowise.danko.mongogridfileservice.util.exceptions;

public class FileNotFound extends RuntimeException {

    public FileNotFound(String id){
        super("File with id " + id + " doesnt exist");
    }
}
