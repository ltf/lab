#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

pushd $DIR
git add .
git commit -m "pmonitor: auto commit and update"
git push

cp -f *.php os/

	pushd $DIR/os
	git add .
	git commit -m "pmonitor: auto commit and update"
	git push
	popd
popd
