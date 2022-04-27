import org.json.*;
import java.io.*;
public class MedicoTask {
    public static void main(String[] args) {
    String directory = ""; //JSON directory here?
    JSONObject obj = new JSONObject(directory);
    if (!obj.getString("status").equals("OK")){
        return;
    }
    }
}
