package pl.niewiel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String,Socket> users=new HashMap<>();
    public static void main(String[] args) {
        try(ServerSocket ss=new ServerSocket(8189) ) {
            while (true){
                Socket s =ss.accept();
                Thread t=new Thread(new UserHandler(s));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loginUser(String userName,Socket socket){
        users.put(userName,socket);
    }

    public static Socket getSocket(String user){
        return users.get(user);
    }

    public static void usersList(PrintWriter pw){
        users.keySet().forEach(pw::println);
    }
}
