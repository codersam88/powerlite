package sam.commit.powerlite;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class PowerService extends Service{

    IReciever mReceiver;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate()
	{
		 
		super.onCreate();

	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){

        IntentFilter iFil = new IntentFilter(Intent.ACTION_SCREEN_ON);
        iFil.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new IReciever();
        registerReceiver(mReceiver, iFil);
		
		return START_STICKY;
	}

    @Override
    public  void onDestroy(){
        unregisterReceiver(mReceiver);
    }
	
	
	
	


}







