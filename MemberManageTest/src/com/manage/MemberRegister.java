package com.manage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class MemberRegister extends MemberManagements implements ActionListener {
	private JTextField name;
	private JTextField phone;
	private JTextField code;
	JButton bt1 = new JButton("등록");
	JButton bt2 = new JButton("취소");
	MyFrame frame = new MyFrame();
	JRadioButton female;
	JRadioButton male;
	ButtonGroup bg = new ButtonGroup();
	InsertMember insert = new InsertMember();
	public MemberRegister(){
		frame.setTitle("회원등록");
		frame.setSize(500, 330);
		frame.setLocation(500, 300);
	
		JLabel mCode = new JLabel("학번 :");
		mCode.setBounds(27, 16, 90, 30);
		frame.getContentPane().add(mCode);
		
		JLabel mName = new JLabel("이름 :");
		mName.setBounds(207, 16, 90, 30);
		frame.getContentPane().add(mName);

		JLabel mPhone = new JLabel("전화번호 :");
		mPhone.setBounds(27, 96, 90, 30);
		frame.getContentPane().add(mPhone);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(280, 21, 62, 21);
		frame.getContentPane().add(name);
	
		
		phone = new JTextField();
		phone.setColumns(10);
		phone.setBounds(129, 101, 144, 21);
		frame.getContentPane().add(phone);
		

		bt1.setBounds(97, 232, 97, 40);
		frame.getContentPane().add(bt1);
		
		bt2.setBounds(280, 232, 97, 40);
		frame.getContentPane().add(bt2);
		
		code = new JTextField();
		code.setColumns(10);
		code.setBounds(97, 21, 62, 21);
		if(list.size() != 0){
			code.setText(null);
		}else{
			code.setText(1001 + "");
		}
		frame.getContentPane().add(code);
	
		
		JLabel label = new JLabel("('-'없이 입력해주세요)");
		label.setBounds(280, 104, 138, 15);
		frame.getContentPane().add(label);
		
		frame.setVisible(true);
		
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt1){
			String memCode = code.getText();
			String memName = name.getText();
			String memPhone = phone.getText();
			if(memName.equals("")){
				JOptionPane.showMessageDialog(this, "회원 이름을 입력해 주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
			}else if(memPhone.equals("")){
				JOptionPane.showMessageDialog(this, "회원 전화번호를 입력해 주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
			}else{
				if(!integerOrNot(memCode)){
					JOptionPane.showMessageDialog(this, "회원 코드는 문자를 입력할 수 없습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				}else if(!integerOrNot(memPhone)){
					JOptionPane.showMessageDialog(this, "전화번호는 문자를 입력할 수 없습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				}else if(!(memPhone.substring(0,1).equals("0"))){
					JOptionPane.showMessageDialog(this, "잘못된 전화번호를 입력하였습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				}else{
					int check = JOptionPane.showConfirmDialog(this, "입력한 내용이 맞습니까?\n" + 
							"학번 : "+memCode + "\n이름 : "+memName + "\n전화번호 : " + memPhone,"메시지", JOptionPane.INFORMATION_MESSAGE );
					if(check == 0){
						Members m = new Members();
						m.setId(Integer.parseInt(memCode));
						m.setName(memName);
						m.setPhone(memPhone);
						try {
							insert.create(m);
							JOptionPane.showMessageDialog(this, "회원이 등록되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
							int check2 = JOptionPane.showConfirmDialog(this, "계속 입력하시겠습니까?");
							if(check2 == 0){
								code.setText(null);
								name.setText(null);
								phone.setText(null);
							}else if(check2 == 1){
								frame.dispose();
							}
						} catch (SQLException e1) {
							// TODO 자동 생성된 catch 블록
							e1.printStackTrace();
						}
						
						
					}
				}
			}
		}else if(e.getSource() == bt2){
			frame.dispose();
		}
	}
	
	public boolean integerOrNot(String strData){ // 입력값이 숫자인지 문자인지 판별 : 
		char[] charData = strData.toCharArray();
		boolean check=true;
		while(check){
			for(int i=0; i<charData.length; i++){		
				if(!Character.isDigit(charData[i])){
						check = !check;
						break;
				}
			}
			break;	
		}return check;
	}
}

