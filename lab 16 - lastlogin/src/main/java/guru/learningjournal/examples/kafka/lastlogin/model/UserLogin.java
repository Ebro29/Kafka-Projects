
package guru.learningjournal.examples.kafka.lastlogin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLogin {

    //2 fields of the KTable: login id and created time
    @JsonProperty("LoginID")
    private String loginID;
    @JsonProperty("CreatedTime")
    private Long createdTime;

}
