package Room;


import java.util.Hashtable;
import BaseClass.BaseRoomContainer;
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

public class MedicoRoom extends BaseRoomContainer {
    public MedicoRoom(String ID, int NumberOfMedicos) throws Exception { super(ID, NumberOfMedicos); }

    public MedicoRoom(String ID) throws Exception {super(ID, 1);}            // A common room may have 2-3 beds ?

    public MedicoRoom(MedicoRoom room) throws Exception { super((BaseRoomContainer) room); }
    
    public MedicoRoom(BaseRoomContainer room) throws Exception { super((BaseRoomContainer) room); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Find object in pool
    public String[] GetMedico(String ID) { return this.GetObject(ID); }

    protected String[] GetMedico(Medico object) { return this.GetObject(object.GetID()); }

    public boolean IsMedicoAvailable(String ID) { return this.GetObject(ID) != null; }

    protected boolean IsMedicoAvailable(Medico object) { return this.GetObject(object.GetID()) != null; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Updater
    public boolean AddNewMedico(String ID, String name) throws Exception { return this.AddNewPerson(ID, name); }

    protected boolean AddNewMedico(Medico object) throws Exception {
        return this.AddNewPerson(object.GetID(), object.GetName());
    }

    public boolean RemoveMedico(String ID) throws Exception { return this.RemovePerson(ID); }

    protected boolean RemoveMedico(Medico object) throws Exception { return this.RemovePerson(object.GetID()); }


    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Function
    public int GetMaxNumberOfMedicos() { return this.GetMaxCapacity(); }
    public int GetCurrentNumberOfMedicos()  { return this.GetCurrentCapacity(); }

    public void SetNewNumberOfMedicos(int NumberOfMedicos) { 
        Utils.CheckArgumentCondition(NumberOfMedicos >= 0, "Number of Beds must be a non-negative integer.");
        this.SetMaxCapacity(NumberOfMedicos); 
    }


    // ---------------------------------------------------------------------------------------------------------------------
    // Serializaton & Deserialization
    public Hashtable<String, Object> Serialize() { return super.Serialize(); }

    public static MedicoRoom Deserialize(Hashtable<String, Object> data) throws Exception {
        return new MedicoRoom(BaseRoomContainer.Deserialize(data));
    }


    

}
