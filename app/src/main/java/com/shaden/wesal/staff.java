package com.shaden.wesal;

public class staff {
    private String name;
    private String id;


    private String assigned;

    public staff(){}

    public staff(String name) {
        this.name = name;
        this.id = "null";
        this.assigned = "null";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString (){
        return name;
    }

    public String getAssigned() { return assigned; }

    public void setAssigned(String assigned) { this.assigned = assigned; }
}
