package BaseClass;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import Person.Person;
import Utility.JsonUtils;

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
 * This class is to build a simple representation of the "Object Manager" which is a parent class
 * of "LToolPool", "LResourcesPool", "GToolPool", "GResourcesPool". The information storage is to 
 * map the ID of the object to {ID, name, amount}.
 * 
 * It also support to be the super-class of the "PatientRoom" and "MedicoRoom"
 *  
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/30207940/what-happens-to-protected-method-of-a-super-class-in-base-class-in-java
 * 2) https://stackoverflow.com/questions/47800155/is-protected-method-in-super-class-visible-in-sub-class-in-a-different-package
**/

public class BaseRoomContainer extends AbstractObject {
    // ---------------------------------------------------------------------------------------------------------------------
    // Plus one here is to store the ID of this class.
    private final static int SERIALIZATION_CAPACTITY = 10000 + 1;
    private final static float SERIALIZATION_LOAD_FACTOR = 0.75f;
    
    private Hashtable<String, Object> LocalPool;
    private int MaxCapacity;

    public BaseRoomContainer(String ID, int MaxCapacity) throws Exception {
        super(ID);
        JsonUtils.CheckArgumentCondition(MaxCapacity >= 0, "The maximum capacity must be non-negative.");

        this.MaxCapacity = MaxCapacity;
        int capacity = BaseRoomContainer.GetSerializationCapacity();
        float loadFactor = BaseRoomContainer.GetSerializationLoadFactor();
        this.LocalPool = new Hashtable<String, Object>(capacity, loadFactor);
    }

    public BaseRoomContainer(String ID) throws Exception { this(ID, 10000); }

