package ClientApp.Listener;

import ClientApp.ClientService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatchMeListener implements ActionListener {
    ClientService clientService = ClientService.getInstance();
    JList<Object> userList;
    String fromUser;

    public CatchMeListener(JList<Object> selected, String login) {
        userList = selected;
        fromUser = login;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(userList.getSelectedValue());
        String user = (String) userList.getSelectedValue();

        if (!user.equals("")) {
            String msg = "@" + user + " Zaczepia Cię " + fromUser;
            clientService.sendMsg(msg);
        }
    }
}
