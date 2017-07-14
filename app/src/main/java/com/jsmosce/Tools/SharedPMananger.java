package com.jsmosce.Tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.jsmosce.base.BaseApplication;

public class SharedPMananger {
    public static final String FILENAME = "Jsmsonsce";
    public static final String USER = "Jsmsonsce_User";


    private SharedPreferences shp;
    public static SharedPMananger sharedp;

    private SharedPMananger(Context context) {
        super();
        if (context == null) {
            return;
        }
        shp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);

    }

    public SharedPreferences getShp() {
        return shp;
    }

    public static SharedPMananger getInstance() {
        if (sharedp == null) {
            sharedp = new SharedPMananger(BaseApplication.myApplication);
        }
        return sharedp;
    }

    public static float getFloat(final String pKey, final float pDefaultValue)
            throws Exception {
        return SharedPMananger.getInstance().getShp()
                .getFloat(pKey, pDefaultValue);
    }

    public static boolean putFloat(final String pKey, final float pValue) {
        try {
            final SharedPreferences.Editor editor = SharedPMananger
                    .getInstance().getShp().edit();
            editor.putFloat(pKey, pValue);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getInt(final String pKey, final int pDefaultValue)
            throws Exception {
        return SharedPMananger.getInstance().getShp()
                .getInt(pKey, pDefaultValue);
    }

    public static boolean putInt(final String pKey, final int pValue) {
        try {
            final SharedPreferences.Editor editor = SharedPMananger
                    .getInstance().getShp().edit();
            editor.putInt(pKey, pValue);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getLong(final String pKey, final long pDefaultValue)
            throws Exception {
        return SharedPMananger.getInstance().getShp()
                .getLong(pKey, pDefaultValue);
    }

    public static boolean putLong(final String pKey, final long pValue) {
        try {
            final SharedPreferences.Editor editor = SharedPMananger
                    .getInstance().getShp().edit();
            editor.putLong(pKey, pValue);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getBoolean(final String pKey,
                                     final boolean pDefaultValue) throws Exception {
        return SharedPMananger.getInstance().getShp()
                .getBoolean(pKey, pDefaultValue);
    }

    public static boolean putBoolean(final String pKey, final boolean pValue) {
        try {
            final SharedPreferences.Editor editor = SharedPMananger
                    .getInstance().getShp().edit();
            editor.putBoolean(pKey, pValue);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getString(final String pKey, final String pDefaultValue)
            throws Exception {
        return SharedPMananger.getInstance().getShp()
                .getString(pKey, pDefaultValue);
    }

    public static boolean putString(final String pKey, final String pValue) {
        try {
            final SharedPreferences.Editor editor = SharedPMananger
                    .getInstance().getShp().edit();
            editor.putString(pKey, pValue);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(final String key) {
        try {
            final SharedPreferences.Editor editor = SharedPMananger
                    .getInstance().getShp().edit();
            editor.remove(key);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
