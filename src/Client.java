import javax.jws.Oneway;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private String login;
    private int port;
    private String serverAddress;

    private Thread read;
    BufferedReader input;
    PrintWriter output;
    Socket server;

    final JTextField jtfMessage = new JTextField();
    final JTextPane jtpMessages = new JTextPane();
    final JTextPane jtpList = new JTextPane();

    public Client() {
        this.serverAddress = "localhost";
        this.port = 50000;
        this.login = "nazwa";

        Font font = new Font("Verdana", Font.PLAIN, 12);

        //FRAME
        final JFrame jfr = new JFrame("CatchMe Klient");
        jfr.getContentPane().setLayout(null);
        jfr.setSize(600, 600);
        jfr.setResizable(false);
        jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //LIST
        jtpList.setBounds(430, 10, 140, 350);
        jtpList.setEditable(true);
        jtpList.setFont(font);
        jtpList.setMargin(new Insets(5, 5, 5, 5));
        jtpList.setEditable(false);
        JScrollPane jtpListSP = new JScrollPane(jtpList);
        jtpListSP.setBounds(430, 10, 140, 350);

        //INPUT FOR MESSAGE
        jtfMessage.setBounds(0, 380, 500, 50);
        jtfMessage.setFont(font);
        jtfMessage.setMargin(new Insets(5, 5, 5, 5));
        final JScrollPane jtfMessageSP = new JScrollPane(jtfMessage);
        jtfMessageSP.setBounds(20, 380, 650, 50);

        jtpList.setContentType("text/html");
        jtpList.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        //BUTTON FOR SENDING
        final JButton jbSend = new JButton("Wyślij");
        jbSend.setFont(font);
        jbSend.setBounds(500, 510, 100, 35);

        //MESSAGES
        jtpMessages.setBounds(10, 10, 400, 350);
        jtpMessages.setFont(font);
        jtpMessages.setMargin(new Insets(5, 5, 5, 5));
        jtpMessages.setEditable(false);
        JScrollPane jtpMessagesSP = new JScrollPane(jtpMessages);
        jtpMessagesSP.setBounds(10, 10, 400, 350);

        jtpMessages.setContentType("text/html");
        jtpMessages.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        //BUTTON FOR DISCONNECTING
        final JButton jbDisconnect = new JButton("Rozłącz");
        jbDisconnect.setFont(font);
        jbDisconnect.setBounds(10, 510, 130, 35);

        //KEY LISTENER FOR SENDING
        jtfMessage.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMsg();
                }
            }
        });
        jbSend.addActionListener(new ActionListener() {
            @Override
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

        jtfAddress.setBounds(10, 450, 130, 35);
        jtfPort.setBounds(180, 450, 130, 35);
        jtfLogin.setBounds(340, 450, 130, 35);
        jbConnect.setFont(font);
        jbConnect.setBounds(20, 500, 500, 35);


        jtpMessages.setBackground(new Color(133, 173, 237));
        jtpList.setBackground(new Color(133, 173, 237));

        //ADDING ELEMENTS TO FRAME
        jfr.add(jtfAddress);
        jfr.add(jtfPort);
        jfr.add(jtfLogin);
        jfr.add(jtpMessagesSP);
        jfr.add(jtpListSP);
        jfr.add(jbConnect);
        jfr.setVisible(true);


        addToJTPane(jtpMessages,
                "<h3> W CELU WYSŁANIA WIADOMOŚCI PRYWATNEJ WPISZ @nazwa_adresata");


        jbConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    serverAddress=jtfAddress.getText();
                    port=Integer.parseInt(jtfPort.getText());
                    login=jtfLogin.getText();

                    addToJTPane(jtpMessages,"Łączenie z "+ serverAddress+ " na porcie "+port+"...");
                    server=new Socket(serverAddress,port);
                    addToJTPane(jtpMessages,"Połączono z "+server.getRemoteSocketAddress());

                    input=new BufferedReader(new InputStreamReader(server.getInputStream()));
                    output=new PrintWriter(server.getOutputStream(),true);

                    output.println(login);

                    read=new Read();
                    read.start();
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
                    jtpMessages.setBackground(Color.WHITE);
                    jtpList.setBackground(Color.WHITE);

                }catch(Exception ex){
                    addToJTPane(jtpMessages,"Nie udało się połączyć z serwerem");
                    JOptionPane.showMessageDialog(jfr,ex.getMessage());
                }
            }
        });

        jbDisconnect.addActionListener(new ActionListener() {
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
                addToJTPane(jtpMessages,"Rozłączono");
                output.close();
            }
        });

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


    public void addToJTPane(JTextPane jtp,String msg){
        HTMLDocument doc=(HTMLDocument)jtp.getDocument();
        HTMLEditorKit editorKit=(HTMLEditorKit) jtp.getEditorKit();
        try{
            editorKit.insertHTML(doc, doc.getLength(), msg,0,0,null);
            jtp.setCaretPosition(doc.getLength());
        }catch(Exception ex){
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
    }




    class Read extends Thread {

        public void run() {
            String msg;

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    msg=input.readLine();
                    if(msg!=null){

                    }

                }
            } catch (IOException ex) {
                System.out.println("Nie udało się przetworzyć wiadomości");
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}

