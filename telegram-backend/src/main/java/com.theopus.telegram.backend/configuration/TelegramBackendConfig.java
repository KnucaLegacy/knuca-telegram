package com.theopus.telegram.backend.configuration;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theopus.telegram.backend.repository.ChatRepository;
import com.theopus.telegram.backend.repository.MapDbChatRepository;
import com.theopus.telegram.backend.repository.MapDbUserRepository;
import com.theopus.telegram.backend.repository.UserRepository;
import com.theopus.telegram.bot.MessageSender;

@Configuration
public class TelegramBackendConfig {

    @Value("${telegram.bot.db.file}")
    private String dbFile;

    @Value("${telegram.bot.send.rate.per.sec}")
    private Integer rate;


    @Bean(destroyMethod = "close")
    public DB db() {
        System.out.println(dbFile);
        return DBMaker
                .memoryDB()
//                .fileDB(dbFile)
//                .fileMmapEnableIfSupported()
                .make();
    }

    @Bean
    public UserRepository userRepo(DB db, ObjectMapper mapper) {
        return new MapDbUserRepository(db, mapper);
    }

    @Bean
    public ChatRepository chatRepo(DB db, ObjectMapper mapper) {
        return new MapDbChatRepository(db, mapper);
    }


    @Bean(destroyMethod = "close")
    public MessageSender sender() {
        return new MessageSender(rate);
    }

}
