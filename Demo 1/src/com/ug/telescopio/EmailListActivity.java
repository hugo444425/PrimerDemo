package com.ug.telescopio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class EmailListActivity extends ListActivity implements OnClickListener {

    List<HashMap<String, String>> emails = new ArrayList<HashMap<String, String>>();
    public final static String EMAIL = "email";
    public final static String DATE_ADDED = "date";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
        
        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        
        SimpleAdapter adapter = new SimpleAdapter(this, emails, android.R.layout.simple_list_item_2, new String[] {EMAIL, DATE_ADDED}, new int[] {android.R.id.text1, android.R.id.text2 });        
        setListAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        EditText editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        String email = editTextEmail.getText().toString();
        
        if (!email.trim().equals("")) {                     
            HashMap<String, String> element = new HashMap<String, String>();
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());
            element.put(EMAIL, email);
            element.put(DATE_ADDED, "agregado " + date);
            
            if(!email.trim().equals("")){
            	if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            		emails.add(element);
                    final SimpleAdapter adapter = (SimpleAdapter) getListAdapter();
                    adapter.notifyDataSetChanged();
            	} else {
            		Toast.makeText(this, "ocurrio un error", Toast.LENGTH_SHORT).show();
            	}
            }
            
            
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {        
        Object element = getListAdapter().getItem(position);
        try {
            @SuppressWarnings("unchecked")
            String email = ((HashMap<String, String>)element).get(EMAIL);
            Intent intent = new Intent(getApplicationContext(), ShowSearchQueryActivity.class);
            intent.putExtra(ShowSearchQueryActivity.QUERY, email);
            startActivity(intent);
        } catch (ClassCastException e) {
            Log.e("TAG","Clase incorrecta " + Log.getStackTraceString(e));        
        }                
    }

}