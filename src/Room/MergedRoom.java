package Room;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;

import Utility.DataUtils;
import Utility.JsonUtils;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

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
 * "PatientRoom", and "MedicoRoom". See the description in each components. 
 * 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
 * 2) https://stackoverflow.com/questions/28947250/create-a-directory-if-it-does-not-exist-and-then-create-the-files-in-that-direct
**/

public class MergedRoom {
    private static final String ROOM_DIRECTORY = "database/Room";
    private static final String ROOM_CODE_FILE = "RoomCode.json";

    private RoomUnit Room = null;
    private PatientRoom PtRoom = null;
    private MedicoRoom MedRoom = null;
    private LToolPool LTPoolRoom = null;
    private LResourcePool LRPoolRoom = null;


    public MergedRoom() {  }

    // ---------------------------------------------------------------------------------------------------------------------
    // Load-er && Build-er functions
    private void LoadComponentRoom() {
        // This method is to load (or build) a component room.
        // Step 01: Load constant value
        String ID = this.GetRoom().GetID();
        String working_directory = this.GetWorkingDirectory();
        String[] COMPONENT_FILENAME = MergedRoom.GetSavedName();

        // Step 02: Try to find the component file with ".json" extension
        // If failed, then create a new file with ".json" extension and load back 

        for (int i = 0; i < COMPONENT_FILENAME.length; i++) {
            try {
                Path path = Files.createTempFile(working_directory + "/" + COMPONENT_FILENAME[i], ".json");
                if (Files.exists(path)) {
                    JSONObject obj = JsonUtils.ReadJsonFileAsObject(path.toString());
                    Hashtable<String, Object> data = JsonUtils.CastJsonToHashtable(obj, null);
                    this.MakeComponentRoom(i, data);
                } else { this.MakeComponentRoom(i, ID); }
            } catch (Exception e) { e.printStackTrace(); }
        }
        return ;
    }

    public RoomUnit LoadOneRoom(String RoomType, String RoomBlock, String RoomFloor, String RoomNumber) throws Exception {
        String RoomCode = RoomUnit.ConstructRoomCodeID(RoomBlock, RoomType, RoomFloor, RoomNumber);
        String Temp1RoomCode = RoomUnitUtils.ConstructRoomCodeID(RoomBlock, RoomFloor, RoomNumber);
        String Temp2RoomCode = RoomUnitUtils.ConstructRoomCodeID(RoomFloor, RoomNumber);
        
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(MergedRoom.GetRoomCodeFile());

        String[] Argument = MergedRoom.GetSavedName();

        for (Object obj: array) {
            JSONObject jsonObject = (JSONObject) obj;
            String JsonRoomCode = (String) jsonObject.get(Argument[0]);
            if (JsonRoomCode.equals(RoomCode) || JsonRoomCode.contains(Temp1RoomCode) || 
            JsonRoomCode.contains(Temp2RoomCode)) {
                String RoomName = (String) jsonObject.get(Argument[1]);
                String RoomDescription = (String) jsonObject.get(Argument[2]);

                return this.BuildOneRoom(RoomBlock, RoomType, RoomFloor, RoomNumber, RoomName, RoomDescription);
            }
        }
        return null;
    }

    public RoomUnit LoadOneRoom(String RoomBlock, String RoomFloor, String RoomNumber) throws Exception {
        return this.LoadOneRoom("", RoomBlock, RoomFloor, RoomNumber);
    }

    public RoomUnit LoadOneRoom(String RoomFloor, String RoomNumber) throws Exception {
        return this.LoadOneRoom("", "", RoomFloor, RoomNumber);
    }

