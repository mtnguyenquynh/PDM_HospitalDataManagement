package Room;

import BaseClass.IntermediateObject;
import java.util.Hashtable;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
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
    public static String GetPrefixCodeTerm() { return RoomUnit.GetPrefix().GetPrefixCode().replace("-", ""); }

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
        // The room-type is strongly dependent on the functionalities of the hospital room. 
        // So the length of the room-type is not limited. However, we can predict and set 
        // the constraint of the symbol's length. In this code, we choose the constraint to 
        // be at most five (5) characters. As long it is a common letter (upper-case or 
        // lower-case is acceptable), it can passed this test.
        if (RoomType.length() > 5) { return false; }
        for (int i = 0; i < RoomType.length(); i++) {
            if (!Character.isLetter(RoomType.charAt(i))) { return false; }
        }
        return true;
    }

    private static boolean VerifyRoomFloor(String RoomFloor) {
        if (RoomFloor.length() != 2) { return false; }
        if (!Character.isDigit(RoomFloor.charAt(0))) { return false; }
        if (!Character.isDigit(RoomFloor.charAt(1))) { return false; }
        return true;
    }

    private static boolean VerifyRoomNumber(String RoomNumber) {
        if (RoomNumber.length() != 3) { return false; }
        if (!Character.isDigit(RoomNumber.charAt(0))) { return false; }
        if (!Character.isDigit(RoomNumber.charAt(1))) { return false; }
        if (!Character.isDigit(RoomNumber.charAt(2))) { return false; }
        return true;
    }

    private static boolean VerifyRoomCodeID(String RoomCodeID) {
        // This function is to verify the room-code ID as a single identity.
        // As mentioned in the documentation above, we decompose the room-code ID into
        // multiple parts using delimitor "-" and then verify each part.
        String[] DecomposedRoomCodeID = RoomUnit.DecomposeRoomCodeID(RoomCodeID);
        if (DecomposedRoomCodeID.length != 5) { return false; }
        if (!RoomUnit.GetPrefixCodeTerm().equals(DecomposedRoomCodeID[0])) { return false; }
        if (!RoomUnit.VerifyRoomBlock(RoomUnit.GetRoomBlock(RoomCodeID))) { return false; }
        if (!RoomUnit.VerifyRoomType(RoomUnit.GetRoomType(RoomCodeID))) { return false; }
        if (!RoomUnit.VerifyRoomFloor(RoomUnit.GetRoomFloor(RoomCodeID))) { return false; }
        if (!RoomUnit.VerifyRoomNumber(RoomUnit.GetRoomNumber(RoomCodeID))) { return false; }
        return true;
    }
    
    public boolean VerifyRoomCodeID() { return RoomUnit.VerifyRoomCodeID(this.GetID()); }

    // -----------------------------------------------------------
    // Constructing or Generating the Room-Code
    // Note that the most complex part is to guaranteed that each part of the room-code ID
    // must be canonicalized or standardized. For example, the room-block must be in upper-case.
    // Note that this class is the low-level so guarantee the uniqueness amongst the room-code
    // ID is not necessary, as this job is for the RoomManager.java.
    // Note that if each part of the room-code ID is not satisfied the easiest condition, then 
    // it is invalid, which returns the null or raise IllegalArgumentException.
    // Since the RoomBlock, RoomFloor, and RoomNumber can be an integer, we have to program another 
    // method with different signature. With this integer method, you can cast back to String, 
    // call the respective method; but it is not necessary and this call is extremely faster.

    private static String CastRoomBlock(String RoomBlock) {
        // This method will cast your RoomBlock to have two characters, except if the input is
        // the letter-like character with one character only.
        if (RoomBlock == null) { return null; }
        if (RoomBlock.length() > 2 || RoomBlock.length() == 0) { return null; }
        if (RoomBlock.length() == 1) {
            char c = RoomBlock.charAt(0);
            
            // String.format("%02d", Integer.parseInt(Character.toString(c)));
            if (Character.isDigit(c)) { return "0" + String.valueOf(c); } 
            if (Character.isLetter(c)) { return RoomBlock.toUpperCase(); }
            return null;
        }

        if (RoomBlock.length() == 2) {
            char c1 = RoomBlock.charAt(0);
            char c2 = RoomBlock.charAt(1);

            if (Character.isDigit(c1) && Character.isDigit(c2)) { return RoomBlock; }
            if (Character.isLetter(c1) && Character.isLetter(c2)) { return RoomBlock.toLowerCase(); }
            return null;
        }
        return null;

    }

    private static String CastRoomBlock(int RoomBlock) {
        if (RoomBlock <= 0 || RoomBlock >= 99) { return null; }
        // You can cast to string, call RoomUnit.CastRoomBlock(String) and then cast back to int.
        // However, it is not necessary and this call is extremely faster.
        return String.format("%02d", RoomBlock); 
    }

    private static String CastRoomType(String RoomType) {
        // This method will cast your RoomType to have at most five characters.
        if (RoomType == null) { return null; }
        if (RoomType.length() > 5) { return null; }
        for (int i = 0; i < RoomType.length(); i++) {
            if (!Character.isLetter(RoomType.charAt(i))) { return null; }
        }
        return RoomType.toUpperCase();
    }

    private static String CastRoomFloor(String RoomFloor) {
        // This method will cast your RoomFloor to have two digit-like characters.
        if (RoomFloor == null) { return null; }
        if (RoomFloor.length() == 0 || RoomFloor.length() > 2) { return null; }
        for (int i = 0; i < RoomFloor.length(); i++) {
            if (!Character.isDigit(RoomFloor.charAt(i))) { return null; }
        }
        return String.format("%02d", Integer.parseInt(RoomFloor));
    }

    private static String CastRoomFloor(int RoomFloor) {
        if (RoomFloor <= 0 || RoomFloor >= 99) { return null; }
        return String.format("%02d", RoomFloor);
    }

    private static String CastRoomNumber(String RoomNumber) {
        // This method will cast your RoomNumber to have three digit-like characters.
        if (RoomNumber == null) { return null; }
        if (RoomNumber.length() == 0 || RoomNumber.length() > 3) { return null; }
        for (int i = 0; i < RoomNumber.length(); i++) {
            if (!Character.isDigit(RoomNumber.charAt(i))) { return null; }
        }
        return String.format("%03d", Integer.parseInt(RoomNumber));
    }

    private static String CastRoomNumber(int RoomNumber) {
        if (RoomNumber <= 0 || RoomNumber >= 999) { return null; }
        return String.format("%03d", RoomNumber);
    }

    private static boolean _IsNumeric_(String string) { return StringUtils.isNumeric((CharSequence) string); }



    public static String ConstructRoomCodeID(String RoomBlock, String RoomType, String RoomFloor, String RoomNumber) {
        // This function is to construct the room-code ID as a single identity.
        // As mentioned in the documentation above, we construct the room-code ID by
        // concatenating the parts of the room-code ID.
        String RoomBlockStr, RoomTypeStr, RoomFloorStr, RoomNumberStr;
         
        if (StringUtils.isNumeric((CharSequence) RoomBlock)) { 
            RoomBlockStr = RoomUnit.CastRoomBlock(Integer.parseInt(RoomBlock)); 
        } else { RoomBlockStr = RoomUnit.CastRoomBlock(RoomBlock); }

        RoomTypeStr = RoomUnit.CastRoomType(RoomType);

        if (StringUtils.isNumeric((CharSequence) RoomFloor)) { 
            RoomFloorStr = RoomUnit.CastRoomFloor(Integer.parseInt(RoomFloor)); 
        } else { RoomFloorStr = RoomUnit.CastRoomFloor(RoomFloor); }

        if (StringUtils.isNumeric((CharSequence) RoomNumber)) { 
            RoomNumberStr = RoomUnit.CastRoomNumber(Integer.parseInt(RoomNumber)); 
        } else { RoomNumberStr = RoomUnit.CastRoomNumber(RoomNumber); }

        if (RoomBlockStr == null || RoomTypeStr == null || RoomFloorStr == null || RoomNumberStr == null) { 
            String FullMessage = "";
            if (RoomBlockStr == null) { FullMessage += "Invalid RoomBlock: " + RoomBlock.toString() + "; "; }
            if (RoomTypeStr == null) { FullMessage += "Invalid RoomType: " + RoomType + "; "; }
            if (RoomFloorStr == null) { FullMessage += "Invalid RoomFloor: " + RoomFloor.toString() + "; "; }
            if (RoomNumberStr == null) { FullMessage += "Invalid RoomNumber: " + RoomNumber.toString() + "; "; }
            throw new IllegalArgumentException(FullMessage);
        }
        String[] iterable = {RoomUnit.GetPrefixCodeTerm(), RoomBlockStr, RoomTypeStr, RoomFloorStr, RoomNumberStr};
        return StringUtils.join(iterable, "-");
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
