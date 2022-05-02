package Utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Class;
import java.util.Hashtable;

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

}