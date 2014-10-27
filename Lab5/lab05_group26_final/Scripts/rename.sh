#!/bin/bash
#RENAME FILES

ROOTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
REQNAME=[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9] #required format of file name

find ./ -name "$REQNAME*" -type f -printf "%f\n" | #finding such files and passing the file names excluding path directory
	while read FNAME #reading file name
	do
		len=${#FNAME} #finding length of the file name string
		DATE=${FNAME:0:2} #date of file name
		MONTH=${FNAME:2:2} #month of file name
		YEAR=${FNAME:4:4} #year of file name
		ANY=${FNAME:8:`expr $len-8`} # leftover part of file name
		if [ "$DATE" -le "31" -a "$MONTH" -le "12" -a "$YEAR" -ge "2013" -a "$YEAR" -le "2015" ]; then #conditions on date,month,year
			RNAME=$YEAR-$MONTH-$DATE-$ANY #new file name
			mv ${FNAME} ${RNAME}; #renaming the file
		fi;
	done

