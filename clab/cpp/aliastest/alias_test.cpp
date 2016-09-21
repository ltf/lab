//#include <iostream>
//using std::cout;

#include <stdio.h>

int main(){

	int x = 88;
	printf("x: %d\n",x);

	int &y = x;
	printf("y: %d\n",y);

	int &z(y);
	printf("z: %d\n",z);

	int *p(&x);
	printf("*p: %d\n",*p);

	printf("z: %d\n",z);
	printf("y: %d\n",y);
	printf("x: %d\n",x);
	printf("*p: %d\n",*p);
	//cout<<"test";
	return 0;

}






