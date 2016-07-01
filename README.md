# MAHAds - <a href="https://play.google.com/store/apps/developer?id=MobAppHome">MobAppHome</a>  advertisment library 

Library for advertisement own apps through other apps..-
By the help of this lib you can provide your apps to users of your other apps...

#Images
<img src="https://github.com/settarxan/MAHAds/blob/master/imgs/exit_dlg.png" width="200px"/>
<img src="https://github.com/settarxan/MAHAds/blob/master/imgs/programs_dlg.png" width="200px"/>

#Service structure
You need small service provide your application list. Structure of the service as below

root->
    imgs
    program_version.php
    program_list.php

  I)imgs  
  II) program_version.php returns json as below 

```json
	{
	 "version":"13"
	}
```

  II) program_list.php returns json as below. There is two  application in this sample:
  
```json
	{
	"programs":[ 
		{
		  "name":"Avto Nişanlar", 
		  "desc":"Bütün yol nişanları", 
		  "uri":"com.mobapphome.avtonishanlar",  
		  "img":"imgs/avto_nishanlar2.png", 
		  "release_date":"10/10/2014"
		 },
		 {
		   "name":"Məzənnə", 
		   "desc":"Valyuta çeviricisi və məzənnələr", 
		   "uri":"com.mobapphome.currency",  
		   "img":"imgs/mezenne2.png", 
		   "release_date":"05/12/2014"
		 }
	]
	}
```

  
#Installation manual

1)Add MAHAds lib to project

2)MAHAds use three lib 
	I) appcompat-v7
	II)android-smart-image-view-1.0.0.jar
	III)jsoup-1.7.3.jar

3)Add following to your project somewere start or splash. In place of "url_str" below write your service files root.
Code: 
```java
	MAHAdsController.init(this, url_str);
	MAHAdsController.setInternalCalled(getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));
```


4)When you dont want open exit dialog on exit you call activity as below adn send argument
```java
	Intent app = pack.getLaunchIntentForPackage(pckgName);
	app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
	getContext().startActivity(app);						
```

```java
	MAHAdsController.setInternalCalled(getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));
```
sets this value on initialization and checks on exit as below

5)Put following code to your start activity. (Because open exit dialog on exit)
Code:	
```java
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
```

6) Change color values for MAHAds for your project. And customize  the values
```xml
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
```

7) Add this to string.xml in main project8

```xml
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
```
    	
8) Add following permission to main project
```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

9)To show on menu on activity do following

    a) add to menu.xml
```xml
	<item
        android:id="@+id/action_mahads"
        android:orderInCategory="100"
        android:title="@string/mah_ads_free_aps"
        android:icon="@drawable/ic_action_more"
        app:showAsAction="ifRoom"/> 
```
	
     b)on menu click methd write
```java
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
```
	
#Developed By
Settar Hummetli - settarxan@gmail.com


#License
Copyright 2015  - <a href="https://www.linkedin.com/pub/settar-hummetli/41/a75/937">Settar Hummetli</a>   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
