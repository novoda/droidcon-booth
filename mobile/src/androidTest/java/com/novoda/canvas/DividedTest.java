package com.novoda.canvas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novoda.canvas.NovodaActivity;
import com.novoda.canvas.R;
import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.divided.FlippableCard;
import com.novoda.canvas.divided.Question;

public class DividedTest extends NovodaActivityTest {

    private FlippableCard flippableCardCenter;

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();

        View view = LayoutInflater.from(activity).inflate(R.layout.divided_cards, root, true);
        flippableCardCenter = (FlippableCard) view.findViewById(R.id.flippable_card_center);
        loadQuestions(activity);
    }

    private void loadQuestions(Activity activity) {
        flippableCardCenter.update(getRandomQuestion());
    }

    private Question getRandomQuestion() {
        return Question.values()[randomPick()];
    }

    private int randomPick() {
        return NovodaActivity.RANDOM.nextInt(Question.values().length);
    }

}
