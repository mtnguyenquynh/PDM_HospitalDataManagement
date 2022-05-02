package Patient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved
 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
 **/
public class PatientManager {
    public void serialized() {
        ArrayList<Patient> patients = new ArrayList<>();
        for (int i = 0; i < Patient.countJsonObject(); i++) {
            // add json object into arraylist
        }
        // Serialized
        try {
            FileOutputStream fos = new FileOutputStream("PatientData");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(patients);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void deserialized() {
        // Deserialized
        ArrayList<Patient> patients = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream("PatientData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            patients = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

        // Verify list data
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }
}
