package MedicalRecord;

import java.util.ArrayList;

public class MedicalRecord {
   private ArrayList<Treatment> treatments; 
   private String RDoc_MedicoID;
   private String DNurse_MediocoID;
   private String Patient_ID;
   
    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(ArrayList<Treatment> treatments) {
        this.treatments = treatments;
    }

    public String getRDoc_MedicoID() {
        return RDoc_MedicoID;
    }

    public String getDNurse_MediocoID() {
        return DNurse_MediocoID;
    }

    public String getPatient_ID() {
        return Patient_ID;
    }
}
