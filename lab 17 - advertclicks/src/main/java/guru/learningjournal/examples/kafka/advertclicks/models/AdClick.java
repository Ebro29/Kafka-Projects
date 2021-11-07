
package guru.learningjournal.examples.kafka.advertclicks.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdClick { //when an ad is clicked, pass the inventory id

    @JsonProperty("InventoryID")
    private String inventoryID;

}
