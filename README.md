# Implementation of the Publisher & Subscriber pattern (FlowAPI & Reactor)

# 1.FlowAPI

a) Java Flow API Synchronous

Когда subscriber требует 100 мс для обработки, publisher блокируется в течение этого времени. Следующее значение будет опубликовано только после завершения обработки подписчика для предыдущего значения. Все происходит в одном потоке.

<img width="547" alt="image" src="https://user-images.githubusercontent.com/101212012/199703112-a50c7956-6a5d-4f76-a7a8-70a8e0f3c03f.png">

b) Java Flow API Asynchronous

В output имена потоков для publisher и subscriber различны. Publisher не ждет, пока subscriber завершит обработку. SubmissionPublisher имеет буфер по умолчанию 256, поэтому до 256 значений publisher не блокируется. После 256 буфер заполнен и publisher блокируется до тех пор, пока буфер не освободится.

<img width="545" alt="image" src="https://user-images.githubusercontent.com/101212012/199710102-0be6000f-32da-4b65-bf90-9b38075e6663.png">


с) Java Flow API Backpressure handling (Buffer strategy)

Пока буфер не заполнится (до 16 значений), publisher не будет заблокирован, а публикация значений продолжится в неблокирующем режиме. Но после заполнения буфера publisher начнет блокироваться до тех пор, пока subscriber не использует предыдущие значения.

<img width="545" alt="image" src="https://user-images.githubusercontent.com/101212012/199710607-6df75013-e077-4083-b947-d7bc338113c4.png">


d) Java Flow API Backpressure handling (Drop strategy)

Subscriber не может потреблять значения со скоростью publisher. Поэтому вызовем обработчика ошибок, чтобы subscriber знал об отброшенных значениях.

<img width="545" alt="image" src="https://user-images.githubusercontent.com/101212012/199709494-5ed418fa-76a8-460c-82b7-5b422bf819d1.png">


# 2. Project Reactor

a) Reactor Synchronous

Когда subscriber требует 100 мс для обработки, publisher блокируется в течение этого времени. Следующее значение будет опубликовано только после завершения обработки подписчика для предыдущего значения. Все происходит в одном потоке.

<img width="542" alt="image" src="https://user-images.githubusercontent.com/101212012/199707006-f44f059e-4e6c-4e52-a8e9-7a2f75edf579.png">

b) Reactor Asynchronous

Имена потоков для издателя и подписчика различаются. Также publisher не ждет, пока subscriber закончит обработку.

<img width="544" alt="image" src="https://user-images.githubusercontent.com/101212012/199707429-b0867848-6002-414b-bbe2-dce277289b76.png">

с) Reactor Parallel processing

Если необходимо выполнить некоторую обработку большого набора исходящих данных, то ее можно перевести в параллельную операцию, а затем, после завершения, ее можно объединить обратно

<img width="543" alt="image" src="https://user-images.githubusercontent.com/101212012/199707793-d7490805-bdcf-4c46-8521-726cf2592e0c.png">

