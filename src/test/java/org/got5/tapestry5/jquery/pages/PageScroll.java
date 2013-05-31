package org.got5.tapestry5.jquery.pages;

import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

public class PageScroll {

    @Property
    private int value;

    @Property
    private int pageNumber;

    private static final int PAGE_SIZE = 100;

    @OnEvent("nextPage")
    List<Integer> moreValues(int multiplyBy) throws InterruptedException {
        List<Integer> values = new ArrayList<Integer>();
        for (int i = pageNumber * PAGE_SIZE;
             i < pageNumber * PAGE_SIZE + PAGE_SIZE;
             ++i) {
            values.add(i * multiplyBy);
        }

        Thread.sleep(500);
        return values;
    }

}

