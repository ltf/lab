#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
EXE="executable"
pushd $DIR

# gcc -g -O2 -Wall -o $EXE alias_test.cpp 
gcc -g -Wall -o $EXE alias_test.cpp 
./$EXE
# rm $EXE


popd
