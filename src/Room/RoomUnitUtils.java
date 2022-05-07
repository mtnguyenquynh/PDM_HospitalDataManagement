package Room;

import PrefixState.Prefix;
import org.apache.commons.lang3.StringUtils;


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
 * This class is a subset of the "RoomUnit" class, which provides the static-method
 * (helper) functions to embed the rule for the "RoomUnit" class.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public abstract class RoomUnitUtils {
    private static final Prefix prefix = PrefixState.Prefix.RoomUnit;

    public static Prefix GetPrefix() { return RoomUnitUtils.prefix; }
    public static String GetPrefixCode() { return RoomUnitUtils.GetPrefix().GetPrefixCode(); }
    public static String GetPrefixCodeNotation() { return RoomUnitUtils.GetPrefix().GetPrefixCodeNotation(); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Extracting the information of the room-code
    public static String[] DecomposeRoomCodeID(String RoomCodeID) { return RoomCodeID.split("-"); }
    
    public static String GetRoomType(String RoomCodeID) {return RoomUnitUtils.DecomposeRoomCodeID(RoomCodeID)[1]; }
    
    public static String GetRoomBlock(String RoomCodeID) {return RoomUnitUtils.DecomposeRoomCodeID(RoomCodeID)[2]; }
    
    public static RoomUnitEnum GetRoomTypeEnum(String RoomCodeID) { 
        return RoomUnitEnum.GetEnum(RoomUnitUtils.GetRoomType(RoomCodeID)); 
    }

    public static String GetRoomFloor(String RoomCodeID) {return RoomUnitUtils.DecomposeRoomCodeID(RoomCodeID)[3]; }

    public static String GetRoomNumber(String RoomCodeID) {return RoomUnitUtils.DecomposeRoomCodeID(RoomCodeID)[4]; }
    
    // -----------------------------------------------------------
    // Verifying the room-code
    public static boolean VerifyRoomBlock(String RoomBlock) {
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

    public static boolean VerifyRoomType(String RoomType) {
        // The room-type is strongly dependent on the functionalities of the hospital room. 
        // So the length of the room-type is not limited. However, we can predict and set 
        // the constraint of the symbol's length. In this code, we choose the constraint to 
        // be at most five (5) characters. As long it is a common letter (upper-case or 
        // lower-case is acceptable), it can passed this test.
        if (RoomType.length() > 5) { return false; }
        for (int i = 0; i < RoomType.length(); i++) {
            if (!Character.isLetter(RoomType.charAt(i))) { return false; }
        }
        return !RoomUnitEnum.FindEnum(RoomType);
    }

    public static boolean VerifyRoomFloor(String RoomFloor) {
        if (RoomFloor.length() != 2) { return false; }
        if (!Character.isDigit(RoomFloor.charAt(0))) { return false; }
        if (!Character.isDigit(RoomFloor.charAt(1))) { return false; }
        return true;
    }

    public static boolean VerifyRoomNumber(String RoomNumber) {
        if (RoomNumber.length() != 3) { return false; }
        if (!Character.isDigit(RoomNumber.charAt(0))) { return false; }
        if (!Character.isDigit(RoomNumber.charAt(1))) { return false; }
        if (!Character.isDigit(RoomNumber.charAt(2))) { return false; }
        return true;
    }

    public static boolean VerifyRoomCodeID(String RoomCodeID) {
        // This function is to verify the room-code ID as a single identity.
        // As mentioned in the documentation above, we decompose the room-code ID into
        // multiple parts using delimitor "-" and then verify each part.
        String[] DecomposedRoomCodeID = RoomUnitUtils.DecomposeRoomCodeID(RoomCodeID);
        if (DecomposedRoomCodeID.length != 5) { return false; }
        if (!RoomUnitUtils.GetPrefixCodeNotation().equals(DecomposedRoomCodeID[0])) { return false; }
        if (!RoomUnitUtils.VerifyRoomBlock(RoomUnitUtils.GetRoomBlock(RoomCodeID))) { return false; }
        if (!RoomUnitUtils.VerifyRoomType(RoomUnitUtils.GetRoomType(RoomCodeID))) { return false; }
        if (!RoomUnitUtils.VerifyRoomFloor(RoomUnitUtils.GetRoomFloor(RoomCodeID))) { return false; }
        if (!RoomUnitUtils.VerifyRoomNumber(RoomUnitUtils.GetRoomNumber(RoomCodeID))) { return false; }
        return true;
    }

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

    public static String CastRoomBlock(String RoomBlock) {
        // This method will cast your RoomBlock to have two characters, except if the input is
        // the letter-like character with one character only.
        if (RoomBlock == null) { return null; }
        if (StringUtils.isNumeric((CharSequence) RoomBlock)) { 
            return RoomUnitUtils.CastRoomBlock(Integer.parseInt(RoomBlock)); 
        }

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

    public static String CastRoomBlock(int RoomBlock) {
        if (RoomBlock <= 0 || RoomBlock >= 99) { return null; }
        // You can cast to string, call RoomUnitUtils.CastRoomBlock(String) and then cast back to int.
        // However, it is not necessary and this call is extremely faster.
        return String.format("%02d", RoomBlock); 
    }

    public static String CastRoomType(String RoomType) {
        // This method will cast your RoomType to have at most five characters.
        if (RoomType == null) { return null; }
        if (RoomType.length() > 5) { return null; }
        for (int i = 0; i < RoomType.length(); i++) {
            if (!Character.isLetter(RoomType.charAt(i))) { return null; }
        }
        return RoomType.toUpperCase();
    }

    public static String CastRoomFloor(String RoomFloor) {
        // This method will cast your RoomFloor to have two digit-like characters.
        if (RoomFloor == null) { return null; }
        if (StringUtils.isNumeric((CharSequence) RoomFloor)) { 
            return RoomUnitUtils.CastRoomFloor(Integer.parseInt(RoomFloor)); 
        }
        if (RoomFloor.length() == 0 || RoomFloor.length() > 2) { return null; }
        for (int i = 0; i < RoomFloor.length(); i++) {
            if (!Character.isDigit(RoomFloor.charAt(i))) { return null; }
        }
        return String.format("%02d", Integer.parseInt(RoomFloor));
    }

    public static String CastRoomFloor(int RoomFloor) {
        if (RoomFloor <= 0 || RoomFloor >= 99) { return null; }
        return String.format("%02d", RoomFloor);
    }

    public static String CastRoomNumber(String RoomNumber) {
        // This method will cast your RoomNumber to have three digit-like characters.
        if (RoomNumber == null) { return null; }
        if (StringUtils.isNumeric((CharSequence) RoomNumber)) { 
            return RoomUnitUtils.CastRoomNumber(Integer.parseInt(RoomNumber)); 
        }

        if (RoomNumber.length() == 0 || RoomNumber.length() > 3) { return null; }
        for (int i = 0; i < RoomNumber.length(); i++) {
            if (!Character.isDigit(RoomNumber.charAt(i))) { return null; }
        }
        return String.format("%03d", Integer.parseInt(RoomNumber));
    }

    public static String CastRoomNumber(int RoomNumber) {
        if (RoomNumber <= 0 || RoomNumber >= 999) { return null; }
        return String.format("%03d", RoomNumber);
    }

    public static String ConstructRoomCodeID(String RoomType, String RoomBlock, String RoomFloor, String RoomNumber) {
        // This function is to construct the room-code ID as a single identity.
        // As mentioned in the documentation above, we construct the room-code ID by
        // concatenating the parts of the room-code ID.        
        String RoomBlockStr = RoomUnitUtils.CastRoomBlock(RoomBlock); 
        String RoomTypeStr = RoomUnitUtils.CastRoomType(RoomType);
        String RoomFloorStr = RoomUnitUtils.CastRoomFloor(RoomFloor); 
        String RoomNumberStr = RoomUnitUtils.CastRoomNumber(RoomNumber);

        if (RoomBlockStr == null || RoomTypeStr == null || RoomFloorStr == null || RoomNumberStr == null) { 
            String FullMessage = "";
            if (RoomBlockStr == null) { FullMessage += "Invalid RoomBlock: " + RoomBlock.toString() + "; "; }
            if (RoomTypeStr == null) { FullMessage += "Invalid RoomType: " + RoomType + "; "; }
            if (RoomFloorStr == null) { FullMessage += "Invalid RoomFloor: " + RoomFloor.toString() + "; "; }
            if (RoomNumberStr == null) { FullMessage += "Invalid RoomNumber: " + RoomNumber.toString() + "; "; }
            throw new IllegalArgumentException(FullMessage);
        }
        String[] iterable = {RoomTypeStr, RoomBlockStr, RoomFloorStr, RoomNumberStr};
        return RoomUnitUtils.GetPrefixCode() + StringUtils.join(iterable, "-");
    }
    
    public static String ConstructRoomCodeID(String RoomBlock, String RoomFloor, String RoomNumber) {
        String RoomBlockStr = RoomUnitUtils.CastRoomBlock(RoomBlock); 
        String RoomFloorStr = RoomUnitUtils.CastRoomFloor(RoomFloor); 
        String RoomNumberStr = RoomUnitUtils.CastRoomNumber(RoomNumber);

        if (RoomBlockStr == null || RoomFloorStr == null || RoomNumberStr == null) { 
            String FullMessage = "";
            if (RoomBlockStr == null) { FullMessage += "Invalid RoomBlock: " + RoomBlock.toString() + "; "; }
            if (RoomFloorStr == null) { FullMessage += "Invalid RoomFloor: " + RoomFloor.toString() + "; "; }
            if (RoomNumberStr == null) { FullMessage += "Invalid RoomNumber: " + RoomNumber.toString() + "; "; }
            throw new IllegalArgumentException(FullMessage);
        }
        String[] iterable = {RoomBlockStr, RoomFloorStr, RoomNumberStr};
        return StringUtils.join(iterable, "-");
    }

    public static String ConstructRoomCodeID(String RoomFloor, String RoomNumber) {
        String RoomFloorStr = RoomUnitUtils.CastRoomFloor(RoomFloor); 
        String RoomNumberStr = RoomUnitUtils.CastRoomNumber(RoomNumber);
        if (RoomFloorStr == null || RoomNumberStr == null) { 
            String FullMessage = "";
            if (RoomFloorStr == null) { FullMessage += "Invalid RoomFloor: " + RoomFloor.toString() + "; "; }
            if (RoomNumberStr == null) { FullMessage += "Invalid RoomNumber: " + RoomNumber.toString() + "; "; }
            throw new IllegalArgumentException(FullMessage);
        }
        String[] iterable = {RoomFloorStr, RoomNumberStr};
        return StringUtils.join(iterable, "-");
    }


}
