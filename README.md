# Blue2Serial

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Blue2Serial-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1623)

A simple Android library for implementing Bluetooth Serial Port Profile (SPP) communication.

## Download

Blue2Serial is available on the jCenter repository. If you are using Android Studio, you can simply add a dependency on your app module's `build.gradle` file to import this library.

```Gradle
dependencies {
    compile 'com.macroyau:blue2serial:0.1.5'
}
```

Please add the following lines to the `build.gradle` file if Android Studio fails to resolve the jCenter repository.

```Gradle
repositories {
    maven {
        url "http://dl.bintray.com/macroyau/maven"
    }
}
```

## Usage

Read the sample Bluetooth terminal app [source code](https://github.com/MacroYau/Blue2Serial/blob/master/app/src/main/java/com/macroyau/blue2serial/demo/TerminalActivity.java) to get started!

Don't forget to declare the Bluetooth permission in your app's `AndroidManifest.xml`.
```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
```

## License

```
The MIT License (MIT)

Copyright (c) 2016 Macro Yau

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
