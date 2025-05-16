//package org.example;
//
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class PubSubController {
//
//    private final PubSubPublisherService publisherService;
//
//    public PubSubController(PubSubPublisherService publisherService) {
//        this.publisherService = publisherService;
//    }
//
//    @PostMapping("/publish")
//    public ResponseEntity<String> publish(@RequestBody String message) {
//        try {
//            publisherService.publishMessage(message);
//            return ResponseEntity.ok("Message published successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Failed to publish message: " + e.getMessage());
//        }
//    }
//}
