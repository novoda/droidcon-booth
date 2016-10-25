#!/bin/bash

while true; do

 git pull origin droidcon-2016

 adb kill-server
 adb start-server

 ./gradlew connectedAndroidTest

done
