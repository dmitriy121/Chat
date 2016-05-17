package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;


class MessageUpdateThread extends Thread {
	private int n;

	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				URL url = new URL("http://localhost:8080/getlistmessages?from=" + n);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				InputStream is = http.getInputStream();

				try {
					int sz = is.available();
					if (sz > 0) {
						byte[] buf = new byte[is.available()];
						is.read(buf);

						Gson gson = new GsonBuilder().create();
						Message[] list = gson.fromJson(new String(buf), Message[].class);

						for (Message m : list) {
							n++;

							Platform.runLater(() -> {

								GUI.clientMessageList.add(m.getText());
								GUI.clientnoteList = FXCollections.observableArrayList(GUI.clientMessageList);
								GUI.clientlistMessageView.setItems(GUI.clientnoteList);
							});
						}
					}
				} finally {
					is.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}
}

class UsersOnlineUpdateThread extends Thread {

	@Override
	public void run() {

		try {
			URL url = new URL("http://localhost:8080/getlistusers");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			InputStream is = http.getInputStream();
			String res = new String();

			List <String> list = new ArrayList<String>();

			try {
				int sz = is.available();
				if (sz > 0) {
					byte[] buf = new byte[is.available()];
					is.read(buf);
					res = new String(buf);

					list.add(res);}

				} finally {
					is.close();
				}

			Platform.runLater(() -> {

				for(int i =0; i<list.size();i++)

				GUI.clientUsersOnlineList.add(list.get(i));
				GUI.clientnoteListUsersOnline = FXCollections.observableArrayList(GUI.clientUsersOnlineList);
				GUI.clientOnlineView.setItems(GUI.clientnoteListUsersOnline);
			});

		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}

}

public class Main extends Application {

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Image ico = new Image("client\\5.png");

		Scene sceneLogin = new Scene(GUI.GUILogin(), 452, 452);
		Scene sceneChat = new Scene(GUI.GUIClientChatRoom(), 452, 452);
		Scene sceneRegister = new Scene(GUI.GUIRegisterUser(), 452, 452);

		primaryStage.setResizable(false);
		primaryStage.setScene(sceneLogin);
		primaryStage.setTitle("Chat Me App");
		primaryStage.getIcons().add(ico);
		primaryStage.show();

		GUI.register.setOnAction(e -> {

			GUI.clientNameRegistr.setText("");
			GUI.clientLoginRegistr.setText("");
			GUI.clientPasswordRegistr.setText("");

			primaryStage.setScene(sceneRegister);

		});

		GUI.registerMe.setOnAction(e->{

			if(GUI.clientNameRegistr.getText().length()==0 ||
					GUI.clientLoginRegistr.getText().length() ==0 ||
						GUI.clientPasswordRegistr.getText().length() == 0){

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Registration error");
				alert.setContentText("Unable to register user." + "\n"
						+ "Please make sure that all fields are filled.");
				alert.show();
			}
			else {
				Message m = new Message();

				String name = GUI.clientNameRegistr.getText();
				String login = GUI.clientLoginRegistr.getText();
				String password = GUI.clientPasswordRegistr.getText();

				m.setText("@@@"+name+"@@@"+login+"@@@"+password);
				m.setFrom(login);

				System.out.println(m.toString());

				try {
					int res = m.send("http://localhost:8080/addUser",m.toString());

					if (res == 999) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Registration error");
						alert.setContentText("Unable to register. Please change your login, as a user with such login exists.");
						alert.show();
					}

					else if (res == 200) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Conformation");
						alert.setHeaderText("Confirmation of registration");
						alert.setContentText("Thank you for registration.");
						alert.show();
						primaryStage.setScene(sceneLogin);
						return;
					}

					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("HTTP error");
						alert.setContentText("Unable to connect with server." + "\n"
								+"HTTP error code: "+ res);
						alert.show();
						return;
					}

				} catch (IOException ex) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Server error");
					alert.setContentText("Server is unavaliable." + "\n"
							+ ex.getMessage());
					alert.show();
					return;
				}
			}
		});

		GUI.backToMainMenu.setOnAction(e ->{

			primaryStage.setScene(sceneLogin);
		});

		GUI.logOut.setOnAction(e ->{

			Message m = new Message();
			m.setFrom(GUI.clientLogin.getText());

			try {
				int res = m.send("http://localhost:8080/removeusertatus", m.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			primaryStage.setScene(sceneLogin);
			GUI.clientLogin.setText("");
			GUI.clientPassword.setText("");


		});

		GUI.login.setOnAction(e -> {

			Message m = new Message();

			String name = GUI.clientNameRegistr.getText();
			String login = GUI.clientLogin.getText();
			String password = GUI.clientPassword.getText();

			m.setText("@@@"+name+"@@@"+login+"@@@"+password);
			m.setFrom(login);

			try {
				int res = m.send("http://localhost:8080/authorize",m.toString());

				if (res == 998) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Authorization error");
					alert.setContentText("Wrong password or login. Please try again.");
					alert.show();
					return;
				}
				else if (res == 200) {
					primaryStage.setScene(sceneChat);

					MessageUpdateThread th = new MessageUpdateThread();
					th.setDaemon(true);
					th.start();

					UsersOnlineUpdateThread th2 = new UsersOnlineUpdateThread();

					th2.setDaemon(true);
					th2.start();
					th2.sleep(100);

					return;
				}
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Server error");
					alert.setContentText("Server is unavaliable." + "\n"
							+ res);
					alert.show();
					return;
				}

			} catch (IOException ex) {
				return;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});

		GUI.sendClientMessage.setOnAction(e -> {

			Message m = new Message();
			m.setText(GUI.clientinputText.getText());
			m.setFrom(GUI.clientLogin.getText());

			try {
				int res = m.send("http://localhost:8080/addmessage",m.toString());
				if (res != 200) {
					System.out.println("HTTP error: " + res);
					return;
				}

			} catch (IOException ex) {
				System.out.println("Error: " + ex.getMessage());
				return;
			}
			GUI.clientinputText.setText(null);
		});

	}
}
