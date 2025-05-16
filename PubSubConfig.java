package org.example;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.*;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class PubSubConfig {

    private final String emulatorHost = "localhost:8085"; // Emulator address
    private final String projectId = "test-project";
    private final String topicId = "one";
    private final String subscriptionId = "my-subscription";

    @Bean
    public ManagedChannel managedChannel() {
        // Connect to the Pub/Sub emulator using gRPC
        return ManagedChannelBuilder.forTarget(emulatorHost)
                .usePlaintext() // Disable encryption for the emulator
                .build();
    }

    @Bean
    public TransportChannelProvider transportChannelProvider(ManagedChannel managedChannel) {
        // Create a transport channel provider using the managed channel
        return FixedTransportChannelProvider.create(
                GrpcTransportChannel.create(managedChannel));
    }

    @Bean
    public CredentialsProvider credentialsProvider() {
        // No credentials needed for the emulator
        return NoCredentialsProvider.create();
    }

    @Bean
    public TopicAdminClient topicAdminClient(
            TransportChannelProvider transportChannelProvider,
            CredentialsProvider credentialsProvider) throws IOException {
        // Topic Admin Client to manage topics
        return TopicAdminClient.create(
                TopicAdminSettings.newBuilder()
                        .setTransportChannelProvider(transportChannelProvider)
                        .setCredentialsProvider(credentialsProvider)
                        .build());
    }

    @Bean
    public SubscriptionAdminClient subscriptionAdminClient(
            TransportChannelProvider transportChannelProvider,
            CredentialsProvider credentialsProvider) throws IOException {
        // Subscription Admin Client to manage subscriptions
        return SubscriptionAdminClient.create(
                SubscriptionAdminSettings.newBuilder()
                        .setTransportChannelProvider(transportChannelProvider)
                        .setCredentialsProvider(credentialsProvider)
                        .build());
    }

    @Bean
    public Publisher publisher(TransportChannelProvider transportChannelProvider,
                               CredentialsProvider credentialsProvider) throws IOException {
        // Publisher to publish messages to the topic
        TopicName topicName = TopicName.of(projectId, topicId);
        return Publisher.newBuilder(topicName)
                .setCredentialsProvider(credentialsProvider)
                .setChannelProvider(transportChannelProvider)
                .build();
    }

    @Bean
    public Subscriber subscriber(TransportChannelProvider transportChannelProvider,
                                 CredentialsProvider credentialsProvider) {
        // Subscriber to listen to messages from the subscription
        SubscriptionName subscriptionName = SubscriptionName.of(projectId, subscriptionId);

        return Subscriber.newBuilder(String.valueOf(subscriptionName), (PubsubMessage message, AckReplyConsumer consumer) -> {
                    // Message handling logic
                    System.out.println("Received message: " + message.getData().toStringUtf8());
                    consumer.ack();
                })
                .setChannelProvider(transportChannelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build();
    }
}
