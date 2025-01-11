package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.utils.Timer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimerTests extends AbstractHeadlessGdxTest{
    //tests that aren't setTimerTest() assume setTimer() works and tests assume getTimeLeft() works
    @Test
    public void setTimerTest() {
        Timer timer = new Timer();
        timer.setTimer(100000);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long expectedOut = 90000;
        long actualOut = timer.getTimeLeft();
        assertEquals(expectedOut, actualOut, 200, "Timer doesn't start or run correctly");
    }

    @Test
    public void pauseAndResumeTimerTest() {
        Timer timer = new Timer();
        timer.setTimer(100000);

        assertThrows(IllegalStateException.class, timer::resumeTimer, "Timer attempts to resume when not paused");

        timer.pauseTimer();
        long expectedOut = timer.getTimeLeft();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long actualOut = timer.getTimeLeft();
        assertEquals(expectedOut, actualOut, 0.05, "Timer doesn't pause correctly");

        expectedOut = timer.getTimeLeft() - 10000;
        timer.resumeTimer();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        actualOut = timer.getTimeLeft();
        assertEquals(expectedOut, actualOut, 200, "Timer doesn't start or run correctly");
    }
}
