package guru.learningjournal.examples.kafka.windowcount.services;

import guru.learningjournal.examples.kafka.windowcount.bindings.InvoiceListenerBinding;
import guru.learningjournal.examples.kafka.windowcount.model.SimpleInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Window;
import org.apache.kafka.streams.kstream.Windows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Log4j2
@Service
@EnableBinding(InvoiceListenerBinding.class)
public class InvoiceListenerService {

    @StreamListener("invoice-input-channel")
    public void process(KStream<String, SimpleInvoice> input) {
        //start input stream and apply peek method to print input message key and created time to the log
        input.peek((k, v) -> log.info("Key = " + k + " Created Time = " //convert created time to readable string
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
                //group by store id
                .groupByKey()
                //set the 5 minutes window
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)))
                .count()
                //convert ktable to kstream
                .toStream()
                //loop data to create the output (Store ID, Time Window)
                .foreach((k, v) -> log.info(
                        "StoreID: " + k.key() +
                                " Window start: " +
                                Instant.ofEpochMilli(k.window().start())
                                        .atOffset(ZoneOffset.UTC) +
                                " Window end: " +
                                Instant.ofEpochMilli(k.window().end())
                                        .atOffset(ZoneOffset.UTC) +
                                " Count: " + v +
                                " Window#: " + k.window().hashCode()
                ));

    }
}
