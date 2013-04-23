package me.livanec.don.tic_tac;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
public class ColorChooser extends Activity implements
		AdapterView.OnItemSelectedListener {
	private Spinner backgroundSpinner;
	private Spinner lineColorSpinner;
	private Spinner o_spinner;
	private Spinner x_spinner;
	static int back_color_index = 0;
	static int line_color_index = 1;
	static int o_color_index = 1;
	static int x_color_index = 1;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.color_chooser);

		backgroundSpinner = (Spinner) findViewById(R.id.background_spinner);
		lineColorSpinner = (Spinner) findViewById(R.id.line_color_spinner);
		o_spinner = (Spinner) findViewById(R.id.o_marker_color_spinner);
		x_spinner = (Spinner) findViewById(R.id.x_marker_color_spinner);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.color_array,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		backgroundSpinner.setAdapter(adapter);
		lineColorSpinner.setAdapter(adapter);
		o_spinner.setAdapter(adapter);
		x_spinner.setAdapter(adapter);

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	/** get Colors from spinners */
	public void ok(View view) {
		back_color_index = backgroundSpinner.getSelectedItemPosition();
		line_color_index = lineColorSpinner.getSelectedItemPosition();
		x_color_index = x_spinner.getSelectedItemPosition();
		o_color_index = o_spinner.getSelectedItemPosition();
		finish();
	}

}
