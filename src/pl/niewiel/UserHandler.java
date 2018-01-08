package pl.niewiel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UserHandler implements Runnable {
    private Socket userSocket;
    private static int userCount = 0;
    private boolean isQuit = false;
    private PrintWriter pw;

    UserHandler(Socket userSocket) {
        this.userSocket = userSocket;
        userCount++;
    }

    @Override
    public void run() {
        try (InputStream is = userSocket.getInputStream();
             OutputStream os = userSocket.getOutputStream();
             Scanner sc = new Scanner(is);
             PrintWriter pw = new PrintWriter(os, true)) {
            pw.println("Witam na serwerze USER COUNT");
            pw.println("jesteś " + userCount + " podłączonym użytkownikiem");
            printMonit(pw);
            while (sc.hasNextLine() && !isQuit) {
                String line = sc.nextLine();
                switch (line) {
                    case "stop":
                        pw.println("serwer zostanie wyłączony");
                        System.exit(1);
                    case "quit":
                        pw.println("sesja zostanie zakończona");
                        isQuit = true;
                        userCount--;
                    case "count":
                        pw.println("liczba użytkowników: " + userCount);
                        printMonit(pw);
                    case "login":
                        StringTokenizer st = new StringTokenizer(line);
                        String t1 = st.nextToken();
                        String t2 = st.nextToken();
                        Main.loginUser(t2, userSocket);
                        printMonit(pw);
                        
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printMonit(PrintWriter pw) {
        this.pw = pw;
        pw.print(">");
    }
}
