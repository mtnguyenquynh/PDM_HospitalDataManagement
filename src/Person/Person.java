package Person;
public class Person {
    private String id, name, email, phone_number, gender, nationality;
    public Person(String id, String name, String email, String phone_number, 
    String gender, String nationality){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.nationality = nationality;
    }
    public String getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phone_number;
    }
    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getGender() {
        return gender;
    }
    public String getNationality() {
        return nationality;
    }
}