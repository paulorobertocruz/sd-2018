import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;
import java.net.Socket;


public class Cliente{
    static int porta_mensagem = 12345;
    static int porta_arquivo = 12346;
    static String ip;

    public static void main(String[] args) throws UnknownHostException, IOException{

        if(args.length < 1){
            System.out.println("informe o ip do servidor");
            System.exit(0);
        }

        Cliente.ip = args[0];

        Socket cliente_mensagem;

        try {
            cliente_mensagem = new Socket(Cliente.ip, Cliente.porta_mensagem);
            
            System.out.println("Cliente se conectou ao servidor!");
        
            Scanner teclado = new Scanner(System.in);
            PrintStream saida_mensagem = new PrintStream(cliente_mensagem.getOutputStream());
            BufferedReader entrada_mensagem = new BufferedReader(new InputStreamReader(cliente_mensagem.getInputStream()));

            String cmd_a;
            String cmd_b;
            String cmd_v[];

            while(true){

                if(teclado.hasNextLine())
                {
                    cmd_a = teclado.nextLine();
                }
                else
                {
                    cmd_a = null;
                }
                
                if(entrada_mensagem.ready())
                {
                    cmd_b = entrada_mensagem.readLine();
                }
                else
                {
                    cmd_b = null;
                }
                if( cmd_a != null | cmd_b != null){
                    System.out.println("---------------------------");
                }
            
                if(cmd_a != null)
                {
                    if(cmd_a.startsWith("file:")){
                        cmd_v = cmd_a.split(":");
                        String filename[] = cmd_v[1].split("/");
                        //mensagens
                        saida_mensagem.println("filename:" + filename[filename.length - 1]);
                        Cliente.manda_arquivo(cmd_v[1]);
                    }
                    else
                    {
                        if(cmd_a.equals("exit")){
                            saida_mensagem.println(cmd_a);
                            break;
                        }
                        else{
                            saida_mensagem.println(cmd_a);
                        }
                    }
                }

                if(cmd_b != null)
                {
                    System.out.println(cmd_b);
                }
            }

            saida_mensagem.close();
            entrada_mensagem.close();
            teclado.close();

        } catch (Exception e) {
            System.out.println("não foi possivel conectar-se ao servidor");
            System.exit(0);
        }
    }

    public static void manda_arquivo(String caminho)
    {
        Socket cliente_arquivo;

        DataOutputStream saida_arquivo;

        FileInputStream arquivo_in;
        System.out.println(caminho);
        try
        {
            cliente_arquivo = new Socket(Cliente.ip, Cliente.porta_arquivo);
            saida_arquivo = new DataOutputStream(cliente_arquivo.getOutputStream());
            arquivo_in = new FileInputStream(caminho);
            
            int count;
            byte[] buffer = new byte[4096];
            
            while(arquivo_in.read(buffer) > 0)
            {
                saida_arquivo.write(buffer);
            }
            
            System.out.println("Enviando arquivo.");
            arquivo_in.close();
            cliente_arquivo.close();
        }
        catch( Exception e)
        {
            e.printStackTrace();
            System.out.println("Arquivo não enviado.");
        }

        
    }
}