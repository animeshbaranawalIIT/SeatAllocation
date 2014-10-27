BEGIN{
	n=1;
}
{
	if(n<=9999 && n%50==0) print $0;
	n = n+1;
}


