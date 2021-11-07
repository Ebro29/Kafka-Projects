package guru.learningjournal.examples.kafka.otpvalidation.services;

import guru.learningjournal.examples.kafka.otpvalidation.bindings.OTPListenerBinding;
import guru.learningjournal.examples.kafka.otpvalidation.model.PaymentConfirmation;
import guru.learningjournal.examples.kafka.otpvalidation.model.PaymentRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

//Stream listener
@Log4j2
@Service
@EnableBinding(OTPListenerBinding.class)
public class OTPValidationService {

    @Autowired
    private RecordBuilder recordBuilder;

    //2 Kstreams share a transaction ID key and amount of partitions (co-partitioned)
    @StreamListener //receive 2 inputs: payment request and payment confirmation
    public void process(@Input("payment-request-channel") KStream<String, PaymentRequest> request,
                        @Input("payment-confirmation-channel") KStream<String, PaymentConfirmation> confirmation) {

        //2 log entries that help read the input value
        request.foreach((k, v) -> log.info("Request Key = " + k + " Created Time = "
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)));

        confirmation.foreach((k, v) -> log.info("Confirmation Key = " + k + " Created Time = "
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)));


        request.join(confirmation, //request the other KStream to join it
                (r, c) -> recordBuilder.getTransactionStatus(r, c), //value joiner argument with 2 lambda (left: PaymentRequest, right: PaymentConfirmation)
                JoinWindows.of(Duration.ofMinutes(5)), //5 minutes window for One Time Passcode to get used or expire
                StreamJoined.with(Serdes.String(), //common key among events serde
                        new JsonSerde<>(PaymentRequest.class), //Payment request serde
                        new JsonSerde<>(PaymentConfirmation.class))) //payment confirmation serde
                //print the records iteratively
                .foreach((k, v) -> log.info("Transaction ID = " + k + " Status = " + v.getStatus()));

    }
}