    public RoomUnit BuildOneRoom(String RoomBlock, String RoomType, String RoomFloor, String RoomNumber, 
                                 String RoomName, String RoomDescription) throws Exception {
        String RoomCode = RoomUnit.ConstructRoomCodeID(RoomBlock, RoomType, RoomFloor, RoomNumber);
        if (RoomName == null) { RoomName = "";}
        if (RoomDescription == null) { RoomDescription = "";}

        try { this.Room = new RoomUnit(RoomCode, RoomName, RoomDescription); } 
        catch (Exception e) { e.printStackTrace(); }
        this.LoadComponentRoom();
        return this.Room;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Ultility functions



    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public static String GetRoomDirectory() { return MergedRoom.ROOM_DIRECTORY; }
    public static String GetRoomCodeFilename() { return MergedRoom.ROOM_CODE_FILE; }
    public static String GetRoomCodeFile() { return MergedRoom.GetRoomDirectory() + "/" + MergedRoom.GetRoomCodeFilename(); }    
    public static String[] GetSavedName() { 
        return new String[] {"RoomCode", "name", "description"} ; 
    }
    
    public static String[] GetComponentName() { 
        return new String[] {"PatientRoom", "MedicoRoom", "LToolPool", "LResourcePool"}; 
    }

    public String GetWorkingDirectory() { 
        DataUtils.CheckArgumentCondition(this.GetRoom().GetID() != null, "The room cannot be loaded.");
        return MergedRoom.GetRoomDirectory() + "/" + this.GetRoom().GetID(); 
    }

    public String GetComponentDirectory(int index) {
        return this.GetWorkingDirectory() + "/" + MergedRoom.GetComponentName()[index];
    }

    public RoomUnit GetRoom() { return this.Room; }
    public void SetRoomName(String RoomName) throws Exception { this.GetRoom().SetName(RoomName); }
    public void SetRoomDescription(String RoomDescription) { this.GetRoom().SetDescription(RoomDescription); }


    public PatientRoom GetPtRoom() { return this.PtRoom; }
    public MedicoRoom GetMedRoom() { return this.MedRoom; }
    public LToolPool GetLTPoolRoom() { return this.LTPoolRoom; }
    public LResourcePool GetLRPoolRoom() { return this.LRPoolRoom; }

    // -----------------------------------------------------------
    // Setter
    private void MakePtRoom(String ID) throws Exception { this.PtRoom = new PatientRoom(ID); }
    private void MakeMedRoom(String ID) throws Exception { this.MedRoom = new MedicoRoom(ID); }
    private void MakeLTPoolRoom(String ID) throws Exception { this.LTPoolRoom = new LToolPool(ID); }
    private void MakeLRPoolRoom(String ID) throws Exception { this.LRPoolRoom = new LResourcePool(ID); }

    private void MakePtRoom(Hashtable<String, Object> data) throws Exception { this.PtRoom = PatientRoom.Deserialize(data); }
    private void MakeMedRoom(Hashtable<String, Object> data) throws Exception { this.MedRoom = MedicoRoom.Deserialize(data); }
    private void MakeLTPoolRoom(Hashtable<String, Object> data) throws Exception { this.LTPoolRoom = LToolPool.Deserialize(data); }
    private void MakeLRPoolRoom(Hashtable<String, Object> data) throws Exception { this.LRPoolRoom = LResourcePool.Deserialize(data); }

    private void MakeComponentRoom(int index, Hashtable<String, Object> data) throws Exception {
        switch(index) {
            case 0: this.MakePtRoom(data); break;
            case 1: this.MakeMedRoom(data); break;
            case 2: this.MakeLTPoolRoom(data); break;
            case 3: this.MakeLRPoolRoom(data); break;
            default: throw new Exception("The index is out of range.");
        }
    }

    private void MakeComponentRoom(int index, String ID) throws Exception {
        switch(index) {
            case 0: this.MakePtRoom(ID); break;
            case 1: this.MakeMedRoom(ID); break;
            case 2: this.MakeLTPoolRoom(ID); break;
            case 3: this.MakeLRPoolRoom(ID); break;
            default: throw new Exception("The index is out of range.");
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------
    public void Reset() throws Exception {
        // Serialize all component room -> Set null -> Run garbage collector
        JsonUtils.SaveHashTableIntoJsonFile(this.GetComponentDirectory(0), 
                                        this.GetPtRoom().Serialize(), null);
        JsonUtils.SaveHashTableIntoJsonFile(this.GetComponentDirectory(1), 
                                        this.GetMedRoom().Serialize(), null);
        JsonUtils.SaveHashTableIntoJsonFile(this.GetComponentDirectory(2), 
                                        this.GetLTPoolRoom().Serialize(), null);
        JsonUtils.SaveHashTableIntoJsonFile(this.GetComponentDirectory(3), 
                                        this.GetLRPoolRoom().Serialize(), null);
        
        this.Room = null;
        this.PtRoom = null;
        this.MedRoom = null;
        this.LTPoolRoom = null;
        this.LRPoolRoom = null;
        System.gc();
    }
}
