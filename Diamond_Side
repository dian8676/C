/*2013 DIAN PARK*/

#include<stdio.h> 



int main(void)

{

 int num,i,j;



 do

 {

  printf("Enter the Length of a Side : ");

  scanf("%d",&num);



  if(num<0) break; //Exits when a number less than zero is entered.


  for(j=1;j<=num;j++) //Create the upper triangle first. J is the height of the triengle.

  {

   for(i=num-j;i>0;i--) // The blank must start with the number minus 1 in the side length.

    printf(" ");

   for(i=1;i<=j*2-1;i++) // * must have odd numbers printed before it can become a diamond.

    printf("*");



   printf("\n");

  }



  for(j=num-1;j>0;j--) // The triangle below shall not have a single line.

  {

   for(i=1;i<=num-j;i++) // The reverse of the above expression. Increase the number to the length of one side.

    printf(" ");

   for(i=j*2-1;i>0;i--) // * is an odd number and should be reduced gradually.

    printf("*");



   printf("\n");

  }

 }while(1);



 return 0;

}
