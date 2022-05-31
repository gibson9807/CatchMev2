package ClientApp;

import ClientApp.GUI.CatchMeFrame;
import ClientApp.GUI.StartFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientService {
    private static ClientService instance;
    public static CatchMeFrame catchMeFrame;
    public static StartFrame startFrame;

    private String login;
    private int port = 50000;
    private String serverAddress = "localhost";

    private Thread readUserListThread;
    BufferedReader input;
    PrintWriter output;
    Socket server;

    private ClientService(){}

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public void loadStartFrame() {
        startFrame = new StartFrame(serverAddress, port);
        startFrame.setVisible(true);
    }

    public void loadCatchMeFrame(String login, String serverAddress, int port) {
        catchMeFrame = new CatchMeFrame(login);
        catchMeFrame.setVisible(true);

        this.serverAddress = serverAddress;
        this.port = port;
        setInputAndOutput();
        output.println(login);

        readUserListThread = new ReadUserList();
        readUserListThread.start();
    }

    private void setInputAndOutput() {
        try {
            server = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            output = new PrintWriter(server.getOutputStream(), true);
            System.out.println(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        System.out.println(msg);
        output.println(msg);
    }


    //#########################################################################################

    class ReadUserList extends Thread {

        public void run() {
            String msg;

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    msg = input.readLine();
                    if (msg != null) {
                        if (msg.charAt(0) == '[') {
                            msg = msg.substring(1, msg.length() - 1);
                            ArrayList<String> userList = new ArrayList<>(Arrays.asList(msg.split(", ")));
                            catchMeFrame.repaintUserList(userList);
                        } else {
                            catchMeFrame.showMsg(msg);
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println("Nie udało się przetworzyć wiadomości");
            }
        }
    }
}
