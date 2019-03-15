/* 2013 DIAN PARK */

#include<stdio.h>

#include<math.h>



int main(void)

{

 int x1, y1, x2, y2, distance, area;



 do

 {

  printf("Please Enter the First Point (x1, y1): ");

  scanf("%d %d", &x1, &y1);



  if( x1<0 || y1<0 ) break;



  printf("Please Enter the Second Point (x2, y2): ");

  scanf("%d %d", &x2, &y2);



  if( x2<0 || y2<0 ) break;



  distance = sqrt(pow(x2-x1,2)+pow(y2-y1,2));

  area = (x2-x1)*(y2-y1);



  printf("The length between the two points is %d and the width is %d.\n\n", distance, area);

 }while( x1>=0 || y1>=0 || x2>=0 || y2>=0 );



 return 0;



}
