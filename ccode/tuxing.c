 /***********************************************************
  FileName: radius.c
  Author: dewafer
  Version: 0
  Date: 2008-7-19
  Description: input line number then draw a pane of star.
 ***********************************************************/

#include <stdio.h>

int main(){
	int i,j,n;
	printf("please Input N:");
	scanf("%d",&n);
	for(i=0;i<n;i++){
		for(j=0;j<n;j++){
			if(0!=i && i!=n-1){
				if(0!=j && j!=n-1){
					printf(" ");
				}else{
					printf("*");
				}
			}else{
				printf("*");
			}
		}
		printf("\n");
	}
	return 0;
}
