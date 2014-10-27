#!/bin/bash

g++ kaprekar.cpp -o kaprekar
./kaprekar >data
rm kaprekar

gnuplot <<EOF
reset
set term png
set output "plot.png"
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.8
set yrange [0:300]; set xrange [-1:7]
p 'data' u 2
EOF
