package UI;
import java.awt.Dimension;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Resource_Page {

	private JFrame frmResource;
	private JTextField txtIDField;
	private JTextField txtNameField;
	private JTextField txtAmountField;
	private JTextField txtGeneric;
	private JTextField txtDosage;
	private JTextField txtBrand;
	private JTextField txtDrug;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Resource_Page window = new Resource_Page();
					window.frmResource.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Resource_Page() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frmResource = new JFrame();
		frmResource.setBounds(0, 0, width, height);
		frmResource.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmResource.getContentPane().setLayout(null);
		frmResource.setVisible(true);
		frmResource.getContentPane().setBackground(new Color(135, 206, 250));

		
		JTextArea txtRs = new JTextArea();
		txtRs.setBounds(43, 24, 824, 653);
		frmResource.getContentPane().add(txtRs);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnInsert.setBounds(1099, 387, 100, 33);
		frmResource.getContentPane().add(btnInsert);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnSearch.setBounds(1215, 387, 100, 33);
		frmResource.getContentPane().add(btnSearch);
		
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblID.setBounds(915, 39, 51, 25);
		frmResource.getContentPane().add(lblID);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblName.setBounds(915, 74, 51, 25);
		frmResource.getContentPane().add(lblName);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblAmount.setBounds(915, 109, 77, 33);
		frmResource.getContentPane().add(lblAmount);
		
		JLabel lblCalculationUnit = new JLabel("Calculation Unit");
		lblCalculationUnit.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblCalculationUnit.setBounds(915, 152, 153, 25);
		frmResource.getContentPane().add(lblCalculationUnit);
		
		JLabel lblGenericName = new JLabel("Generic name");
		lblGenericName.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblGenericName.setBounds(915, 193, 153, 25);
		frmResource.getContentPane().add(lblGenericName);
		
		JLabel lblDosageForm = new JLabel("Dosage Form");
		lblDosageForm.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblDosageForm.setBounds(915, 229, 137, 25);
		frmResource.getContentPane().add(lblDosageForm);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblBrand.setBounds(915, 272, 51, 25);
		frmResource.getContentPane().add(lblBrand);
		
		JLabel lblDrugClass = new JLabel("Drug class");
		lblDrugClass.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblDrugClass.setBounds(915, 307, 121, 25);
		frmResource.getContentPane().add(lblDrugClass);
		
		txtIDField = new JTextField();
		txtIDField.setBounds(1099, 46, 216, 19);
		frmResource.getContentPane().add(txtIDField);
		txtIDField.setColumns(10);
		
		txtNameField = new JTextField();
		txtNameField.setColumns(10);
		txtNameField.setBounds(1099, 81, 216, 19);
		frmResource.getContentPane().add(txtNameField);
		
		txtAmountField = new JTextField();
		txtAmountField.setColumns(10);
		txtAmountField.setBounds(1099, 120, 216, 19);
		frmResource.getContentPane().add(txtAmountField);
		
		txtGeneric = new JTextField();
		txtGeneric.setColumns(10);
		txtGeneric.setBounds(1099, 199, 216, 19);
		frmResource.getContentPane().add(txtGeneric);
		
		txtDosage = new JTextField();
		txtDosage.setColumns(10);
		txtDosage.setBounds(1099, 235, 216, 19);
		frmResource.getContentPane().add(txtDosage);
		
		txtBrand = new JTextField();
		txtBrand.setColumns(10);
		txtBrand.setBounds(1099, 274, 216, 19);
		frmResource.getContentPane().add(txtBrand);
		
		txtDrug = new JTextField();
		txtDrug.setColumns(10);
		txtDrug.setBounds(1099, 314, 216, 19);
		frmResource.getContentPane().add(txtDrug);
		
		String[] list = {"Gram", "Liter"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox comboBox = new JComboBox(list);
		comboBox.setBounds(1099, 158, 83, 21);
		frmResource.getContentPane().add(comboBox);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalPool_Menu global = new GlobalPool_Menu();
				global.getClass();
				frmResource.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnBack.setBounds(43, 708, 85, 21);
		frmResource.getContentPane().add(btnBack);
	}
}
