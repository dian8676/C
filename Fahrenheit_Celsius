/* 2013 DIAN PARK */
#include<stdio.h> //4



int main(void)

{

 float tem;

 char cf;





 do
 
 
 { printf("Please enter C if the temperature you want to enter is Celsius, or F if it is Fahrenheit.: ");

  scanf("%c",&cf);

  printf("Please enter the temperature.: ");

  scanf("%f", &tem);

 

 



  switch(cf)

  {

  case 'f': case 'F':

   {

    tem=(tem-32.0)/(9.0/5.0);



    printf("The value of converting the entered Fahrenheit to the Celsius temperature is %.2f degrees. \n",tem);

 

    break;

   }



  case 'c': case 'C':

   {

    tem=tem*9.0/5.0 + 32.0;



    printf("The value of converting the entered Celsius to the Fahrenheit temperature is %.2f degrees. \n",tem);



    break;

   }

  

  default:

   {

    printf("Error");

    break;

   }

  } 



 }while( cf=='x' || cf=='X');





 return 0;

}
