package org.example;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/pubsub")
public class ad {

    @Autowired
    private Publisher publisher;

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody String message) {
        try {
            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            // Publish asynchronously
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);

            // Block to get the result (for API response)
            String messageId = messageIdFuture.get();  // Can throw InterruptedException or ExecutionException

            return ResponseEntity.ok("Message published with ID: " + messageId);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Log full error
            return ResponseEntity.status(500).body("Publishing failed: " + e.getMessage());
        }
    }

}
