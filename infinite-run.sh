#!/bin/bash

while true; do

 git pull origin droidcon-2016

 /opt/android-sdk/platform-tools/adb kill-server
 /opt/android-sdk/platform-tools/adb start-server

 ./gradlew connectedAndroidTest

done
