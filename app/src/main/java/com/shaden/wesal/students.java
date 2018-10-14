package com.shaden.wesal;

public class students {

        private String firstname;
        private String middleName;
        private String lastname;
        private String nationalId;
        private String height;
        private String  weight;
        private String bloodType;
        public students() {
        }

        public students(String firstname, String middleName, String lastname,String nationalId, String height, String weight, String bloodType) {
            this.firstname = firstname;
            this.middleName = middleName;
            this.lastname = lastname;
            this.nationalId = nationalId;
            this.height = height;
            this.weight = weight;
            this.bloodType = bloodType;
        }

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}