package FlowAPI;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class FlowAsyncWithBackpressureDropExample {

    public static void main(String[] args) throws InterruptedException {

        SubmissionPublisher<Integer> publisherBkpE = new SubmissionPublisher<>();

        Subscriber<Integer> subscriberBkpE = new Subscriber<>() {
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

        publisherBkpE.subscribe(subscriberBkpE);

        for (int i = 0; i < 500; i++) {
            System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
            publisherBkpE.offer(i, (s, a) -> {
                s.onError(new Exception("Can't handle backpressure any more. Dropping value " + a));
                return true;
            });
        }

        publisherBkpE.close();

        Thread.sleep(600000);
    }
}