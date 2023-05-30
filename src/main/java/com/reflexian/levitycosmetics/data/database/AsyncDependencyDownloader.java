package com.reflexian.levitycosmetics.data.database;

import com.reflexian.levitycosmetics.LevityCosmetics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class AsyncDependencyDownloader {
    private static final int BUFFER_SIZE = 8192;

    public static final AsyncDependencyDownloader shared = new AsyncDependencyDownloader();

    public void downloadAsync(String url, String destinationPath, Callback callback) {
        Thread downloadThread = new Thread(() -> {
            try {
                URL connectionUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) connectionUrl.openConnection();
                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    callback.onError(responseCode);
                    return;
                }

                String filename = Optional.ofNullable(connection.getHeaderField("Content-Disposition"))
                        .map(value -> value.replaceFirst(".*?filename=\"?\"?", "").replaceAll("\"?", ""))
                        .orElse(url.substring(url.lastIndexOf("/") + 1));

                String filePath = destinationPath + File.separator + filename;
                Path directoryPath = Path.of(destinationPath);
                Files.createDirectories(directoryPath);
                Path destination = Path.of(filePath);
                if (Files.exists(destination)) {
                    callback.onExist();
                    return;
                }

                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(filePath)) {

                    byte[] buffer = new byte[BUFFER_SIZE];
                    long downloadedBytes = 0;
                    long fileSize = connection.getContentLengthLong();
                    int previousProgress = 0;

                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        downloadedBytes += bytesRead;

                        int progress = (int) ((downloadedBytes * 100) / fileSize);
                        if (progress != previousProgress) {
                            LevityCosmetics.getInstance().getLogger().info("Downloading Dependency... " + progress + "%");
                            previousProgress = progress;
                        }
                    }
                }

                callback.onSuccess();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                callback.onError(0);
            }
        });

        downloadThread.start();
    }
}
