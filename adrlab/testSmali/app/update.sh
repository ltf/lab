#!/usr/bin/env bash
pushd build/outputs/apk/
rm -rf app-debug
java -jar ~/devtool/apktool/apktool_2.3.0.jar d -b app-debug.apk
scp app-debug/smali/Hook.smali myci:~/tmp/lls/ws/classes/
scp app-debug/smali/Hook\$*.smali myci:~/tmp/lls/ws/classes/
vi app-debug/smali/HookTest.smali
popd
