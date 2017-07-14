package com.jsmosce.Tools;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.jsmosce.base.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author victor_freedom (x_freedom_reddevil@126.com)
 * @ClassName: CrashHandler
 * @createddate 2014-12-25 下午11:41:12
 * @Description: UncaughtException处理类, 当程序发生Uncaught异常的时候, 有该类来接管程序, 并记录发送错误报告.
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    // CrashHandler 实例
    private static CrashHandler INSTANCE = new CrashHandler();

    // 程序的 Context 对象
    private Context mContext;
    // app对象
    private BaseApplication app;

    // 系统默认的 UncaughtException 处理类
    private UncaughtExceptionHandler mDefaultHandler;

    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private CrashHandler() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * @param context
     * @param app     传入的app
     * @throws
     * @Title: init
     * @Description: 初始化
     */
    public void init(Context context, BaseApplication app) {
        // 传入app对象，为完美终止app
        this.app = app;
        mContext = context;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.d(TAG, "message:" + ex.getMessage());
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // 释放资源不能像常规的那样在activity的onDestroy方法里面执行，因为如果出现全局异常捕获，activity的关闭有时候是不会再走相关的生命周期函数的（onDesktroy,onStop,onPause等）。
            // 这里是博主在退出app之前需要释放掉的一些资源，通过之前讲的AppActivityManager来拿到对应的实例activity释放里面的资源，然后调用AppExit退出应用程序
            //Tools.activitys.get("MainActivity").finish();
            //Tools.activitys.get("SystemSettingActivity").finish();
//            Intent i2 = new Intent(app, MainActivity.class);
//            i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            app.startActivity(i2);
            // 当执行这一句的时候，其实APP有时候并没有完美的退出（方法详情可以查看博主之前的写的activity管理的文章）
            // 博主的项目里面有网络连接、有后台服务、多线程等各种。执行完这个方法之后，虽然能够闪退出去，但是，当再次进入APP的时候，是回出现ANR的，说明，这样还是没有的完美退出APP
            //AppActivityManager.getAppActivityManager().AppExit(mContext);
            // 之前说application的时候说过，当app退出的时候，会执行onTerminate方法，但是有时候不会主动执行。那么，博主想，如果我们强制执行这个方法，能不能让app完美的终止呢?答案是肯定的。
            //app.onTerminate();

//            Intent startMain =new Intent(Intent.ACTION_MAIN);
//
//            startMain.addCategory(Intent.CATEGORY_HOME);
//            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            app.startActivity(startMain);
            //Tools.activitys.get("SystemSettingActivity").finish();
////          app.onTerminate();
////          android.os.Process.killProcess(android.os.Process.myPid());
////          System.exit(0);
//            TigetherEvent tigetherEvent = new TigetherEvent();
//            tigetherEvent.setUpdateFlag(5555);
            mDefaultHandler.uncaughtException(thread, ex);
            //Tools.getTopActivity();

            //EventBus.getDefault().post(tigetherEvent);

            //mDefaultHandler.uncaughtException(thread, ex);
//            Tools.activitys.get("MainActivity").finish();

//            Intent i = app.getPackageManager()
//                    .getLaunchIntentForPackage(app.getPackageName());
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            app.startActivity(i);

        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中 *
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/jsmosce/error/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }

}