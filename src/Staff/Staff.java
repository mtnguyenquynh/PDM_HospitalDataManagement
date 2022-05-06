package Staff;

import Person.Person;

public class Staff extends Person {
    
    public Staff(String ID, String name, String description) throws Exception {
        super(ID, name, description);
    }

    public Staff(String ID, String name) throws Exception { super(ID, name); }
    
}
