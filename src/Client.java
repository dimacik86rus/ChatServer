import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;

    // создаем удобные средства ввода и вывода
    Scanner in;
    PrintStream out;
    ChatServer server;

    public Client(Socket socket, ChatServer server) {

        this.socket = socket;
        this.server = server;
        // запускаем поток
        new Thread(this).start();
    }

    void receve(String massage){
        out.println(massage);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.print("Welcom to chat!");
            out.print("What your the name?");
            String name = in.next();
            out.println("Hello polzovatel " + name);
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sandAll(name + ":" + input );
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}