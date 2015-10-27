#!/bin/bash
set -e

while true; do

 git rev-parse --is-inside-work-tree
 git config remote.origin.url git@github.com:novoda/droidcon-booth.git 
 git --version
 git -c core.askpass=true fetch --tags --progress git@github.com:novoda/droidcon-booth.git +refs/heads/*:refs/remotes/origin/*
 git config core.sparsecheckout 
 git checkout -f master

 ./gradlew connectedAndroidTest

done
