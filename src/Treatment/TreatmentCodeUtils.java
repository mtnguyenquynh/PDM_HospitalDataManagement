package Treatment;

import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class TreatmentCodeUtils {
    public static boolean LoadJsonDataIntoHashTable(File json_data, Hashtable<String, String> table) {
        /**
         * This method will load everything from the json file into the 2-valued string hash table.
         **/
        boolean success = true; // True if the task is successful.
        try {
            BufferedReader br = new BufferedReader(new FileReader(json_data));
            String line;
            while ((line = br.readLine()) != null) {
                String[] line_split = line.split(":");
                String code = line_split[0];
                String description = line_split[1];
                table.put(code, description);
            }
            br.close();
        } catch (IOException e) { 
            System.out.println("Error: " + e.getMessage()); 
            success = false;
        }
        
        System.out.println("----------------------------------------------------------------------------------");
        return success;
    }

    public static boolean SaveHashTableIntoJsonFile(String directory, Hashtable<String, String> table) {
        /**
         * This method will load everything from the json file into the 2-valued string hash table.
         **/
        boolean success = true; // True if the task is successful.



        return success;
        
    }

}
