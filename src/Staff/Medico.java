package Staff;

import Person.Person;

public class Medico extends Person {
    
    public Medico(String ID, String name, String description) throws Exception {
        super(ID, name, description);
    }

    public Medico(String ID, String name) throws Exception {
        super(ID, name, null);
    }
    
}
