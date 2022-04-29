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

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter Function
    public String GetID() { return this.id; }
    public String GetName() { return this.name; }
    public String GetEmail() { return this.email; }
    public String GetPhoneNumber() { return this.phone_number; }
    public String GetGender() { return this.gender; }
    public String GetNationality() { return this.nationality; }


    // -----------------------------------------------------------
    // Setter Function
    public void SetEmail(String email) { this.email = email; }
    public void SetPhoneNumber(String phone_number) { this.phone_number = phone_number; }
    
}