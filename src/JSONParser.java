import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import org.json.simple.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class JSONParser {
    static String nameS;
    static String genderS;
    static String locationS;
    static String emailS;
    public static HashMap parser() {
        int responseCode = ConnectionConfig.APIConnection();
        HashMap<String,String> userValues = new HashMap<>();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            try {
                URL url = new URL("https://randomuser.me/api");
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();
                org.json.simple.parser.JSONParser parse = new org.json.simple.parser.JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);
                JSONArray arr = (JSONArray) data_obj.get("results");
                for (int i = 0; i < arr.size(); i++) {

                    JSONObject new_obj = (JSONObject) arr.get(i);
                    JSONObject name = (JSONObject) new_obj.get("name");
                    JSONObject location = (JSONObject) new_obj.get("location");
                    nameS = name.get("first") + " " + name.get("last");
                    genderS = (String) new_obj.get("gender");
                    locationS = (String) location.get("country");
                    emailS = (String) new_obj.get("email");
                    System.out.println(name.get("first") + " - " + name.get("last"));
                    System.out.println("gender " + new_obj.get("gender"));
                    System.out.println("location " + location.get("city") + ", " + location.get("country"));
                    userValues.put("name",nameS);
                    userValues.put("gender", genderS);
                    userValues.put("location",locationS);
                    userValues.put("email",emailS);
                    return userValues;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userValues;
    }
}
