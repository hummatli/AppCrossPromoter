<h1 align="center">AppCrossPromoter - Java (Kotlin, Android)</h1>
<h4 align="center">Android Ad Library</h4>

<p align="center">
  <a target="_blank" href="https://bintray.com/hummatli/maven/app-cross-promoter/_latestVersion"><img src="https://api.bintray.com/packages/hummatli/maven/app-cross-promoter/images/download.svg"></a>
  <a target="_blank" href="https://android-arsenal.com/api?level=15"><img src="https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat"></a>
  <a target="_blank" href="http://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000"></a>
</p>

<p align="center">Cross-promote your own apps and manage direct-sold campaigns. Free, open source, third party Android library for cross-promote, advertisement of own apps through your other apps. Library has built with Kotlin language. Check out the <a href="https://github.com/hummatli/AppCrossPromoter/wiki">wiki</a>.</p>

<p align="center">
<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/exit_dlg.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/programs_dlg.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/img3.png" width="200px"/>

</p>


<!--[ ![Download](https://api.bintray.com/packages/hummatli/maven/app-cross-promoter/images/download.svg) ](https://bintray.com/hummatli/maven/app-cross-promoter/_latestVersion) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](http://www.apache.org/licenses/LICENSE-2.0)-->



### Description
Free, open source, third party Android library for cross-promote, advertisement of own apps through your other apps.
By the help of this lib you can provide your apps list to users through your own other apps and let to install them. 
`Library has built with Kotlin language on Android Studio IDE` and binaries have added to `jcenter()`  `maven` repository.
<br>You can check  [jCenter() download statistics](https://bintray.com/hummatli/maven/app-cross-promoter#statistics) on this [link](https://bintray.com/hummatli/maven/app-cross-promoter#statistics)

There is a list of [application using AppCrossPromoter](https://github.com/hummatli/AppCrossPromoter#applications-using-appcrosspromoter). It would be nice if see your app link there too. If you use this library and want to see your app in the start of the [list](https://github.com/hummatli/AppCrossPromoter#applications-using-appcrosspromoter) please [inform me](mailto:settarxan@gmail.com) or send a pull request.

* [jCenter() download statistics](https://bintray.com/hummatli/maven/app-cross-promoter#statistics)
* [Application using AppCrossPromoter](https://github.com/hummatli/AppCrossPromoter#applications-using-appcrosspromoter)

<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/green_star.png" width="20px"/>  _**Don't forget to start the protect to support us**_   

### Contributors
* Developer:
[Sattar Hummatli](https://github.com/hummatli) - [LinkedIn](https://www.linkedin.com/in/hummatli), settarxan@gmail.com, [Other libs](https://github.com/hummatli/AppCrossPromoter#other-libraries-by-developer)
* Translator `French`: [Fariz Aghayev](https://github.com/farizaghayev)
* Translator `Portuguese`: [azzarr](https://github.com/azzarr)
* Translator `Hindi, German, Spanish`: [Harsh Dalwadi](https://github.com/dalwadi2)
* Translator `Italian`: [Rawnly](https://github.com/rawnly)

### Contents
* [Description](https://github.com/hummatli/AppCrossPromoter#description)
* [Service structure](https://github.com/hummatli/AppCrossPromoter#service-structure)
* [Library structure](https://github.com/hummatli/AppCrossPromoter#library-structure)
* [Installation manual](https://github.com/hummatli/AppCrossPromoter#installation-manual)
* [Help - Issues](https://github.com/hummatli/AppCrossPromoter#help---issues)
* [Releases - Upgrade documentation](https://github.com/hummatli/AppCrossPromoter#releases---upgrade-documentation)
* [To contribute](https://github.com/hummatli/AppCrossPromoter#to-contribute)
* [Contributors](https://github.com/hummatli/AppCrossPromoter#contributors)
* [Localization](https://github.com/hummatli/AppCrossPromoter#localization)
* [Applications using AppCrossPromoter](https://github.com/hummatli/AppCrossPromoter#applications-using-appcrosspromoter)
* [Other libraries by developer](https://github.com/hummatli/AppCrossPromoter#other-libraries-by-developer)

### Sample App in PlayStore
<a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample">AppCrossPromoter - Sample</a> app has published on Google PlayStore. You can easly test module functionality with downloading it.
<br><a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample"><img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/google-play-badge.png" height="90px"/></a> <!--img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/mahads_google_play_url_qr_code.jpg" height="100px"/-->

### Service structure
To provide your apps list you have to implement service provider. Structure of the service is as below. Your root folder has to contain `imgs` folder and two files `program_version.json`, `program_list.json`.

For details check <a href="https://github.com/hummatli/AppCrossPromoter-AndroidLib/wiki/Service-structure">wiki</a>.</p>

### Library structure
`You can call with the same way in Kotlin and Java. Library contains samples both in Kotlin and Java`

For details check <a href="https://github.com/hummatli/AppCrossPromoter-AndroidLib/wiki/Library-structure">wiki</a>.</p>
  
### Installation manual
To import library(to downlaod) to you project add following lines to project's `build.gradle` file. The last stable version is `2.5.5`

```gradle
repositories {
    maven { url 'https://dl.bintray.com/hummatli/maven/' }
}

dependencies {
    compile 'com.mobapphome.library:app-cross-promoter:2.5.5'
}
```
For details check <a href="https://github.com/hummatli/AppCrossPromoter-AndroidLib/wiki/Installation-manual">wiki</a>.</p>

### Proguard configuration
AppCrossPromoter uses [Jsoup](https://github.com/jhy/jsoup), [GSON](https://github.com/google/gson), [Glide](https://github.com/bumptech/glide) libraries. Therefore if you want to create your project with proguard you'll need to add proguard configuration to your proguard file. Look at [Progurad File](https://github.com/hummatli/AppCrossPromoter/blob/master/proguard-rules-app-cross-promoter.pro)

### Help - Issues
If you have any problem with setting and using library or want to ask question, please let me know. Create [issue](https://github.com/hummatli/AppCrossPromoter/issues) or write to <i><a href="mailto:settarxan@gmail.com">settarxan@gmail.com</a></i>. I will help.

### Releases - Upgrade documentation
See [releases](https://github.com/hummatli/AppCrossPromoter/releases). Please,read release notes to migrate your app from old version to a newer.

### To contribute
I am open to hear offers and opinions from you  

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
* [Add yours language](https://github.com/hummatli/AppCrossPromoter/blob/master/README.md#to-contribute-for-localization)

#### To contribute for localization  
**To help translator in context I have added prefixes to the start of the string names.
Be carefull when translating. Prefixes are following:**   
_* < command verb (actions)> - These are commands verbs. Meaninaction on UI , dialogs_   
_* < adjective > - adjectives_    

We need help to add new language localization support for libarary. If you have any hope to help us we were very happy and you can check following <i><a href="https://github.com/hummatli/AppCrossPromoter/issues">GitHub Issues URL</a></i> to contribute.
To contribute get <a href="https://github.com/hummatli/AppCrossPromoter/blob/master/app-cross-promoter/src/main/res/values/strings.xml">res/values/string.xml</a> file and translate to newer language. Place it on res/values-"spacific_lang"/string.xml

### Applications using AppCrossPromoter
Please feel free to [contact](mailto:settarxan@gmail.com) me or submit a pull request to add your app in the start of the list.

Icon | Application | Icon | Application
------------ | ------------- | ------------- | -------------
[Your app] |[ping](mailto:settarxan@gmail.com) me or send a pull request | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_en.png" width="48" height="48" /> | [Millionaire - in English](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.en)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_ru.png" width="48" height="48" /> | [Миллионер - на Pусском](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.ru) | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_tr.png" width="48" height="48" /> | [Milyoner - Türkçe](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.tr)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_az.png" width="48" height="48" /> | [Milyonçu](https://play.google.com/store/apps/details?id=com.mobapphome.milyoncu) | <img src="https://lh3.ggpht.com/kfuLs-Ic0xR3SOFdjJ3FVeI0es2oXTCEt1T2y8tEVeYm7otSuSSBDlrpz4wXtIygf4k=w300-rw" width="48" height="48" /> | [Məzənnə](https://play.google.com/store/apps/details?id=com.mobapphome.currency)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_ads_sample_icon.png" width="48" height="48" /> | [AppCrossPromoter - Sample](https://play.google.com/store/apps/details?id=appcrosspromoter.sample) | <img src="https://lh4.ggpht.com/b_9Tt-HGVWTUEpq4tpPvvf9iH9lbrMu6HDPitLxd5bzpUhf68Ifm0arFy7tH12GAJ8M=w300-rw" width="48" height="48" /> | [DYP Qanunlar və Cərimələr](https://play.google.com/store/apps/details?id=com.mobapphome.avtolowpenal)
<img src="https://lh6.ggpht.com/9g7gUdqyzc51oPIGX7pGf1_gs70WDizny9JfUExteTw_v0BFRLzx69xSmwhg3t7XQiE=w300-rw" width="48" height="48" /> | [Avto Nişanlar](https://play.google.com/store/apps/details?id=com.mobapphome.avtonishanlar) | <img src="https://lh5.ggpht.com/P_TyFmB5BzYDGWl3yliDHkQr_ttrYzHS3yQk3mBS3QuJJ5TJZ1pMj8lx-wmUmAHiUw=w300-rw" width="48" height="48" /> | [Ləzzət](https://play.google.com/store/apps/details?id=com.mobapphome.lezzet)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_de.png" width="48" height="48" />| [Millionär - Deutsche](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.ge) | |


### Other libraries by developer
* [![AndroidAppUpdater](https://img.shields.io/badge/GitHUB-AndroidAppUpdater-green.svg)](https://github.com/hummatli/AndroidAppUpdater) - Android update checker library. Library for notifing update information to installed android apps on android device.  
* [![SimpleEncryptionLib](https://img.shields.io/badge/GitHUB-SimpleEncryptionLib-green.svg)](https://github.com/hummatli/SimpleEncryptionLib) - Library for encryption and decryption strings on Android apps and PC Java applications.

### License
Copyright 2017  - [Sattar Hummatli](https://www.linkedin.com/in/hummatli)   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
