package BaseClass;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

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
 * This class is to build a simple representation of the "Person Manager" which is a parent class
 * of "PatientRoom" and "MedicoRoom". The information storage is to 
 * map the ID of the object to {ID, name}.
 * 
 * In the programming scheme of the "Room", we did not intended to use some special methods, but
 * just a basic data-classes wrapper on top of it. Note that we don't store any non-serializable
 * object but just a basic description into it, whhich comprised composite-reference key as 
 * the primary key, and some syncronizable data.
 * 
 * Note that in some special real-world scenario, patient can switch across the bed, but this 
 * information can be viewed as redundant as the possibility of not finding the accurate patient
 * (from the data perspective) is neglibible. We may also have the possibility of switching the 
 * patient from this room to another room, but this is the responsibility of "RoomManager.class".
 * 
 * See the note in "BaseClass.BaseObjectPool" for more details.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class BasePersonRoom extends AbstractObject {
    
    private final static int MAX_CAPACTIY = 10000;
    private final static int SERIALIZATION_CAPACTITY = MAX_CAPACTIY + 1;
    private final static float SERIALIZATION_LOAD_FACTOR = 0.75f;
    private Hashtable<String, Object> LocalPool;

    public BasePersonRoom(String ID) throws Exception {
        super(ID);
        int capacity = BaseObjectPool.GetSerializationCapacity();
        float loadFactor = BaseObjectPool.GetSerializationLoadFactor();
        this.LocalPool = new Hashtable<String, Object>(capacity, loadFactor);
    }

    public BasePersonRoom(BasePersonRoom obj) throws Exception {
        super(obj.GetID());
        this.LocalPool = obj.LocalPool;
    }

    //  ---------------------------------------------------------------------------------------------------------------------
    public static String[] GetObjectInformation(String ID, String name, int number) {
        BasePersonRoom.ValidateInput(ID, name, number);
        String[] ObjectInfo = {ID, name, String.valueOf(number)};
        return ObjectInfo;
    }

    protected static String[] GetObjectInformation(AbstractObject object, int number) {
        Utils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return BasePersonRoom.GetObjectInformation(object.GetID(), object.GetName(), number);
    }

    protected static Object[] GetObjectInformationAsObjectList(AbstractObject object, int number) {
        Utils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return (Object[]) BaseObjectPool.GetObjectInformation(object, number);
    }

    protected static Object GetObjectInformationAsObject(AbstractObject object, int number) {
        Utils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return (Object) BaseObjectPool.GetObjectInformation(object.GetID(), object.GetName(), number);
    }


}
