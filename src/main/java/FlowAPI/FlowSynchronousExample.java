package FlowAPI;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class FlowSynchronousExample {
    public static void main(String[] args) {

        Publisher<Integer> publisherSync = subscriber -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                subscriber.onNext(i);
            }
            subscriber.onComplete();
        };

        Subscriber<Integer> subscriberSync = new Subscriber<>() {

            @Override
            public void onSubscribe(Subscription subscription) {
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(Thread.currentThread().getName() + " | Received = " + item);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
            }
        };

        publisherSync.subscribe(subscriberSync);
    }
}