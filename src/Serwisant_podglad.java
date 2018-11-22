import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Serwisant_podglad extends JFrame {

	private JPanel contentPane;
	private JTextField Serwisant;
	static Connection connection=null;
	private static JTable Table;
	private static String Kod_maszyny;
	private static String Gniazdo;
	private static String Nazwa_maszyny;
	private static String Wydzial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Serwisant_podglad frame = new Serwisant_podglad("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Serwisant_podglad(String serwisancik) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 617, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Serwisant = new JTextField();
		Serwisant.setFont(new Font("Tahoma", Font.BOLD, 13));
		Serwisant.setHorizontalAlignment(SwingConstants.CENTER);
		Serwisant.setBounds(183, 11, 278, 20);
		Serwisant.setText(serwisancik);
		contentPane.add(Serwisant);
		Serwisant.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 591, 313);
		contentPane.add(scrollPane);
		
		Table = new JTable();
		Table.setCellSelectionEnabled(true);
		Table.setColumnSelectionAllowed(true);
		Table.setFillsViewportHeight(true);
		scrollPane.setViewportView(Table);
		
		
		Table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if( event.getValueIsAdjusting()) 
	        	{
	        		String Data = "";
		       //     String Data = Table.getValueAt(Table.getSelectedRow(), ).toString();
		            String Data_serwisu = Table.getValueAt(Table.getSelectedRow(), 2).toString();
		            String Tytul = Table.getValueAt(Table.getSelectedRow(), 1).toString();
		            String Powod = Table.getValueAt(Table.getSelectedRow(), 3).toString();		  
		            String Rozwiazanie = Table.getValueAt(Table.getSelectedRow(), 4).toString();
		         //   String Serwisant = Table.getValueAt(Table.getSelectedRow(), 5).toString();


		            Notice_podglad poglad = new Notice_podglad(Nazwa_maszyny,Data,Data_serwisu,Tytul,Powod,Rozwiazanie,serwisancik, Wydzial ,Kod_maszyny);
		            poglad.setVisible(true);
	            
	        	}	            

	        }
	    });
		
		
		scrollPane.setViewportView(Table);
		Refresh(serwisancik);
		Get_wydzial();
	}
	
	public static void Get_wydzial() 
	{
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		String data = "select * from maszyny where kod ='"+Kod_maszyny+"'";
		PreparedStatement pst;
	try {
			
			pst = connection.prepareStatement(data);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
			 Wydzial = rs.getString("Wydzial");
			 Gniazdo = rs.getString("Gniazdo");
			 Nazwa_maszyny = rs.getString("Nr_maszyny");
			}
		
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static void Refresh(String serwisancik)
	{
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		String data = "select Nr_maszyny,Tytul,Data_serwisu,Powod,Co_Zrobiono from maszyna_1 where Kto = '"+serwisancik+"'";
		PreparedStatement pst;
		try {
			
			pst = connection.prepareStatement(data);
			ResultSet rs=pst.executeQuery();
			Table.setModel(DbUtils.resultSetToTableModel(rs));
			while(rs.next())
			{
			 Kod_maszyny = rs.getString("Nr_Maszyny");
			}
		
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		  TableColumn column = null;
		    for (int i = 0; i < Table.getColumnCount(); i++) {
		        column = Table.getColumnModel().getColumn(i);
		        if (i == 1) 
		            column.setPreferredWidth(170);
		         else 
		            column.setPreferredWidth(20);
		        
		    }  
		
		
		
	}
}
