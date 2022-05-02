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
/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved
 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
 **/

public class Patient extends Person implements Serializable {

    public Patient(String id, String name, String email, String phone_number, String gender, String nationality) {
        super(id, name, email, phone_number, gender, nationality);
    }

    JSONParser jsonParser = new JSONParser();

    FileReader reader;
    // Read JSON file
    private Object obj;
    private static JSONArray patient;

    @SuppressWarnings("unchecked")

    public JSONArray PatientData() throws IOException, ParseException {
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
