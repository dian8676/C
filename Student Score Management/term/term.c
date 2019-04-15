/*
1301587 박소현
2013.12.16
텀 프로젝트 : 학생 데이터베이스
*/
#define _CRT_SECURE_NO_WARNINGS

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

typedef struct{
	int st_id;
	char st_name[20];
	char dept_name[50];
	int grade_kor;
	int grade_math;
	int grade_eng;
} Student;

typedef struct{
	int kor;
	int math;
	int eng;
}Rank;	//각각의 순위를 입력할 구조체

void menu(){	//메뉴함수
	printf("-------------------------------------\n");
	printf("1. 전체출력\n");
	printf("2. 레코드 추가\n");
	printf("3. 검색\n");
	printf("4. 국어, 수학, 영어 부분 1등과 꼴등\n");
	printf("0. 종료\n");
	printf("-------------------------------------\n\n\n");
}

void ave_var(Student *mem,double *aver,double *var,int n){	//평균과 분산을 구하는 함수
	int i;
	double sum,vsum,tosum=0;

	for(i=0;i<n;i++){
		sum=mem[i].grade_kor+mem[i].grade_math+mem[i].grade_eng;
		aver[i]=sum/3.0;
		tosum+=sum;	//총 평균을 위해 모든 점수를 더한다.
		vsum=(mem[i].grade_kor-aver[i])*(mem[i].grade_kor-aver[i])+(mem[i].grade_math-aver[i])
			*(mem[i].grade_math-aver[i])+(mem[i].grade_eng-aver[i])*(mem[i].grade_eng-aver[i]);
		var[i]=vsum/3.0;
	}
	aver[n]=tosum/(n*3.0);	//총평균
	for(tosum=0,i=0;i<n;i++)
		tosum+=(mem[i].grade_kor-aver[n])*(mem[i].grade_kor-aver[n])+(mem[i].grade_math-aver[n])
		*(mem[i].grade_math-aver[n])+(mem[i].grade_eng-aver[n])*(mem[i].grade_eng-aver[n]);
	var[n]=tosum/(n*3.0);	//총분산
}

Student *add (Student* mem,int n,int a){	//동적할당을 추가하는 함수
	Student *tem=mem; //새 포인터를 선언해 같은 부분을 가르킨다
	int i;

	tem=(Student*)malloc(sizeof(Student)*(n+a));	//n+a만큼 동적 할당 한다.

	for(i=0;i<n;i++)	//mem에있는 수를 복사한다.
		tem[i]=mem[i];

	free(mem);	//원래 수 동적할당 해제
	return tem;
}

void sort(Student *mem,int n){	//정렬하는 함수
	Student temp;
	int i,j;

	for(i=n-1;i>=1;i--){    // 2개씩 비교하여 정렬함.  
		for(j=0;j<i;j++){  
			if(mem[j].st_id>mem[j+1].st_id){  
				temp=mem[j];   // 위치를 바꾸기 위한 부분  
				mem[j]=mem[j+1];  
				mem[j+1]=temp;  
			}  
		}
	}
}

void search(Student *mem,int n, char *s){	//검색하는 함수
	int i, type;
	type=(s[0]>='0' && s[0]<='9')? 0:1;	//숫자일경우 0, 아닐경우 1을 한다.
	
	if(type==0){
		printf("%5s %6s %13s %3s %3s %3s\n","학번","이름","학과","국","수","영");
		for(i=0;i<n;i++){
			if(mem[i].st_id==atoi(s))
				printf("%05d %6s %13s %3d %3d %3d\n",mem[i].st_id,mem[i].st_name,
					mem[i].dept_name,mem[i].grade_kor,mem[i].grade_math,mem[i].grade_eng);
			else if(i==n-1)
				printf("정보가 없습니다.\n");
		}
		printf("\n");
	}
	else if(type==1){
		printf("%5s %6s %13s %3s %3s %3s\n","학번","이름","학과","국","수","영");
		for(i=0;i<n;i++){
			if(strcmp(mem[i].st_name,s)==0)
				printf("%05d %6s %13s %3d %3d %3d\n",mem[i].st_id,mem[i].st_name,
					mem[i].dept_name,mem[i].grade_kor,mem[i].grade_math,mem[i].grade_eng);
			else if(i==n-1)
				printf("정보가 없습니다.\n");		
		}
		printf("\n");
	}
	else printf("정보가 없습니다.\n\n");
}

void score(Student *mem,Rank *grade,int n){
	int i,j;
	for(i=0;i<n;i++){
		grade[i].kor=1; //i번의 등수를 1등으로 초기화
		for(j=0;j<n;j++){
			if(mem[j].grade_kor>mem[i].grade_kor) //자신보다 높은 점수를 받은 학생이 있으면 1등 증가
				grade[i].kor++;
		}
	}
	for(i=0;i<n;i++){
		grade[i].math=1; //i번의 등수를 1등으로 초기화
		for(j=0;j<n;j++){
			if(mem[j].grade_math>mem[i].grade_math) //자신보다 높은 점수를 받은 학생이 있으면 1등 증가
				grade[i].math++;
		}
	}
	for(i=0;i<n;i++){
		grade[i].eng=1; //i번의 등수를 1등으로 초기화
		for(j=0;j<n;j++){
			if(mem[j].grade_eng>mem[i].grade_eng) //자신보다 높은 점수를 받은 학생이 있으면 1등 증가
				grade[i].eng++;
			
		}
	}
}

