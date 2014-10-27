#!/bin/bash

cd bonds

for file in *
do awk -f ../SD.awk "$file"  >> ../sd_bonds.txt
done 

cd ../international

for file in *
do awk -f ../SD.awk "$file"  >> ../sd_int.txt
done 

cd ../stocks

for file in *
do awk -f ../SD.awk "$file"  >> ../sd_stocks.txt
done 
