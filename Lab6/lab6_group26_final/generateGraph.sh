#!/bin/bash

awk -f filter.awk data.csv >plot.csv
awk -F, '{ 
	ENTRY=$1;
	LENGTH=$2;
	EVEN=$3; ODD=LENGTH-EVEN;
	SUM=$4; PRIME=$5;
	print $1 $2 " "ODD $4 $5;
 	}' plot.csv >plot1.csv
awk -F, '{ print $1 $2 $3 $4 $5; }' plot.csv >plot2.csv
rm plot.csv

gnuplot <<EOF
set term png
set output "plot1.png"
set title "Plot 1"
set xrange[8000:9000]
set xlabel "n"
set ytics 25
plot "plot1.csv" u 1:2 with lines title "Length", "plot1.csv" u 1:3 with lines title "Number of odd integers"
pause -1
EOF

rm plot1.csv plot2.csv
