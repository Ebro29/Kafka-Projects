package guru.learningjournal.examples.kafka.windowcount.configs;

import guru.learningjournal.examples.kafka.windowcount.model.SimpleInvoice;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //Create an instance of the class at runtime
public class InvoiceTimeExtractor implements TimestampExtractor{

    @Override
    //obtain message record with extract method then extract the timestamp and return it
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        SimpleInvoice invoice = (SimpleInvoice) consumerRecord.value();
        return ((invoice.getCreatedTime() > 0) ? invoice.getCreatedTime() : prevTime);
    }

    @Bean //used with the timestamp extractor in application.yaml
    public TimestampExtractor invoiceTimesExtractor() {
        return new InvoiceTimeExtractor();
    }
}
