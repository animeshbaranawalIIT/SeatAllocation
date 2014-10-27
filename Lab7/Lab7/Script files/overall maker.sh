#!/bin/bash

paste bonds.txt international.txt stocks.txt file.txt | sed -e 's/\t\t/\t     /g;s/\t/ /g;s/ /\t/g'| sed 's/.txt//' >> overall.txt

