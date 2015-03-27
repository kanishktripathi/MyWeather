This is a simple Android app for finding weather at a particular location. 
Android OS Version required: Android 4.0 (Ice Cream Sandwich) or above

**Description**
- The app by default looks for the current location of user and finds weather of that  place(Location services must be enabled).
- The user can also search the locaton from an autosuggest box.
- There's also a favorite screen where user can add upto 10 of his favorite places.
- The app saves the last search of location and loads that location on re-start/resume.
- The screen background shows day(blue) or night(black) at a location. This may not reflect based on actual time because we used the last weather update time from the Yahoo API response. The update time could be upto an hour or two behind actual time.
  

**API**

For finding the weather, actual place from geo-coordinates and searching the places, we used the Yahoo weather foreast and geo tables API. We used a free version of it which did not provide hourly forecast. See the links for details:
https://developer.yahoo.com/yql/console/
https://developer.yahoo.com/weather/

**Installation and build**
You can use the code anytime. This project was developed using android ADT bundle but it can be used with Android Studio also. 
Just copy the libs, src and res folder contents in your project. To use the app, install the .apk file provided in the repository.

**Summary**

This project was helpful in learning of key aspects of Android programming like Bitmap caching, Async tasks, UI layouts, Location services, location service battery optimization to name a few. Android services were not used for HTTP connections because there was not large data to interact with and it was not necessary to get weather report in background.

**License**
WTFPL
![Shot5](http://upload.wikimedia.org/wikipedia/commons/thumb/0/05/WTFPL_logo.svg/140px-WTFPL_logo.svg.png "Shot 5")


**Screenshots**

Device: Motorola Moto G


![Shot5](/screenshots/shot5.png?raw=true "Shot 5")

![Shot3](/screenshots/shot3.png?raw=true "Shot 3")

![Shot4](/screenshots/shot4.png?raw=true "Shot 4")

![Shot1](/screenshots/shot1.png?raw=true "Shot 1")

![Shot2](/screenshots/shot2.png?raw=true "Shot 2")
