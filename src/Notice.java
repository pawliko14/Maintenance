import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

///import org.jfree.util.Log;

//import com.itextpdf.kernel.log.SystemOutCounter;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Notice extends JFrame {

	private JPanel contentPane;
	private JTextField txtPowod;
	private JTextField txtRozwiazanie;
	private JTextField txtMaszynka;
	private JTextField txtTytul;
	private JTextField textField;
	private JTextField txtSerwisant;
	private JTextField textField_2;
	private JTextField textField_1;
	private JEditorPane editorPane_1;
	private JEditorPane editorPane;
	
	private File selectedFile;
	private File selectedFile_2;
	private FileInputStream inputStream;
	
	Connection connection=null;
	private JTextField sciezka;
	private JTextField textField_3;
	private JTextField Data_serwisu;
	private JTextField sciezka_2;
	
	private String Nazwa_pliku_do_bazy;
	private String Nazwa_pliku_do_bazy2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					Notice frame = new Notice("","","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	private static String GenerujCzas()
	{
		Date now = new Date();
	    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	    String datka = dateFormatter.format(now);
		
		return datka;
	}
	
	private void Update(String Nazwa_maszyny,String Dzial, String Nr_maszyny) throws SQLException, IOException
	{
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		PreparedStatement pst = null;
		
		
		// PIERWSZA MOZLIWOSC, KIEDY  OBIE SCIEZKI SA PUSTE, - PLKIKI NIE ZOSTALY WYBRANE
		// ZAPISUJE WTEDY DO BAZY PUSTE POLA ( W MIEJSCU SCIEZEK)
		
		// dziala dobrze
		if(sciezka.getText().equals("Sciezka do zalacznika nr.1") && sciezka_2.getText().equals("Sciezka do zalacznika nr.2"))
		{
			System.out.println("obie sciezki sa puste");
			
			String query = "insert into serwisowane (Nr_Maszyny, Tytul,`Data`,Data_serwisu,Powod,Co_Zrobiono,Kto, Zdjecie,Sciezka_1,Sciezka_2) values (?,?,?,?,?,?,?,'',?,?)";
				pst=connection.prepareStatement(query);		
				
				pst.setString(1, Nazwa_maszyny);
				pst.setString(2, textField.getText());
				pst.setString(3, GenerujCzas());
				pst.setString(4, Data_serwisu.getText()); // automatycznie wygenerowany czas
				pst.setString(5, editorPane_1.getText());
				pst.setString(6, editorPane.getText());
				pst.setString(7, textField_2.getText());
				pst.setString(8, "");
				pst.setString(9, "");
		
				ResultSet rs=pst.executeQuery();
				pst.close();
				rs.close();
				
		}
		// kiedy obie sciezki sa wypelnione, 
		// czyli kiedy pojawiaja sie oba zalaczniki
		// oraz kiedy nie sa to sciezki z pierwszego scenariusza
		else if(!sciezka.getText().equals("") && !sciezka_2.getText().equals("") && !sciezka.getText().equals("Sciezka do zalacznika nr.1") && !sciezka_2.getText().equals("Sciezka do zalacznika nr.2"))
		{
			System.out.println("obie sciezki sa w uzyciu");
		//	Nazwa_pliku_do_bazy = sciezka.getText();
			int pierwzy_znak = licz_znak_1(sciezka.getText());
			Nazwa_pliku_do_bazy = sciezka.getText().substring(pierwzy_znak, sciezka.getText().length());
			Nazwa_pliku_do_bazy2 = sciezka_2.getText().substring(pierwzy_znak, sciezka_2.getText().length());
		
			String query = "insert into serwisowane (Nr_Maszyny, Tytul,`Data`,Data_serwisu,Powod,Co_Zrobiono,Kto, Zdjecie,Sciezka_1,Sciezka_2) values (?,?,?,?,?,?,?,'',?,?)";
					
				pst=connection.prepareStatement(query);		
				pst.setString(1, Nazwa_maszyny);
				pst.setString(2, textField.getText());
				pst.setString(3, Data_serwisu.getText());
				pst.setString(4, GenerujCzas());
				pst.setString(5, editorPane_1.getText());
				pst.setString(6, editorPane.getText());
				pst.setString(7, textField_2.getText());
				pst.setString(8, Sciezka_do_multimediow(Dzial,Nr_maszyny,Nazwa_pliku_do_bazy,selectedFile).toString());  // Sciezka_do_multimediow(Dzial,Nr_maszyny) <- to powinno byc zapisane ale brak dzial i nr maszyny
				pst.setString(9, Sciezka_do_multimediow(Dzial,Nr_maszyny,Nazwa_pliku_do_bazy2,selectedFile_2).toString());
				
				ResultSet rs=pst.executeQuery();
				pst.close();
				rs.close();
		}	
		
		// kiedy tylko DRUGA sciezka  jest zajeta, a pierwsza 'pusta'
		// czyli kiedy tylko drugi zalacznik
		else if(sciezka.getText().equals("Sciezka do zalacznika nr.1") && !sciezka_2.getText().equals("Sciezka do zalacznika nr.2"))
		{
			System.out.println("tylko druga sciezka w uzyciu");
			//	Nazwa_pliku_do_bazy = sciezka.getText();
				int pierwzy_znak = licz_znak_1(sciezka_2.getText());
				Nazwa_pliku_do_bazy2 = sciezka_2.getText().substring(pierwzy_znak, sciezka_2.getText().length());
	
				String query = "insert into serwisowane (Nr_Maszyny, Tytul,`Data`,Data_serwisu,Powod,Co_Zrobiono,Kto, Zdjecie,Sciezka_1,Sciezka_2) values (?,?,?,?,?,?,?,'','',?)";
				
				
					pst=connection.prepareStatement(query);		
					pst.setString(1, Nazwa_maszyny);
					pst.setString(2, textField.getText());
					pst.setString(3, Data_serwisu.getText());
					pst.setString(4, GenerujCzas());
					pst.setString(5, editorPane_1.getText());
					pst.setString(6, editorPane.getText());
					pst.setString(7, textField_2.getText());
					pst.setString(8, Sciezka_do_multimediow(Dzial,Nr_maszyny,Nazwa_pliku_do_bazy2,selectedFile_2).toString());  // Sciezka_do_multimediow(Dzial,Nr_maszyny) <- to powinno byc zapisane ale brak dzial i nr maszyny
					

					ResultSet rs=pst.executeQuery();
					pst.close();
					rs.close();
				
		}
		
		
		// kiedy tylko pierwsza sciezka  jest zajeta, a DRUGA 'pusta'
				// czyli kiedy tylko PIERWSZY zalacznik
				else if(!sciezka.getText().equals("Sciezka do zalacznika nr.1") && sciezka_2.getText().equals("Sciezka do zalacznika nr.2"))
				{
					System.out.println("tylko pierwsza sciezka w uzyciu");
					//	Nazwa_pliku_do_bazy = sciezka.getText();
						int pierwzy_znak = licz_znak_1(sciezka.getText());
						Nazwa_pliku_do_bazy = sciezka.getText().substring(pierwzy_znak, sciezka.getText().length());

						String query = "insert into serwisowane (Nr_Maszyny, Tytul,`Data`,Data_serwisu,Powod,Co_Zrobiono,Kto, Zdjecie,Sciezka_1,Sciezka_2) values (?,?,?,?,?,?,?,'',?,'')";
					
							pst=connection.prepareStatement(query);		
							pst.setString(1, Nazwa_maszyny);
							pst.setString(2, textField.getText());
							pst.setString(3, Data_serwisu.getText());
							pst.setString(4, GenerujCzas());
							pst.setString(5, editorPane_1.getText());
							pst.setString(6, editorPane.getText());
							pst.setString(7, textField_2.getText());
							//  TO NIE BEDZIE DZIALAC, ALE NA RAZIE TESTOWO
							pst.setString(8, Sciezka_do_multimediow(Dzial,Nr_maszyny,Nazwa_pliku_do_bazy,selectedFile).toString());  // Sciezka_do_multimediow(Dzial,Nr_maszyny) <- to powinno byc zapisane ale brak dzial i nr maszyny
							

							ResultSet rs=pst.executeQuery();
							pst.close();
							rs.close();
				}
		else 
		{
			System.out.println("Jakis problem, sprawdz");
		}	
	}
	
	
	public Notice(String Nazwa_maszyny,String Dzial,String Nr_maszyny) {
		
		this.setTitle("Tworzenie Raportu");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPowod = new JTextField();
		txtPowod.setEditable(false);
		txtPowod.setText("POWOD");
		txtPowod.setHorizontalAlignment(SwingConstants.CENTER);
		txtPowod.setBounds(10, 83, 86, 20);
		contentPane.add(txtPowod);
		txtPowod.setColumns(10);
		
		txtRozwiazanie = new JTextField();
		txtRozwiazanie.setEditable(false);
		txtRozwiazanie.setHorizontalAlignment(SwingConstants.CENTER);
		txtRozwiazanie.setText("ROZWIAZANIE");
		txtRozwiazanie.setColumns(10);
		txtRozwiazanie.setBounds(10, 201, 86, 20);
		contentPane.add(txtRozwiazanie);
		
		txtMaszynka = new JTextField();
		txtMaszynka.setEditable(false);
		txtMaszynka.setText(Nr_maszyny);
		txtMaszynka.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaszynka.setBounds(37, 11, 249, 20);
		contentPane.add(txtMaszynka);
		txtMaszynka.setColumns(10);
		
		JButton Zalacznik_1 = new JButton("Zalacznik 1");
		Zalacznik_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Zalacznik_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
					
					System.out.println(selectedFile.getAbsolutePath());				
					sciezka.setText(selectedFile.getAbsolutePath());				
				}
				
			}
		});
		Zalacznik_1.setBounds(10, 381, 89, 23);
		contentPane.add(Zalacznik_1);
		
		JButton Zapisz = new JButton("Zapisz");
		Zapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int pierwszy_znak = licz_znak_1(sciezka.getText());    // wyciaganie pierwszego '/' ze stringa ( w sensie ostaneigo)
					Nazwa_pliku_do_bazy = sciezka.getText().substring(pierwszy_znak, sciezka.getText().length());
					Nazwa_pliku_do_bazy2 = sciezka_2.getText().substring(pierwszy_znak, sciezka_2.getText().length());
					
					if(textField.getText().equals("Skrocony opis raportu") || editorPane_1.getText().equals("Co zostalo zrobione / zdiagnozowane") || editorPane.getText().equals("Powod serwisu") || Data_serwisu.getText().equals("2018-11-24")) {
						String st = "Pola nie sa wypelnione";
						JOptionPane.showMessageDialog(null, st);
					}
					else
					{
						Update(Nazwa_maszyny,Dzial,Nr_maszyny);
						Notice.this.dispose();		
						System.out.println("utworzono nowy rekord");

					}	
					Service.Refresh();
					//Service.Fill();
		
					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "niewykryty blad, dzwon do Konrada");
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "niewykryty blad, dzwon do Konrada");
					e.printStackTrace();
				}
			}
		});
		Zapisz.setBounds(335, 468, 89, 23);
		contentPane.add(Zapisz);
		
		txtTytul = new JTextField();
		txtTytul.setEditable(false);
		txtTytul.setHorizontalAlignment(SwingConstants.CENTER);
		txtTytul.setText("TYTUL");
		txtTytul.setBounds(10, 42, 86, 20);
		contentPane.add(txtTytul);
		txtTytul.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(106, 83, 317, 107);
		contentPane.add(scrollPane);
		
		editorPane_1 = new JEditorPane();
		editorPane_1.setText("Powod serwisu");
		editorPane_1.setForeground(Color.gray);
		scrollPane.setViewportView(editorPane_1);
		
		
		editorPane_1.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (editorPane_1.getText().equals("Powod serwisu")) {
		        	editorPane_1.setText("");
		        	editorPane_1.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (editorPane_1.getText().isEmpty()) {
		        	editorPane_1.setForeground(Color.GRAY);
		        	editorPane_1.setText("Powod serwisu");
		        }
		    }
		    });
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(106, 203, 317, 167);
		contentPane.add(scrollPane_1);
		
		editorPane = new JEditorPane();
		editorPane.setText("Co zostalo zrobione / zdiagnozowane");
		editorPane.setForeground(Color.gray);
		scrollPane_1.setViewportView(editorPane);
		
		editorPane.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (editorPane.getText().equals("Co zostalo zrobione / zdiagnozowane")) {
		        	editorPane.setText("");
		        	editorPane.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (editorPane.getText().isEmpty()) {
		        	editorPane.setForeground(Color.GRAY);
		        	editorPane.setText("Co zostalo zrobione/zdiagnozowane");
		        }
		    }
		    });
		
		
		
		textField = new JTextField("Skrocony opis raportu");
		textField.setForeground(Color.gray);
		textField.setBounds(109, 42, 314, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		textField.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (textField.getText().equals("Skrocony opis raportu")) {
		        	textField.setText("");
		        	textField.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (textField.getText().isEmpty()) {
		        	textField.setForeground(Color.GRAY);
		        	textField.setText("Skrocony opis raportu");
		        }
		    }
		    });
		
		
		txtSerwisant = new JTextField();
		txtSerwisant.setEditable(false);
		txtSerwisant.setHorizontalAlignment(SwingConstants.CENTER);
		txtSerwisant.setText("SERWISANT");
		txtSerwisant.setBounds(10, 471, 86, 20);
		contentPane.add(txtSerwisant);
		txtSerwisant.setColumns(10);
		
		textField_2 = new JTextField("Imie Nazwisko");
		textField_2.setForeground(Color.gray);
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(109, 471, 186, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_2.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (textField_2.getText().equals("Imie Nazwisko")) {
		        	textField_2.setText("");
		        	textField_2.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (textField_2.getText().isEmpty()) {
		        	textField_2.setForeground(Color.GRAY);
		        	textField_2.setText("Imie Nazwisko");
		        }
		    }
		    });
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(296, 11, 126, 20);
		contentPane.add(textField_1);
		
		sciezka = new JTextField();
		sciezka.setText("Sciezka do zalacznika nr.1");
		sciezka.setEditable(false);
		sciezka.setBounds(109, 382, 313, 20);
		contentPane.add(sciezka);
		sciezka.setColumns(10);
		
		
		sciezka.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (sciezka.getText().equals("Sciezka do zalacznika nr.1")) {
		        	sciezka.setText("");
		        	sciezka.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (sciezka.getText().isEmpty()) {
		        	sciezka.setForeground(Color.GRAY);
		        	sciezka.setText("Sciezka do zalacznika nr.1");
		        }
		    }
		    });
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_3.setText("DATA SERWISU");
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(10, 502, 86, 20);
		contentPane.add(textField_3);
		
		Data_serwisu = new JTextField("2018-11-24");
		Data_serwisu.setForeground(Color.gray);
		Data_serwisu.setHorizontalAlignment(SwingConstants.CENTER);
		Data_serwisu.setColumns(10);
		Data_serwisu.setBounds(111, 501, 113, 20);
		contentPane.add(Data_serwisu);
		
		
		JButton Zalacznik_2 = new JButton("Zalacznik 2");
		Zalacznik_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Zalacznik_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile_2 = jfc.getSelectedFile();
					
					sciezka_2.setText(selectedFile_2.getAbsolutePath());				
				}
				
				
			}
		});
		Zalacznik_2.setBounds(10, 415, 89, 23);
		contentPane.add(Zalacznik_2);
		
		sciezka_2 = new JTextField();
		sciezka_2.setText("Sciezka do zalacznika nr.2");
		sciezka_2.setEditable(false);
		sciezka_2.setColumns(10);
		sciezka_2.setBounds(109, 416, 315, 20);
		contentPane.add(sciezka_2);
		
		sciezka_2.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (sciezka_2.getText().equals("Sciezka do zalacznika nr.2")) {
		        	sciezka_2.setText("");
		        	sciezka_2.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (sciezka_2.getText().isEmpty()) {
		        	sciezka_2.setForeground(Color.GRAY);
		        	sciezka_2.setText("Sciezka do zalacznika nr.2");
		        }
		    }
		    });
		
		
		Data_serwisu.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (Data_serwisu.getText().equals("2018-11-24")) {
		        	Data_serwisu.setText("");
		        	Data_serwisu.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (Data_serwisu.getText().isEmpty()) {
		        	Data_serwisu.setForeground(Color.GRAY);
		        	Data_serwisu.setText("2018-11-24");
		        }
		    }
		    });
	}
	
	private File Sciezka_do_multimediow(String Dzial, String Kod_maszyny, String nazwa_pliku, File selected) throws IOException
	{
		
		System.out.println("istnieje+ nazwa maszyny:" + Dzial);
		String Dzial_skrocone_pierwsze = Dzial.substring(0,2);  // wyciaga ze stringa tylko 2 pierwsze indexy
		String Dzial_skrocone_drugie = Dzial.substring(3,Dzial.length());
		
		File file4 = new File(Parameters.getPathToMultimedia() + "/" + Dzial_skrocone_pierwsze + "/" + Dzial_skrocone_drugie + "/"+ Kod_maszyny + "/" + nazwa_pliku );
		boolean exists4 = file4.exists();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String data = dateFormat.format(date); //2013/10/15 16:16:39
		
		if(!exists4)
			System.out.println("FINALNY nie istnieje :"+ file4.getAbsolutePath());

		else {
			System.out.println("FINALNY istnieje, plik zostanie zapisany z inna nazwa: " + file4.getAbsolutePath());
			file4 = new File(Parameters.getPathToMultimedia() + "/" + Dzial_skrocone_pierwsze + "/" + Dzial_skrocone_drugie + "/"+ Kod_maszyny + "/"+ data +"-"+ nazwa_pliku );
			copyFileUsingJava7Files(selected,file4);
			 return file4;
		}

		System.out.println("sciezka: " + file4.getAbsolutePath());
		copyFileUsingJava7Files(selected,file4);
		
		return file4;
	}
	

	 private static void copyFileUsingJava7Files(File source, File dest) throws IOException 
	   {
		 int pierwszy_znak = licz_znak_1(dest.toString());
		 String dlugosc = dest.toString().substring(0, pierwszy_znak-1);
		 
		 File sciezka_bez_koncowki = new File(dlugosc);
		 
		 if(sciezka_bez_koncowki.exists())
		    Files.copy(source.toPath(), dest.toPath());
		 
		 else {
			 sciezka_bez_koncowki.mkdirs();
			 Files.copy(source.toPath(), dest.toPath());
		 }
		 
		}
	 
	 private static int licz_znak_1(String sciezka)
	 {
		 int l_znakow = sciezka.length();		 
		 int i=0;
		 for(i = l_znakow; i > 0; i--)
		 {
			 char a_char = sciezka.charAt(i-1);
			 	if(a_char == '\\')
			 	{ 		
					 System.out.println("liczba znakow PO:" + i);
			 		break;
			 	}
		 }
		 return i;
		 
	 }
	 

}
