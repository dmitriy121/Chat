import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import static sun.java2d.loops.SurfaceType.Custom;

public class HTTPGetListUsers extends HttpServlet {

    private MessageUsersList msgUsersList = MessageUsersList.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        Iterator<String> itr = msgUsersList.getList().iterator();

        while(itr.hasNext()){

            String c = itr.next();
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(c) ;

        if (json != null) {
            OutputStream os = resp.getOutputStream();
            os.write(json.getBytes("utf-8"));
            System.out.println(json);
        }

        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) {

        }
        Message msg = Message.fromJSON(new String(sb));

        if (msg != null)
            msgUsersList.remove(msg.getFrom());
        else
            resp.setStatus(400); // Bad request
    }


}
