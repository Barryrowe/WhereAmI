package pineapple.piranha.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class WhereAmIPreferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
}
