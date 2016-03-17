package com.leaners_mall.ica.learnersmall.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

public class NetUtils {
	private static int size = 1024;

	public boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isAvailable() && ni.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static void hideKeypad(final Context context) {
		try {
			InputMethodManager inputManager = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(((Activity) context)
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

	public static boolean isNetworkConnected(Context context) {
		boolean connected = false;

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		connected = (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable() && cm
				.getActiveNetworkInfo().isConnected());

		return connected;
	}

	public static void showNoInternet(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setMessage("Internet of the phone is not working.")
				.setTitle("No Internet.").setCancelable(false)
				.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						activity.finish();
						System.exit(0);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showProblemConnectingServer(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setMessage(
				"Could not connect to server, due to some reason. Please try later.")
				.setTitle("Could not connect to server.").setCancelable(false)
				.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						activity.finish();
						System.exit(0);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/*
	 * public static void showNoDataFound(final Activity activity) {
	 * AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	 * 
	 * builder.setMessage(
	 * "No data found. Please try with different search string.")
	 * .setTitle("No data found.").setCancelable(false) .setNegativeButton("Ok",
	 * new DialogInterface.OnClickListener() { public void
	 * onClick(DialogInterface dialog, int id) { dialog.cancel(); } });
	 * AlertDialog alert = builder.create(); alert.show(); }
	 */

}
