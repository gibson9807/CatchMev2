import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private List<User> clientsList;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port){
        this.port=port;
        this.clientsList=new ArrayList<User>();
    }

    public void sendMsgToUser(String msg, User userFrom,String user){
        boolean found=false;
        for(User u: this.clientsList){
            if(u.getLogin().equals(user)&&u!=userFrom){
                found=true;
                userFrom.getStreamOut().println(userFrom.toString()+" --> "+u.toString()+": "+msg);
                u.getStreamOut().println("(<b>PW</b>)"+userFrom.toString()+": "+msg);
            }
        }
        if(!found){
            userFrom.getStreamOut().println(userFrom.toString()+ "NIE UDAŁO SIĘ WYSŁAĆ: "+msg);
        }
    }

    public void broadcastMsgToAllUsers(String msg,User userFrom){
        for(User u:this.clientsList){
            u.getStreamOut().println(userFrom.toString()+": "+msg);
        }
    }

    public void distributeToAllUsers(){
        for(User u:this.clientsList){
            u.getStreamOut().println(this.clientsList);
        }
    }

    public void deleteUser(User user){
        this.clientsList.remove(user);
    }

    public void run() throws IOException {
        serverSocket=new ServerSocket(port){
            protected void finalize() throws IOException{
                this.close();
            }
        };

        while(true){
            Socket client= serverSocket.accept();

            String login=(new Scanner(client.getInputStream())).nextLine();
            System.out.println("Nowy Klient: "+login+" Host: "+client.getInetAddress().getHostAddress());

            User newClient=new User(login,client);
            this.clientsList.add(newClient);

            newClient.getStreamOut().println("Witaj "+ newClient.toString());

            new Thread(new UserMsg(this,newClient)).start();
        }

    }


    public static void main(String[] args)throws IOException {
        new Server(50000).run();
    }
}
class User{
    private static int userIDcounter=0;
    private int userID;
    private PrintStream streamOut;
    private InputStream streamIn;
    private String login;
    private Socket client;

    public User(String login, Socket client) throws IOException {
        this.userID = userIDcounter;
        this.streamOut = new PrintStream(client.getOutputStream());
        this.streamIn = client.getInputStream();
        this.login = login;
        this.client = client;
        userIDcounter+=1;
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
class UserMsg implements Runnable{

    private Server server;
    private User user;

    public UserMsg(Server server, User user){
        this.server =server;
        this.user=user;
        this.server.distributeToAllUsers();
    }


    @Override
    public void run() {
        String msg;

        Scanner scanner=new Scanner(this.user.getStreamIn());
        while(scanner.hasNextLine()){
            msg=scanner.nextLine();

            if (msg.startsWith("@")){
                if(msg.contains(" ")){

                    server.sendMsgToUser(
                            msg.substring((msg.indexOf(" "))+1),user,msg.substring(1,msg.indexOf((" ")))
                    );

                }
            }else
            {
                server.broadcastMsgToAllUsers(msg,user);
            }
        }
        server.deleteUser(user);
        this.server.distributeToAllUsers();
        scanner.close();
    }
}