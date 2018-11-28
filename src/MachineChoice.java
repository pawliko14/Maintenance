import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MachineChoice extends JFrame implements WindowListener  {

	private JFrame frame;
	private JTextField txtTest;
	private JTextField txtDzia;
	private JTextField txtNazwanr;
	private JComboBox comboBox_Dzial;
	private JComboBox comboBox_Nazwa;
	private JComboBox comboBox_Serwisant;
	private String selectedValue;
	
	static Connection connection = MaintenanceConnection.dbConnector("tosia", "1234");
	private JTextField textField;
	private JTextField txtWyborSerwisanta;
	private JTextField txtSerwisant;
	private JTextField textField_1;
	private JTextField txtWyszukiwaniePoFrazie;
	private JTextField fraza;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {


				try {
					MachineChoice window = new MachineChoice(connection);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
	}
	
	public void FillComboBox1() throws SQLException
	{
		PreparedStatement st = null;
		ResultSet rs = null; 
		String sql1 = "select distinct Wydzial from maszyny";
		
		st = connection.prepareStatement(sql1);
		rs = st.executeQuery();
		
		while(rs.next())
		{
			String nest = rs.getString("Wydzial");
			comboBox_Dzial.addItem(nest);
		}
		
	}
	
	public void FillComboBox2() throws SQLException 
	{
		PreparedStatement st = null;
		ResultSet rs = null; 
		String sql1 = "select Kod from maszyny where Wydzial = '"+selectedValue+"' ";
		
		st = connection.prepareStatement(sql1);
		rs = st.executeQuery();
		
		while(rs.next())
		{
			String Name = rs.getString("Kod");
			comboBox_Nazwa.addItem(Name);
		}
	}
	
	public void FillComboBox3() throws SQLException
	{
		PreparedStatement st = null;
		ResultSet rs = null; 
		String sql1 = "select distinct kto from serwisowane";
		
		st = connection.prepareStatement(sql1);
		rs = st.executeQuery();
		
		while(rs.next())
		{
			String Kto = rs.getString("kto");
			comboBox_Serwisant.addItem(Kto);
		}
		
	}

	/**
	 * Create the application.
	 */
	public MachineChoice(Connection connection) {
		
		initialize();
		
		setResizable(false);
		
		this.setTitle("Wybor Maszyny");

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		txtTest = new JTextField();
		txtTest.setHorizontalAlignment(SwingConstants.CENTER);
		txtTest.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtTest.setEditable(false);
		txtTest.setText("WYBOR MASZYNY");
		txtTest.setBounds(28, 11, 256, 20);
		getContentPane().add(txtTest);
		txtTest.setColumns(10);
		
		txtDzia = new JTextField();
		txtDzia.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtDzia.setEditable(false);
		txtDzia.setHorizontalAlignment(SwingConstants.CENTER);
		txtDzia.setText("DZIA\u0141");
		txtDzia.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		
		txtDzia.setBounds(28, 42, 86, 20);
		getContentPane().add(txtDzia);
		txtDzia.setColumns(10);
		
		txtNazwanr = new JTextField();
		txtNazwanr.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtNazwanr.setText("NAZWA(NR)");
		txtNazwanr.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		
		txtNazwanr.setHorizontalAlignment(SwingConstants.CENTER);
		txtNazwanr.setEditable(false);
		txtNazwanr.setColumns(10);
		txtNazwanr.setBounds(28, 83, 86, 20);
		getContentPane().add(txtNazwanr);
		
		comboBox_Dzial = new JComboBox();
		comboBox_Dzial.setBounds(124, 42, 100, 20);
		getContentPane().add(comboBox_Dzial);
		
		 comboBox_Nazwa = new JComboBox();
		comboBox_Nazwa.setBounds(124, 83, 101, 20);
		getContentPane().add(comboBox_Nazwa);
		
		JButton OK_button = new JButton("OK");
		OK_button.setEnabled(false);
		OK_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Service Service;
				try {
					Service = new Service(comboBox_Dzial.getSelectedItem().toString(), comboBox_Nazwa.getSelectedItem().toString());
					Service.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
	
			}
		});
		OK_button.setBounds(28, 123, 256, 23);
		getContentPane().add(OK_button);
		
		JButton ok_dzial = new JButton("OK");
		ok_dzial.setFont(new Font("Tahoma", Font.PLAIN, 10));
		ok_dzial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				selectedValue = comboBox_Dzial.getSelectedItem().toString();
		 		try {
		 			comboBox_Nazwa.removeAllItems();
					FillComboBox2();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		ok_dzial.setBounds(234, 41, 56, 23);
		getContentPane().add(ok_dzial);
		
		JButton ok_nazwa = new JButton("OK");
		ok_nazwa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		ok_nazwa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OK_button.setEnabled(true);
				
			//	Image img = new ImageIcon(this.getClass().getResource("haco_machine_1.png")).getImage();
		//		Machine_photo.setIcon(new ImageIcon(img));

			}
		});
		ok_nazwa.setBounds(235, 82, 55, 23);
		getContentPane().add(ok_nazwa);
		
		textField = new JTextField();
		textField.setBackground(Color.GRAY);
		textField.setForeground(Color.DARK_GRAY);
		textField.setEditable(false);
		textField.setBounds(305, 2, 10, 166);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		txtWyborSerwisanta = new JTextField();
		txtWyborSerwisanta.setText("WYBOR SERWISANTA");
		txtWyborSerwisanta.setHorizontalAlignment(SwingConstants.CENTER);
		txtWyborSerwisanta.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtWyborSerwisanta.setEditable(false);
		txtWyborSerwisanta.setColumns(10);
		txtWyborSerwisanta.setBounds(336, 11, 260, 20);
		getContentPane().add(txtWyborSerwisanta);
		
		comboBox_Serwisant = new JComboBox();
		comboBox_Serwisant.setBounds(430, 42, 166, 20);
		getContentPane().add(comboBox_Serwisant);
		
		txtSerwisant = new JTextField();
		txtSerwisant.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSerwisant.setText("SERWISANT");
		txtSerwisant.setHorizontalAlignment(SwingConstants.CENTER);
		txtSerwisant.setEditable(false);
		txtSerwisant.setBounds(334, 42, 86, 20);
		getContentPane().add(txtSerwisant);
		txtSerwisant.setColumns(10);
		
		JButton OK_button1 = new JButton("OK");
		OK_button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Serwisant_podglad podglad = new Serwisant_podglad(comboBox_Serwisant.getSelectedItem().toString());
				podglad.setVisible(true);
			}
		});
		OK_button1.setBounds(336, 80, 260, 23);
		getContentPane().add(OK_button1);
		
		textField_1 = new JTextField();
		textField_1.setForeground(Color.DARK_GRAY);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBackground(Color.GRAY);
		textField_1.setBounds(28, 169, 568, 10);
		getContentPane().add(textField_1);
		
		txtWyszukiwaniePoFrazie = new JTextField();
		txtWyszukiwaniePoFrazie.setText("WYSZUKIWANIE PO FRAZIE");
		txtWyszukiwaniePoFrazie.setHorizontalAlignment(SwingConstants.CENTER);
		txtWyszukiwaniePoFrazie.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtWyszukiwaniePoFrazie.setEditable(false);
		txtWyszukiwaniePoFrazie.setColumns(10);
		txtWyszukiwaniePoFrazie.setBounds(124, 190, 345, 20);
		getContentPane().add(txtWyszukiwaniePoFrazie);
		
		fraza = new JTextField();
		fraza.setBounds(148, 221, 207, 20);
		getContentPane().add(fraza);
		fraza.setColumns(10);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				pokaz_wyszukiwanie();
				
			}
		});
		btnNewButton.setBounds(365, 219, 89, 23);
		getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 254, 603, 138);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting()) {
					//String Data = "";
					String Kod_maszyny = table.getValueAt(table.getSelectedRow(), 0).toString();
					String Tytul = table.getValueAt(table.getSelectedRow(), 1).toString();
					String Data1 = table.getValueAt(table.getSelectedRow(), 2).toString();	
					String Powod = table.getValueAt(table.getSelectedRow(), 3).toString();
					String Rozwiazanie = table.getValueAt(table.getSelectedRow(), 4).toString();
					String Serwisant = table.getValueAt(table.getSelectedRow(), 5).toString();
					String Sciezka_1 = table.getValueAt(table.getSelectedRow(), 6).toString();
					String Sciezka_2 = table.getValueAt(table.getSelectedRow(), 7).toString();

					Notice_podglad poglad = new Notice_podglad("Nazwa_maszyny", Data1, "Data_serwisu", Tytul, Powod,
							Rozwiazanie, Serwisant, "Wydzial", Kod_maszyny,Sciezka_1,Sciezka_2);
					poglad.setVisible(true);

				}

			}
		});
		
		scrollPane.setViewportView(table);
		
	}
	
	private void pokaz_wyszukiwanie()
	{
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		String data = "select Nr_Maszyny,Tytul,Data_serwisu,Powod,Co_Zrobiono,Kto,Sciezka_1,Sciezka_2 from serwisowane where (Powod like '"+fraza.getText()+"' or Co_Zrobiono like '%"+fraza.getText()+"%' or Tytul like '%"+fraza.getText()+"%' )";
				
		PreparedStatement pst;
		try {

			pst = connection.prepareStatement(data);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
	
			pst.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 1)
				column.setPreferredWidth(170);
			else
				column.setPreferredWidth(20);

		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
