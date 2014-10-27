#!/bin/bash
# COUNT NUMBER OF FILES 

ROOTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# TASK1: COUNT NUMBER OF FILES EXCLUDE SUBDIRECTORIES
echo `find ./ -maxdepth 1 -type f | wc -l`
# echo displays count
# find finds the files 
# maxdepth 1 implies no subdirectories to be searched
# type f implies only regualr files to be searched
# the output of path is redirected to wc which counts the number of lines

# TASK2: DISPLAYING IMAGEFILES
find ./ -name '*.sh' -type f -printf "%f\n" 
# find finds the files ending with .sh
# type f implies they should be regular files
# printf %f implies only file name to be printed
