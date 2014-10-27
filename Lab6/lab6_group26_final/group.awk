BEGIN{
	max=0;
	min=0;
	lines=0;
}
{ 
	if(lines==0){ min=$0; max=$0; lines++; }	
	if(min>=$0) min=$0;
	if(max<=$0) max=$0;
	a[$0]++; 
}
END{	
	for(x in a) count++;
	t=0;
	while(t<=max){
		if(a[t+min]!=0) { print t+min, a[t+min]; }
		t++;
	}
}
