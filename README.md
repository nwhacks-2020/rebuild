# <img src="./readme-img/icon.jpg" width="30"> Rebuild (nwHacks 2020): Disaster Recovery Application

[![Licence](
    https://img.shields.io/github/license/nwhacks-2020/rebuild)](
        https://github.com/nwhacks-2020/rebuild/blob/master/LICENSE
)

To download the app from the Google Play Store, click here: https://play.google.com/store/apps/details?id=com.nwhacks2020.rebuild

## Quick Setup Instructions

1. Go to the [REBUILD](https://nwhacks-2020.github.io/rebuild/) website with an **Android** device.
2. Click on **Download on Android device** to download the app to your phone.
3. Open the download file.
4. Click on **Install** to install the app on your phone.

### Codebase

Clone the repository and navigate into the directory:
```shell
git clone https://github.com/nwhacks-2020/rebuild.git
cd rebuild/
```

### Android Studio Set Up

Install [Android Studio](https://developer.android.com/studio/) and import the project.

### Google Maps API Key

Generate a [Google Maps SDK API key (step 1)](https://developers.google.com/maps/documentation/android-sdk/get-api-key).

Duplicate the Google Maps API key template file to hold the API key:
```shell
cp Rebuild/app/src/debug/res/values/google_maps_api_template.xml Rebuild/app/src/debug/res/values/google_maps_api.xml

cp Rebuild/app/src/release/res/values/google_maps_api_template.xml Rebuild/app/src/release/res/values/google_maps_api.xml
```

Choose the `debug/` folder for development, or the `release/` folder for release.

In the new file `google_maps_api.xml`, uncomment the line with the key and replace the value of the placeholder with your API key. This new file will not be tracked by Git.

Build and run the Android application on a physical or virtual device.

#### Creating a Production Release

##### Step 1: Building a Release Variant

Create a new file in the root directory to hold the secure keystore details:
```shell
cp keystore.properties.template keystore.properties
```

This file will be ignored by Git. Set your keystore information in this file.

Under _Build_, click _Select Build Variant_.

In the _Build Variants_ view which appears, change the **Active Build Variant** from _debug_ to _release_.

Build the application normally to your phone. The keystore credentials will be verified and applied.

##### Step 2: Creating the Signed App

Under _Build_, click _Generate Signed Bundle / APK_.

Follow the instructions, inputting your keystore path and credentials, to create a signed app which can be uploaded to the app store.
