#Reactive Programming
##### Definition: Reactive programming is programming with asynchronous data streams. by Abraham Lincoln, 1863

## Vertx

##Event Looping 
### Single Thread Reactor Pattern x Multi-Reactor Pattern
In a standard reactor implementation there is a single event loop thread which runs around in a loop delivering all events to all handlers as they arrive.
https://vertx.io/docs/vertx-core/java/

##Async coordination
Can be achieved with Vert.x futures.
**CompositeFuture.all** takes several futures args(up to 6) and returns a future that is <succeded> when all futures are and failed when at least one of the futures is failed.
Note: While *all* composition waits until all futures are successful(or one fails), the *any* composition waits for the first succeeded future.
The **join** composition waits until all futures are completed, either with a success or a failure.
**compose** is a sequential composition. https://vertx.io/docs/vertx-core/java/#_sequential_composition

##Verticles
###Standart Verticles 
-> Area assigned an in event loop thread when created. So Vertx worry about the threading and scaling. No more worrying about synchronized and volatile anu more and also avoid many
other cases of race conditions and deadlock.

###Worker Verticles
-> Run from Vertx worker thread pool. Are designed for calling clocking code, as they won't block any event loops.
Worker verticle are never executed concurrently by Vert.x by more than one Thread, but can executed by different threads at different times.

##Event Bus (https://vertx.io/docs/vertx-core/java/#event_bus)
The event bus is the **nervous system** of Vert.x
There is a single event bus instance for every Vert.x instance.
The event bus supports publish/subscribe, point-to-point, and request-response messaging.

####Addressing
Messages are sent on the event bus to an address

####Handlers
- Messages are received by handlers. You register a handler at an address.
- Many different handlers can be registered at the same address.
- A single handler can be registered at many different addresses.

####Publish / subscribe messaging
- The event bus supports **publishing** messages.
- Messages are published to an address. Publishing means delivering the message to all handlers that are registered at that address.
- This is the familiar publish/subscribe messaging pattern.

####Point-to-point and Request-Response messaging
- The event bus also supports point-to-point messaging.
- Messages are sent to an address. Vert.x will then route them to just one of the handlers registered at that address.
- If there is more than one handler registered at the address, one will be chosen using a non-strict round-robin algorithm.
- With point-to-point messaging, an optional reply handler can be specified when sending the message.
- When a message is received by a recipient, and has been handled, the recipient can optionally decide to reply to the message. If they do so, the reply handler will be called.
- When the reply is received back by the sender, it too can be replied to. This can be repeated ad infinitum, and allows a dialog to be set up between two different verticles.
- This is a common messaging pattern called the request-response pattern.


####Best-effort delivery
Vert.x does its best to deliver messages and won’t consciously throw them away. This is called best-effort delivery.
- However, in case of failure of all or parts of the event bus, there is a possibility messages might be lost.
- If your application cares about lost messages, you should code your handlers to be idempotent, and your senders to retry after recovery.

##Cluster managers
In Vert.x a cluster manager is used for various functions including:
- Discovery and group membership of Vert.x nodes in a cluster
- Maintaining cluster wide topic subscriber lists (so we know which nodes are interested in which event bus addresses)
- Distributed Map support
- Distributed Locks
- Distributed Counters



##Shared Data API
As its name suggests, the SharedData API allows you to safely share data between:
- different parts of your application, or
- different applications in the same Vert.x instance, or
- different applications across a cluster of Vert.x instances.

In practice, it provides:
- synchronous maps (local-only)
- asynchronous maps
- asynchronous locks
- asynchronous counters
