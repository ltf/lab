#!/bin/bash

echo 1. '$#: ' $#
echo 2. '$*: ' $*
echo 3. '$@: ' $@

DIR=$#
echo 4. '$DIR $#": '"$DIR $#"

echo '$BASH_SOURCE: '$BASH_SOURCE

echo '$BASH_SOURCE[0]: '$BASH_SOURCE[0]

echo '$(dirname $BASH_SOURCE[0]): '$(dirname $BASH_SOURCE[0])

echo '$(dirname $BASH_SOURCE[0]): '$(dirname $BASH_SOURCE[0])

echo "cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd "

DIR2="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo 'dirname $DIR2: '`dirname $DIR2`

echo $DIR2 

