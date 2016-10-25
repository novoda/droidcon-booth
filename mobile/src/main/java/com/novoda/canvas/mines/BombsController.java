package com.novoda.canvas.mines;

import de.devisnik.mine.IGame;
import de.devisnik.mine.MinesGameAdapter;

public final class BombsController extends MinesGameAdapter {

	private final IGame game;
	private final CounterView counterView;

	public BombsController(IGame game, final CounterView counter) {
		this.game = game;
		counterView = counter;
		counterView.setValue(game.getBombCount() - game.getBoard().getFlagCount());
		game.addListener(this);
	}
	
	public void dispose() {
		game.removeListener(this);
	}
	
	@Override
	public void onChange(int flags, int mines) {
		counterView.setValue(mines - flags);
	}
}
