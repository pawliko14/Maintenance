import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.SystemColor;

public class Notice_podglad extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtSerwisant;
	private JTextField textField_2;
	private JTextField textData;
	private JTextField txtDataSerwisu;
	private JTextField Data_serwisu;
	private JTextField sciezka_1;
	private JTextField sciezka_2;
	Connection connection=null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notice_podglad frame = new Notice_podglad("","","","","","","","","","","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void Otworz_png(String Kod_maszyny, String Dzial) throws IOException
	{
		// TRZEBA WYKMINIC JAKI PLIK
		// jaka strukture zrobic, czy przy kazdym wywolaniu sprawdzac czy istnieje taki podfolder
		// TRZBA ZROBIC JAKAS METODA REKURENCYJNA, ORAZ SPRAWDDZIC JAK ZAPISAC DOKLADNE
		// ZDJECIE/ PNG/ WAV ITP ZEBY OTWIERALO SIE Z AUTOMATU
		
		File file = new File(Parameters.getPathToMultimedia());
		boolean exists = file.exists();
		
		if(!exists)
		{
			System.out.println("file nie istnieje + nazwa maszyny:"+Dzial);
		}
		else
		{
			System.out.println("istnieje+ nazwa maszyny:" +Dzial);
			String Dzial_skrocone_pierwsze = Dzial.substring(0,2);  // wyciaga ze stringa tylko 2 pierwsze indexy
			String Dzial_skrocone_drugie = Dzial.substring(3,Dzial.length());
			
			File file2 = new File(Parameters.getPathToMultimedia() + "/" + Dzial_skrocone_pierwsze);
			boolean exists2 = file2.exists();
			
				if(!exists2)
					System.out.println("file nie istnieje :"+ file2.getAbsolutePath());

				else {
					System.out.println("istnieje" + file2.getAbsolutePath());
					
					
						File file3 = new File(Parameters.getPathToMultimedia() + "/" + Dzial_skrocone_pierwsze + "/" + Dzial_skrocone_drugie);
						boolean exists3 = file3.exists();
						
							if(!exists3)
								System.out.println("file nie istnieje :"+ file3.getAbsolutePath());
	
							else {
								System.out.println("istnieje" + file3.getAbsolutePath());
								
									File file4 = new File(Parameters.getPathToMultimedia() + "/" + Dzial_skrocone_pierwsze + "/" + Dzial_skrocone_drugie + "/"+ Kod_maszyny);
									boolean exists4 = file4.exists();
									
										if(!exists4)
											System.out.println("FINALNY nie istnieje :"+ file4.getAbsolutePath());
				
										else {
											System.out.println("FINALNY istnieje" + file4.getAbsolutePath());
											
											File imageFile = new File(file4.getAbsolutePath() + "/java.png");
											BufferedImage image = ImageIO.read(imageFile);

											DisplayImage(image);
											
										}
								
							}
						
				}

			

		}
		
	}

	private void DisplayImage(BufferedImage img) throws IOException
    {
    
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.getContentPane().add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


	/**
	 * Create the frame.
	 */
	public Notice_podglad(String Nazwa_maszyny,String Data,String Data_serwisu1, String Tytul, String Powod, String Rozwiazanie, String Serwisant, String Dzial,String Kod_maszyny,String sciezka_do_zdj1, String sciezka_do_zdj2) {

		this.setTitle("Podglad Raportu");
		

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextField txtPowod = new JTextField();
		txtPowod.setEditable(false);
		txtPowod.setText("POWOD");
		txtPowod.setHorizontalAlignment(SwingConstants.CENTER);
		txtPowod.setBounds(10, 83, 86, 20);
		contentPane.add(txtPowod);
		txtPowod.setColumns(10);
		
		JTextField txtRozwiazanie = new JTextField();
		txtRozwiazanie.setEditable(false);
		txtRozwiazanie.setHorizontalAlignment(SwingConstants.CENTER);
		txtRozwiazanie.setText("ROZWIAZANIE");
		txtRozwiazanie.setColumns(10);
		txtRozwiazanie.setBounds(10, 201, 86, 20);
		contentPane.add(txtRozwiazanie);
		
		JTextField txtMaszynka = new JTextField();
		txtMaszynka.setEditable(false);
		txtMaszynka.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaszynka.setBounds(37, 11, 257, 20);
		txtMaszynka.setText(Nazwa_maszyny);
		contentPane.add(txtMaszynka);
		txtMaszynka.setColumns(10);
		
		JButton Zalacznik_1 = new JButton("Zalacznik 1");
		Zalacznik_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int pierwszy_znak = licz_znak_1(sciezka_1.getText());
				String subst = sciezka_2.getText().substring(pierwszy_znak, sciezka_2.getText().length());
				
				// jesli jest to plik z rozszerzeniem filmowym, otworz film, jesli nie, to ELSE
				if(subst.equals("mp3") || subst.equals("mp4")  || subst.equals("avi") || subst.equals("3gp"))
				{
					try {
						Desktop.getDesktop().open(new File(sciezka_2.getText()));
					} catch (IOException e) {
				        JOptionPane.showMessageDialog(null, "Brak pliku o podanej sciezce" );
						e.printStackTrace();
					}
				}
				else if(subst.equals("pdf"))
				{
					if (Desktop.isDesktopSupported()) {
					    try {
					        File myFile = new File(sciezka_2.getText());
					        Desktop.getDesktop().open(myFile);
					    } catch (IOException ex) {
					        // no application registered for PDFs
					    }
					}
				}
				
				else
				{
				
					try {
						File file4 = new File(sciezka_1.getText());
						File imageFile = new File(file4.getAbsolutePath());
						BufferedImage image = ImageIO.read(imageFile);
	
						DisplayImage(image);
					} catch (IOException e) {
				        JOptionPane.showMessageDialog(null, "Brak pliku o podanej sciezce");
						e.printStackTrace();
					}
				}
			}
		});
		Zalacznik_1.setBounds(7, 381, 89, 23);
		contentPane.add(Zalacznik_1);
		
		JButton btnNewButton_1 = new JButton("Zapisz");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(336, 498, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JTextField txtTytul = new JTextField();
		txtTytul.setEditable(false);
		txtTytul.setHorizontalAlignment(SwingConstants.CENTER);
		txtTytul.setText("TYTUL");
		txtTytul.setBounds(10, 42, 86, 20);
		contentPane.add(txtTytul);
		txtTytul.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(106, 83, 317, 110);
		contentPane.add(scrollPane);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBackground(SystemColor.control);
		editorPane_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		editorPane_1.setForeground(Color.BLACK);
		editorPane_1.setEnabled(true);
		editorPane_1.setEditable(false);	
		editorPane_1.setText(Powod);
		
		scrollPane.setViewportView(editorPane_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(106, 201, 317, 176);
		contentPane.add(scrollPane_1);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBackground(SystemColor.control);
		editorPane.setForeground(Color.BLACK);
		editorPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		editorPane.setEnabled(true);
		editorPane.setEditable(false);
		editorPane.setText(Rozwiazanie);
		
		scrollPane_1.setViewportView(editorPane);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(109, 42, 314, 20);
		textField.setText(Tytul);
		
		contentPane.add(textField);
		textField.setColumns(10);
		
		txtSerwisant = new JTextField();
		txtSerwisant.setEditable(false);
		txtSerwisant.setHorizontalAlignment(SwingConstants.CENTER);
		txtSerwisant.setText("SERWISANT");
		txtSerwisant.setBounds(10, 462, 86, 20);
		contentPane.add(txtSerwisant);
		txtSerwisant.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(106, 462, 188, 20);
		textField_2.setText(Serwisant);
		
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textData = new JTextField();
		textData.setHorizontalAlignment(SwingConstants.CENTER);
		textData.setEditable(false);
		textData.setColumns(10);
		textData.setBounds(312, 11, 113, 20);
		textData.setText(Data);
		contentPane.add(textData);
		
		txtDataSerwisu = new JTextField();
		txtDataSerwisu.setHorizontalAlignment(SwingConstants.CENTER);
		txtDataSerwisu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtDataSerwisu.setEditable(false);
		txtDataSerwisu.setText("DATA SERWISU");
		txtDataSerwisu.setBounds(10, 498, 86, 20);
		contentPane.add(txtDataSerwisu);
		txtDataSerwisu.setColumns(10);
		
		Data_serwisu = new JTextField();
		Data_serwisu.setEditable(false);
		Data_serwisu.setHorizontalAlignment(SwingConstants.CENTER);
		Data_serwisu.setBounds(106, 499, 116, 20);
		Data_serwisu.setText(Data_serwisu1);
		contentPane.add(Data_serwisu);
		Data_serwisu.setColumns(10);
		
		JButton Zalacznik_2 = new JButton("Zalacznik 2");
		Zalacznik_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int pierwszy_znak = licz_znak_1(sciezka_2.getText());
				String subst = sciezka_2.getText().substring(pierwszy_znak, sciezka_2.getText().length());
				
				// jesli jest to plik z rozszerzeniem filmowym, otworz film, jesli nie, to ELSE
				if(subst.equals("mp3") || subst.equals("mp4")  || subst.equals("avi") || subst.equals("3gp"))
				{
					try {
						Desktop.getDesktop().open(new File(sciezka_2.getText()));
					} catch (IOException e) {
				        JOptionPane.showMessageDialog(null, "Brak pliku o podanej sciezce");
						e.printStackTrace();
					}
				}
				else if(subst.equals("pdf"))
				{
					if (Desktop.isDesktopSupported()) {
					    try {
					        File myFile = new File(sciezka_2.getText());
					        Desktop.getDesktop().open(myFile);
					    } catch (IOException ex) {
					        // no application registered for PDFs
					    }
					}
				}
				
				else
				{
				
					try {
						File file4 = new File(sciezka_2.getText());
						File imageFile = new File(file4.getAbsolutePath());
						BufferedImage image = ImageIO.read(imageFile);
	
						DisplayImage(image);
					} catch (IOException e) {
				        JOptionPane.showMessageDialog(null, "Brak pliku o podanej sciezce");
						e.printStackTrace();
					}
				}
				
			}
		});
		Zalacznik_2.setBounds(7, 415, 89, 23);
		contentPane.add(Zalacznik_2);
		
		sciezka_1 = new JTextField();
		sciezka_1.setEditable(false);
		sciezka_1.setBounds(106, 382, 307, 20);
		sciezka_1.setText(sciezka_do_zdj1);
		contentPane.add(sciezka_1);
		sciezka_1.setColumns(10);
		
		sciezka_2 = new JTextField();
		sciezka_2.setEditable(false);
		sciezka_2.setColumns(10);
		sciezka_2.setBounds(109, 416, 304, 20);
		sciezka_2.setText(sciezka_do_zdj2);
		contentPane.add(sciezka_2);
		
		JButton usun_btn = new JButton("Usun");
		usun_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					usun(Nazwa_maszyny, Data, Data_serwisu1,  Tytul,  Powod,  Rozwiazanie,  Serwisant,  Dzial, Kod_maszyny);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  // usuniecie aktualnie podgladanej notakti
			}
		});
		usun_btn.setFont(new Font("Tahoma", Font.BOLD, 13));
		usun_btn.setBounds(336, 464, 89, 23);
		contentPane.add(usun_btn);
	
	}
	
	private void usun(String Nazwa_maszyny,String Data,String Data_serwisu1, String Tytul, String Powod, String Rozwiazanie, String Serwisant, String Dzial,String Kod_maszyny ) throws SQLException
	{
		
		System.out.println("dane: " +Nazwa_maszyny + Data + Data_serwisu1 + Tytul + Powod + Rozwiazanie + Serwisant + Dzial +"kod: "+ Kod_maszyny);
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		try {
			String query = "delete from serwisowane where (Nr_maszyny = '"+Kod_maszyny+"' and Tytul = '"+Tytul+"' and `Data` = '"+Data+"' and Data_serwisu = '"+Data_serwisu1+"' and Powod = '"+Powod+"' and Co_Zrobiono = '"+Rozwiazanie+"' and Kto = '"+Serwisant+"')";
			PreparedStatement pst=connection.prepareStatement(query);
				pst.execute();
				pst.close();
				JOptionPane.showMessageDialog(null,"Usunieto notatke");
				

			}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			
		}
		this.dispose();
		Service.Refresh();
	}
	
	
	 private int licz_znak_1(String sciezka)
	 {
		 int l_znakow = sciezka.length();
		 int licznik = 0;
		 
		 
		 int i=0;
		 for(i = l_znakow; i > 0; i--)
		 {
			 char a_char = sciezka.charAt(i-1);
			 	if(a_char == '.')
			 	{ 		
			 		break;
			 	}
		 }
		 return i;
		 
	 }
}
