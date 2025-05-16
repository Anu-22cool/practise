//package org.example;
//
//import com.google.api.gax.core.NoCredentialsProvider;
//import com.google.cloud.pubsub.v1.Publisher;
//import com.google.protobuf.ByteString;
//import com.google.pubsub.v1.PubsubMessage;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class PubSubPublisherService {
//
//    private final Publisher publisher;
//
//    public PubSubPublisherService(Publisher publisher) {
//        this.publisher = publisher;
//    }
//
//    public void publishMessage(String message) throws ExecutionException, InterruptedException {
//        // Create PubsubMessage to publish
//        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
//                .setData(ByteString.copyFromUtf8(message))
//                .build();
//
//        // Publish message
//        publisher.publish(pubsubMessage).get();
//        System.out.println("Published message: " + message);
//    }
//}
