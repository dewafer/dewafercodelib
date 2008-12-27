 /***********************************************************
  FileName: radius.c
  Author: dewafer
  Version: 0
  Date: 2008-7-19
  Description: input the year number then determine whether
               it is the leap year.
 ***********************************************************/

#include <stdio.h>

int main(){
	int year;
	do{
		printf("Input the year(0 to exit):");
		scanf("%d",&year);
		if((0==year%4 && 0!=year%100)
							|| 0==year%400){
			printf("\nYES\n");
		}
		else{
			printf("\nNO\n");
		}
	}
	while(0!=year);
	return 0;
}
