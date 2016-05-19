#!/usr/bin/env bash

ech(){
	echo "ParentShell: $1"
}
#echo $HOME
#echo $PATH
#env

V1inP="Var1inParent"

V2inP="Var2inParent_Exported"
export V2inP

echo "before run sub"
ech $V1inP
ech $V2inP
ech $V1inC
ech $V2inC
ech $V1inS
ech $V2inS

./testenv_sub.sh
echo "after run sub"
ech $V1inP
ech $V2inP
ech $V1inC
ech $V2inC
ech $V1inS
ech $V2inS

source ./testenv_sub_source.sh
echo "after source sub_source"
ech $V1inP
ech $V2inP
ech $V1inC
ech $V2inC
ech $V1inS
ech $V2inS

./testenv_sub.sh
echo "after rerun sub"
ech $V1inP
ech $V2inP
ech $V1inC
ech $V2inC
ech $V1inS
ech $V2inS
