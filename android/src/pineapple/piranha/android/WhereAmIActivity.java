package pineapple.piranha.android;

import java.util.Date;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WhereAmIActivity extends Activity {
	private static Logger _log = Logger.getLogger(WhereAmIActivity.class
			.getName());
	private static final long _MINUTE_IN_MILLISECONDS = 60000;

	private static final int MENU_ITEM_PREFS = Menu.FIRST;
	private static final int MENU_ITEM_DETAILS = Menu.FIRST + 1;
	private static final int MENU_ITEM_STOP = Menu.FIRST + 2;
	private static final int MENU_ITEM_START = Menu.FIRST + 3;

	private LocationListener locationListener;
	private LocationManager locationManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		printLocation(locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
		printLocation(locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER));

		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				printLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		startListening(this.findViewById(R.id.startButton));
		// locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
		// _MINUTE_IN_MILLISECONDS, 0, locationListener);
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// _MINUTE_IN_MILLISECONDS, 0, locationListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// This is our one standard application action -- inserting a
		// new note into the list.
		menu.add(0, MENU_ITEM_PREFS, 0, R.string.menu_preferences)
				.setShortcut('3', 'a')
				.setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(0, MENU_ITEM_DETAILS, 1, R.string.menu_details)
				.setShortcut('4', 'b')
				.setIcon(android.R.drawable.ic_menu_info_details);

		menu.add(0, MENU_ITEM_STOP, 2, R.string.stop_listening)
				.setShortcut('5', 's')
				.setIcon(android.R.drawable.ic_media_pause);
		menu.add(0, MENU_ITEM_START, 3, R.string.start_listening)
				.setShortcut('6', 't')
				.setIcon(android.R.drawable.ic_media_play);
		// Generate any additional actions that can be performed on the
		// overall list. In a normal install, there are no additional
		// actions found here, but this allows other applications to extend
		// our menu with their own actions.
		/*
		 * Intent intent = new Intent(null, getIntent().getData());
		 * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 * menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, new
		 * ComponentName(this, NotesList.class), null, intent, 0, null);
		 */
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_PREFS:
			_log.info("Preferences Selected!!!");
			Intent intent = new Intent().setClass(this, WhereAmIPreferences.class);			
			startActivityForResult(intent, 1);
			return true;
		case MENU_ITEM_DETAILS:
			_log.info("Details Selected!!!");
			return true;

		case MENU_ITEM_STOP:
			stopListening(findViewById(R.id.stopButton));
			return true;
		case MENU_ITEM_START:
			startListening(findViewById(R.id.startButton));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 11-13-2011 Barry Rowe
	// Call is currently assigned to the stop button through the main.xml
	private void stopListening(View view) {
		_log.info("Stop Listening Invoked!");
		locationManager.removeUpdates(locationListener);

		Button stopButton = (Button) view;
		stopButton.setEnabled(false);
		Button startButton = (Button) this.findViewById(R.id.startButton);
		startButton.setEnabled(true);
	}

	private void startListening(View view) {
		_log.info("Staring Provider Listening Again!");
		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, _MINUTE_IN_MILLISECONDS, 0,
				locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				_MINUTE_IN_MILLISECONDS, 0, locationListener);

		Button startButton = (Button) view;
		startButton.setEnabled(false);
		Button stopButton = (Button) this.findViewById(R.id.stopButton);
		stopButton.setEnabled(true);
	}

	private void printLocation(Location location) {
		_log.info("Prining Location");
		// Do something with location
		TextView locationData = (TextView) this
				.findViewById(R.id.locationsData);

		StringBuilder sb = new StringBuilder();
		if (location != null) {
			sb.append(locationData.getText());
			sb.append("\nAt ");
			sb.append(new Date(location.getTime()).toString());
			sb.append(" You were located at - ");
			sb.append("\n\tLatitude: " + location.getLatitude());
			sb.append("\n\tLongitude: " + location.getLongitude());
			sb.append("\n\tAs determined by ");
			sb.append(location.getProvider());
			sb.append("\n------------------------------------\n");
		} else {
			sb.append("Welcome to Where Am I!");
		}
		locationData.setText(sb.toString());
	}
}