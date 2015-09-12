package com.first.zillowpark;
import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;							
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	   
	//String[] state = {"CA","Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"};
	Button search;
	EditText txtaddress,txtcity;
	TextView addresserror,cityerror,sterror,testtext; 
	String errmsg="This field is required";
	String straddress,strcity,strstate,strurl,jsonstr,jsonstrfinal,test,test1;
	Spinner MySpinner1 ;
	JSONObject jsonobj,jsonobj2;
	int isSelected;
	JSONObject json1,json2;
	String resultresphp="",error_holder="";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   
        
        
        MySpinner1 = (Spinner)findViewById(R.id.spinnerst);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.state_array, android.R.layout.simple_spinner_item);            
	    dataAdapter.setDropDownViewResource
	                     (android.R.layout.simple_spinner_dropdown_item);
	       
	    MySpinner1.setAdapter(
	    	      new SpinAdapter(
	    	            dataAdapter,
	    	            R.layout.spinner_title,
	    	            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
	    	            this));
	    
              txtaddress=(EditText) findViewById(R.id.edit_address);
              addresserror=(TextView) findViewById(R.id.addrerror);
                         	
              txtcity=(EditText) findViewById(R.id.edit_city);
              cityerror=(TextView) findViewById(R.id.cityerror);
              
                            
              sterror=(TextView) findViewById(R.id.stateerror);
              testtext=(TextView) findViewById(R.id.testphpid);
              search = (Button) findViewById(R.id.btnSearch);
              ImageView img = (ImageView)findViewById(R.id.zlogo);
      	    img.setOnClickListener(new View.OnClickListener(){
      	        public void onClick(View v){
      	            Intent intent = new Intent();
      	            intent.setAction(Intent.ACTION_VIEW);
      	            intent.addCategory(Intent.CATEGORY_BROWSABLE);
      	            intent.setData(Uri.parse("http://www.zillow.com/"));
      	            startActivity(intent);
      	        }
      	    });
      	 }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void onSearchClick(View view) {
    	strcity=txtcity.getText().toString();
    	//strstate= MySpinner1.getSelectedItem().toString();   
    	int isSelected =MySpinner1.getSelectedItemPosition();
    	straddress = txtaddress.getText().toString();
    	if(straddress.isEmpty() || strcity.isEmpty() ||isSelected==0)
		{
			if(straddress.isEmpty())
  		  {
				addresserror.setText(errmsg);
  		  }
			else
			{
				addresserror.setText("");
			}
			
			if(strcity.isEmpty())
              {
            	  cityerror.setText(errmsg);
              }
			else
			{
				cityerror.setText("");	
			}
			if(isSelected==0)
			{
            	  sterror.setText(errmsg);
              }
			else
			{
				sterror.setText("");
			}
		}

		if(!straddress.equals("")&& !strcity.equals("")&&isSelected!=0)
		{	
			addresserror.setText("");cityerror.setText("");sterror.setText("");
			new fetchJSONphp().execute();
		}
    	
    }
    
    private class fetchJSONphp extends AsyncTask<Void, Void, Void> {

    	//InputStream inputStream = null;
    	//String resultresphp="";
        @Override
        protected Void doInBackground(Void... params) {
        	try
        	{
        	txtaddress=(EditText) findViewById(R.id.edit_address);
        	String street = URLEncoder.encode(txtaddress.getText().toString(), "UTF-8");
            
        	txtcity=(EditText) findViewById(R.id.edit_city);
        	String city = URLEncoder.encode(txtcity.getText().toString(), "UTF-8");
        	
        	MySpinner1 = (Spinner)findViewById(R.id.spinnerst);
        	String state = URLEncoder.encode(MySpinner1.getSelectedItem().toString(), "UTF-8");
        	
        	// Create http cliient object to send request to server
        	 
            HttpClient Client = new DefaultHttpClient();
         
         // Create URL string

           // String targetURL = "http://rahulhw8-env.elasticbeanstalk.com/?streetInput="+street+"&cityInput="+city+"&stateInput="+state;
//String targetURL="http://php.net/manual/en/function.realpath.php";
            String targetURL = "http://rahulhw8-env.elasticbeanstalk.com/?address="+street+"&city="+city+"&state="+state+"&submit=Search";
         //Log.i("httpget", targetURL);
	         try
	         {
	        	 
	             // Create Request to server and get response
	             HttpGet httpget = new HttpGet(targetURL);
	             ResponseHandler<String> responseHandler = new BasicResponseHandler();
	             resultresphp = Client.execute(httpget, responseHandler);       
	            // testtext.setText(resultresphp);
	          }
	         	catch(Exception ex)
	           {
	        	//testtext.setText("Fail!");
	         		ex.printStackTrace();
	            }    	
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	return null;
        
        }

        @Override
        protected void onPostExecute(Void result) {
        		try {
					json1=(JSONObject) new JSONTokener(resultresphp).nextValue();
					json2=json1.getJSONObject("result");
					error_holder=(String)json2.get("error");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		catch(NullPointerException e)
        		{
        			e.printStackTrace();        			
        		}
        		
        		if(error_holder.equals("Success"))
        		{        			
        			 
    			  	Intent intent = new Intent(MainActivity.this, ResultActivity.class);
    			  	intent.putExtra("JSON_data", resultresphp);
    			  	startActivity(intent);
        		}
        		else if(error_holder.equals("No exact match found--Verify that the given address is correct"))
        		{
        			TextView txt = (TextView) findViewById(R.id.testphpid);
    			  	txt.setText("No exact match found -- Verify that the given address is correct."); 
        		}
        		else if(error_holder.equals(""))
        		{
        			TextView txt = (TextView) findViewById(R.id.testphpid);
    			  	txt.setText(""); 
        		}
        		
        	   	super.onPostExecute(result);
        	}
        
    }
    
}
    
    
    
   
    
