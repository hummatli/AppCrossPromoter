# MAHAds - Library for advertisement own apps through other apps. 

By the help of this lib you can provide your apps to users of your other apps.

===========
#How to use. 

a) You need small service provide your application list. Structure of the service as below

root->
    imgs
    program_version.php
    program_list.php

  I)imgs  
  II) program_version.php returns json as below 

    {"version":"13"}
  
  II) program_list.php returns json as below. There is two  application in this sample:

    {"programs":[ {"name":"Avto Nişanlar", "desc":"Bütün yol nişanları", "uri":"com.mobapphome.avtonishanlar",          "img":"imgs/avto_nishanlar2.png", "release_date":"10/10/2014"} ,{"name":"Məzənnə", "desc":"Valyuta çeviricisi və məzənnələr", "uri":"com.mobapphome.currency", "img":"imgs/mezenne2.png", "release_date":"05/12/2014"}]}
  
b) Installation manual

1)Add MAHAds lib to project

2)MAHAds use three lib 
	I) appcompat-v7
	II)android-smart-image-view-1.0.0.jar
	III)jsoup-1.7.3.jar

3)Add following to your project somewere start or splash
Code: 
    MAHAdsController.init(this, url_str);
    MAHAdsController.setInternalCalled(getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));

4)When you dont want open exit dialog on exit you call activity as below adn send argument
    Intent app = pack.getLaunchIntentForPackage(pckgName);
    app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
    getContext().startActivity(app);						

MAHAdsController.setInternalCalled(getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));
sets this value on initialization and checks on exit as below

5)Put following code to your start activity. (Because open exit dialog on exit)
Code:	
	public void onBackPressed() {
		if(MAHAdsController.isInternalCalled()){
			super.onBackPressed();
		}else{
			final FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); //get the fragment
			final MAHAdsDlgExit frag = MAHAdsDlgExit.newInstance(this, new ExitListiner() {
			
				@Override
				public void onYes() {
					finish();	
				}
			
				@Override
				public void onNo() {
					// TODO Auto-generated method stub
				}
			});
			frag.show(ft, "AdsDialogExit");		
		}	
		//super.onBackPressed();
	}

6) Change color values for MAHAds for your project. And customize  the values
	<color name="mah_ads_title_bar_color">#FF0a23e1</color>						
	<color name="mah_ads_btn_open_install_text_color">#FF0a23e1</color>						
	<color name="mah_ads_text_color">#FF0a23e1</color>						
	<color name="mah_ads_no_img_color">#330a23e1</color>						
	<color name="mah_ads_question_txt_color">#FF0a23e1</color>	
	<color name="mah_ads_new_txt_background_color">#FFFFFFFF</color>						
	<color name="mah_ads_window_background_color">#FFFFFFFF</color>						
								
	<color name="mah_ads_dark_color">#FF0a23e1</color>						
	<color name="mah_ads_middle_color">#660a23e1</color>						
	<color name="mah_ads_light_color">#FFFFFFFF</color>			

7) Add this to string.xml in main project8

    <string name="mah_ads_close">Close</string>
    <string name="mah_ads_dlg_title">Recommended applications</string>
    <string name="mah_ads_text_google_play">Open in GooglePlay</string>
    <string name="mah_ads_info_version">Version</string>
    <string name="mah_ads_internet_update_error">Error, please check internet connection or link</string>
    <string name="mah_ads_open_program">Open</string>
    <string name="mah_ads_install_program">Install</string>
    <string name="mah_ads_refresh_btn">Retry</string>
    <string name="mah_ads_free_aps">Recommended applications</string>
    <string name="mah_ads_new_text">New</string>
    
    <string name="mah_ads_dlg_exit_question">Do you want exit?</string>
    <string name="mah_ads_dlg_exit_positive_btn_txt">Exit</string>
    <string name="mah_ads_dlg_exit_negativ_btn_txt">Stay</string>
    
    <string name="mah_ads_dlg_exit_btn_more_txt_1">Applications</string>
    <string name="mah_ads_dlg_exit_btn_more_txt_2">Detailed</string>
    	
8) Add following permission to main project
    <uses-permission android:name="android.permission.INTERNET" />

9)To show on menu on activity do following

    a) add to menu.xml
	<item
        android:id="@+id/action_mahads"
        android:orderInCategory="100"
        android:title="@string/mah_ads_free_aps"
        android:icon="@drawable/ic_action_more"
        app:showAsAction="ifRoom"/> 
	
     b)on menu click methd write
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_mahads){
			final FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); 
			final MAHAdsDlgPrograms frag = MAHAdsDlgPrograms.newInstance(this);
			frag.show(ft, "AdsDialogFragment");
			return true;			
		}
		return super.onOptionsItemSelected(item);
	}
	
