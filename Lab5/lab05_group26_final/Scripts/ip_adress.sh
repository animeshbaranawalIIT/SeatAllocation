#!/bin/bash
# problem no 4

ROOTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo `ifconfig eth0 2>/dev/null|awk '/inet addr:/ {print $2}'|sed 's/addr://'`

#ifconfig gives all the information and status related to eth0 network interface
# it is then passed into /dev/null file which discards all the data written in it once its use is complete
#then I take the specific line having the segment 'inet addr:' and take the second word in the entire line
#and at last I remove the 'addr:' part from the output 
