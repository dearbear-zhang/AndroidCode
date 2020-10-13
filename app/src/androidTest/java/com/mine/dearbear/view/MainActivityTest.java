package com.mine.dearbear.view;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainActivityTest {

    @Test
    public void onCreate() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.mine.dearbear", appContext.getPackageName());
    }
}