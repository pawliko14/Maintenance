import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

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
	private FileInputStream inputStream;
	
	Connection connection=null;
	private JTextField sciezka;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notice frame = new Notice();
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
	
	
	private void Update() throws SQLException
	{
		connection = MaintenanceConnection.dbConnector("tosia", "1234");
		
		//String query =  "insert into maszyna_1 (Nr_Maszyny, Tytul,`Data`,Powod,Co_Zrobiono,Kto, Zdjecie)\r\n" + 
		//		"values ('Maszyna_1', '"+textField.getText()+"', '2018-10-15', '"+editorPane_1.getText()+"', '"+editorPane.getText()+"', '"+textField_2.getText()+"', '')";
		
		String query = "insert into maszyna_1 (Nr_Maszyny, Tytul,`Data`,Powod,Co_Zrobiono,Kto, Zdjecie) values (?),(?),(?),(?),(?),(?),(?)";
		
		PreparedStatement pst=connection.prepareStatement(query);		
		pst.setString(0, "Maszyna_1");
		pst.setString(1, textField.getText());
		pst.setString(2, "2018-10-15");
		pst.setString(3, editorPane_1.getText());
		pst.setString(4, editorPane.getText());
		pst.setString(5, textField_2.getText());
		pst.setBlob(6, inputStream);

		ResultSet rs=pst.executeQuery();

		pst.close();
		rs.close();

	}
	
	
	public Notice() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 448, 522);
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
		txtMaszynka.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaszynka.setBounds(37, 11, 126, 20);
		contentPane.add(txtMaszynka);
		txtMaszynka.setColumns(10);
		
		JButton Zalacznik_1 = new JButton("Zalacznik 1");
		Zalacznik_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
					try {
						inputStream= new FileInputStream(selectedFile);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(selectedFile.getAbsolutePath());				
					sciezka.setText(selectedFile.getAbsolutePath());				
				}
				
			}
		});
		Zalacznik_1.setBounds(106, 381, 89, 23);
		contentPane.add(Zalacznik_1);
		
		JButton Zapisz = new JButton("Zapisz");
		Zapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println("utworzono nowy rekord");
				try {
					Update();
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
					Notice.this.dispose();		
					Service.Refresh();

					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Zapisz.setBounds(333, 450, 89, 23);
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
		scrollPane.setViewportView(editorPane_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(106, 203, 317, 167);
		contentPane.add(scrollPane_1);
		
		editorPane = new JEditorPane();
		scrollPane_1.setViewportView(editorPane);
		
		textField = new JTextField();
		textField.setBounds(109, 42, 314, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		txtSerwisant = new JTextField();
		txtSerwisant.setEditable(false);
		txtSerwisant.setHorizontalAlignment(SwingConstants.CENTER);
		txtSerwisant.setText("SERWISANT");
		txtSerwisant.setBounds(10, 416, 86, 20);
		contentPane.add(txtSerwisant);
		txtSerwisant.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(106, 415, 186, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(296, 11, 126, 20);
		contentPane.add(textField_1);
		
		sciezka = new JTextField();
		sciezka.setEditable(false);
		sciezka.setBounds(206, 382, 216, 20);
		contentPane.add(sciezka);
		sciezka.setColumns(10);
	}
}
