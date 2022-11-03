package FlowAPI;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;

public class FlowAsyncWithBackpressureBufferExample {
    public static void main(String[] args) throws InterruptedException {

        final int BUFFER = 16;

        SubmissionPublisher<Integer> publisherBkp = new SubmissionPublisher<>(ForkJoinPool.commonPool(), BUFFER);

        Subscriber<Integer> subscriberBkp = new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("Subscribed");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(Thread.currentThread().getName() + " | Received = " + item);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread().getName() + " | ERROR = "
                        + throwable.getClass().getSimpleName() + " | " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        publisherBkp.subscribe(subscriberBkp);

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
            publisherBkp.submit(i);
        }

        publisherBkp.close();

        Thread.sleep(100000);
    }
}
