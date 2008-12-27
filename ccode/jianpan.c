 /***********************************************************
  FileName: radius.c
  Author: dewafer
  Version: 0
  Date: 2008-7-19
  Description: input a char the determine the type of it.
 ***********************************************************/

 #include <stdio.h>

 int main(){

 	char in;

 	printf("input:");
 	scanf("%1c",&in);

 	if(in>=97 && in<=122){
 		printf("\nIt's a small character.\n");
 	}else if(in>=65 && in<=90){
 		printf("\nIt's a capitalized character.\n");
 	}else if(in==32){
 		printf("\nIt's a space\n");
 	}else{
 		printf("\nIt's other character.\n");
 	}

 	return 0;
 }
