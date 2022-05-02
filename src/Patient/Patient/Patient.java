package Patient;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Person.Person;

public class Patient extends Person implements Serializable {

    public Patient() {
        super();
    }

    public Patient(String id, String name, String email, String phone_number, String gender, String nationality) {
        super(id, name, email, phone_number, gender, nationality);
    }

    static JSONParser jsonParser = new JSONParser();

    static FileReader reader;
    // Read JSON file
    private static Object obj;
    private static JSONArray patient;

    @SuppressWarnings("unchecked")

    public static JSONArray PatientData() throws IOException, ParseException {
        try {
            reader = new FileReader("C:/Users/WIN 10/git/PDM_HospitalDataManagement-1/data/person/sample_patient.json");
            obj = jsonParser.parse(reader);
            patient = (JSONArray) obj;

            System.out.println(patient);

            // Iterate over patient array
            // patientList.forEach(patient -> parsePatientObject((JSONObject) patient));
            Iterator<JSONObject> iterator = patient.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patient;
    }

    public static int countJsonObject() {
        return patient.size();
    }

}
