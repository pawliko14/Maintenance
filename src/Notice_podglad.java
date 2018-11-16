import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

public class Notice_podglad extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtSerwisant;
	private JTextField textField_2;
	private JTextField textData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notice_podglad frame = new Notice_podglad("","","","","");
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
	public Notice_podglad(String Data, String Tytul, String Powod, String Rozwiazanie, String Serwisant) {

  

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 515);
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
		txtMaszynka.setBounds(37, 11, 179, 20);
		contentPane.add(txtMaszynka);
		txtMaszynka.setColumns(10);
		
		JButton Zalacznik_1 = new JButton("Zalacznik 1");
		Zalacznik_1.setEnabled(false);
		Zalacznik_1.setBounds(106, 381, 89, 23);
		contentPane.add(Zalacznik_1);
		
		JButton btnNewButton_1 = new JButton("Zapisz");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(336, 443, 89, 23);
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
		editorPane_1.setEditable(false);	
		editorPane_1.setText(Powod);
		
		scrollPane.setViewportView(editorPane_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(106, 201, 317, 176);
		contentPane.add(scrollPane_1);
		
		JEditorPane editorPane = new JEditorPane();
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
		txtSerwisant.setBounds(10, 420, 86, 20);
		contentPane.add(txtSerwisant);
		txtSerwisant.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(106, 420, 188, 20);
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
	
	}
}
