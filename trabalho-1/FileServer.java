import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.lang.Thread;
import java.util.Scanner;
import java.lang.Runnable;
import java.io.OutputStream;


public class FileServer implements Runnable{
    int porta;
    String nome;
    ServerSocket servidor;
    Socket cliente;

    public FileServer(int porta, String nome){
        try{
            this.porta = porta;
            this.nome = nome;
            this.servidor = new ServerSocket(this.porta);
            System.out.println("Servidor ("+this.nome+") de arquivos escutando!");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
            try{    
                Socket cliente = null;
                cliente = servidor.accept();
                new Thread(new FileServerWorker(cliente, this.nome)).start();
                System.out.println("Nova conexao com cliente " + cliente.getInetAddress().getHostAddress());
            }catch(IOException e){
                e.printStackTrace();
            }
 
        }
    }
}