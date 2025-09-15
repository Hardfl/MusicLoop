import javazoom.jl.player.advanced.AdvancedPlayer;
import java.io.FileInputStream;
import java.util.Scanner;

public class SimpleMp3Looper {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // Ask user for file path
            System.out.print("Enter MP3 file path: ");
            String filePath = sc.nextLine();

            // Ask user for start and end times (in seconds)
            System.out.print("Enter start time (seconds): ");
            int startSec = sc.nextInt();

            System.out.print("Enter end time (seconds): ");
            int endSec = sc.nextInt();

            System.out.print("How many loops? ");
            int loops = sc.nextInt();

            for (int i = 0; i < loops; i++) {
                playSegment(filePath, startSec, endSec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playSegment(String filePath, int startSec, int endSec) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            AdvancedPlayer player = new AdvancedPlayer(fis);

            // JLayer plays frames (approx 26 ms each)
            int startFrame = startSec * 38; // rough conversion (38 frames/sec)
            int endFrame = endSec * 38;

            player.play(startFrame, endFrame);
            player.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

