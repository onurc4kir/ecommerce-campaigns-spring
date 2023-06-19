package dev.onurcakir.ecommerce.logging;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class LogCleanupTask {
    private static final int MAX_LOG_DAYS = 5;

    @Scheduled(cron = "0 0 0 * * *") // Every day at 00:00
    public void cleanupLogs() {
        String logFolderPath = "logs/";
        File logFolder = new File(logFolderPath);

        if (logFolder.exists() && logFolder.isDirectory()) {
            File[] logFiles = logFolder.listFiles();

            if (logFiles != null) {
                LocalDate cutoffDate = LocalDate.now().minusDays(MAX_LOG_DAYS);

                for (File logFile : logFiles) {
                    String fileName = logFile.getName();
                    LocalDate logFileDate = LocalDate.parse(fileName.substring(0, 10));

                    if (logFileDate.isBefore(cutoffDate)) {
                        logFile.delete();
                    }
                }
            }
        }
    }
}