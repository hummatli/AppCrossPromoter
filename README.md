# MAHAds - <a href="https://play.google.com/store/apps/developer?id=MobAppHome">MobAppHome</a>  advertisment library 

Library for advertisement own apps through your other apps.
By the help of this lib you can provide your apps list to users through your own other apps and let to install them. Library has build on IDE `Android Studio` and binaries have added to `jcenter()`  `maven` repository.

#PlayStore
<a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample">MAHAndroidUpdater - Sample</a> app has published on Google PlayStore. You can easly test module functionality with downloading it.
<br><a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample"><img src="https://github.com/hummatli/MAHAds/blob/master/imgs/google-play-badge.png" width="200px"/></a> 

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
#Library structure
Library has `MAHAdsController.init()` method. It initialize modul, downloads program list from service and cashes them.

Library contains from to Dialog component
* `MAHAdsDlgExit`- This dialog calls when app quits and offers user quit or stay in app. By the way it offers random two application from your list
* `MAHAdsDlgPrograms` - This dialog list your application from service and let you open nd install them
  
#Installation manual

<b>`1)`</b> To import library to you project add following lines to project's `build.gradle` file. The last stable version is `1.0.6`

```
	dependencies {
    		compile 'com.mobapphome.library:mah-ads:1.0.6'
	}
```

<b>`2)`</b> Call  `MAHAdsController.init()` in your project's starting point. For example: MainActivity's `onCreate()` method or in splash activity. Check url to point your services root path.
Code: 
```java
	MAHAdsController.init(activity, "http://highsoft.az/mahads/");
```

<b>`3)`</b> Call `MAHAdsController.callExitDialog()` when your app quits. It opens `MAHAdsDlgExit` dilog. For example:
Code:	
```java
	public void onBackPressed() {
		MAHAdsController.callExitDialog(activity);
	}
```
<b>Note:</b> To implement `MAHAdsDlgExit` Dialog's `onYes()`, `onNo()`, `onExitWithoutExitDlg()` your main activity has to implement `MAHAdsExitListener`. Otherwise it will through `ClassCastExeption`. `"Your activity must implement MAHAdsExitListener"` 
```java
	public class MainActivity extends AppCompatActivity implements MAHAdsExitListener{
	   @Override
    	   public void onYes() {}

    	   @Override
           public void onNo() {}

           @Override
           public void onExitWithoutExitDlg() {}
	}
```

<b>`4)`</b> To open `MAHAdsDlgPrograms` call `MAHAdsController.callProgramsDialog()` In library sample it has added to menu. Check it
Code:	
```java
	MAHAdsController.callExitDialog(activity);
```

<b>`5)`</b> To customize `MAHAds` dialog UI and overide colors set these values on your main projects `color.xml` file
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

<b>`7)`</b> Localization. Module now supports 4 languages (English, Azerbaijan, Russia, Turkey).  To set localization to app use your own method or if it is static and don't change in program session you can just simply add 		`LocaleUpdater.updateLocale(this, "your_lang");` in the start of your app. For examlpe  `LocaleUpdater.updateLocale(this, "ru");`

<b>`8)`</b> To customize `MAHAds` UI texts and overide them add these lines to main projects `string.xml` and set them values

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
<b>Note:</b> You can even customize dialogs in your application. Copy `layout/mah_ads_dialog_programs.xml`,  `layout/mah_ads_dialog_exit.xml`files and put in your layot dir and customize  them as you want. But keep view ids as they are. They will overide older ones from library. 
 
<b>`8)`</b> As modul takes information from web servcie you need add `INTERNET` permission to main project.
```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

#Proguard configuration
MAHAds uses <a href="https://github.com/jhy/jsoup">Jsoup</a> lib. There for if you want to create your project with proguard you need to add following configuration to your proguard file.

```gradle
##---------------Begin: proguard configuration for Jsoup--------------------------------
-keep public class org.jsoup.** {
public *;
}
##---------------End: proguard configuration for Jsoup--------------------------------
```

#End
Thats all. If you have any probelm with setting library please let me know. Write to settarxan@gmail.com. I will help.

#Contribution
* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create new Pull Request
* Star it

#Contribution for localization
We need help to add new language localization support for libarary. If you have any hope to help us we were very happy and you can check following <i><a href="https://github.com/hummatli/MAHAds/issues">GitHub Issues URL</a></i> to contribute.
If you have any question please ask to me on <i><a href="mailto:settarxan@gmail.com">settarxan@gmail.com</a></i>


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
