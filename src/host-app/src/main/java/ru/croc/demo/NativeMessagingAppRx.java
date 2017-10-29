package ru.croc.demo;

import ru.croc.demo.dto.NativeRequest;
import ru.croc.demo.dto.NativeResponse;
import ru.croc.demo.service.CommunicateService;
import ru.croc.demo.service.MapperService;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

import java.util.concurrent.atomic.AtomicBoolean;


public class NativeMessagingAppRx {

    private final AtomicBoolean interrompe;

    public NativeMessagingAppRx() {
        this.interrompe = new AtomicBoolean(false);
    }

    public static void main(String[] args) {
        NativeMessagingAppRx app = new NativeMessagingAppRx();

        ConnectableObservable<String> observable = app.newObservable();
        observable.observeOn(Schedulers.computation()).subscribe(
                new Observer<String>() {
                    public void onCompleted() {
                    }

                    public void onError(Throwable throwable) {
                    }

                    public void onNext(String s) {

//                        log("Host received " + s);

//                        JSONObject obj = new JSONObject(s);
                        NativeRequest request = MapperService.getInstance().readValue(s, NativeRequest.class);

                        if (request.getMethod().equals("sign")) {
                            // Carrega provider
                            try {
                                for (int i = 1; i < 11; i++) {
//                                    log("Trying to load provide: " + i);
//                                    provider = Security.getProvider("SunMSCAPI");
//                                    if (provider != null) {
//                                        i = 11;
//                                    }
                                    Thread.sleep(1000);
                                }

//                                if (provider != null) {
//                                    provider.setProperty("Signature.SHA1withRSA",
//                                            "sun.security.mscapi.RSASignature$SHA1");
//                                    Security.addProvider(provider);
//                                }

//                                Security.addProvider(provider);

                                // Carrega KeyStore
//                                KeyStore ks = KeyStore.getInstance("Windows-MY", provider);
//                                ks.load(null, null);
//                                log("KeyStore of provider " + provider.getName() + " loaded.");
//                                keyStore = ks;

                                // Carrega Private Key
//                                log("Will load private key");
//                                String certAlias = getPublicKey(obj.getString("thumbprint"), false);
//                                Signature signature = Signature.getInstance("SHA256withRSA");
//                                signature.initSign((PrivateKey) keyStore.getKey(certAlias, null));
//                                signature.update(s.getBytes());

//                                log("Will sign");

                                // Assina Conteúdo
//                                String signedContent = Base64.getEncoder().encodeToString(signature.sign());

//                                JSONObject jsonObjSuccess = new JSONObject();
//                                jsonObjSuccess.put("success", true);
//                                jsonObjSuccess.put("signedContent", signedContent);

                                NativeResponse response = new NativeResponse();
//                                CommunicateService.getInstance().sendMessage(response.);
//                                app.sendMessage(jsonObjSuccess.toString());

//                                log("Sent");
                            } catch (Exception e) {
//                                log("Erro inesperado dentro da iteracao");
//                                String sl = "{\"success\":false," + "\"message\":\"" + "invalid" + "\"}";
//                                try {
//                                    app.sendMessage(sl);
//                                } catch (IOException ex) {
//                                    ex.printStackTrace();
//                                }
                                throw new RuntimeException(e.getMessage(), e);
                            }
                        } else {
                            String sl = "{\"success\":false," + "\"message\":\"" + "invalido" + "\"}";
                            try {
//                                CommunicateService.getInstance().sendMessage(response.);
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage(), e);
                            }
                        }
                    }
                }
        );

        observable.connect();

        while (!app.interrompe.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }

        System.exit(0);
    }


    private ConnectableObservable<String> newObservable() {
        ConnectableObservable<String> observable = Observable
                .create(new Observable.OnSubscribe<String>() {
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onStart();
                        try {
                            while (true) {
                                String _s = CommunicateService.getInstance().readMessage(System.in);
                                subscriber.onNext(_s);
                            }
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io()).publish();

        observable.subscribe(new Observer<String>() {
            public void onCompleted() {
//                log("App closed.");
                interrompe.set(true);
            }

            public void onError(Throwable throwable) {
//                log("Unexpected error!");
                interrompe.set(true);
            }

            public void onNext(String s) {
            }
        });

        return observable;
    }
}