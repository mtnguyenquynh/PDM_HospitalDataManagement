package Person;

import BaseClass.IntermediateObject;

import java.util.Hashtable;

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
 * other classes such as Patient, Doctor, Nurse (medico).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/


public class Person extends IntermediateObject {
    // These attributes are the personal information of a particular person
    private String email, phone_number, gender, nationality;        

    public Person(String ID, String name, String email, String phone_number, 
                  String gender, String nationality, String description) {
        super(ID, name, description);
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.nationality = nationality;
    }

    public Person(String ID, String name, String email, String phone_number, 
                  String gender, String nationality) {
        super(ID, name);
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.nationality = nationality;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public String GetEmail() { return this.email; }
    public String GetPhoneNumber() { return this.phone_number; }
    public String GetGender() { return this.gender; }
    public String GetNationality() { return this.nationality; }

    // -----------------------------------------------------------
    // Setter Function
    public void SetName(String name) throws RuntimeException { 
        throw new RuntimeException("This method is not allowed to be called.");
    }
    public void SetEmail(String email) { this.email = email; }
    public void SetPhoneNumber(String phone_number) { this.phone_number = phone_number; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("email", this.GetEmail());
        result.put("phone_number", this.GetPhoneNumber());
        result.put("gender", this.GetGender());
        result.put("nationality", this.GetNationality());
        return result;
    }

    public static Person Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String email = (String) data.get("email");
        String phone_number = (String) data.get("phone_number");
        String gender = (String) data.get("gender");
        String nationality = (String) data.get("nationality");
        return new Person(ID, name, email, phone_number, gender, nationality);
    }

}