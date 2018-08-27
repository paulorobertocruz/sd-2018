import java.util.Scanner;
import java.lang.Runnable;
import java.lang.Thread;
import java.io.OutputStream;
import java.net.Socket;

public class MessageServerWorker implements Runnable{
    
    Socket cliente;
    String name;

    public MessageServerWorker(Socket cliente_socket, String name){
        this.cliente = cliente_socket;
        this.name = name;
    }

    @Override
    public void run() {
    
        try{
            Scanner entrada = new Scanner(this.cliente.getInputStream());
            OutputStream saida = this.cliente.getOutputStream();
            String msg = "super server, mensagem recebina no server ("+this.name+"):b \n";
            String cliente_name = this.cliente.getInetAddress().getHostAddress();

            while(entrada.hasNextLine()){

                String n = entrada.nextLine();
                System.out.println("Mensagem recebida em ("+this.name+"): \n"+ n);
                saida.write(msg.getBytes());

                if(n.startsWith("filename:")){
                    Server.setFilename(cliente_name, n.split(":")[1]);
                    System.out.println("mensagem server: "+Server.getFilename(cliente_name));
                }
                if(n.equals("exit")){
                    System.out.println("Fecha!");
                    break;
                }
            }
            
            saida.close();
            entrada.close();
            this.cliente.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}