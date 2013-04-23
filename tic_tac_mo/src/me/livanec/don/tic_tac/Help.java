package me.livanec.don.tic_tac;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Help extends Activity{
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.help);
		
	}
	public void back(View view) {
		finish();
	}

}
