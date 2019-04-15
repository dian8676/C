package com.manage;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MemberManagements extends MyFrame{
	static ArrayList<Members> list = new ArrayList<Members>(); 
	MyFrame frame = new MyFrame();
	JFileChooser f = new JFileChooser();
	FileNameExtensionFilter ff;
	Image backImg;
	public MemberManagements() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu2 = new JMenu("도움말");
		menuBar.add(menu2);
		
		JMenuItem menu2Item = new JMenuItem("버젼정보");
		menu2.add(menu2Item);


		menu2Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Version va = new Version();
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	}
	
	public void MmStart(){
		frame.setTitle("회원관리 프로그램");
		frame.setSize(500, 150);
		frame.setLocation(500, 300);
		frame.setVisible(true);
		
		JButton bt1 = new JButton("회원등록");
		bt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberRegister mr = new MemberRegister();
			}
		});
		bt1.setBounds(22, 15, 133, 49);
		frame.getContentPane().add(bt1);
		
		JButton bt2 = new JButton("회원삭제");
		bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Managements mg = new Managements();
			}
		});
		bt2.setBounds(174, 15, 133, 49);
		frame.getContentPane().add(bt2);
		
		JButton bt3 = new JButton("회원검색");
		bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberSearch ms = new MemberSearch();
			}
		});
		bt3.setBounds(324, 15, 133, 49);
		frame.getContentPane().add(bt3);
		
	}
	
}