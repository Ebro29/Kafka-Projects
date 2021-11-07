/*
Ebrahim Shahid
Kafka Lab 15 - KStream to KStream join with Kafka and Maven

Problem - The mWallet initiates a transaction and sends it to the transaction event in the kafka
cluster. the wallet sends the OTP (5 min time limit) to your cell # which when entered makes the wallet
respond back to iNet with a transaction ID. This results in 2 events, one to make the OTP and another
when the OTP gets used. Write an application that joins both streams.

Spring dependencies - Cloud Stream, Spring for Apache Kafka Streams, Lombok
Data Model - requires payment request and payment confirmation - Classes found in model package
Compile with Maven and run kafka-scripts.sh commands in Ubuntu terminal
 */

package guru.learningjournal.examples.kafka.otpvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OtpValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpValidationApplication.class, args);
	}

}
