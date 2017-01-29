# MAHAds - <a href="https://play.google.com/store/apps/developer?id=Sattar+Hummatli+-+MobAppHome">MobAppHome</a>  advertisment library 
[ ![Download](https://api.bintray.com/packages/hummatli/maven/mah-ads/images/download.svg) ](https://bintray.com/hummatli/maven/mah-ads/_latestVersion) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](http://www.apache.org/licenses/LICENSE-2.0) 
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MAHAds-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4509)

<img src="https://raw.githubusercontent.com/hummatli/MAHAndroidUpdater/master/imgs/green_star.png" width="20px"/>  _**Don't forget to start the protect to support us**_

<img src="https://raw.githubusercontent.com/hummatli/MAHAds/master/imgs/exit_dlg.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/MAHAds/master/imgs/programs_dlg.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/MAHAds/master/imgs/img3.png" width="200px"/>

### Description
Library for advertisement own apps through your other apps.
By the help of this lib you can provide your apps list to users through your own other apps and let to install them. 
Library has build on IDE `Android Studio` and binaries have added to `jcenter()`  `maven` repository.
<br>You can check  <a href="https://bintray.com/hummatli/maven/mah-ads#statistics">jCenter() download statistics</a> on this link - https://bintray.com/hummatli/maven/mah-ads#statistics

There is a list of [application using MAHAds](https://github.com/hummatli/MAHAndroidUpdater#applications-using-mahads). It would be nice if see your app link there too. If you use this library and want to see your app in the start of the [list](https://github.com/hummatli/MAHAndroidUpdater#applications-using-mahads) please [inform me](mailto:settarxan@gmail.com) or send a pull request.

### Contributors
* Developer:
[Sattar Hummatli](https://github.com/hummatli) - [LinkedIn](https://www.linkedin.com/in/hummatli), settarxan@gmail.com, [Other libs](https://github.com/hummatli/MAHAds#other-libraries-by-developer)
* Translator `French`: [Fariz Aghayev](https://github.com/farizaghayev)
* Translator `Portuguese`: [azzarr](https://github.com/azzarr)
* Translator `Hindi, German, Spanish`: [Harsh Dalwadi](https://github.com/dalwadi2)
* Translator `Italian`: [Rawnly](https://github.com/rawnly)

### Contents
* [Description](https://github.com/hummatli/MAHAds#description)
* [Service structure](https://github.com/hummatli/MAHAds#service-structure)
* [Library structure](https://github.com/hummatli/MAHAds#library-structure)
* [Installation manual](https://github.com/hummatli/MAHAds#installation-manual)
* [Help - Issues](https://github.com/hummatli/MAHAds#help---issues)
* [Releases - Upgrade documentation](https://github.com/hummatli/MAHAds#releases---upgrade-documentation)
* [To contribute](https://github.com/hummatli/MAHAds#to-contribute)
* [Contributors](https://github.com/hummatli/MAHAdsr#contributors)
* [Localization](https://github.com/hummatli/MAHAds#localization)
* [Applications using MAHAds](https://github.com/hummatli/MAHAds#applications-using-mahads)
* [Other libraries by developer](https://github.com/hummatli/MAHAds#other-libraries-by-developer)

### Sample App in PlayStore
<a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample">MAHAds - Sample</a> app has published on Google PlayStore. You can easly test module functionality with downloading it.
<br><a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample"><img src="https://raw.githubusercontent.com/hummatli/MAHAds/master/imgs/google-play-badge.png" width="200px"/></a> 

### Service structure
To provide your apps list you have to implement service provider. Structure of the service is as below. Your root folder has to contain `imgs` folder and two files `program_version.json`, `program_list.json`.

Now you can set `canonical` - imgs/avto_nishanlar2.png and `noncanonical` - https://highsoft.az/avto_nishanlar2.png link to image for example. 

``` 
root->
    imgs			- "contains logos for your porgram on the list"
    program_version.json 	- "show the ads service version."
    program_list.json 		- "contains program list"
```
 
 `program_version.json ` service has to return json as below. 

```json
	{
	 "version":"13"
	}
```

 `program_list.json` service has to return json as below. There is two  application in this sample:
  
```json
	{
	"programs":[ 
		{
		  "name":"Avto Nişanlar", 
		  "desc":"Bütün yol nişanları", 
		  "uri":"com.mobapphome.avtonishanlar",  
		  "img":"imgs/avto_nishanlar2.png", 
		  "release_date":"22/12/2016",
		  "update_date":"22/12/2016"
		 },
		 {
		   "name":"Məzənnə", 
		   "desc":"Valyuta çeviricisi və məzənnələr", 
		   "uri":"com.mobapphome.currency",  
		   "img":"https://highsoft.az/mezenne2.png", 
		   "release_date":"22/12/2016",
		   "update_date":"22/12/2016"
		 }
	]
	}
```

`release_date` and `update_date` can be missed. Data will read.  
Library has testes with up to `1000 program items` in program list. It works normally.  
You can provide `http://` and `https://` services. Library works both of them.  
You can check you json validity with this [jsonlint.com](http://jsonlint.com/)

### Library structure
Library has `MAHAdsController.init()` method. It initialize modul, downloads program list from service and cashes them.

Library contains from to Dialog component
* `MAHAdsDlgExit`- This dialog calls when app quits and offers user quit or stay in app. By the way it offers random two application from your list
* `MAHAdsDlgPrograms` - This dialog list your application from service and let you open nd install them
  
  
### Installation manual
**1)** To import library to you project add following lines to project's `build.gradle` file. The last stable version is `2.1.0`

```
	dependencies {
    		compile 'com.mobapphome.library:mah-ads:2.1.0'
	}
```

**2)** Call `MAHAdsController.init()` in your project's starting point. For example: MainActivity's `onCreate()` method or in splash activity. Check url to point your services root path.  `MAHAdsController.init()` method has two variation with different arguments.
Code: 
```java
	MAHAdsController.init(this,"http://highsoft.az/mahads/", "github_apps_prg_version.json", "github_apps_prg_list.json")
```

**3)** Call `MAHAdsController.callExitDialog()` when your app quits. It opens `MAHAdsDlgExit` dilog. `MAHAdsController.callExitDialog()` method has three variation with different arguments.  By the help oh this arguments you can customize `Info button` on the upper right corner of dilog.
Code:	
```java
	public void onBackPressed() {
		MAHAdsController.callExitDialog(activity);
	}
```
**Note:** To implement `MAHAdsDlgExit` Dialog's `onYes()`, `onNo()`, `onExitWithoutExitDlg()` , `onEventHappened(String eventStr)` your main activity has to implement `MAHAdsExitListener`. Otherwise it will through `ClassCastExeption`. `"Your activity must implement MAHAdsExitListener"` 
```java
	public class MainActivity extends AppCompatActivity implements MAHAdsExitListener{
	   @Override
    	   public void onYes() {}

    	   @Override
           public void onNo() {}

           @Override
           public void onExitWithoutExitDlg() {}

           @Override
           public void onEventHappened(String eventStr) {}
	}
```

**4)** To open `MAHAdsDlgPrograms` call `MAHAdsController.callProgramsDialog()`. In library sample it has added to menu. `MAHAdsController.callProgramsDialog()` method has three variation with different arguments.  By the help oh this arguments you can customize `Info button` on the upper right corner of dilog. 
Code:	
```java
	MAHAdsController.callProgramsDialog(activity);
```

**5)** To customize `MAHAds` dialog UI and overide colors set these values on your main projects `color.xml` file
```xml
	<color name="mah_ads_window_background_color">#FFFFFFFF</color>
	<color name="mah_ads_title_bar_color">#FF3F51B5</color>
	<!--new--> <color name="mah_ads_title_bar_text_color">#ffffff</color> 
	<color name="mah_ads_colorAccent">#FFFF4081</color>

	<color name="mah_ads_all_and_btn_text_color">#FF3F51B5</color>
	<!--new--> <color name="mah_ads_no_image_color">#3F51B5</color>
	<!--new--> <color name="mah_ads_program_item_desc_text_color">#4a76e6</color> 
	<color name="mah_ads_question_txt_color">#FF3F51B5</color>
	<color name="mah_ads_yes_no_txt_color">#FFFF4081</color>

	<color name="mah_ads_btn_other_border_color">#848ed2</color>
	<color name="mah_ads_btn_background_color_pressed">#333F51B5</color>

	<color name="mah_ads_text_view_new_background_color">#FF0000</color>
	<color name="mah_ads_text_view_new_text_color">#FFFFFFFF</color>
	<color name="mah_ads_no_img_color">#333F51B5</color>			
```

**7)** `Localization:`  Following languages is supporting by the lib - [Supported Languages](https://github.com/hummatli/MAHAds#localization).  To set localization to app use your own method or if it is static and don't change in program session you can just simply add 		`LocaleUpdater.updateLocale(this, "your_lang");` in the start of your app. For examlpe  `LocaleUpdater.updateLocale(this, "ru");`

**8)** To customize `MAHAds` UI texts and overide them add these lines to main projects `string.xml` and set them values.   
To help translators there prefixes on the name of strings
* < command verb (actions)> - These are commands verbs. Meaninaction on UI , dialogs
* < adjective > - adjectives

```xml
    <!-- * command verb--> <string name="cmnd_verb_mah_ads_close">Close</string>
    <string name="mah_ads_dlg_title">Recommended</string>
    <string name="mah_ads_text_google_play">Open in Google Play</string>
    <string name="mah_ads_info_version">Version</string>
    <string name="mah_ads_internet_update_error">Error, please check internet connection and try again.</string>
    <!-- * command verb--> <string name="cmnd_verb_mah_ads_open_program">Open</string>
    <!-- * command verb--> <string name="cmnd_verb_mah_ads_install_program">Install</string>
    <!-- * command verb--><string name="cmnd_verb_mah_ads_refresh_btn">Retry</string>
    <string name="mah_ads_free_aps">Recommended applications</string>
    <!-- * adjective--><string name="adjective_mah_ads_new_text">New</string>
    <string name="mah_ads_updated_text">Updated</string>

    <string name="mah_ads_dlg_exit_question">Do you want to exit?</string>
    <!-- * command verb--><string name="cmnd_verb_mah_ads_dlg_exit_positive_btn_txt">Exit</string>
    <!-- * command verb--><string name="cmnd_verb_mah_ads_dlg_exit_negativ_btn_txt">Stay</string>
    
    <string name="mah_ads_dlg_exit_btn_more_txt_1">Applications</string>
    <string name="mah_ads_dlg_exit_btn_more_txt_2">Detailed</string>

    <string name="mah_ads_info_popup_text">MAHAds library</string>
    <string name="mah_ads_play_service_not_found">Install Google Play Services to install application</string>
```
**Note** You can even customize dialogs in your application. Copy `layout/mah_ads_dialog_programs.xml`,  `layout/mah_ads_dialog_exit.xml`files and put in your layot dir and customize  them as you want. But keep view ids as they are. They will overide older ones from library. 
 
**8)** As modul takes information from web servcie you need add `INTERNET` permission to main project.
```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

### Proguard configuration
MAHAds uses <a href="https://github.com/jhy/jsoup">Jsoup</a> lib. There for if you want to create your project with proguard you need to add following configuration to your proguard file.

```gradle
##-----------------To show exceptions right --------------------------------------
-keep public class * extends java.lang.Exception

##---------------Begin: proguard configuration for Jsoup--------------------------------
-keep public class org.jsoup.** {
public *;
}
##---------------End: proguard configuration for Jsoup--------------------------------

##---------------Begin: proguard configuration for Bumptech/Glide--------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#Following is needed for Glide on DexGuard only. Uncomment it when DexGuard
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
##---------------End: proguard configuration for Bumptech/Glide--------------------------------
```

### Help - Issues
If you have any problem with setting and using library or want to ask question, please let me know. Create [issue](https://github.com/hummatli/MAHAds/issues) or write to <i><a href="mailto:settarxan@gmail.com">settarxan@gmail.com</a></i>. I will help.

### Releases - Upgrade documentation
See [releases](https://github.com/hummatli/MAHAds/releases). Please,read release notes to migrate your app from old version to a newer.

### To contribute
I am open to here offers and opinions from you  

* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create new Pull Request
* Star it

### Localization
Library now supports following languages 
* Azerbaijan
* English
* French
* German
* Hindi
* Italian
* Portuguese
* Russia
* Spanish
* Turkey
* [Add yours language](https://github.com/hummatli/MAHAds/blob/master/README.md#to-contribute-for-localization)

#### To contribute for localization  
**To help translator in context I have added prefixes to the start of the string names.
Be carefull when translating. Prefixes are following:**   
_* < command verb (actions)> - These are commands verbs. Meaninaction on UI , dialogs_   
_* < adjective > - adjectives_    

We need help to add new language localization support for libarary. If you have any hope to help us we were very happy and you can check following <i><a href="https://github.com/hummatli/MAHAds/issues">GitHub Issues URL</a></i> to contribute.
To contribute get <a href="https://github.com/hummatli/MAHAds/blob/master/MAHAds/mah-ads/src/main/res/values/strings.xml">res/values/string.xml</a> file and translate to newer language. Place it on res/values-"spacific_lang"/string.xml

### Applications using MAHAds
Please feel free to [contact](mailto:settarxan@gmail.com) me or submit a pull request to add your app in the start of the list.

Icon | Application | Icon | Application
------------ | ------------- | ------------- | -------------
[Your app] |[ping](mailto:settarxan@gmail.com) me or send a pull request | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_en.png" width="48" height="48" /> | [Millionaire - in English](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.en)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_ru.png" width="48" height="48" /> | [Миллионер - на Pусском](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.ru) | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_tr.png" width="48" height="48" /> | [Milyoner - Türkçe](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.tr)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_az.png" width="48" height="48" /> | [Milyonçu](https://play.google.com/store/apps/details?id=com.mobapphome.milyoncu) | <img src="https://lh3.ggpht.com/kfuLs-Ic0xR3SOFdjJ3FVeI0es2oXTCEt1T2y8tEVeYm7otSuSSBDlrpz4wXtIygf4k=w300-rw" width="48" height="48" /> | [Məzənnə](https://play.google.com/store/apps/details?id=com.mobapphome.currency)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_ads_sample_icon.png" width="48" height="48" /> | [MAHAds - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample) | <img src="https://lh4.ggpht.com/b_9Tt-HGVWTUEpq4tpPvvf9iH9lbrMu6HDPitLxd5bzpUhf68Ifm0arFy7tH12GAJ8M=w300-rw" width="48" height="48" /> | [DYP Qanunlar və Cərimələr](https://play.google.com/store/apps/details?id=com.mobapphome.avtolowpenal)
<img src="https://lh6.ggpht.com/9g7gUdqyzc51oPIGX7pGf1_gs70WDizny9JfUExteTw_v0BFRLzx69xSmwhg3t7XQiE=w300-rw" width="48" height="48" /> | [Avto Nişanlar](https://play.google.com/store/apps/details?id=com.mobapphome.avtonishanlar) | <img src="https://lh5.ggpht.com/P_TyFmB5BzYDGWl3yliDHkQr_ttrYzHS3yQk3mBS3QuJJ5TJZ1pMj8lx-wmUmAHiUw=w300-rw" width="48" height="48" /> | [Ləzzət](https://play.google.com/store/apps/details?id=com.mobapphome.lezzet)


### Other libraries by developer
* [![MAHAndroidUpdater](https://img.shields.io/badge/GitHUB-MAHAndroidUpdater-green.svg)](https://github.com/hummatli/MAHAndroidUpdater) - Android update checker library. Library for notifing update information to installed android apps on android device.  
* [![MAHEncryptorLib](https://img.shields.io/badge/GitHUB-MAHEncryptorLib-green.svg)](https://github.com/hummatli/MAHEncryptorLib) - Library for encryption and decryption strings on Android apps and PC Java applications.

### License
Copyright 2015  - [Sattar Hummatli](https://www.linkedin.com/in/hummatli)   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
