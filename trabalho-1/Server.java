import java.io.IOException;
import java.lang.Thread;
import java.util.HashMap;
import java.util.Map;

public class Server{

    static Map<String, String> filename = new HashMap<String, String>();

    public static void main(String[] args) throws IOException{
        new Thread(new MessageServer(12345, "tagarela")).start();
        new Thread(new FileServer(12346, "arquivista")).start();
    }

    public static void setFilename(String server, String name){
        Server.filename.put(server, name);
    }

    public static String getFilename(String server)
    {
        return Server.filename.get(server);
    }

}