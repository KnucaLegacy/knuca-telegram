package com.theopus.telegram.handler;

import org.junit.Test;

import de.vandermeer.asciitable.AsciiTable;

public class ScheduleHandlerTest {

    @Test
    public void ascii() {
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("9:00", "Лекция");
        table.addRule();
        table.addRow(null, "Cистемний анализ");
        table.addRule();
        table.addRow("ауд. 245", "Цюцюра Т.В, Голенков У.К.");
        table.addRule();
        table.addRow(null, "КНм-51");
        table.addRule();

        String render = table.render(40);
        System.out.println(render);

    }
}