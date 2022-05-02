package Room;

import java.util.ArrayList;

import javax.rmi.CORBA.Util;

import BaseClass.BaseObject;
import Utility.Utils;

import Patient.Patient;

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
 * In the programming scheme of the "Room", we did not intended to use some special methods, but
 * just a basic data-classes wrapper on top of it. Note that we don't store any non-serializable
 * object but just a basic description into it, whhich comprised composite-reference key as 
 * the primary key, and some syncronizable data. 
 * .
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class PatientRoom extends BaseObject {
    
    private ArrayList<String> LocalPool;
    public PatientRoom(String ID, int NumberOfBeds) {
        super(ID, NumberOfBeds);
        this.LocalPool = new ArrayList<String>();
        this._InitPool_();

    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Function
    private ArrayList<String> GetLocalPool() { return this.LocalPool; }
    public int GetNumberOfBeds() { return this.GetNumber(); }
    public int GetCapacity() { return this.GetNumberOfBeds(); }         // Alias of GetNumberOfBeds()

    public void SetNumberOfBeds(int NumberOfBeds) { 
        Utils.CheckArgumentCondition(NumberOfBeds >= 0, "NumberOfBeds cannot be negative.");
        super.SetNumber(NumberOfBeds);
    }

    public void IncrementNumberOfBeds(int NumberOfBeds) { 
        Utils.CheckArgumentCondition(NumberOfBeds >= 0, "NumberOfBeds cannot be negative.");
        super.IncrementNumber(NumberOfBeds);
    }

    public void DecrementNumberOfBeds(int NumberOfBeds) { 
        Utils.CheckArgumentCondition(NumberOfBeds >= 0, "NumberOfBeds cannot be negative.");
        Utils.CheckArgumentCondition(NumberOfBeds <= this.GetNumberOfBeds(), 
                                     "NumberOfBeds cannot be smaller than the current beds available.");
        super.DecrementNumber(NumberOfBeds);
    }
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Distribute patient
    public void DistributePatient(Patient patient, int BedIndex) {

    }
    
    // Initialize the pool & Several Ultility Functions
    private void CleanPool() { this.GetLocalPool().clear(); }

    private void _InitPool_() { 
        if (this.LocalPool.size() != 0) { this.CleanPool(); }
        for (int i = 0; i < this.GetNumberOfBeds(); i++) {
            this.LocalPool.add(null);
        }
    }

    private void CheckIndexInRange(int index) {
        if (index < 0 || index > this.GetNumberOfBeds()) { 
            throw new IndexOutOfBoundsException("The index must be between 0 and " + (this.GetNumberOfBeds() - 1));
        }
    }



}
