package com.manage;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
//import com.jgoodies.forms.factories.DefaultComponentFactory;

public class Version extends Frame {
	MyFrame frame2 = new MyFrame();
	
	public Version()
	{
		frame2.setTitle("��������");
		frame2.setSize(200, 166);
		frame2.setLocation(550, 350);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		frame2.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("������ : ");
		lblNewLabel.setBounds(12, 10, 160, 27);
		frame2.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("�ڼ���");
		label.setBounds(12, 38, 160, 27);
		frame2.getContentPane().add(label);
		
		JLabel label_1 = new JLabel( "���α׷� ���� : v1.2");
		label_1.setBounds(12, 66, 160, 27);
		frame2.getContentPane().add(label_1);
		
		JLabel lblNewLabel_1 = new JLabel("�����մϴ�!!!");
		lblNewLabel_1.setBounds(12, 103, 145, 15);
		frame2.getContentPane().add(lblNewLabel_1);
		frame2.setVisible(true);
	}
}
