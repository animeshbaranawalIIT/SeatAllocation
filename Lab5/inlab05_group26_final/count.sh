#!/bin/bash
#count
IFS=''
read -r LINE                   # helps in reading the entire line including whitespaces into a variable LINE
echo -n "Words=$(echo $LINE | wc -w)"			# echos the word count of the input string
echo
#echo $LINE | tr -d '\n' | wc -c >   
echo "Characters=${#LINE}"			#echos the total no of characters in the line
echo -n "Spaces=$(echo $LINE | sed 's/[^ ]//g'| awk '{ print length }')"	#echos only the no of spaces by deleting all the other things in the string
echo
echo -n "Special symbols=$(echo $LINE | sed 's/[^($,>,<)]//g'| awk '{ print length }')"   #echos only the no of special symbols by deleting all the other things in the string except them
echo
