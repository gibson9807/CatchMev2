package ClientApp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {

   /* private String login;
    private int port;
    private String serverAddress;

    private Thread read;
    BufferedReader input;
    PrintWriter output;
    Socket server;

    final JTextField jtfMessage = new JTextField();
    final JTextPane jtpMessages = new JTextPane();
    final JTextPane jtpList = new JTextPane();*/

    public Client() {
        /*this.serverAddress = "localhost";
        this.port = 50000;
        this.login = "nazwa";*/
        ClientService service = ClientService.getInstance();
        service.loadStartFrame();

        //FRAME
        /*final JFrame jfr = new JFrame("CatchMe Klient"); //  <-------------------------------------------------- start frame
        jfr.getContentPane().setLayout(null);
        jfr.setSize(800, 600);
        jfr.setResizable(false);
        jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/


        //LIST
       /* jtpList.setBounds(520, 10, 255, 450);

        JScrollPane jtpListSP = new JScrollPane(jtpList);
        jtpListSP.setBounds(520, 10, 255, 450);
        jtpListSP.setBackground(new Color(123454));
        jtpList.setBackground(new Color(123454));*/



       /* jtpList.setContentType("text/html");
        jtpList.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);*/

        //INPUT FOR MESSAGE
       /* jtfMessage.setBounds(10, 465, 650, 35);

        final JScrollPane jtfMessageSP = new JScrollPane(jtfMessage);
        jtfMessageSP.setBounds(10, 465, 650, 35);
        jtfMessageSP.setBackground(new Color(987265));
        jtfMessage.setBackground(new Color(987265));*/

        //BUTTON FOR SENDING
        /*final JButton jbSend = new JButton("Wyślij");
        jbSend.setBounds(670, 465, 105, 35);

        //MESSAGES
        jtpMessages.setBounds(10, 10, 500, 450);

        JScrollPane jtpMessagesSP = new JScrollPane(jtpMessages);
        jtpMessagesSP.setBounds(10, 10, 500, 450);
        jtpMessagesSP.setBackground(new Color(57899323));
        jtpMessages.setBackground(new Color(57899323));

        jtpMessages.setContentType("text/html");
        jtpMessages.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);*/

        //BUTTON FOR DISCONNECTING
        /*final JButton jbDisconnect = new JButton("Rozłącz");
        //jbDisconnect.setFont(fontDisconnect);
        jbDisconnect.setBounds(10, 510, 765, 35);*/

        //KEY LISTENER FOR SENDING
        /*jtfMessage.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMsg();
                }
            }
        });*/
        /*jbSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        final JTextField jtfAddress = new JTextField(this.serverAddress);
        final JTextField jtfPort = new JTextField(Integer.toString(this.port));
        final JTextField jtfLogin = new JTextField(this.login);
        final JButton jbConnect = new JButton("Połącz");

        jtfAddress.getDocument().addDocumentListener(new MsgListener(jtfAddress, jtfPort, jtfLogin, jbConnect));
        jtfPort.getDocument().addDocumentListener(new MsgListener(jtfAddress, jtfPort, jtfLogin, jbConnect));
        jtfLogin.getDocument().addDocumentListener(new MsgListener(jtfAddress, jtfPort, jtfLogin, jbConnect));

        jtfAddress.setBounds(10, 470, 230, 35);
        jtfPort.setBounds(280, 470, 230, 35);
        jtfLogin.setBounds(520, 470, 255, 35);
        //jbConnect.setFont(font);
        jbConnect.setBounds(10, 515, 765, 35);*/


        /*jtpMessages.setBackground(new Color(155, 17, 27));
        jtpList.setBackground(new Color(173, 173, 237));

        //ADDING ELEMENTS TO FRAME
        jfr.add(jtfAddress);
        jfr.add(jtfPort);
        jfr.add(jtfLogin);
        jfr.add(jtpMessagesSP);
        jfr.add(jtpListSP);
        jfr.add(jbConnect);
        jfr.setVisible(true);

        addToJTPane(jtpMessages,
                "<h3> W CELU WYSŁANIA WIADOMOŚCI PRYWATNEJ WPISZ @nazwa_adresata");*/

        /*jbConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    serverAddress = jtfAddress.getText();
                    port = Integer.parseInt(jtfPort.getText());
                    String login = jtfLogin.getText();

                    addToJTPane(jtpMessages, "<i>Łączenie z " + serverAddress + " na porcie " + port + "...</i>");
                    server = new Socket(serverAddress, port);
                    addToJTPane(jtpMessages, "Połączono z " + server.getRemoteSocketAddress());

                    input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    output = new PrintWriter(server.getOutputStream(), true);
                    System.out.println(login);

                    output.println(login);


                    //REMOVING ELEMENTS FROM CONNECTION WINDOW
                    jfr.remove(jtfAddress);
                    jfr.remove(jtfPort);
                    jfr.remove(jtfLogin);
                    jfr.remove(jbConnect);
                    //ADDING ELEMENTS FOR CLIENT GUI
                    jfr.add(jtfMessage);
                    jfr.add(jbSend);
                    jfr.add(jbDisconnect);

                    jfr.repaint();
                    jtpMessages.setBackground(new Color(155, 17, 27));
                    jtpList.setBackground(new Color(173, 173, 237));

                } catch (Exception ex) {
                    addToJTPane(jtpMessages, "Nie udało się połączyć z serwerem");
                    JOptionPane.showMessageDialog(jfr, ex.getMessage());
                }
            }
        });*/
        //  DISCONNECT
       /* jbDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfr.add(jtfAddress);
                jfr.add(jtfPort);
                jfr.add(jtfLogin);
                jfr.add(jbConnect);

                jfr.remove(jbDisconnect);
                jfr.remove(jbSend);
                jfr.remove(jtfMessage);

                jfr.revalidate();
                jfr.repaint();

                read.interrupt();

                jtpList.setText(null);
                jtpMessages.setBackground(Color.DARK_GRAY);
                jtpList.setBackground(Color.DARK_GRAY);
                addToJTPane(jtpMessages, "Rozłączono");
                removeUserNameFromFile(login);
                output.close();
            }
        });*/
    }

/*
    private boolean isUserLoginExistsInFIle(String login) {
        if (login == null) return true;
        ArrayList<String> userNameList = getUserListFromFIle();
        boolean isTheSame = false;

        for (String name : userNameList) {
            if (name.equals(login)) {
                return true;
            }
        }
        return isTheSame;
    }

    private ArrayList<String> getUserListFromFIle() {
        *//*ClientApp.UserNameDAO userNameDAO = new ClientApp.UserNameDAO();
        return userNameDAO.readNameFromFile();*//*
        return null;
    }

    private void removeUserNameFromFile(String login) {
       *//* *//*
    }

    public void sendMsg() {
        try {
            String msg = jtfMessage.getText();
            if (!msg.equals("")) {
                output.println(msg);
                jtfMessage.requestFocus();
                jtfMessage.setText(null);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(0);
        }

    }


    public void addToJTPane(JTextPane jtp, String msg) {
        HTMLDocument doc = (HTMLDocument) jtp.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) jtp.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
            jtp.setCaretPosition(doc.getLength());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class MsgListener implements DocumentListener {

        JTextField jtfA;
        JTextField jtfP;
        JTextField jtfL;
        JButton jbC;

        public MsgListener(JTextField jtfA, JTextField jtfP, JTextField jtfL, JButton jbC) {
            this.jtfA = jtfA;
            this.jtfP = jtfP;
            this.jtfL = jtfL;
            this.jbC = jbC;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            jbC.setEnabled(!jtfA.getText().trim().equals("") &&
                    !jtfP.getText().trim().equals("") &&
                    !jtfL.getText().trim().equals(""));
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            jbC.setEnabled(!jtfA.getText().trim().equals("") &&
                    !jtfP.getText().trim().equals("") &&
                    !jtfL.getText().trim().equals(""));
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            jbC.setEnabled(!jtfA.getText().trim().equals("") &&
                    !jtfP.getText().trim().equals("") &&
                    !jtfL.getText().trim().equals(""));
        }
    }*/

   /* class Read extends Thread {

        public void run() {
            String msg;

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    msg = input.readLine();
                    if (msg != null) {
                        if (msg.charAt(0) == '[') {
                            msg = msg.substring(1, msg.length() - 1);
                            ArrayList<String> userList = new ArrayList<String>(Arrays.asList(msg.split(", ")));
                            jtpList.setText(null);
                            for (String u : userList) {
                                addToJTPane(jtpList, u);
                            }
                        } else {
                            addToJTPane(jtpMessages, msg);
                        }
                    }

                }
            } catch (IOException ex) {
                System.out.println("Nie udało się przetworzyć wiadomości");
            }
        }
    }
*/
    public static void main(String[] args) {
        Client client = new Client();
    }
}

