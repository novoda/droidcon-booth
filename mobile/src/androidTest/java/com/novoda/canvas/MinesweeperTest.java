package com.novoda.canvas;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.mines.BoardController;
import com.novoda.canvas.mines.BoardView;
import com.novoda.canvas.mines.BombsController;
import com.novoda.canvas.mines.CounterView;
import com.novoda.canvas.mines.GameTimer;
import com.novoda.canvas.mines.TimerController;
import com.novoda.canvas.R;

import de.devisnik.mine.GameFactory;
import de.devisnik.mine.IGame;
import de.devisnik.mine.robot.AutoPlayer;

public class MinesweeperTest extends NovodaActivityTest {

    public static final int DELAY_MILLIS_BETWEEN_MOVES = 400;

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();
        View view = LayoutInflater.from(activity).inflate(R.layout.main, root, true);

        BoardView boardView = (BoardView) view.findViewById(R.id.board);

        final IGame game = GameFactory.create(8, 8, 8);
        BoardController controller = new BoardController(game, boardView);
        GameTimer gameTimer = new GameTimer(game);
        BombsController bombsController = new BombsController(game, (CounterView) view.findViewById(R.id.count));
        TimerController timerController = new TimerController(game, (CounterView) view.findViewById(R.id.time));

        final AutoPlayer autoPlayer = new AutoPlayer(game, true);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!game.isRunning() && game.isStarted())
                    return;
                autoPlayer.doNextMove();
                handler.postDelayed(this, DELAY_MILLIS_BETWEEN_MOVES);
            }
        }, DELAY_MILLIS_BETWEEN_MOVES);

    }
}
