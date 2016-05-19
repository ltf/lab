#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
EXE="executable"
pushd $DIR

gcc -g -Wall -o $EXE test_array_init.c 
./$EXE
# rm $EXE


popd
