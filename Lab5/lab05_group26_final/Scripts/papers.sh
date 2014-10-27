#!/bin/bash
# problem no 5

ROOTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
awk '/IITB/ {print $0}' ${ROOTDIR}/papers-example1.csv | sed 's/,/ /' | awk '{print $1}' | sort | uniq | wc -w
#first awk searches for all the lines with IITB as the college name
#then sed replaces the ',' with a ' '
#now awk prints only the paper-ids
#then the paper ids are sorted and duplicates are removed
#and at last I show the no of such paper-ids 