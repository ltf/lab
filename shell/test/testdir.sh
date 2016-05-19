#!/usr/bin/env bash
DIR=`pwd`
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "parent pwd:" $DIR
echo "parent script_dir:" $SCRIPT_DIR
echo -e "\n\n------------./bashsource.sh"
$SCRIPT_DIR/bashsource.sh
echo -e "\n\n------------source ./bash_source.sh"
source $SCRIPT_DIR/bashsource.sh
