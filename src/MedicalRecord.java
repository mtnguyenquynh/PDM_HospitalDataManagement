package MedicalRecord;

import java.util.ArrayList;

public class MedicalRecord {
   private ArrayList<Treatment> treatments; 
   private String RDoc_MedicoID;
   private String DNurse_MediocoID;
   private String Patient_ID;
   

    /**
     * @return ArrayList<Treatment> return the treatments
     */
    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    /**
     * @param treatments the treatments to set
     */
    public void setTreatments(ArrayList<Treatment> treatments) {
        this.treatments = treatments;
    }

    /**
     * @return String return the RDoc_MedicoID
     */
    public String getRDoc_MedicoID() {
        return RDoc_MedicoID;
    }

    /**
     * @return String return the DNurse_MediocoID
     */
    public String getDNurse_MediocoID() {
        return DNurse_MediocoID;
    }

    /**
     * @return String return the Patient_ID
     */
    public String getPatient_ID() {
        return Patient_ID;
    }
}
