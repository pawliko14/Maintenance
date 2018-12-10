import java.awt.Color;
import java.awt.EventQueue;

import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField login;
	private JTextField password;
	Connection connection=null;
	private static boolean Admin;
	private static String User;
	private static String Pass;
	
	public static String getPass() {
		return Pass;
	}

	public static void setPass(String pass) {
		Pass = pass;
	}

	public static String getUser() {
		return User;
	}

	public static void setUser(String user) {
		User = user;
	}

	public static boolean getAdmin(){
		return Admin;
	}
	
	public static void setAdmin(boolean admin){
		Admin=admin;
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}

	/**
	 * Create the application.
	 */
	public Login() {
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 335);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		login = new JTextField("Login");
		login.setForeground(Color.gray);
		login.setBounds(81, 183, 253, 20);
		
		login.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (login.getText().equals("Login")) {
		        	login.setText("");
		        	login.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (login.getText().isEmpty()) {
		        	login.setForeground(Color.GRAY);
		        	login.setText("Login");
		        }
		    }
		    });
		
		frame.getContentPane().add(login);
		login.setColumns(10);
		
		password = new JTextField("Password");
		password.setColumns(10);
		password.setBounds(81, 214, 253, 20);
		frame.getContentPane().add(password);
		
		password.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (password.getText().equals("Password")) {
		        	password.setText("");
		        	password.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (password.getText().isEmpty()) {
		        	password.setForeground(Color.GRAY);
		        	password.setText("Password");
		        }
		    }
		    });
		
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("logo.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(147, 11, 131, 161);
		frame.getContentPane().add(label);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NextWindow();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(147, 245, 131, 23);
		frame.getContentPane().add(btnNewButton);
	}
	
	public void NextWindow(){
		try {
			String query="select * from uzytkownicy where Login=? and Haslo=?";
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setString(1, login.getText());
			pst.setString(2, password.getText());
			
			ResultSet rs=pst.executeQuery();
			
			int count=0;
			while(rs.next())
			{
				//count=count+1;
			//	setAdmin(rs.getBoolean("ADMIN"));
				setUser(rs.getString("DBUser"));
				setPass(rs.getString("DBPass"));
				System.out.println(getUser());
				System.out.println(getPass());
				count = 1;
			}
			if(count==1)
			{
				// polaczenie sie na poswiadczeniach Tosi - najprosciej
					rs.close();
					pst.close();
					frame.setVisible(false);
					connection=MaintenanceConnection.dbConnector("tosia", "1234");
					MachineChoice mojeMenu = new MachineChoice(connection);
					mojeMenu.setBounds(100, 100, 630, 430);
					mojeMenu.setVisible(true);
					mojeMenu.FillComboBox1();
					mojeMenu.FillComboBox3();
					System.out.println("admin");

				}
			
			pst.close();
		}catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			
		}
		
		
	}
	
}
