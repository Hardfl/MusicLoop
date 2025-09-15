import javazoom.jl.player.advanced.AdvancedPlayer;
import java.io.FileInputStream;
import java.util.Scanner;

public class ConsoleMp3Looper {
    private static final int FRAMES_PER_SECOND = 38; // approx. for MP3

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter path to MP3 file: ");
        String filePath = scanner.nextLine();

        System.out.print("Enter start time (seconds): ");
        int startSec = scanner.nextInt();

        System.out.print("Enter end time (seconds): ");
        int endSec = scanner.nextInt();

        System.out.print("How many times to loop? ");
        int loops = scanner.nextInt();

        for (int i = 0; i < loops; i++) {
            playSelection(filePath, startSec, endSec);
        }

        System.out.println("Finished looping!");
    }

    private static void playSelection(String filePath, int startSec, int endSec) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            AdvancedPlayer player = new AdvancedPlayer(fis);

            int startFrame = startSec * FRAMES_PER_SECOND;
            int endFrame = endSec * FRAMES_PER_SECOND;

            player.play(startFrame, endFrame);

            player.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
