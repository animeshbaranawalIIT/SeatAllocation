#!/bin/bash

i=1
g++ --std=c++0x updated_fibo.cpp
while (( $i<47 ))
do
	echo $i >input
	./a.out <input >> data.csv
	i=$((i+1))
done
rm a.out input
gnuplot <<EOF
set datafile separator ","
set terminal png
set output "plot.png"
set title "n vs time"
set xlabel "n"
set ylabel "time taken (in seconds)"
set xrange [0:47]
plot "data.csv" u 1:3 with lp title "Time Taken"
EOF
