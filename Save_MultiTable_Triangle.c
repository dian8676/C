/* 2013 DIAN PARK */

#include<stdio.h>



int main(void)

{

 int i=0, money, result=0, a, dan, dan2, b, height;



 for(;;)

 {

  printf("4. A program for saving money by entering\n");

  printf("10. A program that prints the number of multiplication tables by entering\n");

  printf("13. A program that outputs a counter-orthogonal triangle with height input\n");

  printf("0. END\n");

  printf("Please Enter the Number: ");

  scanf("%d",&i);



  if(i==0) break;



  printf("You have selected %d.\n\n",i);



  switch(i)

  {

  case 4:



   for(a=0;result<50000;a++)

   {   

    printf("How much do you want to save?: ");

    scanf("%d",&money);



    result+=money;



   }



   printf("You saved %d won by saving %d time.\n", result, a);



   goto out;







  case 10:



   printf("How many units of multiplication table do you want to?: ");

   scanf("%d",&dan);

   printf("To which number would you like to print?: ");

   scanf("%d",&dan2);



   for(a=1;a<=dan2;a++)

   {

    for(b=2;b<=dan;b++)

     printf("%d x %d = %d\t", b, a, b*a);



    printf("\n");

   }



   goto out;





  case 13:



   printf("Please Enter the Height: ");

   scanf("%d",&height);



   for(b=0;b<=height;b++)

   {

    for(a=1;a<=height-b;a++)

     printf("*");



    printf("\n");

   }



   goto out;



  default:

   printf("Error\n\n\n\n");



  }

 }



 out:



 return 0;

}