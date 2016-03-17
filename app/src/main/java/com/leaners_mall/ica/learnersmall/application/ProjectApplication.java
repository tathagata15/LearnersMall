package com.leaners_mall.ica.learnersmall.application;

import android.app.Application;
import android.util.Log;

public class ProjectApplication extends Application {

	private static String mail_id;
	private static String password;

	private static String main_userID;

	private static boolean bool;

	@Override
	public void onCreate() {
		super.onCreate();
		mail_id = "";
		password = "";

		Log.d("first get bool", "" + bool);
	}

	public static boolean getBool() {
		return bool;
	}

	public static void setBool(boolean bool) {
		ProjectApplication.bool = bool;
	}

	public static String getMailID() {
		return mail_id;
	}

	public static void setMailID(String mail_id) {
		ProjectApplication.mail_id = mail_id;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ProjectApplication.password = password;
	}

}
