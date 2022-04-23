import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    private List<User> clientsList;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port){
        this.port=port;
        this.clientsList=new ArrayList<User>();
    }



}

