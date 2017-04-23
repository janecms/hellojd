#!/bin/bash
if [ $# -ne 1 ]; 
then
	echo $0 basepath;
	echo;
fi;
path=$1;

declare -A statarray;
lines=`find $path -type f -print`

#for line in $lines; do
#	ftype=`file -b "$line"` 
#	let statarray["$ftype"]++; 
#done

    while read line;
     do
       ftype=`file -b "$line"` 
       let statarray["$ftype"]++; 
         #echo $LINE
     done < <(find $path -type f -print)
echo ============file  types  and column=============
for ftype in "${!statarray[@]}"; do
	echo $ftype : ${statarray["$ftype"]}
done