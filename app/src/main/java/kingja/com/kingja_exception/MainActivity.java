package kingja.com.kingja_exception;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                Thread.currentThread().setUncaughtExceptionHandler( CrashHandler.getInstance());
//                Object o=null;
//                o.toString();
//            }
//        }).start();

        Object o=null;
        o.toString();

    }

}
