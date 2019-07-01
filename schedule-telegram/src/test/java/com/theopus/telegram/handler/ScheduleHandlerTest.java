package com.theopus.telegram.handler;

import static com.inamik.text.tables.Cell.Functions.BOTTOM_ALIGN;
import static com.inamik.text.tables.Cell.Functions.HORIZONTAL_CENTER;
import static com.inamik.text.tables.Cell.Functions.LEFT_ALIGN;
import static com.inamik.text.tables.Cell.Functions.RIGHT_ALIGN;
import static com.inamik.text.tables.Cell.Functions.TOP_ALIGN;
import static com.inamik.text.tables.Cell.Functions.VERTICAL_CENTER;

import org.junit.Test;

import com.inamik.text.tables.Cell;
import com.inamik.text.tables.GridTable;
import com.inamik.text.tables.grid.Border;
import com.inamik.text.tables.grid.Util;

import de.vandermeer.asciitable.AsciiTable;

public class ScheduleHandlerTest {

    @Test
    public void name() {
        int height = 1;
        int width = 1;
        GridTable g = GridTable.of(3, 4)
                .put(0, 0, Cell.of("Left", "Top"))
                .put(0, 1, Cell.of("Center", "Top"))
                .put(0, 2, Cell.of("Right", "Top"))

                .put(1, 0, Cell.of("Left", "Center"))
                .put(1, 1, Cell.of("Center", "Center"))
                .put(1, 2, Cell.of("Right", "Center"))

                .put(2, 0, Cell.of("Left", "Bottom"))
                .put(2, 1, Cell.of("Center", "Bottom"))
                .put(2, 2, Cell.of("Right", "Bottom"))

                .applyToRow(0, TOP_ALIGN.withHeight(height))
                .applyToRow(1, VERTICAL_CENTER.withHeight(height))
                .applyToRow(2, BOTTOM_ALIGN.withHeight(height))

                .apply(0, 0, LEFT_ALIGN.withWidth(width).withChar('^'))
                .apply(1, 0, LEFT_ALIGN)
                .apply(2, 0, LEFT_ALIGN)

                .apply(0, 1, HORIZONTAL_CENTER.withWidth(width))
                .apply(1, 1, HORIZONTAL_CENTER.withChar('.'))
                .apply(2, 1, HORIZONTAL_CENTER)

                .apply(0, 2, RIGHT_ALIGN.withWidth(width))
                .apply(1, 2, RIGHT_ALIGN)
                .apply(2, 2, RIGHT_ALIGN.withChar('_'));


        g = Border.DOUBLE_LINE.apply(g);
        Util.print(g);
    }

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