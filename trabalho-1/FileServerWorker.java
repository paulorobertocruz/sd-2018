import java.util.Calendar;
import java.util.Scanner;
import java.lang.Runnable;
import java.lang.Thread;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Date;

public class FileServerWorker implements Runnable{
    
    Socket cliente;
    String name;

    public FileServerWorker(Socket cliente_socket, String name){
        this.cliente = cliente_socket;
        this.name = name;
    }

    @Override
    public void run() {

        try{
            String cliente_name = this.cliente.getInetAddress().getHostAddress();
            String filename;

            while(Server.getFilename(cliente_name) == null)
            {
                System.out.println("fileserver: "+Server.getFilename(cliente_name));
            }
            filename = Server.getFilename(cliente_name);
            


            FileOutputStream arquivo_out = new FileOutputStream("/home/paulo/Documentos/ultimo-semestre/sistemas-distribuidos/arquivos/"+filename);

            DataInputStream data_input = new DataInputStream(this.cliente.getInputStream());
            
            OutputStream saida = this.cliente.getOutputStream();
            
            String msg = "super server, mensagem recebina no server ("+this.name+"):b \n";

            byte[] buffer = new byte[4096];
        
            while( (data_input.read(buffer)) > 0) {
                arquivo_out.write(buffer);
            }
            Server.setFilename(cliente_name, null);
            arquivo_out.close();
            data_input.close();
            saida.close();
            this.cliente.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}