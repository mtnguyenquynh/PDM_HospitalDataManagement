package Room;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map.Entry;

import Utility.DataUtils;
import Utility.Utils;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
 * Task 01: Finding a room:
 * 
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
    private static final String ROOM_CODE_FILE = "database/Room/RoomCode.json";
    private static final String[] SAVED_NAME = {"RoomCode", "name", "description"};
    private static final String[] COMPONENT_NAME = {"PatientRoom", "MedicoRoom", "LToolPool", "LResourcePool"};

    private RoomUnit Room = null;
    private PatientRoom PtRoom = null;
    private MedicoRoom MedRoom = null;
    private LToolPool LTPoolRoom = null;
    private LResourcePool LRPoolRoom = null;


    public MergedRoom() {  }

    // ---------------------------------------------------------------------------------------------------------------------
    // Load-er functions
    public RoomUnit LoadOneRoom(String RoomType, String RoomBlock, String RoomFloor, String RoomNumber) throws ParseException {
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
                String JsonRoomName = (String) jsonObject.get(Argument[1]);
                String JsonRoomDescription = (String) jsonObject.get(Argument[2]);

                this.Room = new RoomUnit(RoomCode, JsonRoomName, JsonRoomDescription);
                this.LoadAllComponents();
                return this.Room;
            }
        }
        return null;
    }

    public RoomUnit LoadOneRoom(String RoomBlock, String RoomFloor, String RoomNumber) throws ParseException {
        return this.LoadOneRoom("", RoomBlock, RoomFloor, RoomNumber);
    }

    public RoomUnit LoadOneRoom(String RoomFloor, String RoomNumber) throws ParseException {
        return this.LoadOneRoom("", "", RoomFloor, RoomNumber);
    }
    
    private void LoadAllComponents() {
        Utils.CheckArgumentCondition(this.GetRoom() != null, "The room is not loaded.");
        
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Build-er functions

    public RoomUnit BuildOneRoom(String RoomBlock, String RoomType, String RoomFloor, String RoomNumber, 
                                 String RoomName, String RoomDescription) throws Exception {
        String RoomCode = RoomUnit.ConstructRoomCodeID(RoomBlock, RoomType, RoomFloor, RoomNumber);
        if (RoomName == null) { RoomName = "";}
        this.Room = new RoomUnit(RoomCode, RoomName, RoomDescription);

        return this.Room;
    }

    /**
     * This function is to create a folder for the room at the database/Room directory.
     * The folder-name is the same as the room-code.
     * In each folder, there are four different files named: "PatientRoom", "MedicoRoom", 
     * "LToolPool", and "LResourcePool" and to deserialize the JSON file.
     * @throws IOException
     */
    private void BuildComponents() throws IOException {
        String dir = MergedRoom.GetRoomDirectory();
        String room_code = this.GetRoom().GetID();


        Files.createDirectories(Paths.get(dir + "/" + room_code));
        this.PtRoom = new PatientRoom(room_code, 3);

        Files.createFile(Paths.get("database/Room/" + this.GetRoom().GetID() + "/" + this.GetRoom().GetID() + "_PtRoom.json"));
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Ultility functions

    private void LoadPatientRoom() {
        // Build the PatientRoom
        String room_code = this.GetRoom().GetID();
        String working_directory = this.GetWorkingDirectory();
        String component_filename = MergedRoom.GetSavedName()[0];
        

        // Create the file
        try {
            Path path = Files.createTempFile(working_directory + "/" + component_filename, ".json");
            if (Files.exists(path)) {
                // Read that JSON file
                JSONParser parser = new JSONParser();
                JSONArray array = (JSONArray) parser.parse(path.toString());
                Hashtable<String, Object> data = DataUtils.ForceGetEmptyHashtable(PatientRoom.class);
                
                Object obj = array.get(0);
                JSONObject jsonObject = (JSONObject) obj;
                this.PtRoom = PatientRoom.Deserialize(data);
                

            } else {
                // Create the file
                this.PtRoom = new PatientRoom(room_code, 3);
            }


        }

    }



    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public static String GetRoomDirectory() { return MergedRoom.ROOM_DIRECTORY; }
    public static String GetRoomCodeFilename() { return MergedRoom.ROOM_CODE_FILE; }
    public static String GetRoomCodeFile() { return MergedRoom.GetRoomDirectory() + "/" + MergedRoom.GetRoomCodeFilename(); }
    public static String[] GetSavedName() { return MergedRoom.SAVED_NAME; }
    public String GetWorkingDirectory() { 
        Utils.CheckArgumentCondition(this.GetRoom().GetID() != null, "The room cannot be loaded.");
        return MergedRoom.GetRoomDirectory() + "/" + this.GetRoom().GetID(); 
    }

    public RoomUnit GetRoom() { return this.Room; }
    public void SetRoomName(String RoomName) throws Exception { this.GetRoom().SetName(RoomName); }
    public void SetRoomDescription(String RoomDescription) { this.GetRoom().SetDescription(RoomDescription); }


    public PatientRoom GetPtRoom() { return this.PtRoom; }
    public MedicoRoom GetMedRoom() { return this.MedRoom; }
    public LToolPool GetLTPoolRoom() { return this.LTPoolRoom; }
    public LResourcePool GetLRPoolRoom() { return this.LRPoolRoom; }

}
