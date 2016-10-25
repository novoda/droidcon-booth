package com.novoda.canvas.mines;

import android.os.Handler;
import de.devisnik.mine.IGame;
import de.devisnik.mine.MinesGameAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {

	private Handler handler;
	private Timer timer;
	private boolean isPaused = true;
	protected boolean isGameRunning;
	private final IGame game;
	private MinesGameAdapter gameListener;

	public GameTimer(IGame game) {
		this.game = game;
		isGameRunning = game.isRunning();
		gameListener = new MinesGameAdapter() {
			@Override
			public void onStart() {
				isGameRunning = true;
				startTimer();
			}

			@Override
			public void onBusted() {
				isGameRunning = false;
				stopTimer();
			}

			@Override
			public void onDisarmed() {
				isGameRunning = false;
				stopTimer();
			}
		};
		game.addListener(gameListener);
	}

	public void pause() {
		if (!isGameRunning || isPaused)
			return;
		isPaused = true;
		stopTimer();
	}

	public void resume() {
		if (!isGameRunning || !isPaused)
			return;
		isPaused = false;
		startTimer();
	}

	private void stopTimer() {
		if (timer == null)
			return;
		timer.cancel();
		timer = null;
	}

	private void startTimer() {
		if (timer != null)
			return;
		timer = new Timer();
		handler = new Handler();
		timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        game.tickWatch();
                    }
                });
            }
        }, 1000, 1000);
	}

	public void dispose() {
		stopTimer();
		game.removeListener(gameListener);
	}
	
}
