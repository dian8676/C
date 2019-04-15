package com.manage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Managements extends MemberManagements  implements ActionListener, ItemListener {
	MyFrame frame2 = new MyFrame();
	JButton delete = new JButton("����");
	JButton cancel = new JButton("���");
	JComboBox<String> cb = new JComboBox<String>();
	private JLabel select = new JLabel("ȸ�� ����");
	private final JTextArea memInfo = new JTextArea();
	
	DBConnect db=new DBConnect();
	ResultSet rs=null;
	
	int s_id;
	String s_name;
	String s_phone;
	
	public Managements(){
		frame2.setTitle("ȸ������");
		frame2.setSize(331, 285);
		frame2.setLocation(550, 350);
	
		cb.setBounds(12, 39, 183, 21);
		cb.addItem("ȸ���� �����ϼ���");
		list();
		frame2.getContentPane().add(cb);
		
		delete.setBounds(207, 82, 97, 32);
		frame2.getContentPane().add(delete);
		
		cancel.setBounds(207, 166, 97, 32);
		frame2.getContentPane().add(cancel);
		
		select.setBounds(12, 14, 57, 15);
		frame2.getContentPane().add(select);
		
		memInfo.setEditable(false);
		memInfo.setBounds(12, 80, 183, 152);
		frame2.getContentPane().add(memInfo);
		
		
		delete.addActionListener(this);
		cancel.addActionListener(this);
		cb.addItemListener(this);

		
		frame2.setVisible(true);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	}
	
	public void itemStateChanged(ItemEvent ie){
		if(ie==null){
			memInfo.setText(null);
		}else if(cb.getSelectedIndex()!=0){
			int select = cb.getSelectedIndex()-1;
			try {
				rs=new DBConnect().rs;
				for(int i=0;rs.next();i++){
					s_id=Integer.parseInt(rs.getString("stdid"));
					s_name=rs.getString("stdname");
					s_phone=rs.getString("stdphone");
					if(i==select){
						memInfo.setText("�й� : "+s_id+"\n�̸� :"+s_name+"\n��ȭ��ȣ : "+s_phone);
						break;
					}
				}
				rs.close();
			} catch (SQLException e1) {
				// TODO �ڵ� ������ catch ���
				e1.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete){
			int select = cb.getSelectedIndex()-1;
			try {
				rs=new DBConnect().rs;
				for(int i=0;rs.next();i++){
					s_id=Integer.parseInt(rs.getString("stdid"));
					s_name=rs.getString("stdname");
					s_phone=rs.getString("stdphone");
					if(i==select){
						db.stmt.executeUpdate("DELETE FROM Student WHERE stdid="+s_id);
						JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
						db.close();
						frame2.dispose();
						break;
					}
				}
				rs.close();
			} catch (SQLException e1) {
				// TODO �ڵ� ������ catch ���
				e1.printStackTrace();
			}
			
		}else if(e.getSource() == cancel){
			db.close();
			frame2.dispose();
		}
	}
	public void list(){
		try {
			rs=new DBConnect().rs;
			while(rs.next()){
				s_id=Integer.parseInt(rs.getString("stdid"));
				s_name=rs.getString("stdname");
				cb.addItem(s_id+ ". " + s_name);
			}
			rs.close();
		} catch (SQLException e1) {
			// TODO �ڵ� ������ catch ���
			e1.printStackTrace();
		}
	}
}

