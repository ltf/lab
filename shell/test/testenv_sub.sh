#!/usr/bin/env bash
ech(){
	echo "ChildShell: $1"
}

ech $V1inP
ech $V2inP

V1inC="Var1inChild"


V2inC="Var2inChild_Exported"
export V2inC

ech $V1inC
ech $V2inC
ech $V1inS
ech $V2inS


