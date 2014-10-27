#!/bin/bash
EDITOR="$0"  #this is used to stop user from appending the editor file itself
if [ "$#" == 1 ] && [ ${EDITOR:2} != "$1" ]; then # editor works if one file is give as input
	
	while read string            # reading the input from terminal
	do
		if [ "$string" != ":q" ]; then
			echo "$string" >> $1  #appending in the file
		else
			break;	              #exit condition :q
		fi	
	done
    exit 1

else
	echo "Please enter legal chain of commands"   #warning 

fi	    

