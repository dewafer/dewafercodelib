 /***********************************************************
  FileName: radius.c
  Author: dewafer
  Version: 0
  Date: 2008-7-19
  Description: input the radius then calculate the perimeter
               and the area of the cricle.
 ***********************************************************/

#include <stdio.h>
#define PI 3.14

int main(){
	float r,l,s;
	printf("Please input the radius:");
	scanf("%f",&r);
	printf("\nl=%f",2*PI*r);
	printf("\ns=%f",PI*r*r);
	return 0;
}
