#!/bin/bash

cd bonds

for file in *
do awk -f ../average.awk "$file"  >> ../bonds.txt
echo "$file" >> ../file.txt 
done  

cd ../international
for file in *
do awk -f ../average.awk "$file"  >> ../international.txt 
done  

cd ../stocks
for file in *
do awk -f ../average.awk "$file"  >> ../stocks.txt 
done
