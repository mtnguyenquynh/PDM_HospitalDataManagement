package Room;

import BaseClass.IntermediateObject;
import java.util.Hashtable;
import PrefixState.Prefix;


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
 * This class simulate what information does a common "room" have in the hospital.
 * For example, the room-code, its (simple) functionality description, its location
 * in the hospital (block A or B, etc). Ok, the important question how to design 
 * an optimal room code ? To mostly cover the meaning of room-code, we visualize 
 * it as this: <prefix>-<room-block>-<room-type>-<room-floor>-<room-number>.
 * 
 * Among that:
 * <prefix>: (String-like) is a prefix, denoted to be "RU" for "Room Unit".
 *           (see in the Prefix.java).
 * <room-block>: Depends on the real-world hospital, but usually an upper-case
 *               character, such as "A" or "B". If the room-block is not character 
 *               (a number), then use two digit-like character to specified the 
 *               room-block such as such as "01" or "02". Note that this is not 
 *               a special character such as "#" or "@".
 * <room-type>: This described the functionalities of the room. For example,
 *              "ICU" for "Intensive Care Unit" or "ER" for "Emergency Room".
 * <room-floor>: This is the floor of the room using the ideology of the US as 
 *               our fake data comes the US-like person. This implies that the 
 *               ground-floor is the first floor (1st). Similarly, we use two 
 *               digit-like characters to specified the floor number.
 * <room-number>: This is the room number in the room-block. As we want the hospital 
 *                can be expandable, we only used three digit-like characters.
 * Note that for all above conditions, if the input is a digit-like, its value 
 * cannot be smaller or equal than zero (0).
 * 
 * Ok, now how to obtain, specify, and construct properly the room-code without
 * vulnerability?
 * To obtain highly guaranteed, we first spilt the code ID into multiple parts using 
 * delimitor "-". And then based on the following definition, we can extract the 
 * wanted string from the code ID.
 * 
 * For example, the code ID is "RU-A-ICU-01-001". Then we can extract the following
 * information from the code ID: 
 * 1) The prefix: "RU"
 * 2) The room-block: "A"
 * 3) The room-type: "ICU"
 * 4) The room-floor: "01"
 * 5) The room-number: "001"        
 * 
 * For developer, we guarantee that the code ID from extraction is always a string.
 * But it flexible the input of the code ID during construction, so if you want to 
 * compute something out of the code ID, you must program by yourself. 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class RoomUnit extends IntermediateObject {
    private static final Prefix prefix = PrefixState.Prefix.RoomUnit;

    public RoomUnit(String ID, String name, String description) {
        super(ID, name, description);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetRoomCodeID() { return this.GetID(); }
    public static Prefix GetPrefix() { return RoomUnit.prefix; }
    public static String GetPrefixCode() { return RoomUnit.GetPrefix().GetPrefixCode(); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Extracting the information of the room-code
    public static String[] DecomposeRoomCodeID(String RoomCodeID) { return RoomCodeID.split("-"); }

    public static String GetRoomBlock(String RoomCodeID) {return RoomUnit.DecomposeRoomCodeID(RoomCodeID)[1]; }
    public String GetRoomBlock() { return RoomUnit.GetRoomBlock(this.GetID()); }

    public static String GetRoomType(String RoomCodeID) {return RoomUnit.DecomposeRoomCodeID(RoomCodeID)[2]; }
    public String GetRoomType() { return RoomUnit.GetRoomType(this.GetID()); }

    public static String GetRoomFloor(String RoomCodeID) {return RoomUnit.DecomposeRoomCodeID(RoomCodeID)[3]; }
    public String GetRoomFloor() { return RoomUnit.GetRoomFloor(this.GetID()); }

    public static String GetRoomNumber(String RoomCodeID) {return RoomUnit.DecomposeRoomCodeID(RoomCodeID)[4]; }
    public String GetRoomNumber() { return RoomUnit.GetRoomNumber(this.GetID()); }

    // -----------------------------------------------------------
    // Verifying the room-code
    private static boolean VerifyRoomBlock(String RoomBlock) {
        // The <room-block> can have at most two characters either numeric or alphabetical.
        // If the <room-block> is a number, then it must be a two digit-like character.
        // If the <room-block> is a character, then it must have one or two characters.
        if (RoomBlock.length() > 2 || RoomBlock.length() == 0) { return false; }
        
        if (RoomBlock.length() == 1) {
            char c = RoomBlock.charAt(0);
            return Character.isLetter(c) && Character.isUpperCase(c);
        }
        
        char c1 = RoomBlock.charAt(0);
        char c2 = RoomBlock.charAt(1);
        if (Character.isDigit(c1)) { return Character.isDigit(c2); }
        if (Character.isLetter(c1) && Character.isUpperCase(c1)) { 
            return Character.isLetter(c2) && Character.isUpperCase(c2); 
        }
        return false;
    }

    private static boolean VerifyRoomType(String RoomType) {
        if (RoomType.length() < 1) { return false; }
        return true;
    }

    private static boolean VerifyRoomFloor(String RoomFloor) {
        if (RoomFloor.length() != 2) { return false; }
        if (RoomFloor.charAt(0) < '0' || RoomFloor.charAt(0) > '9') { return false; }
        if (RoomFloor.charAt(1) < '0' || RoomFloor.charAt(1) > '9') { return false; }
        return true;
    }

    private static boolean VerifyRoomNumber(String RoomNumber) {
        if (RoomNumber.length() != 3) { return false; }
        if (RoomNumber.charAt(0) < '0' || RoomNumber.charAt(0) > '9') { return false; }
        if (RoomNumber.charAt(1) < '0' || RoomNumber.charAt(1) > '9') { return false; }
        if (RoomNumber.charAt(2) < '0' || RoomNumber.charAt(2) > '9') { return false; }
        return true;
    }



    private static boolean VerifyRoomCodeID(String RoomCodeID) {
        String[] DecomposedRoomCodeID = RoomUnit.DecomposeRoomCodeID(RoomCodeID);
        if (DecomposedRoomCodeID.length != 5) { return false; }
        if (!RoomUnit.GetPrefixCode().equals(DecomposedRoomCodeID[0])) { return false; }
        if (!RoomUnit.VerifyRoomBlock(RoomUnit.GetRoomBlock(RoomCodeID))) { return false; }
        if (!RoomUnit.VerifyRoomType(RoomUnit.GetRoomType(RoomCodeID))) { return false; }
        if (!RoomUnit.VerifyRoomFloor(RoomUnit.GetRoomFloor(RoomCodeID))) { return false; }
        if (!RoomUnit.VerifyRoomNumber(RoomUnit.GetRoomNumber(RoomCodeID))) { return false; }
        return true;
    }


    // ---------------------------------------------------------------------------------------------------------------------


    public static String GenerateRoomCode(String roomBlock, String roomType, String roomFloor, String roomNumber) {
        return prefix.GetPrefix() + "-" + roomBlock + "-" + roomType + "-" + roomFloor + "-" + roomNumber;
    }

    public static String GenerateRoomCode(Integer roomBlock, String roomType, String roomFloor, int roomNumber) {
        if (roomBlock < 10) { 
            String RoomBlock = (String) "0" + roomBlock.toString(); 
        }
        else { 
            String RoomBlock = (String) roomBlock.toString(); 
        }
        return prefix.GetPrefix() + "-" + RoomBlock + "-" + roomType + "-" + roomFloor + "-" + roomNumber;
    }






    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("description", this.GetDescription());
        return result;
    }

    public static RoomUnit Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        return new RoomUnit(ID, name, description);
    }
}
