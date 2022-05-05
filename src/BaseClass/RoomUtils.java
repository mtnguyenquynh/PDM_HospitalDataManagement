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
 * This class is to support the data retrieval for each object
 *
 *  
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/30207940/what-happens-to-protected-method-of-a-super-class-in-base-class-in-java
 * 2) https://stackoverflow.com/questions/47800155/is-protected-method-in-super-class-visible-in-sub-class-in-a-different-package
**/


public abstract class RoomUtils {
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Person-related information
    public static String[] GetObjectInformation(String ID, String name) {
        RoomUtils.ValidateInput(ID, name, 1, false);
        String[] ObjectInfo = {ID, name};
        return ObjectInfo;
    }

    public static String[] GetPersonInformation(String ID, String name) {
        return RoomUtils.GetObjectInformation(ID, name);
    }

    public static String[] GetObjectInformation(AbstractObject object) {
        Utils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return RoomUtils.GetObjectInformation(object.GetID(), object.GetName());
    }

    public static Object[] GetObjectInformationAsObjectList(AbstractObject object) {
        return (Object[]) RoomUtils.GetObjectInformation(object);
    }

    public static Object GetObjectInformationAsObject(AbstractObject object) {
        return (Object) RoomUtils.GetObjectInformation(object);
    }

    public static String[] GetPersonInformation(AbstractObject object) {
        Utils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return RoomUtils.GetPersonInformation(object.GetID(), object.GetName());
    }

    public static Object[] GetPersonInformationAsObjectList(AbstractObject object) {
        return (Object[]) RoomUtils.GetPersonInformation(object);
    }

    public static Object GetPersonInformationAsObject(AbstractObject object) {
        return (Object) RoomUtils.GetPersonInformation(object);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Object-related information
    public static String[] GetObjectInformation(String ID, String name, int number) {
        RoomUtils.ValidateInput(ID, name, number);
        String[] ObjectInfo = {ID, name, String.valueOf(number)};
        return ObjectInfo;
    }

    public static String[] GetObjectInformation(AbstractObject object, int number) {
        Utils.CheckArgumentCondition(object != null, "Object cannot be null.");
        return RoomUtils.GetObjectInformation(object.GetID(), object.GetName(), number);
    }

    public static Object[] GetObjectInformationAsObjectList(AbstractObject object, int number) {
        return (Object[]) RoomUtils.GetObjectInformation(object, number);
    }

    public static Object GetObjectInformationAsObject(AbstractObject object, int number) {
        return (Object) RoomUtils.GetObjectInformation(object, number);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    public static void ValidateInput(String ID, String name, int number, boolean NumberValidation) {
        Utils.CheckArgumentCondition(ID != null, "Object's ID cannot be null.");
        Utils.CheckArgumentCondition(name != null, "Object's name cannot be null.");
        if (NumberValidation) { Utils.CheckArgumentCondition(number >= 0, "The amount must be zero or positive."); }
    }

    public static void ValidateInput(String ID, String name, int number) {
        RoomUtils.ValidateInput(ID, name, number, true);
    }
}
