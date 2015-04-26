package sam.commit.powerlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private Button onOffButton;//true when on
	private boolean onOrOff = false;
	private Intent in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.mylayout);
		hookButton();
		SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
		onOrOff = settings.getBoolean("btnText", false);
		setButtonImageAndState(onOrOff);
	}
	
	private void setButtonImageAndState(boolean stat){//this runs only when you are starting activity
		if(stat == false) {
            ((GradientDrawable) onOffButton.getBackground()).setColor(
                    getResources().getColor(R.color.onn));
            ((RelativeLayout) findViewById(R.id.main_layout)).setBackgroundColor(getResources().getColor(R.color.off));
            onOffButton.setText(R.string.turn_on);
        }
		else
		{
            ((GradientDrawable)onOffButton.getBackground()).setColor(
                    getResources().getColor(R.color.off));
            ((RelativeLayout)findViewById(R.id.main_layout)).setBackgroundColor(getResources().getColor(R.color.onn));
            onOffButton.setText(R.string.turn_off);

		}
	}
	
	private void hookButton(){//changing state

		onOffButton = (Button)findViewById(R.id.onebutton);

		onOffButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{

				if(onOrOff == false)//then we need to turn it on
				{//checking its previous state
                    in = new Intent(getApplicationContext(),PowerService.class);
					startService(in);
					Toast.makeText(getApplicationContext(), "App is now ON", Toast.LENGTH_SHORT).show();
                    ((GradientDrawable)onOffButton.getBackground()).setColor(
                            getResources().getColor(R.color.off));
                    ((RelativeLayout)findViewById(R.id.main_layout)).setBackgroundColor(getResources().getColor(R.color.onn));
                    onOffButton.setText(R.string.turn_off);
                    onOrOff = true;

				}
				else if(IReciever.isFlashOn==true)
				{
					Toast.makeText(getApplicationContext(), "Please first turn off the "
							+ "flash using power button", Toast.LENGTH_LONG).show();
				}
				else
				{
                    setButtonImageAndState(onOrOff);
					onOrOff = false;
					in = new Intent(getApplicationContext(),PowerService.class);
					stopService(in);
					Toast.makeText(getApplicationContext(), "App is now OFF", Toast.LENGTH_SHORT).show();
                    ((GradientDrawable) onOffButton.getBackground()).setColor(
                            getResources().getColor(R.color.onn));
                    ((RelativeLayout) findViewById(R.id.main_layout)).setBackgroundColor(getResources().getColor(R.color.off));
                    onOffButton.setText(R.string.turn_on);
                    onOrOff = false;
					
				}
				
			}
		});
		
	}
	@Override
	protected void onStop(){
		super.onStop();
		SharedPreferences settings = getPreferences( Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("btnText", onOrOff);
		editor.commit();
	}

}
