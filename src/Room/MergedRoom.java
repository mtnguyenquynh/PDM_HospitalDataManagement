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
 * Ok so we have complete our task in five components: RoomUnit, PatientRoom, MedicoRoom,
 * LToolPool, LResourcePool. Now, this class allow the module to bind the relationship between
 * them. Note that we are dealing with one room only and once declare, you cannot change the 
 * room
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
 * 2) https://stackoverflow.com/questions/28947250/create-a-directory-if-it-does-not-exist-and-then-create-the-files-in-that-direct
**/

public class RoomManager {
    private static final String ROOM_DIRECTORY = "database/Room";
    private static final String ROOM_CODE_FILE = "database/Room/RoomCode.json";
    private static final String[] SAVED_NAME = {"RoomCode", "name", "description"};
    private static final String[] COMPONENT_NAME = {"PatientRoom", "MedicoRoom", "LToolPool", "LResourcePool"};

    private RoomUnit Room = null;
    private PatientRoom PtRoom = null;
    private MedicoRoom MedRoom = null;
    private LToolPool LTPoolRoom = null;
    private LResourcePool LRPoolRoom = null;


    public RoomManager() {  }

    // ---------------------------------------------------------------------------------------------------------------------
    // Load-er functions
    public RoomUnit LoadOneRoom(String RoomBlock, String RoomType, String RoomFloor, String RoomNumber) throws ParseException {
        String RoomCode = RoomUnit.ConstructRoomCodeID(RoomBlock, RoomType, RoomFloor, RoomNumber);
        String TempRoomCode = RoomUnitUtils.ConstructRoomCodeID(RoomFloor, RoomNumber);
        
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(RoomManager.GetRoomCodeFile());

        String[] Argument = RoomManager.GetSavedName();

        for (Object obj: array) {
            JSONObject jsonObject = (JSONObject) obj;
            String JsonRoomCode = (String) jsonObject.get(Argument[0]);
            if (JsonRoomCode.equals(RoomCode) || JsonRoomCode.contains(TempRoomCode)) {
                String JsonRoomName = (String) jsonObject.get(Argument[1]);
                String JsonRoomDescription = (String) jsonObject.get(Argument[2]);

                this.Room = new RoomUnit(RoomCode, JsonRoomName, JsonRoomDescription);
                this.LoadAllComponents();
                return this.Room;
            }
        }
        return null;
    }

    public RoomUnit LoadOneRoom(String RoomFloor, String RoomNumber) throws ParseException {
        return this.LoadOneRoom(null, null, RoomFloor, RoomNumber);
    }

    
    private void LoadAllComponents() {
        Utils.CheckArgumentCondition(this.GetRoom() != null, "The room is not loaded.");
        // Use the os to load the serialized file inside the directory
        // this.PtRoom = (PatientRoom) Utils.LoadSerializedObject(this.GetRoom().GetRoomCode() + "_PtRoom.ser");
        // this.MedRoom = (MedicoRoom) Utils.LoadSerializedObject(this.GetRoom().GetRoomCode() + "_MedRoom.ser");

        // Use the json file to load the serialized file inside the directory
        this.PtRoom = (PatientRoom) Utils.LoadSerializedObject(this.GetRoom().GetRoomCode() + "_PtRoom.json");
        this.MedRoom = (MedicoRoom) Utils.LoadSerializedObject(this.GetRoom().GetRoomCode() + "_MedRoom.json");
        this.LTPoolRoom = (LToolPool) Utils.LoadSerializedObject(this.GetRoom().GetRoomCode() + "_LTPoolRoom.json");


        this.PtRoom = new PatientRoom(this.Room);
        this.MedRoom = new MedicoRoom(this.Room);
        this.LTPoolRoom = new LToolPool(this.Room);
        this.LRPoolRoom = new LResourcePool(this.Room);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Build-er functions

    public RoomUnit BuildOneRoom(String RoomBlock, String RoomType, String RoomFloor, String RoomNumber, 
                                 String RoomName, String RoomDescription) throws ParseException {
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
        String dir = RoomManager.GetRoomDirectory();
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
        String component_filename = RoomManager.GetSavedName()[0];
        

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
                jsonObject.keySet().forEach(key -> { data.put(key, jsonObject.get(key)); });
                this.PtRoom = PatientRoom.Deserialize(data);
                

            } else {
                // Create the file
                this.PtRoom = new PatientRoom(room_code, 3);
            }


        }

    }



    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public static String GetRoomDirectory() { return RoomManager.ROOM_DIRECTORY; }
    public static String GetRoomCodeFilename() { return RoomManager.ROOM_CODE_FILE; }
    public static String GetRoomCodeFile() { return RoomManager.GetRoomDirectory() + "/" + RoomManager.GetRoomCodeFilename(); }
    public static String[] GetSavedName() { return RoomManager.SAVED_NAME; }
    public String GetWorkingDirectory() { 
        Utils.CheckArgumentCondition(this.GetRoom().GetID() != null, "The room cannot be loaded.");
        return RoomManager.GetRoomDirectory() + "/" + this.GetRoom().GetID(); 
    }

    public RoomUnit GetRoom() { return this.Room; }
    public void SetRoomName(String RoomName) throws Exception { this.GetRoom().SetName(RoomName); }
    public void SetRoomDescription(String RoomDescription) { this.GetRoom().SetDescription(RoomDescription); }


    public PatientRoom GetPtRoom() { return this.PtRoom; }
    public MedicoRoom GetMedRoom() { return this.MedRoom; }
    public LToolPool GetLTPoolRoom() { return this.LTPoolRoom; }
    public LResourcePool GetLRPoolRoom() { return this.LRPoolRoom; }

}
