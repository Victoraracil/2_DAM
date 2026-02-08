import com.dam.model.Bid;
import com.dam.model.GameStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class AuctionController {

    // ---- LOGIN SETTINGS ----
    @FXML private TextField txtHost;
    @FXML private TextField txtPort;
    @FXML private TextField txtUser;
    @FXML private javafx.scene.control.CheckBox chkSpectator;
    @FXML private Button btnConnect;

    // ---- ITEM DISPLAY ----
    @FXML private Label lblItemName;
    @FXML private Label lblDescription;

    // ---- ESTADO SUBASTA ----
    @FXML private Label lblPrice;
    @FXML private Label lblTime;
    @FXML private Label lblLeader;

    // ---- ZONA DE PUJA ----
    @FXML private TextField txtBid;
    @FXML private Button btnBid;
    @FXML private Label lblStatus;

    private SSLSocket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Thread timerThread;
    private volatile boolean runningTimer = false;


    @FXML
    public void initialize() {
        // Al arrancar, desactivamos pujas hasta conectarnos
        btnBid.setDisable(true);
        lblStatus.setText("Not connected");
    }

    @FXML
    public void connect() {
        try {
            // Decirle a Java que confíe en nuestro servidor
            System.setProperty("javax.net.ssl.trustStore", "client.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");

            SSLSocketFactory factory =
                    (SSLSocketFactory) SSLSocketFactory.getDefault();

            socket = (SSLSocket) factory.createSocket(
                    txtHost.getText(),
                    Integer.parseInt(txtPort.getText())
            );

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            lblStatus.setText("Connected securely");
            btnConnect.setDisable(true);

            // Solo habilitamos pujas si no es espectador
            btnBid.setDisable(chkSpectator.isSelected());
            if (chkSpectator.isSelected()) {
                lblStatus.setText("Connected as SPECTATOR");
            }
            listenServer();

        } catch (Exception e) {
            lblStatus.setText("Connection failed");
            e.printStackTrace();
        }
    }

    @FXML
    public void placeBid() {
        try {
            double amount = Double.parseDouble(txtBid.getText());

            Bid bid = new Bid(1, txtUser.getText(), amount);
            out.writeObject(bid);
            out.flush();
            out.reset();

            lblStatus.setText("Bid sent: " + amount + "€");

        } catch (Exception e) {
            lblStatus.setText("Invalid bid");
        }
    }


    private void listenServer() {
        new Thread(() -> {
            try {
                while (true) {
                    Object obj = in.readObject();

                    if (obj instanceof GameStatus gs) {
                        // Las actualizaciones de UI deben ir dentro de Platform.runLater
                        javafx.application.Platform.runLater(() -> {
                            lblItemName.setText(gs.getCurrentItem().getName());
                            lblDescription.setText(
                                    gs.getCurrentItem().getDescription()
                            );
                            lblPrice.setText(
                                    "Current Price: " + gs.getCurrentHighestBid() + "€"
                            );
                            lblLeader.setText(
                                    "Leader: " + gs.getWinnerName()
                            );
                            lblStatus.setText(gs.getMessage());
                        });
                        // Iniciar contador SOLO cuando empieza una nueva ronda
                        if (gs.getMessage().contains("NUEVA SUBASTA")) {
                            startTimer(30);
                        }

                        // Reiniciar el timer también cuando haya nueva puja
                        if (gs.getMessage().contains("Nueva puja")) {
                            startTimer(10);
                        }


                        if (gs.getMessage().contains("SUBASTA TERMINADA")) {
                            runningTimer = false;
                        }


                    }
                }
            } catch (Exception e) {
                System.out.println("Server disconnected");
            }
        }).start();
    }

    private void startTimer(int seconds) {
        // Si ya hay un contador corriendo, lo paramos
        runningTimer = false;
        if (timerThread != null) {
            try {
                timerThread.interrupt();
            } catch (Exception ignored) {}
        }

        runningTimer = true;

        timerThread = new Thread(() -> {
            int timeLeft = seconds;

            while (timeLeft >= 0 && runningTimer) {
                final int displayTime = timeLeft;

                javafx.application.Platform.runLater(() ->
                        lblTime.setText("Time Left: " + displayTime)
                );

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }

                timeLeft--;
            }
        });

        timerThread.setDaemon(true);
        timerThread.start();
    }







}

