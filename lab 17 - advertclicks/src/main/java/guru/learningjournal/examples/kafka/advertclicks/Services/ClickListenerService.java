package guru.learningjournal.examples.kafka.advertclicks.Services;

import guru.learningjournal.examples.kafka.advertclicks.bindings.ClicksListenerBinding;
import guru.learningjournal.examples.kafka.advertclicks.models.AdClick;
import guru.learningjournal.examples.kafka.advertclicks.models.AdInventories;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

@Log4j2
@Service //Listener Service
@EnableBinding(ClicksListenerBinding.class)
public class ClickListenerService {

    @StreamListener
    //We are reading all advert inventories into a Global KTable and read all ad clicks into a standard KStream
    public void process(@Input("inventories-channel") GlobalKTable<String, AdInventories> inventory,
                        @Input("clicks-channel") KStream<String, AdClick> click) {

        click.foreach((k, v) -> log.info("Click Key: {}, Value: {}",k, v));

        //Listener Body
        click.join(inventory, //Join the clicks with the inventories
                (clickKey, clickValue) -> clickKey, //you join the global KTable with a foreign key, but in this case we return the same key
                (clickValue, inventoryValue) -> inventoryValue)
                .groupBy((joinedKey, joinedValue) -> joinedValue.getNewsType(),
                        Grouped.with(Serdes.String(), //custom serde for error handling
                                new JsonSerde<>(AdInventories.class)))
                .count()
                .toStream()
                .foreach((k, v) -> log.info("Click Key: {}, Value: {}",k, v));
    }
}
