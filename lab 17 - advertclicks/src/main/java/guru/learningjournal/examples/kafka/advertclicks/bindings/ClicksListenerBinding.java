package guru.learningjournal.examples.kafka.advertclicks.bindings;

import guru.learningjournal.examples.kafka.advertclicks.models.AdClick;
import guru.learningjournal.examples.kafka.advertclicks.models.AdInventories;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface ClicksListenerBinding { //Binder interface

    @Input("inventories-channel") //Read inventory channels into a global KTable
    GlobalKTable<String, AdInventories> inventoryInputStream();

    @Input("clicks-channel") //clicks channel is a standard KStream
    KStream<String, AdClick> clickInputStream();

}
