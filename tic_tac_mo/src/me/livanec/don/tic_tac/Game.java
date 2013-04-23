package me.livanec.don.tic_tac;

import me.livanec.don.tic_tac.helper.GameController;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Game extends Activity {
	private static final String TAG = Game.class.getSimpleName();
	private SharedPreferences preferences;
	private GameController controller;
	static BoardView boardView;
	public MediaPlayer mp;
	public long tie;
	public long x_won;
	public long o_won;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		startNewGame();
		final Object data = getLastNonConfigurationInstance();
		if (data != null)
			controller = (GameController) data;
		initStats();
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mp = MediaPlayer.create(this, R.raw.outstanding);
	}

	private void initStats() {
		preferences = getSharedPreferences(getString(R.string.scoreboard), 0);
		preferences.edit().putLong(getResources().getString(R.string.x_won), 0)
				.commit();
		preferences.edit().putLong(getResources().getString(R.string.o_won), 0)
				.commit();
		preferences.edit()
				.putLong(getResources().getString(R.string.tie_stat), 0)
				.commit();
	}

	private void startNewGame() {
		Log.d(TAG, "Starting a new game.");
		controller = new GameController();
		boardView = new BoardView(this);
		setContentView(boardView);
		boardView.requestFocus();
	}

	public GameController getController() {
		return controller;
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_game:
			startNewGame();
			break;
		case R.id.color_chooser:
			startActivity(new Intent(Game.this, ColorChooser.class));
			break;
		case R.id.help:
			startActivity(new Intent(Game.this, Help.class));

			break;
		case R.id.clear_score:
			preferences.edit()
					.putLong(getResources().getString(R.string.x_won), 0)
					.commit();
			preferences.edit()
					.putLong(getResources().getString(R.string.o_won), 0)
					.commit();
			preferences.edit()
					.putLong(getResources().getString(R.string.tie_stat), 0)
					.commit();
			boardView.invalidate();
			break;
		}
		return true;
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return controller;
	}

}
