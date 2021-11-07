package guru.learningjournal.examples.kafka.lastlogin.services;

import guru.learningjournal.examples.kafka.lastlogin.bindings.UserListenerBinding;
import guru.learningjournal.examples.kafka.lastlogin.model.UserDetails;
import guru.learningjournal.examples.kafka.lastlogin.model.UserLogin;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

@Log4j2
@Service
@EnableBinding(UserListenerBinding.class)
public class LoginListenerService { //Listener Class

    @StreamListener
    //Read both input channels: user master and user login as 2 KTables
    public void process(@Input("user-master-channel") KTable<String, UserDetails> users,
                        @Input("user-login-channel") KTable<String, UserLogin> logins) {

        users.toStream().foreach((k, v) -> log.info("User Key: {}, Last Login: {}, Value{}",
                k, Instant.ofEpochMilli(v.getLastLogin()).atOffset(ZoneOffset.UTC), v));

        logins.toStream().foreach((k, v) -> log.info("Login Key: {}, Last Login: {}, Value{}",
                k, Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC), v));

        //Join the KTables: Start with left table, apply join on the right KTable
        logins.join(users, (l, u) -> {
            u.setLastLogin(l.getCreatedTime());
            return u;
        //update user's last login time with the current login time and convert the KTable to a KStream for the console
        }).toStream().foreach((k, v) -> log.info("Updated Last Login Key: {}, Last Login: {}", k,
                Instant.ofEpochMilli(v.getLastLogin()).atOffset(ZoneOffset.UTC)));

    }

}
