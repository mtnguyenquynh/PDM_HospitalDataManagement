package Room;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import BaseClass.AbstractObject;
import Object.Tool;
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

public class LToolPool extends AbstractObject {
    private final static int SERIALIZATION_CAPACTITY = 10000;
    private final static float SERIALIZATION_LOAD_FACTOR = 0.75f;
    private final static int NumberOfToolInformation = 2;
    private Hashtable<String, String[]> LocalPool;

    public LToolPool(String ID) {
        super(ID);
        int capacity = PatientRoom.GetSerializationCapacity();
        float loadFactor = PatientRoom.GetSerializationLoadFactor();
        this.LocalPool = new Hashtable<String, String[]>(capacity, loadFactor);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Function
    private Hashtable<String, String[]> GetLocalPool() { return this.LocalPool; }
    public static int GetSerializationCapacity() { return LToolPool.SERIALIZATION_CAPACTITY; }
    public static float GetSerializationLoadFactor() { return LToolPool.SERIALIZATION_LOAD_FACTOR; }
    public int GetCurrentCapacity() { return this.GetLocalPool().size(); }

    public static String[] GetToolInformation(Tool tool) {
        Utils.CheckArgumentCondition(tool != null, "Tool cannot be null.");
        String[] ToolInformation = new String[LToolPool.NumberOfToolInformation];
        ToolInformation[0] = tool.GetID();
        ToolInformation[1] = tool.GetName();
        ToolInformation[2] = Integer.toString(tool.GetNumber());
        return ToolInformation;
    }

    public String[] GetToolById(String ID) {
        Utils.CheckArgumentCondition(ID != null, "ID cannot be null.");
        return this.GetLocalPool().get(ID);
    }

    public String[] GetToolByName(String Name) {
        Utils.CheckArgumentCondition(Name != null, "Name cannot be null.");
        String[] ToolInformation = null;
        for (String[] Tool : this.GetLocalPool().values()) {
            if (Tool[1].equals(Name)) {
                ToolInformation = Tool;
                break;
            }
        }
        return ToolInformation;
    }

    public String[] GetTool(Tool tool) { return this.GetToolById(tool.GetID()); }

    public boolean CheckIfToolAvailable(Tool tool) {
        Utils.CheckArgumentCondition(tool != null, "Tool cannot be null.");
        return this.GetLocalPool().containsKey(tool.GetID());
    }

    public void AddTool(Tool tool) throws RuntimeException {
        String[] ToolInformation = new String[LToolPool.NumberOfToolInformation];
        Utils.CheckArgumentCondition(tool != null, "Tool cannot be null.");
        if (this.GetCurrentCapacity() >= LToolPool.GetSerializationCapacity() - 2) {
            throw new RuntimeException("The capacity of the LToolPool is full.");
        }

        ToolInformation[0] = tool.GetID();
        ToolInformation[1] = tool.GetName();
        int NewToolNumber = tool.GetNumber();
        if (this.CheckIfToolAvailable(tool)) {
            String[] OldToolInformation = this.GetTool(tool);
            ToolInformation[2] = Integer.toString(Integer.parseInt(OldToolInformation[2]) + NewToolNumber);
        } else {
            ToolInformation[2] = Integer.toString(NewToolNumber);
        }
        this.GetLocalPool().put(tool.GetID(), ToolInformation);
    }

    public void RemoveTool(Tool tool) {
        if (tool == null) { return ;}
        if (this.CheckIfToolAvailable(tool)) { this.GetLocalPool().remove(tool.GetID()); }
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

    public static LToolPool Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        LToolPool result = new LToolPool(ID);
        Iterator<Entry<String, Object>> iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String[] ToolInformation = (String[]) entry.getValue();
            result.GetLocalPool().put(entry.getKey(), ToolInformation);
        }
        return result;
    }


}
