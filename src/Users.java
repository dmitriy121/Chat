
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Users {

    private String name;
    private String login;
    private String password;
    private static int id;

    public Users() throws IOException {
    }

    private static File usersCredentials = new File("ChatInf\\Credentials.xml");

    public synchronized String addUser (String name,String login, String password) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        String responseAddUser = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(usersCredentials);

        String responseCheckUser = checkUser(login,doc);

        if(responseCheckUser != null){
            responseAddUser ="A user with this login already exists.";
        }

        else {
                Node root = doc.getFirstChild();
                id++;

                Element newUser = doc.createElement("user");
                newUser.setAttribute("id",""+id);

                Element newUserName = doc.createElement("name");
                newUserName.setTextContent(name);

                Element newUserLogin = doc.createElement("login");
                newUserLogin.setTextContent(login);

                Element newUserPassword = doc.createElement("password");
                newUserPassword.setTextContent(password);

                root.appendChild(newUser);
                newUser.appendChild(newUserName);
                newUser.appendChild(newUserLogin);
                newUser.appendChild(newUserPassword);

                DOMSource source = new DOMSource(doc);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
                StreamResult result = new StreamResult(usersCredentials);
                transformer.transform(source, result);
        }
    return responseAddUser;
    }

    public String checkUser(String login, Document doc) throws ParserConfigurationException, IOException, SAXException {

        String response = null;

        Element root = doc.getDocumentElement();

        NodeList usersList = root.getChildNodes();

        for (int i = 0; i < usersList.getLength(); i++) {

            Node node = usersList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                if (login.equals(((Element) node).getElementsByTagName("login").item(0).getTextContent())) {
                    response = "A user with this login already exists.";
                    break;
                }
            }
        }
        return response;
    }

    public static String authorizeUser (String login, String password) throws ParserConfigurationException, IOException, SAXException {

        String authorize = "";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(usersCredentials);

        Node root = doc.getFirstChild();
        NodeList nodeList = root.getChildNodes();

        String credentials = "";

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList nodeUserList = node.getChildNodes();

            for (int k = 0; k < nodeUserList.getLength(); k++) {
                Node node1 = nodeUserList.item(k);

                if(node1.getNodeName()=="login") {

                    if (node1.getChildNodes().item(0).getNodeValue().equals(login)) {
                        credentials =credentials.concat(login);
                    }
                    else{
                     break;
                    }
                }

                if(node1.getNodeName()=="password") {

                    if (node1.getChildNodes().item(0).getNodeValue().equals(password)){
                        credentials = credentials.concat(password);
                    }
                    else{
                        break;
                    }
                }
            }
        }

        if(credentials.equals(login.concat(password))){
            authorize = "User authorized";
        }

        return authorize;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "[" + name + ", " + login+ ", " + password +"]";
    }

}