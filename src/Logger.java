import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void log(String message) {
        try {
            File logFile = new File("logs/userlog.txt");
            logFile.getParentFile().mkdirs(); // Ensures directory exists
            FileWriter writer = new FileWriter(logFile, true); // Append mode
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
            String time = LocalDateTime.now().format(formatter);
            writer.write(time + " - " + message + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing log: " + e.getMessage());
        }
    }
}