    public BaseRoomContainer(BaseRoomContainer obj) throws Exception {
        super(obj.GetID());
        this.LocalPool = obj.LocalPool;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Find object in pool
    public String[] GetObject(String ID) {
        JsonUtils.CheckArgumentCondition(ID != null, "Object's ID cannot be null.");
        return (String[]) this.GetLocalPool().get(ID); 
    }

    protected String[] GetObject(AbstractObject object) {
        JsonUtils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return this.GetObject(object.GetID());
    }

    public boolean IsObjectAvailable(String ID) { return this.GetObject(ID) != null; }

    protected boolean IsObjectAvailable(AbstractObject object) { return this.GetObject(object.GetID()) != null; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Object-related functions
    public int TestObjectMode(String ID, int amount) throws Exception {
        RoomUtils.ValidateInput(ID, "", amount, false);

        String[] PoolObjectInfo = this.GetObject(ID);
        if (PoolObjectInfo == null) { return 1; }

        String PoolAmount = PoolObjectInfo[2];
        int NewAmount = Integer.parseInt(PoolAmount) + amount;
        return NewAmount == 0 ? 0 : 2;
    }
    
    public int AddOrUpdateObject(String ID, String name, int amount) throws Exception {
        RoomUtils.ValidateInput(ID, "", amount, false);

        String[] PoolObjectInfo = this.GetObject(ID);
        if (PoolObjectInfo == null) {
            if (this.IsPoolFull()) { throw new Exception("Pool is full."); }
            String[] ObjectInfo = RoomUtils.GetObjectInformation(ID, name, amount); 
            this.GetLocalPool().put(ID, ObjectInfo);
            return 1;
        }

        String PoolAmount = PoolObjectInfo[2];
        int NewAmount = Integer.parseInt(PoolAmount) + amount;
        if (NewAmount == 0) { 
            this.GetLocalPool().remove(ID);
            return 0;
        } else {
            PoolObjectInfo[2] = String.valueOf(NewAmount);
            this.GetLocalPool().put(ID, PoolObjectInfo);
            return 2;
        }
    }

    protected int AddOrUpdateObject(AbstractObject object, int amount) throws Exception {
        JsonUtils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return this.AddOrUpdateObject(object.GetID(), object.GetName(), amount);
    }

    public boolean UpdateObject(String ID, int amount) throws Exception {
        if (this.TestObjectMode(ID, amount) == 2) {
            this.AddOrUpdateObject(ID, "", amount);
            return true;
        }
        return false;
    }

    protected boolean UpdateObject(AbstractObject object, int amount) throws Exception {
        JsonUtils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return this.UpdateObject(object.GetID(), amount);
    }

    public boolean AddNewObject(String ID, String name, int amount) throws Exception {
        if (this.TestObjectMode(ID, amount) == 1) {
            this.AddOrUpdateObject(ID, "", amount);
            return true;
        }
        return false;
    }

    protected boolean AddNewObject(AbstractObject object, int amount) throws Exception {
        JsonUtils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return this.AddNewObject(object.GetID(), object.GetName(), amount);
    }

    public boolean RemoveObject(String ID) throws Exception {
        String[] PoolObjectInfo = this.GetObject(ID);
        if (PoolObjectInfo == null) { return true; }

        int PoolAmount = Integer.parseInt(PoolObjectInfo[2]);
        if (this.TestObjectMode(ID, 0 - PoolAmount) == 0) {
            this.AddOrUpdateObject(ID, "", PoolAmount);
            return true;
        }
        return false;
    }

    protected boolean RemoveObject(AbstractObject object) throws Exception {
        JsonUtils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return this.RemoveObject(object.GetID());
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Object-related functions
    public int TestPersonMode(String ID) throws Exception {
        RoomUtils.ValidateInput(ID, "");
        String[] PoolObjectInfo = this.GetObject(ID);
        if (PoolObjectInfo == null) { return 1; }
        return 0;
    }
    
    public int AddOrUpdatePerson(String ID, String name, String phone_number) throws Exception {
        RoomUtils.ValidateInput(ID, "");

        String[] PoolObjectInfo = this.GetObject(ID);
        if (PoolObjectInfo == null) {
            if (this.IsPoolFull()) { throw new Exception("Pool is full."); }
            String[] ObjectInfo = RoomUtils.GetPersonInformation(ID, name, phone_number); 
            this.GetLocalPool().put(ID, ObjectInfo);
            return 1;
        } else {
            this.GetLocalPool().remove(ID);
            return 0;
        }
    }

    protected int AddOrUpdatePerson(Person person) throws Exception {
        JsonUtils.CheckArgumentCondition(person != null, "Person cannot be null.");
        return this.AddOrUpdatePerson(person.GetID(), person.GetName(), person.GetPhoneNumber());
    }

    public boolean AddNewPerson(String ID, String name, String phone_number) throws Exception {
        if (this.TestPersonMode(ID) == 1) {
            this.AddOrUpdatePerson(ID, "", phone_number);
            return true;
        }
        return false;
    }

    protected boolean AddNewPerson(Person person) throws Exception {
        JsonUtils.CheckArgumentCondition(person != null, "Person cannot be null.");
        return this.AddNewPerson(person.GetID(), person.GetName(), person.GetPhoneNumber());
    }

    public boolean RemovePerson(String ID) throws Exception {
        if (this.TestPersonMode(ID) == 0) {
            this.AddOrUpdatePerson(ID, "", "");
            return true;
        }
        return false;
    }

    protected boolean RemovePerson(Person person) throws Exception {
        JsonUtils.CheckArgumentCondition(person != null, "Person cannot be null.");
        return this.RemovePerson(person.GetID());
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter Function
    public Hashtable<String, Object> GetLocalPool() { return this.LocalPool; }   

    public static int GetSerializationCapacity() { return BaseRoomContainer.SERIALIZATION_CAPACTITY; }
    public static float GetSerializationLoadFactor() { return BaseRoomContainer.SERIALIZATION_LOAD_FACTOR; }
    public int GetMaxCapacity() { return this.MaxCapacity; }
    public void SetMaxCapacity(int capacity) { this.MaxCapacity = capacity; }
    
    public int GetCurrentCapacity() { return this.GetLocalPool().size(); }

    public boolean IsEmpty() { return this.GetLocalPool().isEmpty(); }
    public boolean IsPoolHasExtraSlot() { return this.GetCurrentCapacity() < this.GetMaxCapacity(); }
    public boolean IsPoolFull() { return !this.IsPoolHasExtraSlot(); }

    // ---------------------------------------------------------------------------------------------------------------------
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("MaxCapacity", this.GetMaxCapacity());

        Hashtable<String, Object> pool = this.GetLocalPool();
        Iterator<Entry<String, Object>> it = pool.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static BaseRoomContainer Deserialize(Hashtable<String, Object> data) throws Exception {
        String id = (String) data.get("id");
        int MaxCapacity = (int) data.get("MaxCapacity");

        BaseRoomContainer room = new BaseRoomContainer(id, MaxCapacity);
        Hashtable<String, Object> pool = room.GetLocalPool();
        Iterator<Entry<String, Object>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            pool.put(entry.getKey(), entry.getValue());
        }
        return room;
    }
}
