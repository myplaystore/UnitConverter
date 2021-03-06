package com.nineinfosys.unitconverter.ConverterActivityList.Magnetism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.nineinfosys.unitconverter.Adapters.RecyclerViewConversionListAdapter;

import com.nineinfosys.unitconverter.ConverterActivities.ActivitySetting;
import com.nineinfosys.unitconverter.ConverterActivityList.Light.ConversionLuminousIntensityListActivity;
import com.nineinfosys.unitconverter.Engines.Magnetism.MagneticFluxDensityConverter;
import com.nineinfosys.unitconverter.R;
import com.nineinfosys.unitconverter.Supporter.ItemList;
import com.nineinfosys.unitconverter.Supporter.Settings;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversionMagneticFluxListActivity extends AppCompatActivity implements TextWatcher {

    List<ItemList> list = new ArrayList<ItemList>();
    private  String stringSpinnerFrom;
    private double doubleEdittextvalue;
    private EditText edittextConversionListvalue;
    private TextView textconversionFrom,textViewConversionShortform;
    View ChildView ;
    DecimalFormat formatter = null;
    private static final int REQUEST_CODE = 100;
    private File imageFile;
    private Bitmap bitmap;
    private PrintHelper printhelper;


    private RecyclerView rView;
    RecyclerViewConversionListAdapter rcAdapter;
    List<ItemList> rowListItem,rowListItem1;

    private String   strTesla = null;
    private String   strWeberpersquare = null;
    private String   strWeberpersquarecentimeter = null;
    private String   strWeberpersquareinch = null;
    private String   strMaxwellpersquaremeter = null;
    private String   strMaxwellpersquarecentimeter = null;
    private String   strMaxwellpersquareinch = null;
    private String   strGauss = null;
    private String   strLinepersquarecentimeter = null;
    private String   strLinepersquareinch = null;
    private String   strGamma = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_list);
        //keyboard hidden first time
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#757575")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Conversion Report");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#494949"));
        }

        //format of decimal pint
        formatsetting();

        MobileAds.initialize(ConversionMagneticFluxListActivity.this, getString(R.string.ads_app_id));
        AdView mAdView = (AdView) findViewById(R.id.adViewUnitConverterList);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        edittextConversionListvalue=(EditText)findViewById(R.id.edittextConversionListvalue) ;
        textconversionFrom=(TextView) findViewById(R.id.textViewConversionFrom) ;
        textViewConversionShortform=(TextView) findViewById(R.id.textViewConversionShortform) ;
        //get the value from temperture activity
        stringSpinnerFrom = getIntent().getExtras().getString("stringSpinnerFrom");
        doubleEdittextvalue= getIntent().getExtras().getDouble("doubleEdittextvalue");
        edittextConversionListvalue.setText(String.valueOf(doubleEdittextvalue));

        NamesAndShortform(stringSpinnerFrom);

        rowListItem = getAllunitValue(stringSpinnerFrom,doubleEdittextvalue);

        //Initializing Views
        rView = (RecyclerView) findViewById(R.id.recyclerViewConverterList);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new GridLayoutManager(this, 1));


        //Initializing our superheroes list
        rcAdapter = new RecyclerViewConversionListAdapter(this,rowListItem);
        rView.setAdapter(rcAdapter);

        edittextConversionListvalue.addTextChangedListener(this);



    }

    private void NamesAndShortform(String stringSpinnerFrom) {
        switch(stringSpinnerFrom)
        {

            case "Tesla - T":
                textconversionFrom.setText("Tesla");
                textViewConversionShortform.setText("T");
                break;

            case  "Weber/square meter - Wb/m²":
                textconversionFrom.setText("Weber/square meter");
                textViewConversionShortform.setText("Wb/m²");

                break;
            case "Weber/square centimeter - Wb/cm²":
                textconversionFrom.setText("Weber/square centimeter");
                textViewConversionShortform.setText("Wb/cm²");
                break;
            case "Weber/square inch - Wb/in²":
                textconversionFrom.setText("Weber/square inch");
                textViewConversionShortform.setText("Wb/in²");
                break;
            case "Maxwell/square meter - Mx/m²":
                textconversionFrom.setText("Maxwell/square meter");
                textViewConversionShortform.setText("Mx/m²");
                break;
            case "Maxwell/square centimeter - Mx/cm²":
                textconversionFrom.setText("Maxwell/square centimeter");
                textViewConversionShortform.setText("Mx/cm²");
                break;
            case   "Maxwell/square inch - Mx/in² ":
                textconversionFrom.setText("Maxwell/square inch");
                textViewConversionShortform.setText(" Mx/in²");
                break;
            case "Gauss - G":
                textconversionFrom.setText("Gauss");
                textViewConversionShortform.setText("G");
                break;
            case "Line/square centimeter - L/cm²":
                textconversionFrom.setText("Line/square centimeter");
                textViewConversionShortform.setText("L/cm² ");
                break;
            case  "Line/square inch - L/m²":
                textconversionFrom.setText("Line/square inch");
                textViewConversionShortform.setText("L/in²");
                break;
            case "Gamma - gamma":
                textconversionFrom.setText("Gamma");
                textViewConversionShortform.setText("gamma");
                break;



        }
    }


    private void formatsetting() {
        //fetching value from sharedpreference
        SharedPreferences sharedPreferences =this.getSharedPreferences(Settings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Fetching thepatient_mobile_Number value form sharedpreferences
        String  FormattedString = sharedPreferences.getString(Settings.Format_Selected_SHARED_PREF,"Thousands separator");
        String DecimalplaceString= sharedPreferences.getString(Settings.Decimal_Place_Selected_SHARED_PREF,"2");
        Settings settings=new Settings(FormattedString,DecimalplaceString);
        formatter= settings.setting();
    }

    private List<ItemList> getAllunitValue(String strSpinnerFrom,double doubleEdittextvalue1) {
        MagneticFluxDensityConverter c = new MagneticFluxDensityConverter(strSpinnerFrom,(int)doubleEdittextvalue1);
        ArrayList<MagneticFluxDensityConverter.ConversionResults> results = c.calculateMagneticFluxDensityConversion();
        int length = results.size();
        for (int i = 0; i < length; i++) {
            MagneticFluxDensityConverter.ConversionResults item = results.get(i);



            strTesla = String.valueOf(formatter.format(item.getTesla()));
                strWeberpersquare = String.valueOf(formatter.format(item.getWeberpersquare()));
             strWeberpersquarecentimeter = String.valueOf(formatter.format(item.getWeberpersquarecentimeter()));

             strWeberpersquareinch = String.valueOf(formatter.format(item.getWeberpersquareinch()));
             strMaxwellpersquaremeter = String.valueOf(formatter.format(item.getMaxwellpersquaremeter()));
              strMaxwellpersquarecentimeter = String.valueOf(formatter.format(item.getMaxwellpersquarecentimeter()));
             strMaxwellpersquareinch = String.valueOf(formatter.format(item.getMaxwellpersquareinch()));
             strGauss = String.valueOf(formatter.format(item.getGauss()));
             strLinepersquarecentimeter = String.valueOf(formatter.format(item.getLinepersquarecentimeter()));
              strLinepersquareinch = String.valueOf(formatter.format(item.getLinepersquareinch()));
             strGamma = String.valueOf(formatter.format(item.getGamma()));



            list.add(new ItemList("T","Tesla",strTesla,"T"));
            list.add(new ItemList("Wb/m²","Weber/square meter",strWeberpersquare,"Wb/m²"));
            list.add(new ItemList("Wb/cm²"," Weber/square centimeter",strWeberpersquarecentimeter,"Wb/cm²"));
            list.add(new ItemList("Wb/in²"," Weber/square inch",strWeberpersquareinch,"Wb/in²"));
            list.add(new ItemList(" Mx/m² ","Maxwell/square meter",strMaxwellpersquaremeter," Mx/m² "));


            list.add(new ItemList("Mx/cm²"," Maxwell/square centimeter",strMaxwellpersquarecentimeter,"Mx/cm²"));
            list.add(new ItemList("Mx/in²","Maxwell/square inch",strMaxwellpersquareinch,"Mx/in²"));
            list.add(new ItemList("G"," Gauss",strGauss,"G"));
            list.add(new ItemList("L/cm²  "," Line/square centimeter",strLinepersquarecentimeter,"L/cm²"));
            list.add(new ItemList("L/in²","Line/square inch",strLinepersquareinch,"L/in²"));
            list.add(new ItemList("gamma","Gamma",strGamma,"gamma"));


        }
        return list;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        rowListItem.clear();
        try {

            double value = Double.parseDouble(edittextConversionListvalue.getText().toString().trim());

            rowListItem1 = getAllunitValue(stringSpinnerFrom,value);


            rcAdapter = new RecyclerViewConversionListAdapter(this,rowListItem1);
            rView.setAdapter(rcAdapter);

        }
        catch (NumberFormatException e) {
            doubleEdittextvalue = 0;

        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

