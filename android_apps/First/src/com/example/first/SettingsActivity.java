package com.example.first;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class SettingsActivity extends Activity {
 
    private static final int RESULT_SETTINGS = 1;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
 
        showUserSettings();
        
		final Button button = (Button) findViewById(R.id.buttonPrefSettings);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startSettings(v) ;
            }
        });        
    }

	public String startSettings(View v) {
        Intent i = new Intent(this, UserSettingActivity.class);
        startActivityForResult(i, RESULT_SETTINGS); 		
		return "test";
	}    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preference_menu_settings, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
 
        case R.id.pref_menu_settings:
            Intent i = new Intent(this, UserSettingActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);
            break;
 
        }
 
        return true;
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case RESULT_SETTINGS:
            showUserSettings();
            break;
 
        }
 
    }
 
    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
 
        StringBuilder builder = new StringBuilder();
 
        builder.append("\n Username: "
                + sharedPrefs.getString("prefUsername", "NULL"));
        builder.append("\n Password: "
                + sharedPrefs.getString("prefUserPassword", "NULL"));
        builder.append("\n SambaAddress: "
                + sharedPrefs.getString("prefSambaAddress", "NULL"));        
        builder.append("\n Send report:"
                + sharedPrefs.getBoolean("prefSendReport", false));
 
        builder.append("\n Sync Frequency: "
                + sharedPrefs.getString("prefSyncFrequency", "NULL"));
 
        TextView settingsTextView = (TextView) findViewById(R.id.textUserSettings);
 
        settingsTextView.setText(builder.toString());
    }
 
}