int main(){
	Student *member;
	Rank *grade;	//등 수 입력받을 구조체
	double *average,*variance;	//평균과 분산
	char *input,yesno;	//검색
	int no,i,j,num,a=0,k,kmx,mmx,emx;
	FILE *fp_in, *fp_out;

	fp_in=fopen("st_dB.txt","r");
	if(fp_in==NULL){
		printf("파일이 존재하지 않습니다.\n");
		exit(1);
	}
	

	fscanf(fp_in,"%d",&no);
	member=(Student*)malloc(sizeof(Student)*no);	//학생 수만큼 할당

	for(i=0;i<no;i++)
		fscanf(fp_in,"%d %s %s %d %d %d",&member[i].st_id,&member[i].st_name,&member[i].dept_name
		,&member[i].grade_kor,&member[i].grade_math,&member[i].grade_eng);
	
	//구조체배열에 데이터 입력
	while(1){
		menu();
		printf("입력 : ");
		scanf("%d",&num);
		if(num==0) break;

		switch(num){
			case 1 :
				average=(double*)malloc(sizeof(double)*(no+1));
				variance=(double*)malloc(sizeof(double)*(no+1)); //평균과 분산 학생수+1만큼 할당, 총 평균 총 분산 입력을위해

				sort(member,no);
				ave_var(member,average,variance,no);
				printf("%5s %6s %13s %3s %3s %3s %6s %6s\n",
					"학번","이름","학과","국","수","영","평균","분산");
				for(i=0;i<no;i++){
					printf("%05d %6s %13s %3d %3d %3d ",member[i].st_id,member[i].st_name,
						member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					printf(" %.2f %.2f\n",average[i],variance[i]);
				}
				printf("총 평균 : %.2f , 총 분산 : %.2f\n\n",average[no],variance[no]);
				
				free(average);
				free(variance);	//해제
				break;
			case 2 :
				printf("몇 명 추가 하시겠습니까 : ");
				scanf("%d",&a);
				if(a>0){
					fp_out=fopen("st_dB.txt","w");	//덮어쓰는부분
					member=add(member,no,a);	//동적할당 추가
					fprintf(fp_out,"%d",no+a);	//학생수 바꿈
					printf("%5s %5s %13s %3s %3s %3s\n","학번","이름","학과","국","수","영");
					for(i=no;i<no+a;i++){
						scanf("%d %s %s %d %d %d",&member[i].st_id,&member[i].st_name,
						&member[i].dept_name,&member[i].grade_kor,&member[i].grade_math,&member[i].grade_eng);
						for(j=0,k=i;j<no;j++){
							if(member[k].st_id==member[j].st_id){
								printf("학번이 존재합니다.다시 입력하세요\n");
								i--;
							}
						}
					}
					no=no+a;
					sort(member,no);	//정렬
					for(i=0;i<no;i++)	//데이터 덮어씀
						fprintf(fp_out,"\n%05d %s %s %d %d %d",member[i].st_id,member[i].st_name,
						member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);		
				}
				else printf("잘못 입력하셨습니다\n");
				printf("\n\n");
				break;
			case 3 : 
				do{
					printf("학번 또는 이름을 입력하시오 : ");
					input=(char*)malloc(sizeof(char)*30);	//입력 동적 할당
					fflush(stdin);	//버퍼를 지움
					
					gets(input);	//입력
					
					search(member,no,input);
					
					free(input);
					printf("더 하시겠습니까? (y/n) ");
					scanf("%c",&yesno);
					if(yesno=='n' || yesno=='N') break;
					printf("\n\n");
				}while(1);
				break;
			case 4 :
				grade=(Rank*)malloc(sizeof(Rank)*no);
				score(member,grade,no);	//등 수를 매김

				for(i=1,kmx=grade[0].kor,mmx=grade[0].math,emx=grade[0].eng;i<no;i++){
					if(grade[i].kor>kmx) kmx=grade[i].kor;
					if(grade[i].math>mmx) mmx=grade[i].math;
					if(grade[i].eng>emx) emx=grade[i].eng;
				}	//각각 최하 등수를 찾는다 (중복이 있을 경우 꼭 마지막 등수가 학생수는 아닐 수 있다)
				printf("%4s %4s %5s %6s %13s %3s %3s %3s\n","과목","등수","학번","이름","학과","국","수","영");
				printf("국어  1등\n");
				for(i=0;i<no;i++)
					if(grade[i].kor==1){	//1등찾기
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("     %d등\n",kmx);
				for(i=0;i<no;i++)
					if(grade[i].kor==kmx){	//꼴등찾기
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("수학  1등\n");
				for(i=0;i<no;i++)	
					if(grade[i].math==1){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("     %d등\n",mmx);
				for(i=0;i<no;i++)	
					if(grade[i].math==mmx){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("영어  1등\n");
				for(i=0;i<no;i++)
					if(grade[i].eng==1){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("     %d등\n",emx);
				for(i=0;i<no;i++)
					if(grade[i].eng==emx){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("\n\n");

				free(grade);	//해제
				break;
			default :
				printf("잘못입력하셨습니다\n\n");
		}
	}
	free(member);	//해제
	return 0;
}