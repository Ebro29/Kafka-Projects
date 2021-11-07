/*
Ebrahim Shahid
Kafka Lab 14 - Finding Session Window with Maven and kafka
Spring dependencies - spring for apache kafka streams, lombok, cloud stream
4 steps
1. Add dependencies in Spring.io
2. Create yaml file and config input/output channels
3. Create Binding interface in ClickListerBinding.java
4. Define data model in UserClick.java
5. Test program by sending messages via confluent local services start in an Ubuntu 20.0.4 terminal
 */

package guru.learningjournal.examples.kafka.sessionwindow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SessionWindowApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionWindowApplication.class, args);
	}

}
