package boot.dynamodb.config;

import boot.dynamodb.dao.CommentRepository;
import boot.dynamodb.util.DynamicTableNameResolver;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableDynamoDBRepositories(
//        dynamoDBMapperConfigRef = "dynamoDBMapperConfig", // todo: might be removed now
        basePackageClasses = {CommentRepository.class})
@Import(DynamicTableNameResolver.class)
public class DynamoDbConfiguration {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.region}")
    private String amazonSigningRegion;

    @Value("${amazon.aws.accesskey:#{null}}")
    private String amazonAwsAccessKey;

    @Value("${amazon.aws.secretkey:#{null}}")
    private String amazonAwsSecretKey;

    private static final Logger LOG = LogManager.getLogger(DynamoDbConfiguration.class);

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        amazonDynamoDBEndpoint, amazonSigningRegion)
                );

        if (amazonAwsAccessKey != null && amazonAwsSecretKey != null) {
            LOG.info("Using explicit accesskey and secretkey");
            clientBuilder.setCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                    amazonAwsAccessKey, amazonAwsSecretKey))
            );
        } else {
            LOG.info("Using instance profile credentials");
            clientBuilder.setCredentials(new InstanceProfileCredentialsProvider(false));
        }
        AmazonDynamoDB client = clientBuilder.build();
        LOG.info("Instantiating amazon dynamodb client: {}", client);
        return client;
    }

    // todo: remove later
//    @Bean
//    public DynamoDBMapperConfig dynamoDBMapperConfig(@Autowired DynamicTableNameResolver resolver) {
//        return new DynamoDBMapperConfig.Builder()
//                .withTableNameResolver(resolver)
//                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
//                .build();
//    }

//    @Bean
//    public DynamoDBMapper dynamoDBMapper(@Autowired AmazonDynamoDB dynamoDb,
//                                         @Autowired DynamoDBMapperConfig dynamoConfig) {
//        return new DynamoDBMapper(dynamoDb, dynamoConfig);
//    }

}
