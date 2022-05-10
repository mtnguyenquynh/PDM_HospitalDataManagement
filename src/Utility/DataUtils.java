package Utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Class;
import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This file contained several helper functions
 * See references:
 * - https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
**/

public abstract class DataUtils {
    
    // ---------------------------------------------------------------------------------------------------------------------
    public static Hashtable<String, Object> ForceGetEmptyHashtable(Class cls) {
        int capacity = 10000;
        float loadFactor = 0.75f;
        if (cls != null) {
            for (Method method: cls.getMethods()) {
                if (method.getName().equals("GetSerializationCapacity")) {
                    try { capacity = (int) method.invoke(null); } 
                    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { }
                }

                if (method.getName().equals("GetSerializationLoadFactor")) {
                    try { loadFactor = (float) method.invoke(null); } 
                    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { }
                }
            }
        }
        return new Hashtable<String, Object>(capacity, loadFactor);
    }

    // ---------------------------------------------------------------------------------------------------------------------

    public static ArrayList<String> CastToStringArrayFromObjectArray(ArrayList<Object> obj) {
        ArrayList<String> result = new ArrayList<String>();
        for (Object o: obj) { result.add((String) o); }
        return result;
    }

    public static ArrayList<Integer> CastToIntArrayFromObjectArray(ArrayList<Object> obj) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (Object o: obj) { result.add((Integer) o); }
        return result;
    }

    public static ArrayList<Object> CastToObjectArrayFromStringArray(ArrayList<String> obj) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (String o: obj) { result.add(o); }
        return result;
    }

    public static ArrayList<Object> CastToObjectArrayFromIntArray(ArrayList<Integer> obj) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Integer o: obj) { result.add(o); }
        return result;
    }


    // ---------------------------------------------------------------------------------------------------------------------
    // Condition-checking
    /**
     * If the following condition is not met, the program will throw an exception.
     * @param condition (boolean): The condition to be checked.
     * @param message (String): The message to be printed if the condition is not met.
     * @throws IllegalArgumentException
     */
    public static void CheckArgumentCondition(boolean condition, String message) throws IllegalArgumentException {
        if (!condition) { throw new IllegalArgumentException(message); }
    }

    public static void CheckCondition(boolean condition, String message) throws Exception {
        if (!condition) { throw new Exception(message); }
    }

}