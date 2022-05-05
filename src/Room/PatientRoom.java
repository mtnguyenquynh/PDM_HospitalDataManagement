package Room;

import java.util.Hashtable;
import BaseClass.BaseRoomContainer;
import Utility.Utils;

import Patient.Patient;

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
 * In the programming scheme of the "Room", there are some regulations such as bed switching, 
 * but in the software, maintaining this property is especially difficult. Thus the "Patient"
 * here, we only stored who was in the room at that time. 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class PatientRoom extends BaseRoomContainer {
    public PatientRoom(String ID, int NumberOfBeds) throws Exception { super(ID, NumberOfBeds); }

    public PatientRoom(String ID) throws Exception {super(ID, 3);}            // A common room may have 2-3 beds ?

    public PatientRoom(PatientRoom room) throws Exception { super((BaseRoomContainer) room); }
    
    public PatientRoom(BaseRoomContainer room) throws Exception { super((BaseRoomContainer) room); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Updater
    public boolean AddNewPatient(String ID, String name) throws Exception { return this.AddNewPerson(ID, name); }

    protected boolean AddNewPatient(Patient object) throws Exception {
        return this.AddNewPerson(object.GetID(), object.GetName());
    }

    public boolean RemovePatient(String ID) throws Exception { return this.RemovePerson(ID); }

    protected boolean RemovePatient(Patient object) throws Exception { return this.RemovePerson(object.GetID()); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Function
    public int GetNumberOfBeds() { return this.GetMaxCapacity(); }
    public int GetNumberOfOccupiedBeds()  { return this.GetCurrentCapacity(); }

    public void SetNumberOfBeds(int NumberOfBeds) { 
        Utils.CheckArgumentCondition(NumberOfBeds >= 0, "Number of Beds must be a non-negative integer.");
        Utils.CheckArgumentCondition(NumberOfBeds >= this.GetCurrentCapacity(), 
                             "The (updated) number of beds are pre-occupied. We cannot move it.");
        this.SetMaxCapacity(NumberOfBeds); 
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() { return super.Serialize(); }

    public static PatientRoom Deserialize(Hashtable<String, Object> data) throws Exception {
        return new PatientRoom(BaseRoomContainer.Deserialize(data));
    }


}
