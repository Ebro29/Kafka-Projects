/*
Ebrahim Shahid
Kafka Lab 16 - KTable to KTable join
Spring Dependencies - Cloud Stream, spring for apache kafka, lombok
Problem -
1. All your user data is streamed to a Kafka topic
2. Some sample records are provided
10001:{"Username": "Prashant", "LoginID": "10001", "LastLogin": "2019-01-01T00:00:00:02"}
3. Every time a new user registers, her details are also streamed to the system
4. Every time a user succesfully logs in to your app, you send an event to the Kafka cluster
5. Some sample records are given below
 100001: {"LoginID": "10001", "CreatedTime": "2019-02-14TI3:01:15:002"}
 */

package guru.learningjournal.examples.kafka.lastlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LastLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(LastLoginApplication.class, args);
	}

}
