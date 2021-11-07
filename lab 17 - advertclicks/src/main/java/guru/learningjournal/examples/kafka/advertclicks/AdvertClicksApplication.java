/*
Ebrahim Shahid
Kafka Lab 17 - KStream to Global KTable join
Spring dependencies - cloud stream, spring for kafka streams, lombok
Problem
You have predefined locations on your website for ad placements at runtime.
These placements are called ad inventories which are tagged with Age, Content, and placement location.
Create a Kafka Streams app that computes the ad clicks by news type - sports, business, or politics news
Step 1 - Bring all the ad inventories into the infrastructure as Json events
Step 2 - Bring all the ad clicks as a real time streaming event into your kafka cluster as Json events

 */

package guru.learningjournal.examples.kafka.advertclicks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdvertClicksApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertClicksApplication.class, args);
	}

}
