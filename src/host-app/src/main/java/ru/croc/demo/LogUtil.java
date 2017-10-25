package ru.croc.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Утилитарный класс логирования. Не делайте так, помните, утилитарные классы в ООП - это плохо.
 *
 * @author AGumenyuk
 * @since 25.10.2017 18:15
 */
public class LogUtil {

    /**
     * Файл для логирования.
     */
    private static final String LOG_FILE = "c:/tmp/log/host-app.log";

    /**
     * Шаблон сообщения.
     * Момент времени, поток, сообщение.
     */
    public static final String TEMPLATE = "{0} - {1} - {2}\r\n";

    /**
     * Шаблон сообщения
     */
    public static final ThreadLocal<DateFormat> format = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
        }
    };

    /**
     *
     */
    private static FileWriter writer;

    static {
        try {
            writer = new FileWriter(new File(LOG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Добавить сообщение в лог.
     *
     * @param msg
     */
    public static void log(String msg) {
        String message = MessageFormat.format(TEMPLATE,
                format.get().format(new Date()),
                Thread.currentThread().getId(),
                msg);
        try {
            writer.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
