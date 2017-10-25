package ru.croc.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.croc.demo.dto.NativeResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author AGumenyuk
 * @since 25.10.2017 18:31
 */
public class CommunicateService {

    /**
     * Максимальный размер сообщения. ПОСМОТРЕТЬ У GOOGLE.
     * скорректирован.
     */
    private static final int MAX_MESSAGE_BYTES_COUNT = 100000;


    /**
     *
     */
    private static CommunicateService instance;

    /**
     *
     * @return
     */
    public static CommunicateService getInstance(){
        if(instance == null){
            instance = new CommunicateService();
        }
        return instance;
    }

    /**
     * Чтение сообщения от Browser extension.
     *
     * @param in входящий поток данных
     * @return строковое представление сообщения
     */
    public String readMessage(final InputStream in) {
        byte[] b = {};
        try {
            b = new byte[4];

            in.read(b); // Read the size of message
            int size = getInt(b);
            if (size == 0) {
                b = new byte[0];
                throw new InterruptedIOException("Size incoming buffer is 0. Blocked communication");
            } else if (size > MAX_MESSAGE_BYTES_COUNT) {
                b = new byte[0];
                throw new InterruptedIOException("Size incoming buffer is very big. Blocked communication");
            }

            b = new byte[size];
            in.read(b);

        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (final Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return toString(b);
    }

    /**
     * Массив байт в строку.
     *
     * @param b
     *            массив байт
     * @return
     */
    private String toString(final byte[] b) {
        String result = "";
        try {
            result = new String(b, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Отправить сообщение в Browser extension через поток вывода.
     *
     * @param message
     *            предварительно подготовленное сообщение в формате json
     * @throws IOException
     */
    public void sendMessage(final String message) throws IOException {
        System.out.write(getBytes(message.length()));
        System.out.write(message.getBytes("UTF-8"));
        System.out.flush();
    }

    /**
     * Отправить сообщение в Browser extension через поток вывода.
     *
     * @param response
     *            результат выполнения метода
     * @throws IOException
     *             ошибка вввода-вывода
     */
    public void sendResponse(final NativeResponse response) {
        ObjectMapper mapper = MapperService.getInstance().getMapper();
        StringWriter stringWriter = new StringWriter();
        try {
            mapper.writeValue(stringWriter, response);
            String responseJson = stringWriter.toString();
            sendMessage(responseJson);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Получить значение из считанных байт.
     *
     * @param bytes
     *            4 байта - длина сообщения
     * @return
     */
    public int getInt(final byte[] bytes) {
        return (bytes[3] << 24) & 0xff000000
                | (bytes[2] << 16) & 0x00ff0000
                | (bytes[1] << 8) & 0x0000ff00
                | (bytes[0] << 0) & 0x000000ff;
    }

    /**
     * Длина в байтовое представление.
     *
     * @param length
     *            длина сообщения
     * @return байтовое представление длины
     */
    public byte[] getBytes(final int length) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (length & 0xFF);
        bytes[1] = (byte) ((length >> 8) & 0xFF);
        bytes[2] = (byte) ((length >> 16) & 0xFF);
        bytes[3] = (byte) ((length >> 24) & 0xFF);
        return bytes;
    }


}
