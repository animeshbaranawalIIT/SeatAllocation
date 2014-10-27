We pledge on our honor that we didnt take or recieve any kind of unauthorized assistance for this assignment or before.



Animesh Baranawal 130050013	100%
Rawal khirodkar 130050014  	100%
Lokit Kumar Paras	130050047	100%

******************************************************************

Applying patch

patch <orginal filename> <patch filename>
patch b2Timer.cpp b2Timer_cpp.patch
patch b2Timer.h b2Timer_h.patch

Counting number of lines:
diff -U 0 b2Timer1.cpp b2Timer.cpp | grep -v ^@ | wc -l
RESULT:15

diff -U 0 b2Timer1.h b2Timer.h | grep -v ^@ | wc -l
RESULT:9

PS:b2Timer1.cpp and b2Timer1.h are the renamed original files

Reference:http://stackoverflow.com/questions/1566461/how-to-count-differences-between-two-files-on-linux

Observations: 
DIFFERENCE BETWEEN PATCHED CPP file and Original CPP file
(all line number with respect to original file)
1. Line number 64,65 are removed.
2. Line 74 is removed.
3. Line 76 is upgraded.
4. Line 81 to 83 are upgraded.

DIFFERENCE BETWEEN PATCHED H file and Original H file
(all line number with respect to original file)
1. Line 22 is upgraded.
2. Line 46 is upgraded.


********************************************************************

User Highest: Three files are changed , makefile , dominos.cpp, callbacks.cpp.

OUTPUT AFTER PULLING performed by HIGHEST USER:
Updating 716e33d..7a749ee
Fast-forward
 cs251_base_code/Makefile          |  90 +++++++++++++++++---
 cs251_base_code/src/callbacks.cpp |   4 +-
 cs251_base_code/src/dominos.cpp   | 175 ++++++++++++++++++++++++++++++++++++--
 3 files changed, 247 insertions(+), 22 deletions(-)





