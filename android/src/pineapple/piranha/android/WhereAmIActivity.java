package pineapple.piranha.android;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class WhereAmIActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        printLocation(null);                
        System.out.println("Starting Activity!!");
     // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
              // Called when a new location is found by the network location provider.
              printLocation(location);
              
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {            	            	
            }

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
          };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
    
    public void printLocation(Location location){
    	System.out.println("Prining Location");
    	//Do something with location
    	TextView locationData = (TextView)this.findViewById(R.id.locationsData);
    	
    	StringBuilder sb = new StringBuilder();
    	if(location != null){
	    	sb.append(locationData.getText());
	    	sb.append("\nAt ");
	    	sb.append(new Date(location.getTime()).toString());
	    	sb.append("You were located at - ");
	    	sb.append("\n\tLatitude: " + location.getLatitude());
	    	sb.append("\n\tLongitude: " + location.getLongitude()); 
	    	sb.append("\n\tAs determined by ");
	    	sb.append(location.getProvider());
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
	    	sb.append("\n------------------------------------\n");
    	}else{
    		sb.append("Welcome to Where Am I!");
    	}
    	locationData.setText(sb.toString());    	
    }
}