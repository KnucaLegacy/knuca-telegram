package com.theopus.telegram.bot;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class TelegramDispatcherTest {

    private TelegramDispatcher test;

    @Before
    public void setUp() throws Exception {
        test = new TelegramDispatcher();
    }

    @Test
    public void oneCommandOneMentionNoData() throws Exception {
        String originalMessage = "@ScheduleInfo_bot /search_group";
        TelegramRequest req = createReq(originalMessage);
        test.dispatch(null, req);
        assertEquals(Collections.singletonList("ScheduleInfo_bot"), req.getMentions().collect(Collectors.toList()));
        assertEquals("search_group", req.getCommand());
        assertEquals("", req.getData());
    }

    @Test
    public void oneCommandNoMention() throws Exception {
        String originalMessage = "/search_group testPayload";
        TelegramRequest req = createReq(originalMessage);
        test.dispatch(null, req);
        assertEquals(Collections.emptyList(), req.getMentions().collect(Collectors.toList()));
        assertEquals("search_group", req.getCommand());
        assertEquals("testPayload", req.getData());
    }

    @Test
    public void oneCommandTwoMention() throws Exception {
        String originalMessage = "@ScheduleBot, @Pidor2, @PIDR /search_group testPayload";
        TelegramRequest req = createReq(originalMessage);
        test.dispatch(null, req);
        assertEquals(Arrays.asList("ScheduleBot", "Pidor2", "PIDR"), req.getMentions().collect(Collectors.toList()));
        assertEquals("search_group", req.getCommand());
        assertEquals("testPayload", req.getData());
    }

    private TelegramRequest createReq(String originalMessage) {
        return new TelegramRequest(null, null, originalMessage, null);
    }
}