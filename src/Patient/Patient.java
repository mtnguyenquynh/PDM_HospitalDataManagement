package Patient;
import Person.Person;
public class Patient extends Person {
    public Patient(String id, String name, String email, String phone_number, String gender, String nationality)
        throws Exception {
        super(id, name, email, phone_number, gender, nationality);
    }
    
}
