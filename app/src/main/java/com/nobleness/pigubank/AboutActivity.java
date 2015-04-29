package com.nobleness.pigubank;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutActivity extends PIGuActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		Log.i(APP_LOG, "AboutActivity.class Started");
		
		InputStream iFile = getResources().openRawResource(R.raw.about);
		try{
			TextView aboutText = (TextView) findViewById(R.id.AboutTV02);
			String strFile = inputStreamToString(iFile);
			aboutText.setText(strFile);
		} catch (Exception e) {
			Log.e(APP_LOG, "InputStream failed", e);
		}
	}

    @Override
    protected void onPause() {
        super.onPause();
        AboutActivity.this.finish();
    }
	
	
	@SuppressWarnings("deprecation")
	public String inputStreamToString(InputStream InStrm) throws IOException {
		StringBuffer strBuff = new StringBuffer();
		DataInputStream dataIStrm = new DataInputStream(InStrm);
		String strLine = null;
		while ((strLine = dataIStrm.readLine()) != null) {
			strBuff.append(strLine + "\n"); 
		}
		dataIStrm.close();
		InStrm.close();
		return strBuff.toString();
	}

}
