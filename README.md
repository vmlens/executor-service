# executor-service

An ExecutorService supporting multiple writing and a single reading thread. The service uses a specialized queue in which the writing threads needs only a read from a thread local field and a write to a volatile field to publish an event to the queue. Writing does not need "compare and swap" operations like the standard JDK concurrent queues, leading to an easier and potentially faster algorithm. A usage example is a background thread writing log events asynchronously to a file.




# Maven
```xml
<dependency>
  <groupId>com.vmlens</groupId>
  <artifactId>executor-service</artifactId>
  <version>1.0.1</version>
</dependency>
```


# Usage


Creating and starting the event bus:
```java
 eventBus = VMLensExecutors.createEventBus();
		 consumer = eventBus.newConsumer(); 
		 eventBus.start( new EventSink()
				 {
					@Override
					public void execute(Object event) {
						// TODO Auto-generated method stub
						
					}
					@Override
					public void close() {
						// TODO Auto-generated method stub
						
					}
					@Override
					public void onWait() {
						// TODO Auto-generated method stub
						
					}
			 
				 }, new ThreadFactory()
				 {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r);
					}
					 
				 });


```

Writing to the event bus:
```java
	consumer.accept(  "event" );
```


# License
executor-service is released under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

# Blog Entries about executor-service
 * [An algorithm for a concurrent queue using only thread local and volatile fields](http://vmlens.com/articles/lock_free_executor_service//) 2/11/2017
 * [A new high throughput java executor service](http://vmlens.com/articles/a-new-high-throughput-java-executor-service/) 12/09/2016
