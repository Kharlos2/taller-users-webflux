package co.com.nequi.dynamodb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamoDBConfig {

    @Bean
    @Profile({"local"})
    public DynamoDbAsyncClient amazonDynamoDB(@Value("${aws.dynamodb.endpoint}") String endpoint,
                                              @Value("${aws.region}") String region,
                                              MetricPublisher publisher) {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
                .build();
    }

    @Bean
    @Profile({"dev", "cer", "pdn"})
    public DynamoDbAsyncClient amazonDynamoDBAsync(MetricPublisher publisher, @Value("${aws.region}") String region) {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .region(Region.of(region))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient(DynamoDbAsyncClient client) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(client)
                .build();
    }
    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1) // o tu región real, por ejemplo: Region.of("us-east-1")
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create("12345", "12345"))
                )
                .build();
    }
}
