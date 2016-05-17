import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.xml.sax.SAXException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;

public class HTTPUserAuthorization extends HttpServlet {

    private MessageUsersList msgUserList = MessageUsersList.getInstance();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        StringBuffer sb = new StringBuffer();
        String line = null;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) {

        }
        String fromStr = sb.toString();
        Gson gson = new GsonBuilder().create();
        Message m = gson.fromJson(fromStr, Message.class);

        String [] userRegInf = m.getText().split("@@@");
        Users user = new Users();

        try {

            if(Users.authorizeUser(userRegInf[2],userRegInf[3]).equals("User authorized")){
            resp.setStatus(200);
                msgUserList.add(userRegInf[2]);
                System.out.println(userRegInf[2]);
            }
            else {
                resp.setStatus(998);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
