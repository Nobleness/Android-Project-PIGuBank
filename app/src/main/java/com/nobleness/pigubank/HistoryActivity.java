package com.nobleness.pigubank;

//import java.io.IOException;

//import org.xmlpull.v1.XmlPullParserException;
//import android.content.res.XmlResourceParser;

//import android.widget.TableLayout;
//import android.widget.TableRow;

public class HistoryActivity extends PIGuActivity{

    //retrieve tablelayout of recent activity
    //TableLayout recentActivityTable = (TableLayout) findViewById(R.id.OverviewTLactivity);
    //XmlResourceParser mockactions = getResources().getXml(R.xml.mockactions);

		/*
		try {
		setupActivityTable(recentActivityTable, mockactions);
		} catch (XmlPullParserException e) {
			Log.e(APP_LOG, "XML Pull Parser Exception");

		} catch (IOException e) {
			Log.e(APP_LOG, "IO Exception");
		}
		*/


    // Tables

    @Override
    protected void onPause() {
        super.onPause();
        HistoryActivity.this.finish();
    }

	/*
	private void setupTableRow(final TableRow tableRow, String text, float textSize) {
		TextView textView = new TextView(this);
		textView.setTextSize(textSize);
		textView.setText(text);
		tableRow.addView(textView);
	}
	*/

	/*
	private void setupActivityRow(final TableLayout ActivityTable,
			String accountValue, String activityValue, String dateValue, String timeValue) {

		final TableRow newRow = new TableRow(HomeActivity.this);


		float textSize = getResources().getDimension(R.dimen.tableTextSize);

		setupTableRow(newRow, accountValue, textSize);
		setupTableRow(newRow, activityValue, textSize);
		setupTableRow(newRow, dateValue, textSize);
		setupTableRow(newRow, timeValue, textSize);
		ActivityTable.addView(newRow);

	}
	*/
	/*
	private void setupActivityTable(final TableLayout activityTable, XmlResourceParser mockactions)
			throws XmlPullParserException, IOException {
		int eventType = -1;

		setupActivityRow(activityTable, "Account", "Activity", "Date", "Time");

		while (eventType != XmlResourceParser.END_DOCUMENT) {
			if(eventType == XmlResourceParser.START_TAG) {

					String tagName = mockactions.getName();

					if (tagName.equals("action")) {
						String accountValue = mockactions.getAttributeValue(null, "account");
						String activityValue = mockactions.getAttributeValue(null, "activity");
						String dateValue = mockactions.getAttributeValue(null, "date");
						String timeValue = mockactions.getAttributeValue(null, "time");
						setupActivityRow(activityTable, accountValue, activityValue, dateValue, timeValue);
					}
				}


			eventType = mockactions.next();
		}

	}
	*/
}
