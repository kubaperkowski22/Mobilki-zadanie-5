package com.example.zadanie;

import java.util.Date;
import java.util.UUID;



public class Task {
    private Category category;
    private UUID id;
    private String name;
    private Date date;
    private boolean done;

    public Task() {
        id = UUID.randomUUID();
        date = new Date();
        category = Category.HOME;
    }

    public void setCategory(Category cat) {
        this.category = cat;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String idk) {
        this.name = idk;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean isChecked) {
        this.done = isChecked;
    }

    public UUID getId() {
        return this.id;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setDate(Date time) {
        this.date = time;
    }
}
