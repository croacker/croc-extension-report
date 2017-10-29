package ru.croc.demo.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author AGumenyuk
 * @since 25.10.2017 18:59
 */
public class DatasourceService {

    public static final String CR_LF = "\r\n";

    private static DatasourceService instance;

    private Writer writer;

    public static DatasourceService getInstance() {
        if (instance == null) {
            instance = new DatasourceService();
        }
        return instance;
    }

    public void store(String json) {
        Writer writer = getWriter();
        try {
            writer.append(json);
            writer.append(CR_LF);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        flush();
    }

    public void close() {
        if(writer != null){
            try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }

    private Writer getWriter() {
        if(writer == null) {
            try {
                writer = new BufferedWriter(new FileWriter(getStoreFile(), true));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return writer;
    }

    private void flush(){
        try {
            getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String getStoreFile(){
        String storeFolder = getStoreFolder();
        return storeFolder + "/books.txt";
    }

    /**
     * Каталог в который будем сохранять результаты.
     *
     * @return
     */
    private String getStoreFolder() {
        String userProfile = System.getProperty("user.home");
        String storeFolder = userProfile + "/AppData/Local/Croc/DemoNativeApp/store";
        File folder = new File(storeFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return storeFolder;
    }

}
