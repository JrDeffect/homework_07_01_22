
import java.io.*;
import java.net.*;
import java.util.*;


public class Server {

    private static String PLAYER_NAME;
    private static final String serverIp = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted client connection....");

            BufferedReader in = new BufferedReader(new InputStreamReader
                    (clientSocket.getInputStream()));
            Scanner scanner = new Scanner(in);
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            try {
                do {
                    String serverMove = getMove();
                    out.println(serverMove);
                    try {
                        PLAYER_NAME = scanner.nextLine();
                        System.out.println(PLAYER_NAME);
                    } catch (NoSuchElementException e) {
                        System.out.println
                                ("Client connection disconnected, exiting....");
                        return;
                    }
                    try {
                        String playerMove = scanner.nextLine();
                        System.out.println(playerMove);

                        String roundWin = winner(playerMove, serverMove);
                        out.println(roundWin);
                    } catch (NoSuchElementException e) {
                        System.out.println
                                ("Client connection disconnected, exiting....");
                        return;
                    }

                } while (true);
            } finally {
                System.out.println("Socket to client connection closed....");
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMove() {
        Random random = new Random();
        int randomNum = random.nextInt((3 - 0) + 0);
        String move = null;
        if (randomNum == 0) {
            move = "rock";
        } else if (randomNum == 1) {
            move = "paper";
        } else if (randomNum == 2) {
            move = "scissors";
        }
        return move;
    }

    public static String winner(String playerMove, String serverMove) {
        String winner;
        if (serverMove.equals(playerMove)) {
            winner = "Draw";
        } else if (serverMove.equals("rock") && playerMove.equals("scissors")) {
            winner = "The Server wins!";
        } else if (serverMove.equals("scissors") && playerMove.equals("paper")) {
            winner = "The Server wins!";
        } else if (serverMove.equals("paper") && playerMove.equals("rock")) {
            winner = "The Server wins!";
        } else {
            winner = PLAYER_NAME + " wins!";
        }
        return winner;
    }
}

