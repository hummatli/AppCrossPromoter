<h1 align="center">AppCrossPromoter - Java (Kotlin, Android)</h1>
<h4 align="center">Android Ads Library</h4>

<p align="center">
  <a target="_blank" href="https://bintray.com/hummatli/maven/app-cross-promoter/_latestVersion"><img src="https://api.bintray.com/packages/hummatli/maven/app-cross-promoter/images/download.svg"></a>
  <a target="_blank" href="https://android-arsenal.com/api?level=15"><img src="https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat"></a>
  <a target="_blank" href="http://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000"></a>
</p>

<p align="center">Cross-promote your own apps and manage the direct-sold campaigns. he free, open source, third party Android library to cross-promote advertise of your own apps through your other Android apps. This library has been built with the Kotlin language in the Android Studio IDE. Check out the <a href="https://github.com/hummatli/AppCrossPromoter/wiki">wiki</a>. To support, <a href="https://www.buymeacoffee.com/hummatli" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a></p>

<p align="center">
<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/exit_dlg.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/programs_dlg.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/img3.png" width="200px"/>

</p>


<!--[ ![Download](https://api.bintray.com/packages/hummatli/maven/app-cross-promoter/images/download.svg) ](https://bintray.com/hummatli/maven/app-cross-promoter/_latestVersion) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](http://www.apache.org/licenses/LICENSE-2.0)-->



### Description
The free, open source, third party Android library to cross-promote advertise of your own apps through your other Android apps. By the help of this lib you can provide the list of your apps to the users inside your other apps and let the to install. `This library has been built with the Kotlin language in the Android Studio IDE` and the binaries have added to the `jcenter()` `maven` repository.
<br>You can check  [the jCenter() download statistics](https://bintray.com/hummatli/maven/app-cross-promoter#statistics) on this [link](https://bintray.com/hummatli/maven/app-cross-promoter#statistics)

There is a list of [the applications which uses AppCrossPromoter](https://github.com/hummatli/AppCrossPromoter#applications-using-appcrosspromoter). It would be nice to see your apps' link there too. If you use this library and want to see it in the head of the [list](https://github.com/hummatli/AppCrossPromoter#applications-using-appcrosspromoter), please [inform me](mailto:settarxan@gmail.com) or send a pull request.

* [The jCenter() download statistics](https://bintray.com/hummatli/maven/app-cross-promoter#statistics)
* [The applications which uses AppCrossPromoter](https://github.com/hummatli/AppCrossPromoter#applications-using-appcrosspromoter)

<img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/green_star.png" width="20px"/>  _**Don't forget to star the protect to support us**_   

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

### A sample App in PlayStore
<a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample">The AppCrossPromoter - Sample</a> app has published on the Google PlayStore. You can easly test the lib's functionality by downloading it.
<br><a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample"><img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/google-play-badge.png" height="90px"/></a> <img src="https://raw.githubusercontent.com/hummatli/AppCrossPromoter/master/imgs/mahads_google_play_url_qr_code.jpg" height="100px"/>

### Service structure
To provide your apps' list, you have to implement the service provider. The structure of the service is as below. Your root folder has to contain a `imgs` folder and two files - `program_version.json`, `program_list.json`.

For details check the <a href="https://github.com/hummatli/AppCrossPromoter-AndroidLib/wiki/Service-structure">wiki</a>.</p>

### Library structure
`You can call it by the same way in Kotlin and Java. The library contains samples both in the Kotlin and Java languages.`

For the details, check <a href="https://github.com/hummatli/AppCrossPromoter-AndroidLib/wiki/Library-structure">the wiki</a>.</p>
  
### Installation manual
The last stable version is `2.5.5`. To configure the library in your project, check <a href="https://github.com/hummatli/AppCrossPromoter-AndroidLib/wiki/Installation-manual">installation wiki page</a>.</p>

### Proguard configuration
AppCrossPromoter uses [Jsoup](https://github.com/jhy/jsoup), [GSON](https://github.com/google/gson), [Glide](https://github.com/bumptech/glide) libraries. Therefore, if you want to create your project with a proguard option, you'll need to add the proguard configuration to your proguard file. Look at the [progurad file](https://github.com/hummatli/AppCrossPromoter/blob/master/proguard-rules-app-cross-promoter.pro)

### Help - Issues
If you have any problems with configuration of the library or want to ask a question, please let me know. Create [issue](https://github.com/hummatli/AppCrossPromoter/issues) or write to <i><a href="mailto:settarxan@gmail.com">settarxan@gmail.com</a></i>. I will help.

<!--### Releases - Upgrade documentation
See the [releases](https://github.com/hummatli/AppCrossPromoter/releases). Please,read the release notes to migrate your app from an old version to a newer one.-->

### To contribute
I am ready to hear offers and opinions from you.  

* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create a new Pull Request
* Star it

### Localization
The library now supports the following languages: 
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
* [Add your language](https://github.com/hummatli/AppCrossPromoter/blob/master/README.md#to-contribute-for-localization)

#### To contribute for localization  
**To help to translator in context, I have added some prefixes to the start of the string names.
Be carefull when you translate. The prefixes are following:**   
_* < command verb (actions)> - These are the command verbs which mean actions on the UI._   
_* < adjective > - adjectives_    

We need your help to add a new language localization support to libarary. If you had any hope to help us, we would be very happy and you could check the following <i><a href="https://github.com/hummatli/AppCrossPromoter/issues">GitHub Issues URL</a></i> to contribute.
To contribute, get the <a href="https://github.com/hummatli/AppCrossPromoter/blob/master/app-cross-promoter/src/main/res/values/strings.xml">res/values/string.xml</a> file and translate it to a newer language then place it on res/values-"spacific_lang"/string.xml.

### Applications using AppCrossPromoter
Please feel free to [contact](mailto:settarxan@gmail.com) me or submit a pull request to add your app in the top of the list.

Icon | Application | Icon | Application
------------ | ------------- | ------------- | -------------
[Your app] |[ping](mailto:settarxan@gmail.com) me or send a pull request | <img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_en.png" width="48" height="48" /> | [Millionaire - in English](https://play.google.com/store/apps/details?id=game.quiz.intellectual.iq.millionaire.english)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_ru.png" width="48" height="48" /> | [Миллионер - на Pусском](https://play.google.com/store/apps/details?id=iqra.viktorina.intellektualnoy.iq.millionaire.russian.millioner.russkiy) | <img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_tr.png" width="48" height="48" /> | [Milyoner - Türkçe](https://play.google.com/store/apps/details?id=oyun.bilgi.entellektuel.iq.millionaire.turkish.milyoner.turkce)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_az.png" width="48" height="48" /> | [Milyonçu](https://play.google.com/store/apps/details?id=oyun.test.sualcavab.iq.millionaire.azerbaijani.milyoncu.azerbaycanca) | <img src="https://lh3.ggpht.com/kfuLs-Ic0xR3SOFdjJ3FVeI0es2oXTCEt1T2y8tEVeYm7otSuSSBDlrpz4wXtIygf4k=w300-rw" width="48" height="48" /> | [Məzənnə](https://play.google.com/store/apps/details?id=com.mobapphome.currency)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/mah_ads_sample_icon.png" width="48" height="48" /> | [AppCrossPromoter - Sample](https://play.google.com/store/apps/details?id=appcrosspromoter.sample) | <img src="https://lh4.ggpht.com/b_9Tt-HGVWTUEpq4tpPvvf9iH9lbrMu6HDPitLxd5bzpUhf68Ifm0arFy7tH12GAJ8M=w300-rw" width="48" height="48" /> | [DYP Qanunlar və Cərimələr](https://play.google.com/store/apps/details?id=com.mobapphome.avtolowpenal)
<img src="https://lh6.ggpht.com/9g7gUdqyzc51oPIGX7pGf1_gs70WDizny9JfUExteTw_v0BFRLzx69xSmwhg3t7XQiE=w300-rw" width="48" height="48" /> | [Avto Nişanlar](https://play.google.com/store/apps/details?id=com.mobapphome.avtonishanlar) | <img src="https://lh5.ggpht.com/P_TyFmB5BzYDGWl3yliDHkQr_ttrYzHS3yQk3mBS3QuJJ5TJZ1pMj8lx-wmUmAHiUw=w300-rw" width="48" height="48" /> | [Ləzzət](https://play.google.com/store/apps/details?id=com.mobapphome.lezzet)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_de.png" width="48" height="48" />| [Millionär - Deutsche](https://play.google.com/store/apps/details?id=spiel.quiz.intellektuell.iq.millionaire.german.millionar.deutsche) | |


### Other libraries by developer
* [![AndroidAppUpdater](https://img.shields.io/badge/GitHUB-AndroidAppUpdater-green.svg)](https://github.com/hummatli/AndroidAppUpdater) - An Android library to check an update information on android device.  
* [![SimpleEncryptionLib](https://img.shields.io/badge/GitHUB-SimpleEncryptionLib-green.svg)](https://github.com/hummatli/SimpleEncryptionLib) - The library to encrypt and decrypt strings on the Android apps and the PC Java applications.

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
