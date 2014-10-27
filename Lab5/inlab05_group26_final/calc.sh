#!/bin/bash
if [[ "$#" != 1 ]]; then
    echo "Please give arguments....Exiting"
    exit 1
fi

seconds=$1 #storing value in variable
if [ "$seconds" -lt "0" ]; then #applying if condition to check if seconds <0
	echo "NOT VALID INPUT";
	else
		hour=`expr $seconds / 3600` # counting hours
		minute=`expr $seconds % 3600 / 60` # counting minutes
		second=`expr $seconds % 60` # counting seconds
		if [[ `expr $minute / 10` == 0 ]]; then minute="0$minute"; fi; # if single digit add 0 before
		if [[ `expr $second / 10` == 0 ]]; then second="0$second"; fi; # if single digit add 0 before
		echo "$hour::$minute::$second" ;
fi;
