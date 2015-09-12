package com.first.zillowpark;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;

import com.first.zillowpark.R;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class ResultActivity extends ActionBarActivity{
	
	ActionBar.Tab tab1, tab2;
	JSONObject finaljsonobj,jsonfrmstr;
	String finaljsonstr;
	Button fbShare;
	private String[] chartUrl = new String[3];
	ImageSwitcher imageSwitcher;
	TextView chartHeading;
	int counter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
				
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		if(b!=null)
		{
			finaljsonstr  =(String) b.get("JSON_data");
			try{
            	
            	TabHost tabs = (TabHost)findViewById(android.R.id.tabhost);
			    tabs.setup();

			    TabHost.TabSpec basicInfo = tabs.newTabSpec("Basic Info");
			    basicInfo.setContent(R.id.basicinfo);
			    basicInfo.setIndicator("Basic Info");
			    tabs.addTab(basicInfo);
			    
			    jsonfrmstr=new JSONObject(finaljsonstr);
			    finaljsonobj=jsonfrmstr.getJSONObject("result");
			    
			    TextView headingurl = (TextView)findViewById(R.id.addrhomelink);
			    final String zillowurl = finaljsonobj.getString("Zillowurl");			    
			    final String street = finaljsonobj.getString("street");
			    final String city = finaljsonobj.getString("city");
			    final String state = finaljsonobj.getString("state");
			    final String zipcode = finaljsonobj.getString("zipcode");
			    headingurl.setText(
			            Html.fromHtml("<a href="+zillowurl+">"+street+", "+city+", "+state+"-"+zipcode+"</a>"));
			    headingurl.setMovementMethod(LinkMovementMethod.getInstance());
			
			    TextView propType = (TextView)findViewById(R.id.proptype);
			    String propTypestr = finaljsonobj.getString("Propertytype");
			    propType.setText(propTypestr);
			    
			    TextView yearBuilt = (TextView)findViewById(R.id.yearbuilt);
			    String yearBuiltstr = finaljsonobj.getString("YearBuilt");
			    yearBuilt.setText(yearBuiltstr);
			    
			    TextView lotSize = (TextView)findViewById(R.id.lotsize);
			    String lotSizestr = finaljsonobj.getString("LotSize");
			    lotSize.setText(lotSizestr);
			    
			    TextView finArea = (TextView)findViewById(R.id.finArea);
			    String finAreastr = finaljsonobj.getString("FinishedArea");
			    finArea.setText(finAreastr);
			    
			    TextView bthRooms = (TextView)findViewById(R.id.bthrooms);
			    String bthRoomsstr = finaljsonobj.getString("bath");
			    bthRooms.setText(bthRoomsstr);
			    
			    TextView bedRooms = (TextView)findViewById(R.id.bedrooms);
			    String bedRoomsstr = finaljsonobj.getString("bed");
			    bedRooms.setText(bedRoomsstr);
			    
			    TextView taxAssyear = (TextView)findViewById(R.id.taxassyr);
			    String taxAssyearstr = finaljsonobj.getString("taxyr");
			    taxAssyear.setText(taxAssyearstr);
			    
			    TextView taxAssamt = (TextView)findViewById(R.id.taxassamnt);
			    String taxAssamtstr = finaljsonobj.getString("Taxassessment");
			    taxAssamt.setText(taxAssamtstr);
			    
			    TextView lstSoldprice = (TextView)findViewById(R.id.lstsoldprice);
			    final String lstSoldpricestr = finaljsonobj.getString("Lastprice");
			    lstSoldprice.setText(lstSoldpricestr);
			    
			    TextView lstSolddate = (TextView)findViewById(R.id.lstsolddate);
			    String lstSolddatestr = finaljsonobj.getString("LastDate");
			    lstSolddate.setText(lstSolddatestr);
			    //here
			    TextView zestproplabel = (TextView)findViewById(R.id.lblzestpropamt);
			    String zestpropdatestr = finaljsonobj.getString("UpdatezDate");
			    zestproplabel.setText(Html.fromHtml("Zestimate <sup>&reg</sup> Property estimate as of ")+zestpropdatestr);
			    zestproplabel.setMovementMethod(LinkMovementMethod.getInstance());
			    
			    TextView zestPropamt = (TextView)findViewById(R.id.zestpropamt);
			    String zestPropamtstr = finaljsonobj.getString("UpdatezDatemoney");
			    zestPropamt.setText(zestPropamtstr);
			    
			    ImageView zestImage = (ImageView)findViewById(R.id.zestimg);
			    String zestimgurl=finaljsonobj.getString("overallchangeimage");
			    if(zestimgurl=="http://cs-server.usc.edu:45678/hw/hw6/down_r.gif")
			    {
			    	zestImage.setBackgroundResource(R.drawable.down);
			    }
			    else
			    {
			    	zestImage.setBackgroundResource(R.drawable.up);
			    }
			    
			    TextView zest30docamnt = (TextView)findViewById(R.id.zest30doc);
			    final String zest30docamntstr = finaljsonobj.getString("Overallchange");
			    zest30docamnt.setText(zest30docamntstr);
			    
			    TextView zestRange = (TextView)findViewById(R.id.zestrange);
			    String zestRangelow = finaljsonobj.getString("Timepropertyrangelow");
			    String zestRangehigh = finaljsonobj.getString("Timepropertyrangehigh");
			    zestRange.setText(zestRangelow+"-"+zestRangehigh);
			    
			    //here
			    TextView rentzestlabel = (TextView)findViewById(R.id.lblrentzestamt);
			    String rentzestdate = finaljsonobj.getString("Zestimatedate");
			    rentzestlabel.setText(Html.fromHtml("Rent Zestimate<sup>&reg</sup> valuation as of ")+rentzestdate);
			    rentzestlabel.setMovementMethod(LinkMovementMethod.getInstance());
			    
			    TextView rentZestamt = (TextView)findViewById(R.id.rentzestamt);
			    String rentZestamtstr = finaljsonobj.getString("Zestimatemoney");
			    rentZestamt.setText(rentZestamtstr);
			    
			    ImageView rentImage = (ImageView)findViewById(R.id.rentzestimg);
			    String rentImageurl=finaljsonobj.getString("rentchangeimage");
			    if(rentImageurl=="http://cs-server.usc.edu:45678/hw/hw6/down_r.gif")
			    {
			    	rentImage.setBackgroundResource(R.drawable.down);
			    }
			    else
			    {
			    	rentImage.setBackgroundResource(R.drawable.up);
			    }
			    
			    TextView rent30amt = (TextView)findViewById(R.id.rentzest30drc);
			    String rent30amtstr = finaljsonobj.getString("Rentchange");
			    rent30amt.setText(rent30amtstr);
			    
			    TextView rentRange = (TextView)findViewById(R.id.rentzestrange);
			    String rentRangelow = finaljsonobj.getString("TimeRentrangeLow");
			    String rentRangehigh = finaljsonobj.getString("TimeRentrangeHigh");
			    rentRange.setText(rentRangelow+"-"+rentRangehigh);
			    
			    //hist zest
			    
			    imageSwitcher = (ImageSwitcher)findViewById(R.id.chartswitch);
			    chartUrl[0] = finaljsonobj.getString("year1chart");
			    chartUrl[1] = finaljsonobj.getString("year5chart");
			    chartUrl[2] = finaljsonobj.getString("year10chart");
			    imageSwitcher.setFactory(new ViewFactory() {

			    	   @Override
			    	   public View makeView() {
			    	      ImageView myView = new ImageView(getApplicationContext());
			    	      //myView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			    	      //myView.setAdjustViewBounds(true);
			    	      myView.setScaleType(ImageView.ScaleType.FIT_XY);
			    	      myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.
			    	      MATCH_PARENT,LayoutParams.MATCH_PARENT));
			    	      return myView;
			    	       }

			    	   });
			    TabHost.TabSpec histZest = tabs.newTabSpec("Historical Zestimates");
			    histZest.setContent(R.id.histzest);
			    histZest.setIndicator("Historical Zestimates");
			    tabs.addTab(histZest);
			    new ImageLoadTask(chartUrl[0], imageSwitcher).execute();
			    
			    chartHeading = (TextView)findViewById(R.id.chartheading);
			    chartHeading.setText("Historical Zestimate for the past 1 year");
			    TextView chartaddr = (TextView)findViewById(R.id.chartaddr);
			    chartaddr.setText(street+", "+city+", "+state+"-"+zipcode);
			    
			    //fb
			    fbShare = (Button) findViewById(R.id.fbbtn);
			    fbShare.setOnClickListener(new OnClickListener() {
					
			    	@SuppressWarnings("deprecation")
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
			    		AlertDialog alert=new AlertDialog.Builder(ResultActivity.this).create();
						
						alert.setTitle("Post To Facebook");
						alert.setButton("Post Property Details", new DialogInterface.OnClickListener() {

			                public void onClick(DialogInterface dialog, int which) {
			                	startFBActivity(street, city, state, zipcode, zillowurl, lstSoldpricestr, zest30docamntstr, chartUrl[0]);
			                	dialog.dismiss();
			                }
			            });
						alert.setButton2("Cancel", new DialogInterface.OnClickListener() {

			                public void onClick(DialogInterface dialog, int which) {
			                    // TODO Auto-generated method stub
			                	Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
			                	dialog.dismiss();
			                }
			            });

			            alert.show();
			           
						
					}
				});
			    
			    TextView footer = (TextView)findViewById(R.id.footer);
			    footer.setText(Html.fromHtml("<p>&copy Zillow, Inc., 2006-2014.<br> Use is subject to <a href='http://www.zillow.com/corp/Terms.htm'>Terms of Use</a><br><a href='http://www.zillow.com/zestimate/'>What's a Zestimate?</a></p>"));
			    footer.setMovementMethod(LinkMovementMethod.getInstance());
            }
            catch(Exception ex) {
            	ex.printStackTrace();
            }
			    
			    
		}
		//Fragment fragmentTab1 = new BasicInfo();
	//	Fragment fragmentTab2 = new HistZestimates();
		
        
        
        //tab1.setTabListener(new MyTabListener(fragmentTab1));
     //   tab2.setTabListener(new MyTabListener(fragmentTab2));
		
	}
	
	private void startFBActivity(String street, String city, String state, String zipcode,String zillowurl, String lstSoldpricestr, String zest30docamntstr, String chart){
    	Intent intent=new Intent(this,Post.class);
    	intent.putExtra("street",street);
    	intent.putExtra("city",city);
    	intent.putExtra("state", state);
    	intent.putExtra("zipcode", zipcode);
    	intent.putExtra("zillowurl", zillowurl);
    	intent.putExtra("lstSoldpricestr", lstSoldpricestr);
    	intent.putExtra("zest30docamntstr", zest30docamntstr);
       	intent.putExtra("chart",chart);
    	startActivity(intent);
    	 return;
    }
	

	
	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

	    private String url;
	    private ImageSwitcher imageSwitcher;

	    public ImageLoadTask(String url, ImageSwitcher imageSwitcher) {
	        this.url = url;
	        this.imageSwitcher = imageSwitcher;
	    }

	    @Override
	    protected Bitmap doInBackground(Void... params) {
	        try {
	            URL urlConnection = new URL(url);
	            HttpURLConnection connection = (HttpURLConnection) urlConnection
	                    .openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);
	            return myBitmap;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Bitmap result) {
	        super.onPostExecute(result);
	        Drawable drawable = new BitmapDrawable(getApplicationContext().getResources(), result);
	        imageSwitcher.setImageDrawable(drawable);
	    }

	}
	
	
	public void next(View view){
	      Animation in = AnimationUtils.loadAnimation(this,
	      android.R.anim.slide_in_left);
	      Animation out = AnimationUtils.loadAnimation(this,
	      android.R.anim.slide_out_right);
	      imageSwitcher.setInAnimation(in);
	      imageSwitcher.setOutAnimation(out);
	      if(counter == 0 || counter == 1) {  
	    	  try{
	    		  new ImageLoadTask(chartUrl[counter+1], imageSwitcher).execute();
	    		  counter++;
	    		  if(counter == 1)
	    			  chartHeading.setText("Historical Zestimate for the past 5 years");
	    		  else 
	    			  chartHeading.setText("Historical Zestimate for the past 10 years");
		    	
	    	  }
	    	  catch(Exception ex){
	    		  ex.printStackTrace();
	    	  }
	    
	      }
	   }
	   public void previous(View view){
	      Animation in = AnimationUtils.loadAnimation(this,
	      android.R.anim.slide_out_right);
	      Animation out = AnimationUtils.loadAnimation(this,
	      android.R.anim.slide_in_left);
	      imageSwitcher.setInAnimation(out);
	      imageSwitcher.setOutAnimation(in);
	      if(counter == 1 || counter == 2) {  
	    	  try{
	    		  new ImageLoadTask(chartUrl[counter-1], imageSwitcher).execute();
	    		  counter--;
	    		  if(counter == 0)
	    			  chartHeading.setText("Historical Zestimate for the past 1 year");
	    		  else 
	    			  chartHeading.setText("Historical Zestimate for the past 5 years");

	    	  }
	    	  catch(Exception ex){
	    		  ex.printStackTrace();
	    	  }
	    
	      }
	   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
