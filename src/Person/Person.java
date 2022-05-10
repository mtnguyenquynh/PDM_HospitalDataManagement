package Person;

import BaseClass.IntermediateObject;
import PrefixState.Prefix;

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
 * Moreover, this object which have the privilege to access the zero-layer internal 
 * system (Treated to be Guest).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/


public class Person extends IntermediateObject {
    // These attributes are the personal information of a particular person
    private String email, last_name, phone_number, gender, nationality;        

    public Person(String ID, String name, String description, String last_name) throws Exception {
        super(ID, PersonUtils.StandardizeName(name), description);
        
        this.last_name = PersonUtils.StandardizeName(last_name);
        this.email = null;
        this.phone_number = null;
        this.gender = null;
        this.nationality = null;
        
        this.prefix = Prefix.Person;
    }

    public Person(String ID, String name, String last_name) throws Exception { 
        this(ID, PersonUtils.StandardizeName(name), null, last_name); 
    }


    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public String GetFirstName() { return this.GetName(); }             // Alias of method GetName()
    public String GetLastName() { return this.last_name; }
    public String GetEmail() { return this.email; }
    public String GetPhoneNumber() { return this.phone_number; }
    public String GetGender() { return this.gender; }
    public String GetNationality() { return this.nationality; }

    // -----------------------------------------------------------
    // Setter Function
    public void SetName(String name) throws Exception { 
        throw new Exception("This method is not allowed to be called.");
    }

    public void SetFirstName(String name) throws Exception { this.SetName(name); }
    
    public void SetLastName(String last_name) throws Exception { 
        throw new Exception("This method is not allowed to be called.");
    }

    public void SetEmail(String email) throws Exception { 
        this.email = PersonUtils.StandardizeEmail(email); 
    }

    public void SetPhoneNumber(String phone_number) throws Exception { 
        this.phone_number = PersonUtils.StandardizePhoneNumber(phone_number); 
    }

    public void SetGender(boolean IsFemale) { this.gender = PersonUtils.StandardizeGender(IsFemale); }
    
    public void SetNationality(String nationality) throws Exception {
        String nationality_ = PersonUtils.StandardizeName(nationality);
        String[] InvalidTerm = {"Us", "Usa", "Uk"};
        String[] ValidTerm = {"US", "USA", "UK"};

        for (int i = 0; i < InvalidTerm.length; i++) {
            nationality_ = nationality_.replace(InvalidTerm[i], ValidTerm[i]);
        }
        this.nationality = nationality_;
    }

    // ----------------------------------------------------------
    public static Prefix GetPrefix() { return Prefix.Person; }
    public static String GetPrefixCode() { return Person.GetPrefix().GetPrefixCode(); }


    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("last_name", this.GetLastName());
        result.put("email", this.GetEmail());
        result.put("phone_number", this.GetPhoneNumber());
        result.put("gender", this.GetGender());
        result.put("nationality", this.GetNationality());
        return result;
    }

    public static Person Deserialize(Hashtable<String, Object> data) throws Exception {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String last_name = (String) data.get("last_name");
        String description = (String) data.get("description");
        Person result = new Person(ID, name, description, last_name);
        
        result.SetEmail((String) data.get("email"));
        result.SetPhoneNumber((String) data.get("phone_number"));

        String gender = (String) data.get("gender");
        result.SetGender(PersonUtils.StandardizeGender(gender));

        result.SetNationality((String) data.get("nationality"));
        return result;
    }

}