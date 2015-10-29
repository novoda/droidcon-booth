package com.novoda.divided;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.novoda.canvas.R;

public class FlippableCard extends FrameLayout {

    private ViewGroup cardBackViewGroup;
    private ViewGroup cardFrontViewGroup;
    private TextView questionTextView;

    public FlippableCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.merge_flippable_card, this);

        cardFrontViewGroup = (ViewGroup) findViewById(R.id.front_of_card);
        cardBackViewGroup = (ViewGroup) findViewById(R.id.back_of_card);

        questionTextView = (TextView) findViewById(R.id.question);
    }

    public void update(Question question) {
        questionTextView.setText(question.getQuestion());
        animateFlip();
    }

    private void animateFlip() {
        ObjectAnimator cardBackTranslateAnimation = getTranslateAnimation();
        AnimatorSet cardFlipAnimation = getCardFlipAnimation();

        AnimatorSet completeAnimation = new AnimatorSet();
        completeAnimation.playSequentially(cardBackTranslateAnimation, cardFlipAnimation);
        completeAnimation.start();
    }

    @NonNull
    private ObjectAnimator getTranslateAnimation() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.divided_cards_height);
        ObjectAnimator cardBackTranslateAnimation = ObjectAnimator.ofFloat(
                this,
                "translationY", 0, -(dimensionPixelSize)
        );
        cardBackTranslateAnimation.setDuration(500);
        return cardBackTranslateAnimation;
    }

    private AnimatorSet getCardFlipAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator cardBackAnimation = ObjectAnimator.ofFloat(
                cardBackViewGroup,
                "rotationY", 0, 90
        );
        cardBackAnimation.setDuration(500);

        ObjectAnimator cardFrontAnimation = ObjectAnimator.ofFloat(
                cardFrontViewGroup,
                "rotationY", -90, 0
        );
        cardFrontAnimation.setDuration(500);

        animatorSet.playSequentially(cardBackAnimation, cardFrontAnimation);
        return animatorSet;
    }

}
