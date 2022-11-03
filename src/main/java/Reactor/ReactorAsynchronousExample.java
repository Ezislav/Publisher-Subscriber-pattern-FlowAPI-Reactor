package Reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ReactorAsynchronousExample {

    public static void main(String[] args) throws InterruptedException {
        Flux<Object> fluxSync = Flux.create(emitter -> {

            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                emitter.next(i);
            }
            emitter.complete();
        }).subscribeOn(Schedulers.elastic()).publishOn(Schedulers.elastic());

        fluxSync.subscribe(i -> {
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(5000);
    }
}