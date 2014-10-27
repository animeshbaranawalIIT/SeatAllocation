#!/bin/bash

# Input given as in the given example
# $ ./string.sh ENGLISHWORD

ROOTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
WORD="$1" # declaring an input with value of input
REVERSE=""

len=${#WORD} #finding length of the string using shell inbuilt # function 
for ((i=$len-1; i>=0; i--)) #using for loop to reverse 
do
	reverse+=${WORD:$i:1} #adding character by character in reverse
done

echo "$reverse"
