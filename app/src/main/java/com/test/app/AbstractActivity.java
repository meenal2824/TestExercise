package com.test.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AbstractActivity extends Activity {

	protected ProgressDialog mProgressDialog;
	protected Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;

	}

	/**
	 * method to display progress dialog
	 */
	protected void showProgressDialog() {
		// TODO Auto-generated method stub

		try {

			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(mContext);
				mProgressDialog.setTitle(getString(R.string.dialog_title));
				mProgressDialog.setMessage(getString(R.string.dialog_message));
				mProgressDialog.setCancelable(false);
				mProgressDialog.setCanceledOnTouchOutside(false);
			}

			if (mProgressDialog != null) {
				mProgressDialog.show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * method to dismiss progress dialog
	 */
	protected void dismissProgressDialog() {
		// TODO Auto-generated method stub
		try {
			if (mProgressDialog != null) {
				mProgressDialog.dismiss();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param message
	 *            method to display toast message
	 */
	protected void displayMessage(String message) {
		// TODO Auto-generated method stub

		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

	}

	/**
	 * Checks Whether the Network is Available or Not.
	 * 
	 * @return
	 */
	protected boolean isOnline(Context _context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) _context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			boolean connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()
					&& networkInfo.isConnectedOrConnecting();

			return connected;
		} catch (Exception e) {
			Log.v("connectivity", e.toString());
		}
		return false;
	}

}
