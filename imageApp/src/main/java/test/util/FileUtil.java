/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author alumne
 */
public class FileUtil {
    
    
    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.delete(path);
            System.out.println("File deleted succesfully");
        } catch (IOException e) {
            System.err.println("Unable to delete the file:" + e.getMessage());
        }
    }
    
    public static void saveFile(String savePath, InputStream inputStream) {
        int read;
        try {
            OutputStream out = new FileOutputStream(new File(savePath));
            InputStream filecontent = inputStream;

            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            System.err.println("Unable to save file" + e.getMessage());
        }
        
    }
}
