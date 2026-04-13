import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
public class GameServer {
    private ServerSocket serverSocket;

    private final int requiredPlayers = 2;
    private int numPlayers;

    // Character Select
    private int p1Select;
    private boolean p1Selected;

    private int p2Select;
    private boolean p2Selected;

    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;

    public GameServer() {
        numPlayers = 0;

        p1Select = 0;
        p1Selected = false;

        p2Select = 0;
        p2Selected = false;

        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Socket created");
        } catch (IOException e) {
            System.out.println("Error in ServerSocket creation: " + e);
        }
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.acceptConnections();
    }

    public void acceptConnections() {
        try {
            System.out.println("Awaiting players...");
            while (numPlayers < requiredPlayers) {
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                numPlayers++;
                
                if (numPlayers == 1) {
                    p1Socket = socket;
                    p1ReadRunnable = new ReadFromClient(numPlayers, in);
                    p1WriteRunnable = new WriteToClient(numPlayers, out);
                } else {
                    p2Socket = socket;
                    p2ReadRunnable = new ReadFromClient(numPlayers, in);
                    p2WriteRunnable = new WriteToClient(numPlayers, out);

                    new Thread(p1ReadRunnable).start();
                    new Thread(p2ReadRunnable).start();
                    new Thread(p1WriteRunnable).start();
                    new Thread(p2WriteRunnable).start();
                }

                System.out.println("A player has joined.");
            }
        } catch (IOException e) {
            System.out.println("Error in accepting connection: " + e);
        }
    }
    private class ReadFromClient implements Runnable {
        private int playerNum;
        private DataInputStream dataIn;

        public ReadFromClient(int num, DataInputStream in) {
            playerNum = num;
            dataIn = in;
        }

        public void run() {
            try {
                while (true) {
                    if (playerNum == 1 && p1Selected) {
                        Thread.sleep(25);
                        continue;
                    }
                    if (playerNum == 2 && p2Selected) {
                        Thread.sleep(25);
                        continue;
                    }

                    int selected = playerNum == 1 ? p1Select : p2Select;
                    switch (dataIn.readInt()) {
                        case KeyEvent.VK_UP:
                            selected -= 2;
                            if (selected < 0) {
                                selected += CharacterSelectScreen.numCharas;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            selected += 2;
                            if (selected > CharacterSelectScreen.numCharas - 1) {
                                selected -= CharacterSelectScreen.numCharas;
                            }
                            break;

                            case KeyEvent.VK_LEFT:
                                selected--;
                                if (selected < 0) {
                                selected = CharacterSelectScreen.numCharas - 1;
                            }
                            break;
                        
                        case KeyEvent.VK_RIGHT:
                            selected++;
                            if (selected > CharacterSelectScreen.numCharas - 1) {
                                selected = 0;
                            }
                            break;
                            
                        default:
                            break;
                        }
                        
                        if (playerNum == 1) { p1Select = selected; } else { p2Select = selected; }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private class WriteToClient implements Runnable {
        private int playerNum;
        private DataOutputStream dataOut;

        public WriteToClient(int num, DataOutputStream out) {
            playerNum = num;
            dataOut = out;
        }

        public void run() {
            try {
                int p1SelectLast = -1;
                int p2SelectLast = -1;

                while (true) {
                    if (p1Select != p1SelectLast || p2Select != p2SelectLast) {
                        dataOut.writeInt(playerNum == 1 ? p1Select : p2Select);
                        dataOut.writeInt(playerNum == 1 ? p2Select : p1Select);
                        dataOut.flush();

                        p1SelectLast = p1Select;
                        p2SelectLast = p2Select;
                    }
                    Thread.sleep(25);
                }
            } catch (Exception e) { }
        }
    }

}
