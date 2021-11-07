package guru.learningjournal.examples.kafka.otpvalidation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


//2 Fields - Transaction ID and Status
//The status succeeds or fails based on it's transaction ID
@Data
public class TransactionStatus {
    @JsonProperty("TransactionID")
    private String transactionID;
    @JsonProperty("Status")
    private String status;
}

