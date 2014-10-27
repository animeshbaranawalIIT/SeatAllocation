#!/bin/bash
# script to move files to directory if file older than 7 days

ROOTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

mkdir -p ./old
find . -maxdepth 1 -type f -mtime +7 -exec mv {} ./old \; 
find . -type d -mtime +7 2>/dev/null -exec mv {} ./old \;
find . -empty -type f -delete
# find finds files in the current directory
# finds only regualr files with modification time older than 7 days
# if found moves it a directory named old
# if old is empty delete it
