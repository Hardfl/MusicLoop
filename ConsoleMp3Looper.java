import java.io.File;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConsoleMp3Looper extends Application {
    private static String filePath;
    private static double startTime;
    private static double endTime;
    private static int loops;

    @Override
    public void start(Stage stage) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found!");
                return;
            }

            Media media = new Media(file.toURI().toString());
            MediaPlayer player = new MediaPlayer(media);

            player.setOnReady(() -> {
                double songLength = media.getDuration().toSeconds();

                if (endTime <= 0 || endTime > songLength) {
                    endTime = songLength;
                }
                if (startTime < 0 || startTime >= endTime) {
                    startTime = 0;
                }

                player.setStartTime(Duration.seconds(startTime));
                player.setStopTime(Duration.seconds(endTime));

                if (loops == 0) {
                    player.setCycleCount(MediaPlayer.INDEFINITE);
                    System.out.println("Looping forever...");
                } else {
                    player.setCycleCount(loops);
                    System.out.println("Looping " + loops + " times...");
                }

                System.out.println("Playing from " + formatTime(startTime) +
                                   " to " + formatTime(endTime));
                player.play();
            });

            stage.setTitle("Console MP3 Looper");
            stage.show();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter MP3 file path: ");
        filePath = sc.nextLine();

        System.out.print("Enter start time (mm:ss or seconds): ");
        startTime = parseTime(sc.nextLine());

        System.out.print("Enter end time (mm:ss or seconds): ");
        endTime = parseTime(sc.nextLine());

        System.out.print("Enter loop count (0 = forever): ");
        loops = sc.nextInt();

        launch(args);
    }

    // parse "mm:ss" or "ss" into seconds
    private static double parseTime(String input) {
        if (input.contains(":")) {
            String[] parts = input.split(":");
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            return minutes * 60 + seconds;
        } else {
            return Double.parseDouble(input);
        }
    }

    // helper to format seconds into mm:ss
    private static String formatTime(double seconds) {
        int m = (int) seconds / 60;
        int s = (int) seconds % 60;
        return String.format("%d:%02d", m, s);
    }
}
