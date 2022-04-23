import javax.jws.Oneway;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{

    private String login;
    private int port;
    private String serverAddress;

    private Thread read;
    BufferedReader input;
    PrintWriter output;
    Socket server;

    final JTextField jtfMessage=new JTextField();
    final JTextPane jtpMessages=new JTextPane();
    final JTextPane jtpList=new JTextPane();

    public Client(){
        this.serverAddress="localhost";
        this.port=8080;
        this.login="nazwa";

        Font font=new Font("Verdana",Font.PLAIN,12);

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
        final JButton jbSend=new JButton("Wyślij");
        jbSend.setFont(font);
        jbSend.setBounds(550,510,50,35);

        //MESSAGES
        jtpMessages.setBounds(10, 10, 400, 350);
        jtpMessages.setFont(font);
        jtpMessages.setMargin(new Insets(5,5,5,5));
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
        jtfMessage.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()== KeyEvent.VK_ENTER){
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

        final JTextField jtfAddress=new JTextField(this.serverAddress);
        final JTextField jtfPort=new JTextField(Integer.toString(this.port));
        final JTextField jtfLogin=new JTextField(this.login);
        final JButton jbConnect=new JButton("Połącz");



        jtfAddress.setBounds(10,450,130,35);
        jtfPort.setBounds(180,450,130,35);
        jtfLogin.setBounds(340,450,130,35);
        jbConnect.setFont(font);
        jbConnect.setBounds(20,500,500,35);


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


    }

    public void sendMsg(){
        try{
            String msg=jtfMessage.getText();
            if(!msg.equals("")){
                output.println(msg);
                jtfMessage.requestFocus();
                jtfMessage.setText(null);
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
            System.exit(0);
        }

    }


    public static void main(String[] args) {
        Client client=new Client();
    }
}
