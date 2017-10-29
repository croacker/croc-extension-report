package ru.croc.demo;

import ru.croc.demo.service.CommunicateService;
import ru.croc.demo.service.DatasourceService;

/**
 * @author AGumenyuk
 * @since 29.10.2017 14:30
 */
public class NativeMessagingApp {

    public static void main(String[] args) {
        try {
            new NativeMessagingApp().run();
            System.exit(0);
        } catch (Exception e) {
            DatasourceService.getInstance().store(e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        DatasourceService.getInstance().close();
    }

    private void run() {
        boolean stop = false;
        while(!stop){
            String requestJson = CommunicateService.getInstance().readMessage(System.in);
            persist(requestJson);
        }
    }

    private void persist(String requestJson){
        DatasourceService.getInstance().store(requestJson);
    }

}
