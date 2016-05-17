import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class MessageUsersList {

    private static final MessageUsersList msgUserList = new MessageUsersList();

    private final TreeSet<String> list = new TreeSet<>();

    public TreeSet<String> getList() {
        return list;
    }

    public static MessageUsersList getInstance() {
        return msgUserList;
    }

    private MessageUsersList() {}

    public synchronized void add(String login) {
        list.add(login);
    }

    public synchronized void remove(String login) {
        list.add(login);
    }

}
