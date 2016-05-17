import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

public class HTTPUserAddition extends HttpServlet {

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
            String responseAddUser = user.addUser(userRegInf[1],userRegInf[2],userRegInf[3]);
            if(responseAddUser !=null){
                resp.setStatus(999);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}
