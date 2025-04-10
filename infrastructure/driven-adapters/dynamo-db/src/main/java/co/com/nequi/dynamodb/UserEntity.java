package co.com.nequi.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class UserEntity {

    private Long id;
    private Long idApi;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    public UserEntity() {}

    public UserEntity(Long id, Long idApi, String email, String firstName, String lastName, String avatar) {
        this.id = id;
        this.idApi = idApi;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DynamoDbAttribute("idApi")
    public Long getIdApi() {
        return idApi;
    }

    public void setIdApi(Long idApi) {
        this.idApi = idApi;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDbAttribute("firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDbAttribute("lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDbAttribute("avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
