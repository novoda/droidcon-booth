#!/bin/bash

while true; do

 git pull origin master
 
 /opt/android-sdk/platform-tools/adb kill-server
 /opt/android-sdk/platform-tools/adb start-server

 ./gradlew connectedAndroidTest

done
