package Room;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import BaseClass.AbstractObject;
import Object.Resource;
import Utility.DataUtils;
import Utility.Utils;

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
 * Designing a LToolPool as well as the LResourcePool is a little bit tricky and not. The hidden
 * reason is that its structure is similar to the "PatientRoom" but the limitation from its amount
 * is unknown, which implied that in theory, the number of "Tool" and "Resource" is unlimited.
 * Thus, one of the greatest option is to set a constraint to prevent memory-overflow and serialize
 * the data.
 * 
 * Similar to the "PatientRoom" and "MedicoRoom", we stored the ID as an primary key along with 
 * the room-code. Reference attributes are the name of those objects and its number.
 * 
 * In the "design" section, we don't need to set the fixed-value amount in the "LocalPool" as
 * the number of objects are varied across the "RoomUnit".
 * 
 *  
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class LResourcePool extends AbstractObject {
    
    private final static int SERIALIZATION_CAPACTITY = 10000;
    private final static float SERIALIZATION_LOAD_FACTOR = 0.75f;
    private final static int NumberOfToolInformation = 2;
    private Hashtable<String, String[]> LocalPool;

    public LResourcePool(String ID) {
        super(ID);
        int capacity = PatientRoom.GetSerializationCapacity();
        float loadFactor = PatientRoom.GetSerializationLoadFactor();
        this.LocalPool = new Hashtable<String, String[]>(capacity, loadFactor);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Function
    private Hashtable<String, String[]> GetLocalPool() { return this.LocalPool; }
    public static int GetSerializationCapacity() { return LResourcePool.SERIALIZATION_CAPACTITY; }
    public static float GetSerializationLoadFactor() { return LResourcePool.SERIALIZATION_LOAD_FACTOR; }
    public int GetCurrentCapacity() { return this.GetLocalPool().size(); }

    public static String[] GetResourceInformation(Resource resource) {
        Utils.CheckArgumentCondition(resource != null, "resource cannot be null.");
        String[] ToolInformation = new String[LResourcePool.NumberOfToolInformation];
        ToolInformation[0] = resource.GetID();
        ToolInformation[1] = resource.GetName();
        ToolInformation[2] = Integer.toString(resource.GetNumber());
        return ToolInformation;
    }

    public String[] GetResourceById(String ID) {
        Utils.CheckArgumentCondition(ID != null, "Resource's ID cannot be null.");
        return this.GetLocalPool().get(ID);
    }

    public String[] GetResourceByName(String Name) {
        Utils.CheckArgumentCondition(Name != null, "Resource's Name cannot be null.");
        String[] ResourceInformation = null;
        for (String[] resource : this.GetLocalPool().values()) {
            if (resource[1].equals(Name)) {
                ResourceInformation = resource;
                break;
            }
        }
        return ResourceInformation;
    }

    public String[] GetResource(Resource resource) { return this.GetResourceById(resource.GetID()); }

    public boolean CheckIfResourceAvailable(Resource resource) {
        Utils.CheckArgumentCondition(resource != null, "Resource cannot be null.");
        return this.GetLocalPool().containsKey(resource.GetID());
    }

    public void AddTool(Resource resource) throws RuntimeException {
        String[] ResourceInformation = new String[LResourcePool.NumberOfToolInformation];
        Utils.CheckArgumentCondition(resource != null, "Resource cannot be null.");
        if (this.GetCurrentCapacity() >= LResourcePool.GetSerializationCapacity() - 2) {
            throw new RuntimeException("The capacity of the LResourcePool is full.");
        }

        ResourceInformation[0] = resource.GetID();
        ResourceInformation[1] = resource.GetName();
        int NewToolNumber = resource.GetNumber();
        if (this.CheckIfResourceAvailable(resource)) {
            String[] OldToolInformation = this.GetResource(resource);
            ResourceInformation[2] = Integer.toString(Integer.parseInt(OldToolInformation[2]) + NewToolNumber);
        } else {
            ResourceInformation[2] = Integer.toString(NewToolNumber);
        }
        this.GetLocalPool().put(resource.GetID(), ResourceInformation);
    }

    public void RemoveTool(Resource resource) {
        if (resource == null) { return ;}
        if (this.CheckIfResourceAvailable(resource)) { this.GetLocalPool().remove(resource.GetID()); }
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = DataUtils.ForceGetEmptyHashtable(this.getClass());
        result.put("id", this.GetID());

        Iterator<Entry<String, String[]>> iter = this.GetLocalPool().entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String[]> entry = iter.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static LResourcePool Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        LResourcePool result = new LResourcePool(ID);
        Iterator<Entry<String, Object>> iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            if (entry.getKey().equals("id")) { continue; }
            String[] ResourceInformation = (String[]) entry.getValue();
            result.GetLocalPool().put(entry.getKey(), ResourceInformation);
        }
        return result;
    }

}
