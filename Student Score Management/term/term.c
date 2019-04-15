/*
1301587 �ڼ���
2013.12.16
�� ������Ʈ : �л� �����ͺ��̽�
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
}Rank;	//������ ������ �Է��� ����ü

void menu(){	//�޴��Լ�
	printf("-------------------------------------\n");
	printf("1. ��ü���\n");
	printf("2. ���ڵ� �߰�\n");
	printf("3. �˻�\n");
	printf("4. ����, ����, ���� �κ� 1��� �õ�\n");
	printf("0. ����\n");
	printf("-------------------------------------\n\n\n");
}

void ave_var(Student *mem,double *aver,double *var,int n){	//��հ� �л��� ���ϴ� �Լ�
	int i;
	double sum,vsum,tosum=0;

	for(i=0;i<n;i++){
		sum=mem[i].grade_kor+mem[i].grade_math+mem[i].grade_eng;
		aver[i]=sum/3.0;
		tosum+=sum;	//�� ����� ���� ��� ������ ���Ѵ�.
		vsum=(mem[i].grade_kor-aver[i])*(mem[i].grade_kor-aver[i])+(mem[i].grade_math-aver[i])
			*(mem[i].grade_math-aver[i])+(mem[i].grade_eng-aver[i])*(mem[i].grade_eng-aver[i]);
		var[i]=vsum/3.0;
	}
	aver[n]=tosum/(n*3.0);	//�����
	for(tosum=0,i=0;i<n;i++)
		tosum+=(mem[i].grade_kor-aver[n])*(mem[i].grade_kor-aver[n])+(mem[i].grade_math-aver[n])
		*(mem[i].grade_math-aver[n])+(mem[i].grade_eng-aver[n])*(mem[i].grade_eng-aver[n]);
	var[n]=tosum/(n*3.0);	//�Ѻл�
}

Student *add (Student* mem,int n,int a){	//�����Ҵ��� �߰��ϴ� �Լ�
	Student *tem=mem; //�� �����͸� ������ ���� �κ��� ����Ų��
	int i;

	tem=(Student*)malloc(sizeof(Student)*(n+a));	//n+a��ŭ ���� �Ҵ� �Ѵ�.

	for(i=0;i<n;i++)	//mem���ִ� ���� �����Ѵ�.
		tem[i]=mem[i];

	free(mem);	//���� �� �����Ҵ� ����
	return tem;
}

void sort(Student *mem,int n){	//�����ϴ� �Լ�
	Student temp;
	int i,j;

	for(i=n-1;i>=1;i--){    // 2���� ���Ͽ� ������.  
		for(j=0;j<i;j++){  
			if(mem[j].st_id>mem[j+1].st_id){  
				temp=mem[j];   // ��ġ�� �ٲٱ� ���� �κ�  
				mem[j]=mem[j+1];  
				mem[j+1]=temp;  
			}  
		}
	}
}

void search(Student *mem,int n, char *s){	//�˻��ϴ� �Լ�
	int i, type;
	type=(s[0]>='0' && s[0]<='9')? 0:1;	//�����ϰ�� 0, �ƴҰ�� 1�� �Ѵ�.
	
	if(type==0){
		printf("%5s %6s %13s %3s %3s %3s\n","�й�","�̸�","�а�","��","��","��");
		for(i=0;i<n;i++){
			if(mem[i].st_id==atoi(s))
				printf("%05d %6s %13s %3d %3d %3d\n",mem[i].st_id,mem[i].st_name,
					mem[i].dept_name,mem[i].grade_kor,mem[i].grade_math,mem[i].grade_eng);
			else if(i==n-1)
				printf("������ �����ϴ�.\n");
		}
		printf("\n");
	}
	else if(type==1){
		printf("%5s %6s %13s %3s %3s %3s\n","�й�","�̸�","�а�","��","��","��");
		for(i=0;i<n;i++){
			if(strcmp(mem[i].st_name,s)==0)
				printf("%05d %6s %13s %3d %3d %3d\n",mem[i].st_id,mem[i].st_name,
					mem[i].dept_name,mem[i].grade_kor,mem[i].grade_math,mem[i].grade_eng);
			else if(i==n-1)
				printf("������ �����ϴ�.\n");		
		}
		printf("\n");
	}
	else printf("������ �����ϴ�.\n\n");
}

void score(Student *mem,Rank *grade,int n){
	int i,j;
	for(i=0;i<n;i++){
		grade[i].kor=1; //i���� ����� 1������ �ʱ�ȭ
		for(j=0;j<n;j++){
			if(mem[j].grade_kor>mem[i].grade_kor) //�ڽź��� ���� ������ ���� �л��� ������ 1�� ����
				grade[i].kor++;
		}
	}
	for(i=0;i<n;i++){
		grade[i].math=1; //i���� ����� 1������ �ʱ�ȭ
		for(j=0;j<n;j++){
			if(mem[j].grade_math>mem[i].grade_math) //�ڽź��� ���� ������ ���� �л��� ������ 1�� ����
				grade[i].math++;
		}
	}
	for(i=0;i<n;i++){
		grade[i].eng=1; //i���� ����� 1������ �ʱ�ȭ
		for(j=0;j<n;j++){
			if(mem[j].grade_eng>mem[i].grade_eng) //�ڽź��� ���� ������ ���� �л��� ������ 1�� ����
				grade[i].eng++;
			
		}
	}
}

int main(){
	Student *member;
	Rank *grade;	//�� �� �Է¹��� ����ü
	double *average,*variance;	//��հ� �л�
	char *input,yesno;	//�˻�
	int no,i,j,num,a=0,k,kmx,mmx,emx;
	FILE *fp_in, *fp_out;

	fp_in=fopen("st_dB.txt","r");
	if(fp_in==NULL){
		printf("������ �������� �ʽ��ϴ�.\n");
		exit(1);
	}
	

	fscanf(fp_in,"%d",&no);
	member=(Student*)malloc(sizeof(Student)*no);	//�л� ����ŭ �Ҵ�

	for(i=0;i<no;i++)
		fscanf(fp_in,"%d %s %s %d %d %d",&member[i].st_id,&member[i].st_name,&member[i].dept_name
		,&member[i].grade_kor,&member[i].grade_math,&member[i].grade_eng);
	
	//����ü�迭�� ������ �Է�
	while(1){
		menu();
		printf("�Է� : ");
		scanf("%d",&num);
		if(num==0) break;

		switch(num){
			case 1 :
				average=(double*)malloc(sizeof(double)*(no+1));
				variance=(double*)malloc(sizeof(double)*(no+1)); //��հ� �л� �л���+1��ŭ �Ҵ�, �� ��� �� �л� �Է�������

				sort(member,no);
				ave_var(member,average,variance,no);
				printf("%5s %6s %13s %3s %3s %3s %6s %6s\n",
					"�й�","�̸�","�а�","��","��","��","���","�л�");
				for(i=0;i<no;i++){
					printf("%05d %6s %13s %3d %3d %3d ",member[i].st_id,member[i].st_name,
						member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					printf(" %.2f %.2f\n",average[i],variance[i]);
				}
				printf("�� ��� : %.2f , �� �л� : %.2f\n\n",average[no],variance[no]);
				
				free(average);
				free(variance);	//����
				break;
			case 2 :
				printf("�� �� �߰� �Ͻðڽ��ϱ� : ");
				scanf("%d",&a);
				if(a>0){
					fp_out=fopen("st_dB.txt","w");	//����ºκ�
					member=add(member,no,a);	//�����Ҵ� �߰�
					fprintf(fp_out,"%d",no+a);	//�л��� �ٲ�
					printf("%5s %5s %13s %3s %3s %3s\n","�й�","�̸�","�а�","��","��","��");
					for(i=no;i<no+a;i++){
						scanf("%d %s %s %d %d %d",&member[i].st_id,&member[i].st_name,
						&member[i].dept_name,&member[i].grade_kor,&member[i].grade_math,&member[i].grade_eng);
						for(j=0,k=i;j<no;j++){
							if(member[k].st_id==member[j].st_id){
								printf("�й��� �����մϴ�.�ٽ� �Է��ϼ���\n");
								i--;
							}
						}
					}
					no=no+a;
					sort(member,no);	//����
					for(i=0;i<no;i++)	//������ ���
						fprintf(fp_out,"\n%05d %s %s %d %d %d",member[i].st_id,member[i].st_name,
						member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);		
				}
				else printf("�߸� �Է��ϼ̽��ϴ�\n");
				printf("\n\n");
				break;
			case 3 : 
				do{
					printf("�й� �Ǵ� �̸��� �Է��Ͻÿ� : ");
					input=(char*)malloc(sizeof(char)*30);	//�Է� ���� �Ҵ�
					fflush(stdin);	//���۸� ����
					
					gets(input);	//�Է�
					
					search(member,no,input);
					
					free(input);
					printf("�� �Ͻðڽ��ϱ�? (y/n) ");
					scanf("%c",&yesno);
					if(yesno=='n' || yesno=='N') break;
					printf("\n\n");
				}while(1);
				break;
			case 4 :
				grade=(Rank*)malloc(sizeof(Rank)*no);
				score(member,grade,no);	//�� ���� �ű�

				for(i=1,kmx=grade[0].kor,mmx=grade[0].math,emx=grade[0].eng;i<no;i++){
					if(grade[i].kor>kmx) kmx=grade[i].kor;
					if(grade[i].math>mmx) mmx=grade[i].math;
					if(grade[i].eng>emx) emx=grade[i].eng;
				}	//���� ���� ����� ã�´� (�ߺ��� ���� ��� �� ������ ����� �л����� �ƴ� �� �ִ�)
				printf("%4s %4s %5s %6s %13s %3s %3s %3s\n","����","���","�й�","�̸�","�а�","��","��","��");
				printf("����  1��\n");
				for(i=0;i<no;i++)
					if(grade[i].kor==1){	//1��ã��
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("     %d��\n",kmx);
				for(i=0;i<no;i++)
					if(grade[i].kor==kmx){	//�õ�ã��
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("����  1��\n");
				for(i=0;i<no;i++)	
					if(grade[i].math==1){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("     %d��\n",mmx);
				for(i=0;i<no;i++)	
					if(grade[i].math==mmx){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("����  1��\n");
				for(i=0;i<no;i++)
					if(grade[i].eng==1){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("     %d��\n",emx);
				for(i=0;i<no;i++)
					if(grade[i].eng==emx){
						printf("%10s"," ");
						printf("%05d %6s %13s %3d %3d %3d\n",member[i].st_id,member[i].st_name,
							member[i].dept_name,member[i].grade_kor,member[i].grade_math,member[i].grade_eng);
					}
				printf("\n\n");

				free(grade);	//����
				break;
			default :
				printf("�߸��Է��ϼ̽��ϴ�\n\n");
		}
	}
	free(member);	//����
	return 0;
}