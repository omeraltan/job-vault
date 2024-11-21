package com.omer.candidate.utility;

import java.io.File;

public class FileUtils {
    public static void clearDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    file.delete(); // Dosyayı sil
                } else if (file.isDirectory()) {
                    clearDirectory(file.getPath()); // Alt klasörleri temizle
                    file.delete(); // Boş klasörü sil
                }
            }
        }
    }
}
