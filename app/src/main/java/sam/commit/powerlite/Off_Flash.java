package sam.commit.powerlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Off_Flash extends Activity{
	
	ImageButton flashOff;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
//                                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.offflash);
		hookButton();
		

	}
	
	private void hookButton(){
		flashOff = (ImageButton)findViewById(R.id.flashOff);
		flashOff.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				IReciever.turnOffFlash();
				finish();
				
			}
		});
		
	}
	

}
