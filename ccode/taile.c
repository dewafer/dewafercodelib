 /***********************************************************
  FileName: radius.c
  Author: dewafer
  Version: 0
  Date: 2008-7-19
  Description: calculate the e and print the calculated times.
 ***********************************************************/

#include <stdio.h>

int main(){
	double e=1.0;
	int i,j,k=0,l=0;

	for(i=1;k<10e5;i++){
		k=i;
		l++;
		for(j=1;j<i;j++){
			k*=j;
		}
		e+=1/(double)k;
		//printf("i=%d,k=%d,e=%f\n",i,k,e);
	}

	printf("result e=%f\n",e);
	printf("and calculated times=%d",l);
	return 0;
}
