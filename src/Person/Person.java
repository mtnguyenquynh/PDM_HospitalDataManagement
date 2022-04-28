package Person;

/**
* Copyright (C) 2022-2022, HDM-Dev Team
* All Rights Reserved

* This file is part of HDM-Dev Team's project. The contents are
* fully covered, controlled, and acknowledged by the terms of the
* BSD-3 license, which is included in the file LICENSE.md, found
* at the root of the project's source code/tree repository.
**/

/**
 * This is a dataclass which describes an instances of a person.
 * which is also a base/abstract class to have better description on 
 * other classes such as Patient, Doctor, Nurse (medico)
**/




public class Person {
    private String id, name, email, phone_number, gender, nationality;

    public Person(String id, String name, String email, String phone_number, 
                  String gender, String nationality) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.nationality = nationality;
    }

    public String getID(){
        return id;
    }
    public void setID(String id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phone_number;
    }
    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}