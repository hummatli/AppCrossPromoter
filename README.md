# MAHAds - <a href="https://play.google.com/store/apps/developer?id=MobAppHome">MobAppHome</a>  advertisment library 

Library for advertisement own apps through other apps.
By the help of this lib you can provide your apps list to users through your own other apps and let to install them. Library has build on IDE `Android Studio` and binaries have added to `jcenter()`  `maven` repository.

#Images
<img src="https://github.com/settarxan/MAHAds/blob/master/imgs/exit_dlg.png" width="200px"/>
<img src="https://github.com/settarxan/MAHAds/blob/master/imgs/programs_dlg.png" width="200px"/>

#Service structure
To provide your apps list you have to implement service provider. Structure of the service is as below. Your root folder has to contain `imgs` folder and two files `program_version.php`, `program_list.php`.

``` 
root->
    imgs			- "contains logos for your porgram on the list"
    program_version.php 	- "show the ads service version."
    program_list.php 		- "contains program list"
```
 
 `program_version.php ` service has to return json as below. 

```json
	{
	 "version":"13"
	}
```

 `program_list.php` service has to return json as below. There is two  application in this sample:
  
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

`1)` To import library to you project add following lines to project's `build.gradle` file. The last stable version is `0.9.7`

```
	dependencies {
    		compile 'com.mobapphome.library:mah-ads:0.9.7'
	}
```

2)Add following to your project's starting point. For example: MainActivity's `onCreate()` method or in splash activity. Check url to point your services root path.
Code: 
```java
	MAHAdsController.init(this, "http://highsoft.az/mahads/");
	MAHAdsController.setInternalCalled(getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));
```


3)When you dont want to open exit dialog when you quit app, you have to start activity with argument `MAHAdsController.MAH_ADS_INTERNAL_CALLED` set to `true`
```java
	Intent app = pack.getLaunchIntentForPackage(pckgName);
	app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
	getContext().startActivity(app);						
```

```java
	MAHAdsController.setInternalCalled(getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));
```
sets this value on initialization and checks on exit as below

4)Put following code to your start activity to open exit dialog on quit
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

5) To customize `MAHAds` dialog UI and overide colors set these values on your main projects `color.xml` file
```xml
    <color name="mah_ads_window_background_color">#FFFFFFFF</color>
    <color name="mah_ads_title_bar_color">#FF3F51B5</color>
    <color name="mah_ads_colorAccent">#FFFF4081</color>

    <color name="mah_ads_all_and_btn_text_color">#FF3F51B5</color>
    <color name="mah_ads_question_txt_color">#FF3F51B5</color>
    <color name="mah_ads_yes_no_txt_color">#FF3F51B5</color>

    <color name="mah_ads_btn_other_border_color">#FF303F9F</color>
    <color name="mah_ads_btn_background_color_pressed">#333F51B5</color>

    <color name="mah_ads_text_view_new_background_color">#FFFF0000</color>
    <color name="mah_ads_text_view_new_text_color">#FFFFFFFF</color>
    <color name="mah_ads_no_img_color">#333F51B5</color>			
```

6) To customize `MAHAds` UI texts and overide them add these lines to main projects `string.xml` and set them values

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
    <string name="mah_ads_dlg_exit_positive_btn_txt">EXIT</string>
    <string name="mah_ads_dlg_exit_negativ_btn_txt">STAY</string>

    <string name="mah_ads_dlg_exit_btn_more_txt_1">Applications</string>
    <string name="mah_ads_dlg_exit_btn_more_txt_2">Detailed</string>
```
    	
7) As modul takes information from web servcie you need add `INTERNET` permission to main project.
```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

8)To set menu on activity add `item` to `menu.xml` and implement `onOptionsItemSelected(MenuItem item)` method

   a) add to menu.xml
```xml
	<item
        android:id="@+id/action_mahads"
        android:orderInCategory="100"
        android:title="@string/mah_ads_free_aps"
        android:icon="@drawable/ic_action_more"
        app:showAsAction="ifRoom"/> 
```
	
   b)overide `onOptionsItemSelected(MenuItem item)`
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

#End
Thats all. If you have any probelm with setting library please let me know. Write to settarxan@gmail.com. I will help.

#Contribution
* Fork it
* Star it
* Add your own stuff
* Use it

#Developed By
Sattar Hummatli - settarxan@gmail.com


#License
Copyright 2015  - <a href="https://www.linkedin.com/in/hummatli">Sattar Hummatli</a>   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
