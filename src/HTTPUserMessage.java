import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HTTPUserMessage extends HttpServlet {

	private MessageList msgList = MessageList.getInstance();
	
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
			msgList.add(msg);
		else
			resp.setStatus(400); // Bad request
	}
}
