#!/usr/bin/env bash
ech(){
	echo "SourceChildShell: $1"
}

ech $V1inP
ech $V2inP

V1inS="Var1inSourceChild"


V2inS="Var2inSourceChild_Exported"
export V2inS

ech $V1inS
ech $V2inS


