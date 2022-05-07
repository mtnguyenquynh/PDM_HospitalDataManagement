package Staff;

import java.util.Hashtable;

import Person.Person;
import Person.PersonUtils;
import PrefixState.Prefix;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This is a dataclass which describes an instances of a normal staff, 
 * which have the privilege to access the second-layer internal system.
 * But in this case, the "internal system" is not developed.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class Staff extends Person {
    
    public Staff(String ID, String name, String description) throws Exception {
        super(ID, name, description);
        this.prefix = Prefix.Staff;
    }

    public Staff(String ID, String name) throws Exception { this(ID, name, null); }

    // --------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public static Prefix GetPrefix() { return Prefix.Staff; }
    public static String GetPrefixCode() { return Staff.GetPrefix().GetPrefixCode(); }
    
    // --------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        // If there are more attributes to be serialized, add them here.
        return result;
    }

    public static Staff Deserialize(Hashtable<String, Object> data) throws Exception {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        Staff result = new Staff(ID, name, description);
        
        result.SetEmail((String) data.get("email"));
        result.SetPhoneNumber((String) data.get("phone_number"));

        String gender = (String) data.get("gender");
        result.SetGender(PersonUtils.StandardizeGender(gender));

        result.SetNationality((String) data.get("nationality"));
        return result;
    }
}
