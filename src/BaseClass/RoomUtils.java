package BaseClass;

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
 * This class is to support the data retrieval for the local pool
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
    public static String[] GetPersonInformation(String ID, String name, String phone_number) {
        RoomUtils.ValidateInput(ID, name, 1, false);
        String[] ObjectInfo = {ID, name, phone_number};
        return ObjectInfo;
    }

    public static String[] GetPersonInformation(Person person) {
        JsonUtils.CheckArgumentCondition(person != null, "Person cannot be null.");
        return RoomUtils.GetPersonInformation(person.GetID(), person.GetName(), person.GetPhoneNumber());
    }

    public static Object[] GetPersonInformationAsObjectList(Person person) {
        return (Object[]) RoomUtils.GetPersonInformation(person);
    }

    public static Object GetPersonInformationAsObject(Person person) {
        return (Object) RoomUtils.GetPersonInformation(person);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Object-related information
    public static String[] GetObjectInformation(String ID, String name, int number) {
        RoomUtils.ValidateInput(ID, name, number);
        String[] ObjectInfo = {ID, name, String.valueOf(number)};
        return ObjectInfo;
    }

    public static String[] GetObjectInformation(AbstractObject object, int number) {
        JsonUtils.CheckArgumentCondition(object != null, "Object cannot be null.");
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
        JsonUtils.CheckArgumentCondition(ID != null, "Object's ID cannot be null.");
        JsonUtils.CheckArgumentCondition(name != null, "Object's name cannot be null.");
        if (NumberValidation) { JsonUtils.CheckArgumentCondition(number >= 0, "The amount must be zero or positive."); }
    }

    public static void ValidateInput(String ID, String name, int number) {
        RoomUtils.ValidateInput(ID, name, number, true);
    }

    public static void ValidateInput(String ID, String name) {  
        RoomUtils.ValidateInput(ID, name, 1, false);
    }
}
