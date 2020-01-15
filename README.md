# Rebuild: Disaster Recovery Application

## Quick Setup Instructions

1. Go to [reBuild](https://nwhacks-2020.github.io/rebuild/) website with an **Android** device.
2. Click on **Download on Android device** to download the app to your phone.
3. Open the download file.
4. click on **install** to install the app on your phone.





### Codebase

Clone the repository and navigate into the directory:
```shell
git clone https://github.com/nwhacks-2020/rebuild.git
cd rebuild/
```


### Android Studio Set Up

Install [Android Studio](https://developer.android.com/studio/) and import the project.

### Google Maps API Key (for development builds)

Generate a [Google Maps SDK API key (step 1)](https://developers.google.com/maps/documentation/android-sdk/get-api-key).
Duplicate the Google Maps API key template file to hold the API key:
```shell
cp Rebuild/app/src/debug/res/values/google_maps_api_template.xml Rebuild/app/src/debug/res/values/google_maps_api.xml
```

In the new file `Rebuild/app/src/debug/res/values/google_maps_api.xml`, uncomment the line with the key and replace the value of the placeholder with your API key. This new file will not be tracked by Git.

Build and run the Android application on a physical or virtual device.
