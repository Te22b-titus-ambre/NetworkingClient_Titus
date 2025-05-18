import java.io.*;
import java.net.*;

public class NetworkingClient {

    public static void main(String[] args) {
        Socket client = null;

        // Jag har valt portnummer 23456
        int portnumber = 23456;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 10; i++) {
            try {
                // Skapa socket mot lokal server
                client = new Socket(InetAddress.getLocalHost(), portnumber);
                System.out.println("Client socket skapad: " + client);

                // Strömmar mot server
                PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
                BufferedReader br = new BufferedReader(
                                        new InputStreamReader(
                                            client.getInputStream()));

                // Läs in meddelande från användaren
                System.out.print("Skriv ditt namn (eller 'Bye' för att avsluta): ");
                String msg = stdIn.readLine().trim();

                // Skicka till server och skriv ut svar
                pw.println(msg);
                String svar = br.readLine();
                System.out.println("Svar från servern: " + svar);

                // Stäng strömmar och socket
                pw.close();
                br.close();
                client.close();

                // Avsluta loopen om användaren skrev "Bye"
                if (msg.equalsIgnoreCase("Bye")) {
                    System.out.println("Avslutar klienten.");
                    break;
                }

            } catch (IOException ie) {
                System.err.println("I/O-error: " + ie.getMessage());
            }
        }
    }
}
