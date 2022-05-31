package ClientApp.Listener;

import ClientApp.ClientService;
import ClientApp.GUI.CatchMeFrame;
import ClientApp.UserNameDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutListener implements ActionListener {
    ClientService clientService = ClientService.getInstance();
    JFrame frame;
    String login;

    public LogoutListener(JFrame catchMeFrame, String myLogin) {
        frame = catchMeFrame;
        login = myLogin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserNameDAO userNameDAO = new UserNameDAO();
        userNameDAO.removeUserNameFromFile(login);

        clientService.loadStartFrame();
        frame.dispose();
    }
}
