package sam.commit.powerlite;

import java.util.Date;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class IReciever extends BroadcastReceiver {

	private static Camera camera;
	static NotificationManager mNotificationManager;
	static boolean isFlashOn = false;//tells whether the flash is currently switched on
	static Parameters params;
	public static final String PREFS_NAME = "MyPrefsFile";
	long time[]={0,0};
	int tInd=0;
	int pressCount = 0;
	//private static final String TAG = "IReceiver";
	
	@Override
	public void onReceive(Context ctxt, Intent intent) {
		
		time[tInd]=new Date().getTime();
		if(Math.abs(time[0]-time[1])<=2000){
			pressCount++;			
		}
		else{
			pressCount=1;
		}
		tInd = (tInd+1)%2;
		//Log.v(TAG, "tInd value "+tInd);
		//Log.v(TAG, "presscount value "+pressCount);
		//Log.v(TAG, "time 0 value "+time[0]);
		//Log.v(TAG, "time 1 value "+time[1]);
		if (pressCount % 3 == 0) {
			if (isFlashOn == false) {
				turnOnFlash(ctxt);
				
			} else {
				turnOffFlash();
			}
		}
		

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			SharedPreferences settings = ctxt.getSharedPreferences(PREFS_NAME,
					0);
			if ((settings.getBoolean("btnText", false)) == true) {
				Intent in = new Intent(ctxt, PowerService.class);
				ctxt.startService(in);
			}
		}

	}

	private void getCamera() {
		if (camera == null) {
			try {
				camera = Camera.open();
				params = camera.getParameters();
			} catch (RuntimeException e) {
				Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
			}
		}
	}

	private void turnOnFlash(Context ctxt) {
		getCamera();
		if (!isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview();
			isFlashOn = true;
			
			
//			KeyguardManager km = (KeyguardManager) ctxt.getSystemService(Context.KEYGUARD_SERVICE);
//			final KeyguardManager.KeyguardLock kl=km.newKeyguardLock("My_App");
//			kl.disableKeyguard();
//			PowerManager pm = (PowerManager) ctxt.getSystemService(Context.POWER_SERVICE);
//			PowerManager.WakeLock wl=pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "My_App");
//			wl.acquire();
			//Intent in = new Intent(ctxt,MainActivity.class);
			//ctxt.startActivity(in);
			NotificationCompat.Builder note = new NotificationCompat.Builder(ctxt)
			.setSmallIcon(R.drawable.ic_action_poweron).setContentTitle("Flash On")
			.setContentText("Flash is turned on");
			Intent resultIntent = new Intent(ctxt,Off_Flash.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctxt);
			stackBuilder.addParentStack(Off_Flash.class);
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(
			            0,
			            PendingIntent.FLAG_UPDATE_CURRENT
			        );
			note.setContentIntent(resultPendingIntent);
			mNotificationManager =
				    (NotificationManager) ctxt.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(0, note.build());

		}

	}

	static void turnOffFlash() {
		if (isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(params);
			camera.stopPreview();
			isFlashOn = false;
			camera.release();
			camera = null;
			mNotificationManager.cancel(0);
		}
	}

}
