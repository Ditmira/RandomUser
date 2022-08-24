import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;

public abstract class ConnectionConfig {
    static int responseCode;
    public static int APIConnection() {
        try {

            URL url = new URL("https://randomuser.me/api");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }
            return responseCode;
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return responseCode;
    }
    public static void DBConnection(){
        HashMap<String,String> userValues= new HashMap<>();
        userValues = JSONParser.parser();
        try {
            String url = "jdbc:mysql://localhost:3306/randomusers_project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            //Statement st = conn.createStatement();

            PreparedStatement st = conn.prepareStatement("INSERT INTO users (name,gender,location,email) VALUES (?, ?,? ,?)");

            st.setString(1, userValues.get("name"));
            st.setString(2, userValues.get("gender"));
            st.setString(3, userValues.get("location"));
            st.setString(4, userValues.get("email"));

            st.executeUpdate();

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
