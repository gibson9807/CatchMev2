package ClientApp.GUI;

import ClientApp.ClientService;
import ClientApp.UserNameDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel loginLabel = new JLabel("Podaj login");
    JTextField loginField = new JTextField();
    JTextField addressField;
    JTextField portField;
    JButton saveButton = new JButton("ZAPISZ");
    JButton resetButton = new JButton("RESET");

    ClientService clientService = ClientService.getInstance();

    public StartFrame(String serverAddress, int port) {
        addressField = new JTextField(serverAddress);
        portField = new JTextField(Integer.toString(port));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBounds(500, 100, 310, 400);
        container.setLayout(null);

        setBoundsToComponents();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setBoundsToComponents() {
        loginLabel.setBounds(120, 50, 100, 30);
        loginField.setBounds(50, 100, 200, 30);
        addressField.setBounds(50, 170, 200, 30);
        portField.setBounds(50, 220, 200, 30);
        saveButton.setBounds(50, 300, 80, 30);
        resetButton.setBounds(170, 300, 80, 30);
    }

    public void addComponentsToContainer() {
        container.add(loginLabel);
        container.add(loginField);
        container.add(addressField);
        container.add(portField);
        container.add(saveButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        saveButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String login = loginField.getText();
            checkLogin(login);

        } else if (e.getSource() == resetButton) {
            loginField.setText("");
        }
    }

    private void checkLogin(String login) {
        if (isUserLoginExistsInFIle(login)) {
            System.out.println("Podaj poprawny login");
            JOptionPane.showMessageDialog(this, "Podaj poprawny login");
        }else{
            loadCatchMeFrame(login);
        }
    }

    private boolean isUserLoginExistsInFIle(String login) {
        if (login == null || login.equals("")) return true;
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
        UserNameDAO userNameDAO = new UserNameDAO();
        return userNameDAO.readNameFromFile();
    }

    private void loadCatchMeFrame(String login) {
        this.dispose();
        clientService.loadCatchMeFrame(login, addressField.getText(), Integer.parseInt(portField.getText()));
    }
}
