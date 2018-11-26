package com.shaden.wesal;

public class Classes {

    private String name;
    private String teacher;
    private String teacherID;
    private String ID;

    public String getStarOfTheWeekId() {
        return starOfTheWeekId;
    }

    public void setStarOfTheWeekId(String starOfTheWeekId) {
        this.starOfTheWeekId = starOfTheWeekId;
    }

    public String getStarOfTheWeekName() {
        return starOfTheWeekName;
    }

    public void setStarOfTheWeekName(String starOfTheWeekName) {
        this.starOfTheWeekName = starOfTheWeekName;
    }

    private String starOfTheWeekId;
    private String starOfTheWeekName;

    public Classes (){}

     public Classes (String name, String teacher){

         this.name=name;
         this.teacher=teacher;
         teacherID = "null";
         ID = "null";
         starOfTheWeekId = "";
         starOfTheWeekName ="";


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


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String toString(){
         return "فصل"+name;
    }
}
