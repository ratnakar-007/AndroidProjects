package com.example.ratnakarsharma.sqlitebasics.model;

/**
 * Created by Ratnakar Sharma on 10/9/2016.
 */

public class Todo {
   String id;
    String task;
    boolean done;

    public Todo(String id, String task, boolean done) {
        this.id = id;
        this.task = task;
        this.done = done;
    }

    /*public Todo(String task, boolean done) {
        this.task = task;
        this.done = done;
    }*/

    public String getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return done;
    }
}