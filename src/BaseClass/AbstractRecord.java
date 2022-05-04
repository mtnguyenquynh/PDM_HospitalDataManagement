package BaseClass;

import java.util.Hashtable;

import Utility.DataUtils;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This module is a general wrapper of both MedicoRecord and Treatment class
 * which stored the creation-datetime and its writable state to open/close the record.
 * The vulnerability can be affected if hackers can manipulated the writable state.
 * However, we are not proficient enough to fix this vulnerability. 
 * But a simple way to prevent hacking is that we disable writable state when 
 * the record is closed. 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class AbstractRecord extends CreationDateTime {
    // ---------------------------------------------------------------------------------------------------------------------
    // Extra field
    private boolean writable;                // Writable state of the record
    public AbstractRecord(boolean writable) { this.writable = writable; }
    public AbstractRecord() { this(true); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getters and Setters
    public boolean IsWritable() { return this.writable; }
    public void CloseRecord() { this.writable = false; }
    public void OpenRecord(boolean force) { 
        if (!this.IsWritable() && !force) { return ; }
        this.writable = true; 
    }
    public void OpenRecord() { this.OpenRecord(false); }
    public void OpenRecordAPI() { this.OpenRecord(false); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> data = super.Serialize();
        data.put("writable", this.IsWritable());
        return data;
    }

    public static AbstractRecord Deserialize(Hashtable<String, Object> data) {
        AbstractRecord record = new AbstractRecord((boolean) data.get("writable"));
        record.SetDate((String) data.get("date"));
        record.SetTime((String) data.get("time"));
        return record;
    }

}
