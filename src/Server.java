import ClientApp.UserNameDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private final List<User> clientsList;
    private ServerSocket serverSocket;
    private final int port;

    public Server(int port) {
        this.port = port;
        this.clientsList = new ArrayList<>();
    }

    public void sendMsgToUser(String msg, User userFrom, String user) {
        boolean found = false;
        for (User u : this.clientsList) {
            if (u.getLogin().equals(user) && u != userFrom) {
                found = true;
                userFrom.getStreamOut().println("<b>" + userFrom.toString() + "</b>" + " --> " + u.toString() + ": " + msg);
                u.getStreamOut().println("(<b>PW</b>)" + userFrom.toString() + ": " + msg);
            }
        }
        if (!found) {
            userFrom.getStreamOut().println("<b><span style='color:red'>" + userFrom.toString() + " NIE UDAŁO SIĘ WYSŁAĆ: " + msg + "</span></b>");
        }
    }

    public void broadcastMsgToAllUsers(String msg, User userFrom) {
        for (User u : this.clientsList) {
            u.getStreamOut().println(userFrom.toString() + ": " + msg);
        }
    }

    public void distributeToAllUsers() {
        for (User u : this.clientsList) {
            u.getStreamOut().println(this.clientsList);
        }
    }

    public void deleteUser(User user) {
        removeUserNameFromFile(user.getLogin());
        this.clientsList.remove(user);
    }

    private void removeUserNameFromFile(String login) {
        UserNameDAO userNameDAO = new UserNameDAO();
        userNameDAO.removeUserNameFromFile(login);
    }

    public void run() throws IOException {
        serverSocket = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };

        while (true) {
            Socket client = serverSocket.accept();

            String login = (new Scanner(client.getInputStream())).nextLine();
            System.out.println("Nowy Klient: " + login + " Host: " + client.getInetAddress().getHostAddress());
            saveUserNameToFile(login);

            User newClient = new User(login, client);
            this.clientsList.add(newClient);
            System.out.println(newClient);

            newClient.getStreamOut().println("<h2><b><span style='color:green'>" + "Witaj " + newClient.toString() + "!</span></b></h2>");

            new Thread(new UserMsg(this, newClient)).start();
        }

    }

    private void saveUserNameToFile(String login) {
        UserNameDAO userNameDAO = new UserNameDAO();
        userNameDAO.setNameToFile(login);
    }


    public static void main(String[] args) throws IOException {
        new Server(50000).run();
    }

    protected void finalize() {
        UserNameDAO userNameDAO = new UserNameDAO();
        userNameDAO.clearFile();
    }
}

class User {
    private static int userIDcounter = 0;
    private final int userID;
    private final PrintStream streamOut;
    private final InputStream streamIn;
    private final String login;
    private Socket client;

    public User(String login, Socket client) throws IOException {
        this.userID = userIDcounter;
        this.streamOut = new PrintStream(client.getOutputStream());
        this.streamIn = client.getInputStream();
        this.login = login;
        this.client = client;
        userIDcounter += 1;
    }

    @Override
    public String toString() {
        return this.getLogin();
    }

    public int getUserID() {
        return this.userID;
    }

    public PrintStream getStreamOut() {
        return this.streamOut;
    }

    public InputStream getStreamIn() {
        return this.streamIn;
    }

    public String getLogin() {
        return this.login;
    }

//    public Socket getClient() {
//        return this.client;
//    }
}

class UserMsg implements Runnable {

    private final Server server;
    private final User user;

    public UserMsg(Server server, User user) {
        this.server = server;
        this.user = user;
        System.out.println(user.getLogin() + " 143 ---- ");
        this.server.distributeToAllUsers();
    }


    @Override
    public void run() {
        String msg;

        Scanner scanner = new Scanner(this.user.getStreamIn());
        while (scanner.hasNextLine()) {
            msg = scanner.nextLine();

            if (msg.startsWith("@")) { // send msg to @user
                if (msg.contains(" ")) {

                    server.sendMsgToUser(
                            msg.substring((msg.indexOf(" ")) + 1), user, msg.substring(1, msg.indexOf((" ")))
                    );

                }
            } else {
                server.broadcastMsgToAllUsers(msg, user); // send msg to all user
            }
        }
        server.deleteUser(user);
        this.server.distributeToAllUsers();
        scanner.close();
    }
}