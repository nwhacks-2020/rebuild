# reBuild

**Share information during critical situations.**

## Inspiration
Disasters can strike quickly and without notice. Most people are unprepared for situations such as earthquakes which occur with alarming frequency along the Pacific Rim. When wifi and cell service are unavailable, medical aid, food, water, and shelter struggle to be shared as the community can only communicate and connect in person.

## What it does
In disaster situations, Rebuild allows users to share and receive information about nearby resources and dangers by placing icons on a map. Rebuild utilizes a mesh network between other instances of the application to automatically transfer data between nearby devices, ensuring that users have the most recent information in their area.

What makes Rebuild a unique and effective app is that it does not require wifi or cell service to share and receive data. The mesh network created by the application between phones is the core of this application, and allows information sharing when all other systems are incapacitated.

## How we built it
We built the Android app with Nearby Connections API, a built-in Android library which manages a number of lower-level technologies to connect local devices. Wifi, Bluetooth, Bluetooth Low-Energy, Location, and other technologies are all controlled by the library to create a connection with any number of devices, which creates an M to N cluster network of devices.

The app also works extensively with Google Maps and Google Maps Location Services. We also designed the user interface mockups and screens in Figma.

## Challenges we ran into
The main challenges we faced while making this project were updating the device location so that the markers are placed accurately, and establishing a reliable mesh-network connection between the app users. While these features still aren't perfect, after a long night we managed to reach something we are satisfied with.

## Accomplishments that we're proud of
WORKING MESH NETWORK! (If you heard the scream of joy last night I apologize.) The Android Nearby Connections API was difficult to configure correctly and accurately, as well as the accurate Google Maps Location gathering and serialization/deserialization of marker data between devices.

## What we learned
We learned a lot about developing mesh networks, and used the Android Nearby Connections API for the first time.

## What's next for Rebuild
The next step for Rebuild would be adding the capability to distribute the application through bluetooth, so that users will not be required to download the app in preparation for a natural disaster.