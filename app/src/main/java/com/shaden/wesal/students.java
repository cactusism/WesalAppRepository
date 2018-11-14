package com.shaden.wesal;

public class students {

        private String firstname;
        private String middleName;
        private String lastname;
        private String nationalId;
        private double height;
        private double weight;
        private String bloodType;
        private String day;
        private String month;
        private String year;
        private String gender;
        private String stId;
        private String motherId;
        private String classID;
        private String className;
        private String fullName;




    public students() {
    }

    public students(String firstname, String middleName, String lastname, String nationalId, double height, double weight, String bloodType, String day, String month, String year, String gender) {
            this.firstname = firstname;
            this.middleName = middleName;
            this.lastname = lastname;
            this.nationalId = nationalId;
            this.height = height;
            this.weight = weight;
            this.bloodType = bloodType;
            this.day = day;
            this.month = month;
            this.year = year;
            this.gender = gender;
            this.stId = "null";
            this.motherId= "null";
            this.classID= "null";
            this.className = "null";
            this.fullName= firstname+" "+middleName+" "+lastname;
        }


        public String getFullName() { return fullName;}
        public void setFullName(){ fullName = firstname+" "+middleName+" "+lastname;}

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDay() { return day; }

    public void setDay(String day) { this.day = day; }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getStId() { return stId; }

    public void setStId(String stId) { this.stId = stId; }

    public String getMotherId() { return motherId; }

    public void setMotherId(String motherId) { this.motherId = motherId; }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String className) {
        this.classID = className;
    }


}