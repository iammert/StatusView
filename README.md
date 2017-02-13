# StatusView
Custom status view for Android.

<img src="https://raw.githubusercontent.com/iammert/StatusView/master/art/art.gif"/>

# Usage

Add to XML

If you want to use ConnectionStatusView
```java
<iammert.com.library.ConnectionStatusView
        android:id="@+id/status"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        app:dismissOnComplete="true" />
```
If you want to use your own layout for status
```java
<iammert.com.library.StatusView
        android:id="@+id/status"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        app:dismissOnComplete="true"
        app:complete="@layout/completeLayout"
        app:error="@layout/errorLayout"
        app:loading="@layout/loadingLayout"/>
```

Then call status method
```java
statusView.setStatus(Status.LOADING);
statusView.setStatus(Status.ERROR);
statusView.setStatus(Status.COMPLETE);
statusView.setStatus(Status.IDLE); //hide status
```

If you want to dismiss automagically after oncomplete is called, then add ```app:dismissOnComplete="true"``` as attribute.

# Getters

You can inflate your custom views for all statuses(ERROR/COMPLETE/LOADING). And you can also get this views to control included UI widgets like button/imageview.
```java
View viewError = statusView.getErrorView();
View viewComplete = statusView.getCompleteView();
View viewLoading = statusView.getLoadingView();
```

# Listeners
```java
statusView.setOnErrorClickListener(listener);
statusView.setOnLoadingClickListener(listener);
```

# Import

Add this to you project build.gradle
```
allprojects {
        repositories {
                ...
                maven { url 'https://jitpack.io' }
        }
}
```
Add this dependency to your app build.gradle

```
compile 'com.github.iammert:StatusView:1.3'
```

License
--------


    Copyright 2017 Mert Şimşek.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
