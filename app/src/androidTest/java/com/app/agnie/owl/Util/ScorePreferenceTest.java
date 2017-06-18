package com.app.agnie.owl.Util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.app.agnie.owl.Util.Entities.Score;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ScorePreferenceTest {

    private Context context;
    private Score testScore;
    private ScorePreference scorePreference;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
        testScore = new Score("test", 100, 56, "English", "2017-6-9");
        scorePreference = new ScorePreference();

    }


    @org.junit.Test
    public void addScore() throws Exception {
        scorePreference.addScore(context, testScore);
        assertEquals(scorePreference.getScores(context).get(0).getCaption(), testScore.getCaption());
    }

    @Test
    public void removeScore() throws Exception {
        scorePreference.removeScore(context, testScore);
        ArrayList<Score> scores = scorePreference.getScores(context);
        assertTrue(scores.isEmpty());
    }

}