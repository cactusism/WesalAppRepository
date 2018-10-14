package com.shaden.wesal;

public class Classes {

    private String name;
    private String teacher;
    private String assistant;

     Classes(){}

     public Classes (String name, String age, String blood){

         this.name=name;
         this.teacher=age;
         this.assistant=blood;


     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }
}
