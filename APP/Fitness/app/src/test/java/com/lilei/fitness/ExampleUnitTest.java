package com.lilei.fitness;

import org.junit.Test;

import static org.junit.Assert.*;

import com.lilei.fitness.utils.TimeUtila;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        System.out.println("时间戳:" + TimeUtila.Companion.getSameDay1Point());
    }
}