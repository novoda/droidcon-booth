package com.novoda.canvas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.divided.FlippableCard;
import com.novoda.divided.Question;

import java.util.Random;

public class DividedTest extends NovodaActivityTest {

    private FlippableCard flippableCardCenter;

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();

        View view = LayoutInflater.from(activity).inflate(R.layout.divided_cards, root, true);
        flippableCardCenter = (FlippableCard) view.findViewById(R.id.flippable_card_center);
        loadQuestions(activity, root);
    }

    private void loadQuestions(Activity activity, final ViewGroup root) {
        flippableCardCenter.update(getRandomQuestion());
    }

    private Question getRandomQuestion() {
        return Question.values()[randomPick()];
    }

    private int randomPick() {
        return new Random().nextInt(Question.values().length);
    }

}
