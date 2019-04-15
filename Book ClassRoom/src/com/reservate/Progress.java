package com.reservate;

public class Progress extends Thread {
	MainFrame mf;
	public Progress(MainFrame mf) {
		this.mf = mf;
		
	}
	public void run(){
		try{
			for(int i = 0; i<=mf.salesRate; i++){
				mf.progress.setValue(i);
				sleep(30);
			}
		}catch(InterruptedException ie){
			System.out.println(ie.getMessage());
			
		}
	}
}
