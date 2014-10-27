#!/bin/bash

i="2"

if test -e "data.csv"; then
	:;
	else
		while [ $i -le 10000 ]
		do
		printf "$i ," >> data.csv
		./runme $i >>data.csv
		i=$[$i+1]
		done;
fi;
