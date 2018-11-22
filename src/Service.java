import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Service extends JFrame {

	private JPanel contentPane;
	private JTextField txtNrMaszyny;
	private JTextField txtDzial;
	private JTextField txtOstatniSerwis;
	private JTextField txtSerwisant;
	private JTextField Nr_Maszyny;
	private JTextField Dzial;
	private JTextField Ostatni_serwis;
	private JTextField Serwisant;
	private JPanel Podstawowe_dane;
	private JPanel panel_1;
	private JTextField txtDanePodstawoweMaszyny;
	private JLabel Ikonka;
	private JPanel panel_2;
	private JTextField historia_zycia;
	private static JTable Table;
	
	private JLabel Nazwa_maszyny;
	
	private static String Dzial_nazwa = "";
	private static String Nazwa_nazwa = "";
	
	static Connection connection=null;
	private JScrollPane scrollPane;
	private JTextField Nr_maszyny;
	private JTextField Aktywnosc;
	private JLabel lblNewLabel;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Service frame = new Service(Dzial_nazwa, Nazwa_nazwa);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void Fill() throws SQLException 
	{
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		
		String query = "select * from maszyny where Wydzial = '"+Dzial_nazwa+"' and Kod = '"+Nazwa_nazwa+"'";
		
		PreparedStatement pst=connection.prepareStatement(query);		
		ResultSet rs=pst.executeQuery();
		while(rs.next())
		{
			Nr_Maszyny.setText(rs.getString("Nr_Maszyny"));
			Nazwa_maszyny.setText(rs.getString("Nr_Maszyny"));
			Dzial.setText(rs.getString("Wydzial"));	
			Nr_maszyny.setText(rs.getString("Kod"));
			Aktywnosc.setText(rs.getString("Sprawnosc"));
		}
	}
	
	private void FillRest() throws SQLException
	{
		
		System.out.println("Maszyna: "+ Nazwa_nazwa);
		
		connection = MaintenanceConnection.dbConnector("tosia", "1234");

		String query = "select * from serwisowane where Nr_Maszyny = '"+Nazwa_nazwa+"'";
		
		PreparedStatement pst=connection.prepareStatement(query);		
		ResultSet rs=pst.executeQuery();
		while(rs.next())
		{
			Ostatni_serwis.setText(rs.getString("Data_serwisu"));
			Serwisant.setText(rs.getString("Kto"));		
		}
		
	}
	
	public static void Refresh()
	{
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		String data = "select  Data, Data_serwisu, Tytul, Powod, Co_Zrobiono, Kto  from serwisowane where Nr_Maszyny = '"+Nazwa_nazwa+"'";
		PreparedStatement pst;
		try {
			
			pst = connection.prepareStatement(data);
			ResultSet rs=pst.executeQuery();
			Table.setModel(DbUtils.resultSetToTableModel(rs));
		
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		  TableColumn column = null;
		    for (int i = 0; i < Table.getColumnCount(); i++) {
		        column = Table.getColumnModel().getColumn(i);
		        if (i == 2) 
		            column.setPreferredWidth(170);
		        
		        else  if (i == 0  || i == 5 || i == 1 ) 
		            column.setPreferredWidth(20);
		        
		         else 
		            column.setPreferredWidth(15);
		        
		    }  
		
		
		
	}
	
	 private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {                                     
	            int row = Table.rowAtPoint( evt.getPoint() );
	            int column = Table.columnAtPoint( evt.getPoint() );
	            String s=Table.getModel().getValueAt(row, column)+"";
	            JOptionPane.showMessageDialog(null, s);
	} 
	
	

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Service(String dzial, String nazwa) throws SQLException {
		
		this.setTitle("Podglad Danych Maszyny");
		
		setResizable(false);
		Dzial_nazwa = dzial;
		Nazwa_nazwa = nazwa;
		
				
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 606, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 11, 566, 20);
		contentPane.add(panel);
		panel.setLayout(null);
		Image img = new ImageIcon(this.getClass().getResource("tool_white.png")).getImage();
		
		Nazwa_maszyny = new JLabel("NAZWA MASZYNY");
		Nazwa_maszyny.setBounds(109, -2, 334, 20);
		panel.add(Nazwa_maszyny);
		Nazwa_maszyny.setForeground(Color.LIGHT_GRAY);
		Nazwa_maszyny.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		
	    Ikonka = new JLabel("");
	    Ikonka.setBounds(30, 0, 54, 20);
	    panel.add(Ikonka);
	    Ikonka.setIcon(new ImageIcon(img));
		
		Podstawowe_dane = new JPanel();
		Podstawowe_dane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Podstawowe_dane.setBounds(10, 72, 566, 75);
		Podstawowe_dane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		contentPane.add(Podstawowe_dane);
		Podstawowe_dane.setLayout(null);
		
		txtNrMaszyny = new JTextField();
		txtNrMaszyny.setBounds(6, 17, 86, 20);
		Podstawowe_dane.add(txtNrMaszyny);
		txtNrMaszyny.setEditable(false);
		txtNrMaszyny.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		txtNrMaszyny.setHorizontalAlignment(SwingConstants.CENTER);
		txtNrMaszyny.setText("Nr. Maszyny");
		txtNrMaszyny.setColumns(10);
		
		txtDzial = new JTextField();
		txtDzial.setBounds(6, 48, 86, 20);
		Podstawowe_dane.add(txtDzial);
		txtDzial.setEditable(false);
		txtDzial.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		txtDzial.setText("Dzial");
		txtDzial.setHorizontalAlignment(SwingConstants.CENTER);
		txtDzial.setColumns(10);
		
		txtOstatniSerwis = new JTextField();
		txtOstatniSerwis.setBounds(181, 17, 86, 20);
		Podstawowe_dane.add(txtOstatniSerwis);
		txtOstatniSerwis.setEditable(false);
		txtOstatniSerwis.setHorizontalAlignment(SwingConstants.CENTER);
		txtOstatniSerwis.setText("Ostatni serwis");
		txtOstatniSerwis.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		
		txtOstatniSerwis.setColumns(10);
		
		txtSerwisant = new JTextField();
		txtSerwisant.setBounds(181, 48, 86, 20);
		Podstawowe_dane.add(txtSerwisant);
		txtSerwisant.setEditable(false);
		txtSerwisant.setText("Serwisant");
		txtSerwisant.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		txtSerwisant.setHorizontalAlignment(SwingConstants.CENTER);
		txtSerwisant.setColumns(10);
		
		Nr_Maszyny = new JTextField();
		Nr_Maszyny.setEditable(false);
		Nr_Maszyny.setBounds(92, 17, 96, 20);
		Podstawowe_dane.add(txtSerwisant);
		Nr_Maszyny.setColumns(10);
		
		Dzial = new JTextField();
		Dzial.setEditable(false);
		Dzial.setBounds(92, 48, 66, 20);
		Podstawowe_dane.add(Dzial);
		Dzial.setColumns(10);
		
		Ostatni_serwis = new JTextField();
		Ostatni_serwis.setEditable(false);
		Ostatni_serwis.setBounds(272, 17, 76, 20);
		Podstawowe_dane.add(Ostatni_serwis);
		Ostatni_serwis.setColumns(10);
		
		Serwisant = new JTextField();
		Serwisant.setEditable(false);
		Serwisant.setBounds(272, 48, 127, 20);
		Podstawowe_dane.add(Serwisant);
		Serwisant.setColumns(10);
		
		Nr_maszyny = new JTextField();
		Nr_maszyny.setEditable(false);
		Nr_maszyny.setColumns(10);
		Nr_maszyny.setBounds(92, 17, 66, 20);
		Podstawowe_dane.add(Nr_maszyny);
		
		Aktywnosc = new JTextField();
		Aktywnosc.setEditable(false);
		Aktywnosc.setBounds(480, 17, 76, 20);
		Podstawowe_dane.add(Aktywnosc);
		Aktywnosc.setColumns(10);
		
		lblNewLabel = new JLabel("Aktywnosc");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(407, 20, 72, 14);
		Podstawowe_dane.add(lblNewLabel);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(20, 41, 544, 20);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtDanePodstawoweMaszyny = new JTextField();
		txtDanePodstawoweMaszyny.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtDanePodstawoweMaszyny.setEditable(false);
		txtDanePodstawoweMaszyny.setBackground(Color.LIGHT_GRAY);
		txtDanePodstawoweMaszyny.setHorizontalAlignment(SwingConstants.CENTER);
		txtDanePodstawoweMaszyny.setText("DANE PODSTAWOWE MASZYNY");
		txtDanePodstawoweMaszyny.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		
		txtDanePodstawoweMaszyny.setBounds(70, 0, 181, 20);
		panel_1.add(txtDanePodstawoweMaszyny);
		txtDanePodstawoweMaszyny.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(10, 158, 566, 20);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		historia_zycia = new JTextField();
		historia_zycia.setBackground(Color.LIGHT_GRAY);
		historia_zycia.setFont(new Font("Tahoma", Font.BOLD, 11));
		historia_zycia.setHorizontalAlignment(SwingConstants.CENTER);
		historia_zycia.setText("HISTORIA ZYCIA MASZYNY");
		historia_zycia.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		historia_zycia.setEditable(false);
		historia_zycia.setBounds(82, 0, 171, 20);
		panel_2.add(historia_zycia);
		historia_zycia.setColumns(10);
		
		JButton btnNewButton = new JButton("Dodaj ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					Notice notka = new Notice(Nazwa_nazwa, Dzial.getText(), Nr_maszyny.getText());
					notka.setVisible(true);
			
			}
		});
		btnNewButton.setBackground(SystemColor.scrollbar);
		btnNewButton.setBounds(487, 0, 79, 20);
		panel_2.add(btnNewButton);
		
		JList list = new JList();
		list.setBounds(485, 225, 1, 1);
		contentPane.add(list);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 194, 566, 240);
		contentPane.add(scrollPane);
		
		Table = new JTable();
		Table.setFillsViewportHeight(true);
		Table.setColumnSelectionAllowed(true);
		Table.setCellSelectionEnabled(true);
		Table.setRowHeight(30);
		
			
	

		// EVENT CLICK ON SELECTED ROW
		
		Table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if( event.getValueIsAdjusting()) 
	        	{
		            String Data = Table.getValueAt(Table.getSelectedRow(), 0).toString();
		            String Data_serwisu = Table.getValueAt(Table.getSelectedRow(), 1).toString();
		            String Tytul = Table.getValueAt(Table.getSelectedRow(), 2).toString();
		            String Powod = Table.getValueAt(Table.getSelectedRow(), 3).toString();		  
		            String Rozwiazanie = Table.getValueAt(Table.getSelectedRow(), 4).toString();
		            String Serwisant = Table.getValueAt(Table.getSelectedRow(), 5).toString();


		            Notice_podglad poglad = new Notice_podglad(Nazwa_maszyny.getText(),Data,Data_serwisu,Tytul,Powod,Rozwiazanie,Serwisant, Dzial.getText(),Nr_maszyny.getText());
		            poglad.setVisible(true);
	            
	        	}	            

	        }
	    });

		scrollPane.setViewportView(Table);
		
		Refresh();
		 Fill();
		 FillRest();
		 
		 
	}
}
