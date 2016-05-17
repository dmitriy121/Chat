package client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date date = new Date();
	private String from;
	private String to;
	private String text;
	
	public String toJSON() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
	public static Message fromJSON(String s) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(s, Message.class);
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(date.toString()).append("; ")
				.append("From: ").append(from)
                .append("; ").append("\n").append("Message: ").append(text).toString();
	}

	public int send(String targetURL,String urlParameters) throws IOException {

		HttpURLConnection connection = null;
		URL url = new URL(targetURL);
		connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		connection.setRequestProperty("Content-Length",
				Integer.toString(urlParameters.getBytes().length));
		connection.setRequestProperty("Content-Language", "en-US");

		connection.setUseCaches(false);
		connection.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream (
				connection.getOutputStream());

        Message m = new Message();
        m.setText(urlParameters);

		try {
			wr.write(m.toJSON().getBytes("utf-8"));
			System.out.println(m);

			return connection.getResponseCode();


		} finally {

            if(connection != null) {
                connection.disconnect();
            }
			wr.close();
		}
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
