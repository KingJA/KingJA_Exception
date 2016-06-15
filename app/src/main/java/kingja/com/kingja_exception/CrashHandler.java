package kingja.com.kingja_exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/15 9:54
 * 修改备注：
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context context;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            synchronized (CrashHandler.class) {
                if (mCrashHandler == null) {
                    mCrashHandler = new CrashHandler();
                }
            }
        }
        return mCrashHandler;
    }

    public void init(Context context) {
        this.context = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("uncaughtException", "uncaughtException: ");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File logDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KLogs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            File logFile = new File(logDir, "CrashLogs.txt");
            FileWriter fw = null;
            try {
                fw = new FileWriter(logFile, true);
                fw.write(getExceptionInfo(ex) + "\n");
                // uploadToServer();
            } catch (IOException e) {
                Log.e("CrashHandler", "load file failed...  ", e.getCause());
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ex.printStackTrace();//打印System.err Log
//        mDefaultHandler.uncaughtException(thread, ex);//打印RuntimeException Log
        AppManager.getAppManager().finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获取异常信息
     *
     * @param ex
     * @return
     */
    @NonNull
    private String getExceptionInfo(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(getFormaTime() + "\n");
        sb.append("===================" + "\n");
        sb.append("Product Model: " + android.os.Build.MODEL + ","
                + android.os.Build.VERSION.SDK_INT + ","
                + android.os.Build.VERSION.RELEASE + ","
                + android.os.Build.CPU_ABI + "\n");
        sb.append("Thread:  " + Thread.currentThread().getName() + "\n");
        sb.append(getVersionInfo(context) + "\n");
        sb.append(ex.toString() + "\n");
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace != null) {
            for (int i = 0; i < stackTrace.length; i++) {
                sb.append("\tat  " + stackTrace[i].toString() + "\n");
            }
        }
        Throwable causeThrowable = ex.getCause();
        if (causeThrowable != null) {
            StackTraceElement[] causeElement = causeThrowable.getStackTrace();
            sb.append("\tCaused by: " + causeThrowable.toString() + "\n");
            for (int i = 0; i < causeElement.length; i++) {
                sb.append("\tat  " + causeElement[i].toString() + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    private String getVersionInfo(Context context) {
        PackageInfo pi;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.packageName + "\tVersionCode:" + pi.versionCode + "\tVersionName:" + pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "版本号:未知\t版本号:未知";
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public String getFormaTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

}