package client;

import java.io.File;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class GUI extends Scene {

    public static Button creatServerRoom = new Button("Creat Chat Room");
    public static Button sendServerMessage = new Button("Send");

    public static ListView<String> serverlistView;
    public static ArrayList<String> servermyList;
    public static TextArea serverinputText;
    public static TextField servertextPortConnection;

    public static Button sendClientMessage = new Button("Send");
    public static Button addClientChatRoom = new Button("ChatRoom");
    public static Button sendClientPersonalMessage = new Button("Send Personal");
    public static Button login = new Button("Login");
    public static Button register = new Button("Register");
    public static Button registerMe = new Button("Register me");
    public static Button backToMainMenu = new Button("Back");
    public static Button logOut = new Button("Logout");

    public static ObservableList<String> clientnoteList;
    public static ArrayList<String> clientMessageList;
    public static ListView<String> clientlistMessageView;

    public static ObservableList<String> clientnoteListUsersOnline;
    public static ArrayList<String> clientUsersOnlineList;
    public static ListView<String> clientOnlineView;

    public static TextArea clientinputText;
    public static TextField clientLoginRegistr;
    public static TextField clientNameRegistr;
    public static TextField clientPasswordRegistr;
    public static TextField clientLogin;
    public static TextField clientPassword;

    public static File image = new File("front.jpg");
    public static File image2 = new File("chat.png");
    public static File image3 = new File("16.png");

    public static BorderPane GUILogin() {

// CLIENT SCENE 1: LOGIN

        BorderPane rootConnect = new BorderPane();

        Label welocme = new Label("WELCOME");
        welocme.setFont(new Font(20));
        welocme.setPadding(new Insets(15,7,5,75));
        welocme.setGraphicTextGap(2);
        welocme.setStyle("-fx-font-weight: bold");

        Label nameLabel = new Label("Login:");
        nameLabel.setFont(new Font(14));
        nameLabel.setPadding(new Insets(8,7,5,42));
        clientLogin = new TextField();
        clientLogin.setMaxWidth(130);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font(14));
        passwordLabel.setPadding(new Insets(8,7,5,19));
        clientPassword = new TextField();
        clientPassword.setMaxWidth(130);

        login.setPrefWidth(100);
        register.setPrefWidth(100);

        HBox hbox = new HBox();
        hbox.setSpacing(15);

        HBox hbox1 = new HBox();
        hbox1.setSpacing(15);

        HBox hbox2  = new HBox();
        hbox2.setSpacing(15);

        HBox hbox3 = new HBox();
        hbox3.setPadding(new Insets(3,7,5,19));
        hbox3.setSpacing(15);

        VBox vbox = new VBox();
        vbox.setSpacing(15);

        hbox.getChildren().addAll(welocme);
        hbox1.getChildren().addAll(nameLabel, clientLogin);
        hbox2.getChildren().addAll(passwordLabel, clientPassword);
        hbox3.getChildren().addAll(login, register);
        vbox.getChildren().addAll(hbox,hbox1,hbox2,hbox3);

        rootConnect.setPadding(new Insets(110, 100, 150, 100));
        rootConnect.setStyle("-fx-background-image: url("+image+"); " +
                                "-fx-background-position: center center; " +
                                "-fx-background-repeat: stretch;");
        rootConnect.setCenter(vbox);
        return rootConnect;
    }

    public static BorderPane GUIClientChatRoom(){

// CLIENT SCENE 2: CHATROOM

        BorderPane rootchat = new BorderPane();

        clientlistMessageView = new ListView<>();
        clientOnlineView = new ListView<>();
        clientMessageList = new ArrayList<>();
        clientUsersOnlineList = new ArrayList<>();

        clientlistMessageView.setMaxWidth(400);
        clientlistMessageView.setPrefHeight(300);

        clientOnlineView.setMaxWidth(100);
        clientOnlineView.setPrefHeight(30);

        clientinputText = new TextArea();

        clientinputText.setMaxWidth(250);
        clientinputText.setPrefHeight(50);

        HBox hboxchat = new HBox();
        HBox hboxchatonline = new HBox();
        HBox hboxchatbuttons = new HBox();
        VBox vboxchat = new VBox();

        VBox logout = new VBox();

        hboxchat.setSpacing(30);
        hboxchat.getChildren().addAll(clientinputText);

        logout.setSpacing(10);
        logout.setPadding(new Insets(0,0,0,80));
        logout.getChildren().addAll(logOut);

        hboxchatbuttons.setSpacing(10);
        hboxchatbuttons.getChildren().addAll(sendClientMessage,sendClientPersonalMessage, addClientChatRoom,logout);

        hboxchatonline.setSpacing(30);
        hboxchatonline.getChildren().addAll(clientlistMessageView, clientOnlineView);


        vboxchat.setSpacing(10);
        vboxchat.getChildren().addAll(hboxchatonline, hboxchat, hboxchatbuttons);

        rootchat.setPadding(new Insets(30, 40, 30, 40));
        rootchat.setStyle("-fx-background-image: url('" + image2 + "'); " +
                            "-fx-background-position: center center; " +
                            "-fx-background-repeat: stretch;");
        rootchat.setCenter(vboxchat);
        return rootchat;

    }

    public static BorderPane GUIRegisterUser(){

// CLIENT SCENE 3: REGISTER

        BorderPane rootreg = new BorderPane();

        Label registration = new Label("REGISTRATION");
        registration.setFont(new Font(20));
        registration.setStyle("-fx-font-weight: bold");

        Label nameLabel = new Label("Your name:");
        nameLabel.setFont(new Font(14));
        nameLabel.setPadding(new Insets(0,24,2,0));
        clientNameRegistr = new TextField();
        clientNameRegistr.setMaxWidth(130);

        Label nameLogin = new Label("Your login:");
        nameLogin.setFont(new Font(14));
        nameLogin.setPadding(new Insets(0,26,5,0));
        clientLoginRegistr = new TextField();
        clientLoginRegistr.setMaxWidth(130);

        Label passwordLabel = new Label("Your password:");
        passwordLabel.setFont(new Font(14));
        passwordLabel.setPadding(new Insets(0,0,15,0));
        clientPasswordRegistr = new TextField();
        clientPasswordRegistr.setMaxWidth(130);

        registerMe.setPrefWidth(100);
        backToMainMenu.setPrefWidth(100);

        HBox hbox = new HBox();
        hbox.setSpacing(15);

        HBox hbox1 = new HBox();
        hbox1.setSpacing(15);

        HBox hbox2  = new HBox();
        hbox2.setSpacing(15);

        HBox hbox3 = new HBox();
        hbox3.setSpacing(15);

        HBox hbox4 = new HBox();
        hbox4.setSpacing(15);

        VBox vbox = new VBox();
        vbox.setSpacing(15);

        hbox.getChildren().addAll(registration);

        hbox1.getChildren().addAll(nameLabel, clientNameRegistr);
        hbox2.getChildren().addAll(nameLogin, clientLoginRegistr);
        hbox3.getChildren().addAll(passwordLabel, clientPasswordRegistr);
        hbox4.getChildren().addAll(backToMainMenu,registerMe);

        vbox.getChildren().addAll(hbox,hbox1,hbox2,hbox3,hbox4);

        rootreg.setPadding(new Insets(30, 0, 150, 40));
        rootreg.setStyle("-fx-background-image: url('" + image3 + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");

        rootreg.setCenter(vbox);
        return rootreg;

    }

    public GUI(Parent root) {
        super(root);
    }
}

