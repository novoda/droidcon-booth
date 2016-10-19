package com.novoda.canvas.mines;

import com.novoda.canvas.R;;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

public class BoardView extends ViewGroup implements OnGestureListener {

	private final boolean isLandscape;
	private final FlingRunnable flingRunnable;
	private GestureDetector gestureDetector;
	private BoardPanel canvas;
	private boolean isScrolling;

	private class FlingRunnable implements Runnable {

		private final Scroller itsScroller;

		public FlingRunnable() {
			itsScroller = new Scroller(getContext());
		}

		@Override
		public void run() {
			if (itsScroller.computeScrollOffset()) {
				canvas.scrollTo(itsScroller.getCurrX(), itsScroller.getCurrY());
				post(this);
			}
		}

		public void start(final int velocityX, final int velocityY) {
			removeCallbacks(this);
			itsScroller.fling(canvas.getScrollX(), canvas.getScrollY(), velocityX, velocityY, 0,
                    canvas.getWidth() - getWidth() + getPaddingRight(), 0, canvas.getHeight() - getHeight()
                            + getPaddingBottom());
			post(this);
		}

		public boolean stop() {
			if (!itsScroller.isFinished()) {
				itsScroller.forceFinished(true);
				removeCallbacks(this);
				return true;
			}
			return false;
		}
	}

	public BoardView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		flingRunnable = new FlingRunnable();
		Configuration configuration = getResources().getConfiguration();
		isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
        setScrollbarFadingEnabled(true);
        setHorizontalFadingEdgeEnabled(true);
        setVerticalFadingEdgeEnabled(true);
	}

	@Override
	protected int computeHorizontalScrollExtent() {
		return getWidth();
	}

	@Override
	protected int computeVerticalScrollExtent() {
		return getHeight();
	}

	@Override
	protected int computeHorizontalScrollOffset() {
		return getCanvas().getScrollX();
	}

	@Override
	protected int computeVerticalScrollOffset() {
		return getCanvas().getScrollY();
	}

	@Override
	protected int computeHorizontalScrollRange() {
		return getCanvas().getWidth();
	}

	@Override
	protected int computeVerticalScrollRange() {
		return getCanvas().getHeight();
	}

	private boolean isBoardFittingOnScreen() {
		return getCanvas().fitsIntoParent();
	}

	public boolean fitsIntoArea(final int width, final int height) {
		if (isLandscape)
			return getWidth() < height || getHeight() < width;
		return getWidth() < width || getHeight() < height;
	}

	@Override
	public boolean dispatchKeyEvent(final KeyEvent event) {
		if (isBoardFittingOnScreen())
			return super.dispatchKeyEvent(event);
		return event.dispatch(this, null, null);
	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		int step = getCanvas().getFieldSize();
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			scrollBoard(step, 0);
			return true;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			scrollBoard(-step, 0);
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			scrollBoard(0, -step);
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			scrollBoard(0, step);
			return true;
		default:
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchTrackballEvent(final MotionEvent event) {
		// Log.i("trackball-event", event.toString());
		if (isBoardFittingOnScreen())
			return super.dispatchTrackballEvent(event);
		int step = getCanvas().getFieldSize();
		scrollBoard(Math.round(event.getX() * step), Math.round(event.getY() * step));
		return true;
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent event) {
		if (isBoardFittingOnScreen())
			return super.dispatchTouchEvent(event);
		boolean flingStopped = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			flingStopped = flingRunnable.stop();
		if (!gestureDetector.onTouchEvent(event))
			if (!isScrolling && !flingStopped)
				// dispatch event so that click/long-press on fields get handled
				return super.dispatchTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (!isScrolling) {
				// we scroll the board, dispatch a cancel event so that
				// click/long-press get cancelled
				MotionEvent cancelEvent = MotionEvent.obtain(event);
				cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
				super.dispatchTouchEvent(cancelEvent);
				cancelEvent.recycle();
				isScrolling = true;
			}
            awakenScrollBars();
        }
		if (event.getAction() == MotionEvent.ACTION_UP)
			isScrolling = false;
		return true;
	}

	public FieldView getField(final int posX, final int posY) {
		if (isLandscape) {
			int height = getCanvas().getDimensionY();
			return getCanvas().getField(posY, -posX + height - 1);
		}
		return getCanvas().getField(posX, posY);
	}

	@Override
	public boolean onDown(final MotionEvent e) {
		// no need to handle down
		return false;
	}

	/*
	 * (non-Javadoc) We expect this to be called ONLY in zoom mode.
	 * 
	 * @seeandroid.view.GestureDetector.OnGestureListener#onFling(android.view.
	 * MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
		flingRunnable.start(-Math.round(velocityX), -Math.round(velocityY));
		return true;
	}

	@Override
	public void onLongPress(final MotionEvent e) {
		// no need to handle long press
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		getCanvas().measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
		positionCanvasOnScreen();
		// set view focusable if board is bigger than screen to enable trackball
		// event receiving
		setFocusable(!isBoardFittingOnScreen());
	}

	private void positionCanvasOnScreen() {
		int canvasWidth = getCanvas().getMeasuredWidth();
		int canvasHeight = getCanvas().getMeasuredHeight();
		int viewWidth = getMeasuredWidth();
		int viewHeight = getMeasuredHeight();
		int x = Math.max(0, (viewWidth - canvasWidth) / 2);
		int y = Math.max(0, (viewHeight - canvasHeight) / 2);
		getCanvas().layout(x, y, x + canvasWidth, y + canvasHeight);
        setHorizontalScrollBarEnabled(viewWidth < canvasWidth);
        setVerticalScrollBarEnabled(viewHeight < canvasHeight);
	}

	/*
	 * (non-Javadoc) We expect this to be called ONLY in zoom mode.
	 * 
	 * @see
	 * android.view.GestureDetector.OnGestureListener#onScroll(android.view.
	 * MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
		scrollBoard(Math.round(distanceX), Math.round(distanceY));
		return true;
	}

	private void scrollBoard(final int relativeX, final int relativeY) {
		int targetX = getTarget(getCanvas().getScrollX(), relativeX, getCanvas().getWidth() + getPaddingRight()
				- getMeasuredWidth());
		int targetY = getTarget(getCanvas().getScrollY(), relativeY, getCanvas().getHeight() + getPaddingBottom()
				- getMeasuredHeight());
		getCanvas().scrollTo(targetX, targetY);
		invalidate();
	}

	private int getTarget(final int start, final int dist, final int max) {
		int target = start + dist;
		if (target < 0 || max <= 0)
			return 0;
		if (target > max)
			return max;
		return target;
	}

	@Override
	public void onShowPress(final MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(final MotionEvent e) {
		return false;
	}

	private BoardPanel getCanvas() {
		if (canvas == null)
			canvas = (BoardPanel) findViewById(R.id.board_panel);
		return canvas;
	}

	public void setSize(final int fieldsX, final int fieldsY) {

		flingRunnable.stop();
		if (gestureDetector == null)
			gestureDetector = new GestureDetector(getContext(), this);
		if (isLandscape)
			getCanvas().setDimension(fieldsY, fieldsX);
		else
			getCanvas().setDimension(fieldsX, fieldsY);
	}

	public void setFieldSizeAndTouchFocus(final int fieldSize, final boolean touchFocus) {
		flingRunnable.stop();
		getCanvas().setZoomModeFieldSize(fieldSize);
		getCanvas().setFieldsFocusableInTouchMode(touchFocus);
		getCanvas().scrollTo(0, 0);
	}
}
