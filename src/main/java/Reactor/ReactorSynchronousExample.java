package Reactor;

import reactor.core.publisher.Flux;

public class ReactorSynchronousExample {

    public static void main(String[] args) {

        Flux<Object> fluxSync = Flux.create(emitter -> {

            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                emitter.next(i);
            }
            emitter.complete();
        });

        fluxSync.subscribe(i -> {
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}