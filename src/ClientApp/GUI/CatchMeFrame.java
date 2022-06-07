package ClientApp.GUI;

import ClientApp.Listener.CatchMeListener;
import ClientApp.ClientService;
import ClientApp.Listener.LogoutListener;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.util.List;


public class CatchMeFrame extends JFrame {
    ClientService clientService = ClientService.getInstance();
    String myLogin;
    Container container = getContentPane();

    //lista uzytkowników
    JPanel userListPanel = new JPanel();
    JScrollPane userListScrollPane = new JScrollPane();
    JList<Object> userList = new JList<>();
    JButton catchMeButton = new JButton("Zaczep");

    //panel wyswietlania wiadomosci
    JPanel messagePanel = new JPanel();
    JLabel nameLabel;
    JButton logoutButton = new JButton("Wyloguj");
    JTextPane messageTextPane = new JTextPane();

    //panel wysylania wiadomosci
    JPanel sendPanel = new JPanel();
    JTextField sendTextField = new JTextField("Wpisz wiadomość");
    JButton sendButton = new JButton("Wyślij");

    public CatchMeFrame(String login) {
        this.myLogin = login;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CatchMe Klient");
        this.getContentPane().setLayout(null);
        this.setBounds(300, 100, 900, 600);
        this.setResizable(false);
        this.nameLabel = new JLabel("Witaj " + login);
        userListPanel.setLayout(null);
        messagePanel.setLayout(null);
        sendPanel.setLayout(null);
        container.setLayout(null);
        messageTextPane.setContentType( "text/html" );

        addUserListComponents();
        setBoundsToComponents();
        addComponentsToContainer();
        addActionEvent();
    }

    private void addUserListComponents() {
        //lista userow
        userListScrollPane.setViewportView(userList);
        userListPanel.add(userListScrollPane);
        userListPanel.add(catchMeButton);

        //panel wiadomosci
        messagePanel.add(nameLabel);
        messagePanel.add(logoutButton);
        messagePanel.add(messageTextPane);

        //panel wysylania
        sendPanel.add(sendTextField);
        sendPanel.add(sendButton);
    }


    private void setBoundsToComponents() {
        //lista userow
        userListPanel.setBounds(0, 0, 300, 590);
        userListScrollPane.setBounds(0, 0, 300, 515);
        catchMeButton.setBounds(75, 520, 150, 40);
        userList.setBounds(0, 0, 300, 450);
        userList.setLayoutOrientation(JList.VERTICAL);

        //panel wiadomosci
        messagePanel.setBounds(300, 0, 600, 500);
        nameLabel.setBounds(0, 0, 450, 50);
        logoutButton.setBounds(450, 0, 130, 50);
        messageTextPane.setBounds(0, 50, 600, 445);

        //panel wysylania
        sendPanel.setBounds(300, 500, 600, 100);
        sendTextField.setBounds(5, 5, 450, 50);
        sendButton.setBounds(470, 5, 100, 50);
    }

    private void addComponentsToContainer() {
        container.add(userListPanel);
        container.add(messagePanel);
        container.add(sendPanel);
    }

    private void addActionEvent() {
        catchMeButton.addActionListener(new CatchMeListener(userList, myLogin));
        logoutButton.addActionListener(new LogoutListener(this, myLogin));
        sendButton.addActionListener(e -> sendMsg());
    }

    public void repaintUserList(List<String> list) {
        userList.setListData(list.toArray());
        userListPanel.repaint();
        userListPanel.setLayout(null);
        userListPanel.setVisible(true);
    }

    public void sendMsg() {
        String msg = sendTextField.getText();
        if (!msg.equals("")) {
            clientService.sendMsg(msg);
            sendTextField.requestFocus();
            sendTextField.setText(null);
        }
        sendPanel.repaint();
        sendPanel.setLayout(null);
    }

    public void showMsg(String msg) {
        HTMLDocument doc = (HTMLDocument) messageTextPane.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) messageTextPane.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
            messageTextPane.setCaretPosition(doc.getLength());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
