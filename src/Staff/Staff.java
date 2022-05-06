package Staff;

import Person.Person;
import PrefixState.Prefix;

public class Staff extends Person {
    
    public Staff(String ID, String name, String description) throws Exception {
        super(ID, name, description);
        this.prefix = Prefix.Staff;
    }

    public Staff(String ID, String name) throws Exception { this(ID, name, null); }


    // ----------------------------------------------------------
    public static Prefix GetPrefix() { return Prefix.Staff; }
    public static String GetPrefixCode() { return Staff.GetPrefix().GetPrefixCode(); }
    
}
