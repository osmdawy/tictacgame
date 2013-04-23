package me.livanec.don.tic_tac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class BoardView extends View {

	private static final String TAG = BoardView.class.getSimpleName();
	private final Game game;
	private final int offset_score = 150;
	private float width; // width of one tile
	private float height; // height of one tile
	private int selX; // X index of selection
	private int selY; // Y index of selection
	private final Rect selRect = new Rect();
	private int[] colors = { 0xff000000, 0xffffffff, 0xff0000ff, 0xff00ff00,
			0xffff0000, 0xffffff00, 0xffcccccc, 0xffff00ff, 0xFF8800, 0xAA66CC };
	private String player_turn;

	public BoardView(Context context) {
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		player_turn = game.getController().getPlayer_turn() + "";
		// draw board
		Paint background = new Paint();
		background.setColor(colors[ColorChooser.back_color_index]);
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		Paint dark = new Paint();
		dark.setColor(colors[ColorChooser.line_color_index]);

		Paint hilite = new Paint();
		hilite.setColor(getResources().getColor(R.color.board_hilite));

		Paint winner = new Paint();
		winner.setColor(getResources().getColor(R.color.board_hint_0));
		winner.setStrokeWidth(10);
		winner.setStrokeCap(Paint.Cap.ROUND);

		for (int i = 0; i < 4; i++) {
			// horizontal
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			// vertical
			canvas.drawLine(i * width, 0, i * width,
					getHeight() - offset_score, dark);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight()
					- offset_score, hilite);
		}

		// winning line

		// if (game.getSession().isGameOver() && game.getSession().isWinner()) {
		// WinningLine line = new WinningLine(getWidth(), getHeight(), game
		// .getSession().getWinningSeries());
		// canvas.drawLine(line.getX1(), line.getY1(), line.getX2(),
		// line.getY2(), winner);
		// }

		// Define color and style for 'X' and 'O'
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(getResources().getColor(R.color.board_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height / 3 * 2.25f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);

		// Draw the number in the center of the tile
		FontMetrics fm = foreground.getFontMetrics();
		// Centering in Y: measure ascent/descent first
		float y = height / 2 - (fm.ascent + fm.descent) / 2;

		int[] offset = new int[] { 1, 3, 5 };
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				if (game.getController().getBoard()[i][j] == 'X') {
					foreground.setColor(colors[ColorChooser.x_color_index]);

				} else {
					foreground.setColor(colors[ColorChooser.o_color_index]);

				}
				canvas.drawText(game.getController().getBoard()[i][j] + "",
						offset[i] * width / 2, (j) * height + y, foreground);

			}
		}
		Paint score = new Paint(Paint.ANTI_ALIAS_FLAG);
		score.setColor(colors[ColorChooser.x_color_index]);
		score.setStyle(Style.FILL);
		score.setTextSize(height / 5f);
		score.setTextScaleX(width / height);
		score.setTextAlign(Paint.Align.CENTER);
		// draw player turn
		canvas.drawText("Players turn :  " + player_turn, width * 2 / 2,
				getHeight() - offset_score + 40, score);
		canvas.drawLine(0, getHeight() - offset_score + 45, getWidth(),
				getHeight() - offset_score + 45, dark);
		// draw score
		canvas.drawText(
				"X WINS : "
						+ game.getPreferences().getLong(
								getResources().getString(R.string.x_won), 0)
						+ " |"
						+ " O WINS : "
						+ game.getPreferences().getLong(
								getResources().getString(R.string.o_won), 0)
						+ " |"
						+ " Tie :"
						+ game.getPreferences().getLong(
								getResources().getString(R.string.tie_stat), 0),
				width * 3 / 2, getHeight() - offset_score + 85, score);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w / 3f;
		height = (h - offset_score) / 3f;
		getRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		if (!game.getController().isGameOver()) {
			select((int) (event.getX() / width), (int) (event.getY() / height));
			Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);
			setSelectedTile();
		} 
		return true;
	}

	public void setSelectedTile() {
		if (game.getController().setIfValid(selX, selY)) {
			if (game.getController().getWinner() == 'X') {
				game.getPreferences()
						.edit()
						.putLong(getResources().getString(R.string.x_won),
								++game.x_won).commit();
				game.mp = MediaPlayer.create(game, R.raw.outstanding);
				game.mp.start();
				Toast.makeText(game, "X WINS", Toast.LENGTH_SHORT).show();
			} else if (game.getController().getWinner() == 'O') {
				game.getPreferences()
						.edit()
						.putLong(getResources().getString(R.string.o_won),
								++game.o_won).commit();
				game.mp = MediaPlayer.create(game, R.raw.outstanding);
				game.mp.start();
				Toast.makeText(game, "O WINS", Toast.LENGTH_SHORT).show();

			}
			if (game.getController().isGameOver()
					&& game.getController().getWinner() == ' ') {
				game.getPreferences()
						.edit()
						.putLong(getResources().getString(R.string.tie_stat),
								++game.tie).commit();
				game.mp = MediaPlayer.create(game, R.raw.finish);
				game.mp.start();
				Toast.makeText(game, "Game Over", Toast.LENGTH_SHORT).show();

			}

			invalidate();// may change hints
		} else {
			Log.d(TAG, "setSelectedTile: invalid: " + selX + " " + selY);
			game.mp = MediaPlayer.create(game, R.raw.doh);
			game.mp.start();
			startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
		}
	}

	private void select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}

	private void getRect(int x, int y, Rect rect) {
		float big_width = width;
		float big_height = height;
		rect.set((int) (x * big_width), (int) (y * big_height), (int) (x
				* big_width + big_width), (int) (y * big_height + big_height));
	}

}
