package Reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

public class ReactorParallelProcessingExample {

    public static void main(String[] args) {

        ParallelFlux<Object> fluxParallel = Flux.create(emitter -> {

            for (int i = 0; i < 100; i++) {
                emitter.next(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            emitter.complete();
        }).parallel().runOn(Schedulers.parallel()).map(i -> {
            int number = (int) i;
            System.out.println(Thread.currentThread().getName() + " | Sending square of " + number);

            return number * number;
        });

        fluxParallel.sequential()
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " | Received square = " + i));
    }
}