package com.manage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MemberSearch extends MemberManagements implements ActionListener {
	MyFrame frame5 = new MyFrame();
	JButton sCode = new JButton("학번");
	JButton sName = new JButton("이름");
	JButton sPhone = new JButton("전화 번호");
	JButton sAll = new JButton("전체 학생");
	JButton sCancel = new JButton("취소");
	JTextArea textArea = new JTextArea();
	
	InsertMember im=new InsertMember();
	public MemberSearch(){
		frame5.setTitle("검색");
		frame5.setSize(477, 293);
		frame5.setLocation(550, 350);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	
		JLabel lblNewLabel = new JLabel("검색");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 16));
		lblNewLabel.setBounds(354, 3, 43, 32);
		frame5.getContentPane().add(lblNewLabel);
		
		sCode.setBounds(354, 40, 95, 32);
		frame5.getContentPane().add(sCode);
		
		sName.setBounds(354, 82, 95, 32);
		frame5.getContentPane().add(sName);
		
		
		sAll.setBounds(354, 166, 95, 32);
		frame5.getContentPane().add(sAll);
		
		sCancel.setBounds(354, 208, 95, 32);
		frame5.getContentPane().add(sCancel);
		
		textArea.setEditable(false);
		textArea.setBounds(12, 9, 331, 234);
		frame5.getContentPane().add(textArea);
		
		JList list_1 = new JList();
		list_1.setBounds(242, 48, 1, 1);
		frame5.getContentPane().add(list_1);
		
		frame5.setVisible(true);
		
		sCode.addActionListener(this);
		sName.addActionListener(this);
		sPhone.addActionListener(this);
		sAll.addActionListener(this);
		sCancel.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		int dataCnt=1;
		String[] nameCollection = null;
		String nameCode =null;
		ResultSet rs=null;
		String s_id;
		String s_name;
		String s_phone;
		if(e.getSource() == sCode){
			String code = JOptionPane.showInputDialog("검색할 코드을 입력하세요");
			if(code == null){
				return;
			}if(im.stmt!=null){
				try {
					rs = im.stmt.executeQuery("SELECT * FROM Student");
				
					while(rs.next()){
						s_id=rs.getString("stdid");
						s_name=rs.getString("stdname");
						s_phone=rs.getString("stdphone");
						if(code.equals(s_id)){
							textArea.setText("학번 : "+s_id+"\n이름 : "+s_name+"\n전화번호 : "+s_phone);
							break;
						}
					}
					
				} catch (SQLException e1) {
				// TODO 자동 생성된 catch 블록
				e1.printStackTrace();
				}
			}else{
					JOptionPane.showMessageDialog(this, "등록된 회원이 없습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			}
				
			
				
				
		}else if(e.getSource() == sName){
			int cnt = 0;
			String name = JOptionPane.showInputDialog("검색할 이름을 입력하세요");
			if(name == null){
				return;
			}
			if(im.stmt!=null){
				try {
					rs = im.stmt.executeQuery("SELECT * FROM Student");
				
					while(rs.next()){
						s_id=rs.getString("stdid");
						s_name=rs.getString("stdname");
						s_phone=rs.getString("stdphone");
						if(name.equals(s_name)){
							textArea.setText("학번 : "+s_id+"\n이름 : "+s_name+"\n전화번호 : "+s_phone);
						}
						
					}					
				} catch (SQLException e1) {
				// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "등록된 회원이 없습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(e.getSource() == sAll){
			AllSearch al = new AllSearch();
		}else if(e.getSource() == sCancel){
			frame5.dispose();
		}
	}
}
