package com.novoda.divided;

import android.support.annotation.StringRes;

import com.novoda.canvas.R;

public enum Question {

    ENUM(R.string.linear_layout_weights),
    RESOURCES(R.string.extract_resources),
    FINAL(R.string.when_to_use_final);

    private int question;
    private int answerOne;
    private int answerTwo;

    Question(@StringRes int question) {
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
    }

    public int getQuestion() {
        return question;
    }

}
