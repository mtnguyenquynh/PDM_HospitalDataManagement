package Treatment;

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
 * This module stored all the treatment codes that can be found in the hospital.
 * Add more codes if you want to specify the treatment.
 * @author Ichiru Take
 * @version 0.0.1
 * See references:
 * - https://www.tutorialspoint.com/java/java_enum_class.htm
 * - https://www.geeksforgeeks.org/differences-between-hashmap-and-hashtable-in-java/
 * - https://www.geeksforgeeks.org/hashtable-in-java/
 * - https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Hashtable.html 
**/

public class TreatmentCodeSavedPool {
    public static ArrayList<String[]> LoadPool() {
        ArrayList<String[]> pool = new ArrayList<String[]>(10);

        pool.add(new String[]{"00-00-0000", "Null or Empty treatment"});
        pool.add(new String[]{"00-00-0001", "X-rays"});
        pool.add(new String[]{"00-00-0002", "Ultrasound"});
        pool.add(new String[]{"00-00-0003", "CT Scan"});
        pool.add(new String[]{"00-00-0004", "MRI"});
        pool.add(new String[]{"00-00-0005", "Blood test"});
        pool.add(new String[]{"00-00-0006", "Urine test"});
        pool.add(new String[]{"00-00-0007", "Surgical operation"});
        pool.add(new String[]{"00-00-0008", "Health-check"});        
        return pool;


    }
}
