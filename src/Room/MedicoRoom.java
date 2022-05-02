package Room;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import BaseClass.BaseObject;
import Utility.Utils;

import Staff.Medico;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved
 *
 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class is to bind the relationship between the "RoomUnit" with the "ObjectRoom", 
 * "PatientRoom", and "MedicoRoom". In the "ObjectRoom" (which is a combination of the
 * "LToolPool" and "LResourcesPool"). The relationship can be implicitly described below:
 * 
 * Denoted a "RoomUnit" is a single room in the hospital. According to the "Software Architecture, 
 * Desgin & Engineering" and the "Principle of Database Management":
 * 1) PatientRoom: On each room, we assume a bed is for one patient, but since the number of
 *    bed is not fixed (undefined), but because the bed can be either empty or not, so the 
 *    primary key of the "PatientRoom" is the {"RoomUnit.ID", "Patient.ID", "Bed Index"}.
 *  
 * 2) MedicoRoom: In this scenario, the relationship is somehow related to the business logic.
 *    rather than the common ideology. In the "MedicoRoom", we assume that there must be at 
 *    least one medico responsible for that room (can be more than two). Thus the primary 
 *    key is the {"RoomUnit.ID", (Responsible) "Medico.ID"}.
 * 
 * 3) ObjectRoom: In this scenario, the relationship is somehow related to the Medico, but in 
 *    the program, this constraint is undefined. But in a simple manner, there are no different
 *    between the LToolPool and LResourcePool, which are both the collection of <Tool> and 
 *    <Resource>. Thus the primary key of the "ObjectRoom" is the {"RoomUnit.ID", "Object.ID"}.
 * 
 * In this class, we want to keep track the medico's ID, name, and phone number.
 * 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class MedicoRoom extends BaseObject {
    private final static int NumberOfMedicoInformation = 3;
    private final static int SERIALIZATION_CAPACTITY = 5;
    private final static float SERIALIZATION_LOAD_FACTOR = 1.0f;

    private String[] MainMedico;
    private Hashtable<String, String[]> OtherMedico;

    public MedicoRoom(String ID) {
        super(ID);
        
        int capacity = MedicoRoom.GetSerializationCapacity();
        float load_factor = MedicoRoom.GetSerializationLoadFactor();
        this.OtherMedico = new Hashtable<String, String[]>(capacity, load_factor);
    }

    public MedicoRoom(String ID, Medico medico) {
        this(ID);
        Utils.CheckArgumentCondition(medico != null, "Medico cannot be null");
        this.SetMainMedico(medico);
    }


    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public static String[] GetAnyMedicoForMedicoRoom(Medico medico) {
        String[] MedicoInformation = new String[NumberOfMedicoInformation];
        MedicoInformation[0] = medico.GetID();
        MedicoInformation[1] = medico.GetName();
        MedicoInformation[2] = medico.GetPhoneNumber();
        return MedicoInformation;
    }
    
    public String[] GetMainMedico() { return this.MainMedico; }
    public Hashtable<String, String[]> GetOtherMedico() { return this.OtherMedico; }

    public void SetMainMedico(Medico medico) { 
        Utils.CheckArgumentCondition(medico != null, "Medico cannot be null");
        this.MainMedico = MedicoRoom.GetAnyMedicoForMedicoRoom(medico);  
    }

    public void SetMainMedico(String[] MedicoInformation) { 
        // This function is called only during "Serialization-Deserialization"
        Utils.CheckArgumentCondition(MedicoInformation != null, "MedicoInformation cannot be null");
        this.MainMedico = MedicoInformation; 
    }

    public void SetOtherMedico(Medico medico) {
        Utils.CheckArgumentCondition(medico != null, "Medico cannot be null");
        String[] MedicoInformation = MedicoRoom.GetAnyMedicoForMedicoRoom(medico);
        this.OtherMedico.put(medico.GetID(), MedicoInformation);
    }
    
    public void SetOtherMedico(String[] MedicoInformation) {
        // This function is called only during "Serialization-Deserialization"
        Utils.CheckArgumentCondition(MedicoInformation != null, "MedicoInformation cannot be null");
        this.OtherMedico.put(MedicoInformation[0], MedicoInformation);
    }

    public static int GetSerializationCapacity() { return MedicoRoom.SERIALIZATION_CAPACTITY; }
    public static float GetSerializationLoadFactor() { return MedicoRoom.SERIALIZATION_LOAD_FACTOR; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serializaton & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = new Hashtable<String, Object>();
        result.put("id", this.GetID());
        result.put("MainMedico", this.MainMedico);
        
        Iterator<Entry<String, String[]>> iter = this.OtherMedico.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String[]> entry = iter.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    public static MedicoRoom Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        MedicoRoom result = new MedicoRoom(ID);

        result.SetMainMedico((String[]) data.get("MainMedico"));
        for (String key: data.keySet()) {
            if (!key.equals("id") && !key.equals("MainMedico")) {
                String[] MedicoInformation = (String[]) data.get(key);
                result.OtherMedico.put(key, MedicoInformation);
            }
        }
        return result;
    }

    

}
