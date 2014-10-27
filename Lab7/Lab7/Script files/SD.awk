#!/bin/awk

{sum+=$1; array[NR]=$1} 
END {for(x=1;x<=NR;x++){sumsq+=((array[x]-(sum/NR))^2);}
      print sqrt(sumsq/NR)} 
