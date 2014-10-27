BEGIN{
	max=0;
	count=0;
	average=0;
}
{
	average = average + $5;
	count = count + 1;
	if(max<=$5) max = $5;
}
END{
	average = average / count;
	print average " "max;
}
