import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.util.Scanner;

public class ConsoleMp3Looper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Ask user for file
            System.out.print("Enter path to MP3 file: ");
            String filePath = scanner.nextLine();

            // Ask start/end time
            System.out.print("Enter start time in seconds: ");
            int startSec = scanner.nextInt();

            System.out.print("Enter end time in seconds: ");
            int endSec = scanner.nextInt();

            // Ask loop count
            System.out.print("How many times to loop: ");
            int loopCount = scanner.nextInt();

            // Play the selection in a loop
            for (int i = 0; i < loopCount; i++) {
                playSelection(filePath, startSec, endSec);
            }

            System.out.println("Done looping!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playSelection(String filePath, int startSec, int endSec) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            AdvancedPlayer player = new AdvancedPlayer(fis);

            int startFrame = startSec * 38; // rough estimate (38 frames/sec for MP3s at 128kbps)
            int endFrame = endSec * 38;

            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    // Can be used for debugging
                }
            });

            // Play only between the frames
            player.play(startFrame, endFrame);

            player.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
