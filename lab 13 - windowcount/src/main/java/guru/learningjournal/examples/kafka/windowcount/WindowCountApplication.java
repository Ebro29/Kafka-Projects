/*
Ebrahim Shahid
Kafka Lab 13 - Window Count using Apache Kafka and Maven
Purpose of code - Display input count by 5 minutes
InvoiceTimeExtractor overrides extract method in class InvoiceTimeExtractor
 */

package guru.learningjournal.examples.kafka.windowcount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WindowCountApplication {

	public static void main(String[] args) {
		SpringApplication.run(WindowCountApplication.class, args);
	}

}
