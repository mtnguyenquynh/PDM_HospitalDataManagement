package Staff;

import Person.Person;

public class Doctor extends Person {
    
    public Doctor(String ID, String name, String description) throws Exception {
        super(ID, name, description);
    }

    public Doctor(String ID, String name) throws Exception {
        super(ID, name, null);
    }
    
}
