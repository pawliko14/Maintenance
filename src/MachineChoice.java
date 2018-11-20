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
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MachineChoice extends JFrame implements WindowListener  {

	private JFrame frame;
	private JTextField txtTest;
	private JTextField txtDzia;
	private JTextField txtNazwanr;
	private JComboBox comboBox_Dzial;
	private JComboBox comboBox_Nazwa;
	private String selectedValue;
	
	static Connection connection = MaintenanceConnection.dbConnector("tosia", "1234");

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

	/**
	 * Create the application.
	 */
	public MachineChoice(Connection connection) {
		
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
		
		JLabel Machine_photo = new JLabel("");
		Machine_photo.setHorizontalAlignment(SwingConstants.CENTER);
		Machine_photo.setBounds(294, 11, 212, 135);
		getContentPane().add(Machine_photo);
		
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
				
				Image img = new ImageIcon(this.getClass().getResource("haco_machine_1.png")).getImage();
				Machine_photo.setIcon(new ImageIcon(img));

			}
		});
		ok_nazwa.setBounds(235, 82, 55, 23);
		getContentPane().add(ok_nazwa);
		initialize();
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
