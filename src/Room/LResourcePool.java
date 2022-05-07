package Room;

import java.util.Hashtable;

import BaseClass.BaseRoomContainer;
import Object.Resource;


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
 * See the note in "BaseClass.BaseObjectPool" for more details.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class LResourcePool extends BaseRoomContainer {
    
    public LResourcePool(String ID) throws Exception { super(ID); }

    public LResourcePool(BaseRoomContainer object_pool) throws Exception { super(object_pool); }

    public LResourcePool(LResourcePool object_pool) throws Exception { super((BaseRoomContainer) object_pool); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Find object in pool
    public String[] GetResource(String ID) { return this.GetObject(ID); }

    public String[] GetResource(Resource object) { return this.GetObject(object.GetID()); }

    public boolean IsResourceAvailable(String ID) { return this.GetObject(ID) != null; }

    public boolean IsResourceAvailable(Resource object) { return this.GetObject(object.GetID()) != null; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Updater
    public boolean UpdateResource(String ID, String name, int amount) throws Exception { 
        return this.UpdateObject(ID, amount);
    }

    public boolean UpdateResource(Resource object, int amount) throws Exception {
        return this.UpdateObject(object.GetID(), amount);
    }

    public boolean AddNewResource(String ID, String name, int amount) throws Exception { 
        return this.AddNewObject(ID, name, amount);
    }

    public boolean AddNewResource(Resource object, int amount) throws Exception {
        return this.AddNewObject(object.GetID(), object.GetName(), amount);
    }

    public boolean RemoveResource(String ID) throws Exception { return this.RemoveObject(ID); }

    public boolean RemoveResource(Resource object) throws Exception { return this.RemoveObject(object.GetID()); }


    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() { return super.Serialize(); }

    public static LResourcePool Deserialize(Hashtable<String, Object> data) throws Exception {
        return new LResourcePool(BaseRoomContainer.Deserialize(data));
    }

}
