#!/bin/bash
curl http://www.cse.iitb.ac.in/page14 >bla
curl http://www.math.iitb.ac.in/people/faculty/faculty.html >bla2

sed '/<!--/,/-->/d' bla2 >bla3

echo "Department Name                   Count "
awk '!/^#.*Research Interests:/{m=gsub("Research Interests:","");total+=m}END{print "Computer Science Faculty:          "total}' bla
awk '!/^#.*bgcolor=#e3e3e3/{m2=gsub("bgcolor=#e3e3e3","");total2+=m2}END{print "Mathematics Faculty:               "total2}' bla3
rm bla bla2 bla3