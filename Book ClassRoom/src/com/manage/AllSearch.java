package com.manage;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class AllSearch extends MemberManagements {
	MyFrame frame5 = new MyFrame();
	private JTable table_1;
	InsertMember im= new InsertMember();
	ResultSet rs=null;
	public AllSearch() {
		frame5.setTitle("전체회원");
		frame5.setSize(700, 448);
		frame5.setLocation(350, 250);
		frame5.getContentPane().setLayout(null);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		
		JTable table = null;
		String[] column =  {
			"코드", "이름", "전화번호"
		};
		Object[][] ob = new Object[10][3];
		try {
			rs=im.stmt.executeQuery("SELECT * FROM Student");
			for(int i=0;rs.next();i++){
				ob[i][0] = rs.getString("stdid");
				ob[i][1] = rs.getString("stdname");
				ob[i][2] = rs.getString("stdphone");
			}
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
		
		
		table= new JTable(ob,column);
		table.getColumn("코드").setPreferredWidth(50);
		table.getColumn("이름").setPreferredWidth(50);
		table.getColumn("전화번호").setPreferredWidth(100);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		for(int i=0; i<tcm.getColumnCount(); i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		table.setSize(660,387);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(660, 387);	
		scrollPane.setLocation(12, 10);
		scrollPane.setPreferredSize(new Dimension(369, 203));
		frame5.getContentPane().add(scrollPane);
		
		frame5.setVisible(true);
	}
}
