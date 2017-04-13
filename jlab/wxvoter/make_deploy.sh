#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

pushd $DIR
./gradlew clean exejar

# kill running process
ssh iosci "ps aux | grep wxvoter.jar | grep -v grep | awk '{print \$2}' | xargs kill -9"

scp build/libs/wxvoter.jar iosci:/Users/renren_ios/selenium/wxvoter.jar

# run service
ssh iosci 'cd ~/selenium && nohup java -jar wxvoter.jar >>wxvoter.log 2>&1 &'
popd
