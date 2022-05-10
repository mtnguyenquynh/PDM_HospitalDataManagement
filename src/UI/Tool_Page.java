package UI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tool_Page {

	private JFrame frmTool;
	private JTextField idField;
	private JTextField nameField;
	private JTextField amountField;
	private JTextField calculationField;
	private JTextField supplierField;
	private JTextField originalityField;
	private JTextField material_field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tool_Page window = new Tool_Page();
					window.frmTool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tool_Page() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frmTool = new JFrame();
		frmTool.setBounds(0, 0, width, height);
		frmTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTool.getContentPane().setLayout(null);
		frmTool.getContentPane().setBackground(new Color(135, 206, 250));

		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(22, 35, 809, 625);
		frmTool.getContentPane().add(textArea);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalPool_Menu global = new GlobalPool_Menu();
				global.getClass();
				frmTool.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnBack.setBounds(22, 694, 85, 21);
		frmTool.getContentPane().add(btnBack);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnInsert.setBounds(995, 450, 85, 21);
		frmTool.getContentPane().add(btnInsert);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnSearch.setBounds(1119, 450, 107, 21);
		frmTool.getContentPane().add(btnSearch);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblNewLabel.setBounds(933, 41, 45, 21);
		frmTool.getContentPane().add(lblNewLabel);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblName.setBounds(933, 72, 66, 21);
		frmTool.getContentPane().add(lblName);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblAmount.setBounds(933, 103, 85, 21);
		frmTool.getContentPane().add(lblAmount);
		
		JLabel lblCalculationUnit = new JLabel("Calculation Unit");
		lblCalculationUnit.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblCalculationUnit.setBounds(933, 134, 147, 31);
		frmTool.getContentPane().add(lblCalculationUnit);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblSupplier.setBounds(933, 175, 124, 21);
		frmTool.getContentPane().add(lblSupplier);
		
		JLabel lblMaterial = new JLabel("Material");
		lblMaterial.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblMaterial.setBounds(933, 237, 97, 21);
		frmTool.getContentPane().add(lblMaterial);
		
		JLabel lblOriginality = new JLabel("Originality");
		lblOriginality.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblOriginality.setBounds(933, 206, 97, 21);
		frmTool.getContentPane().add(lblOriginality);
		
		idField = new JTextField();
		idField.setBounds(1158, 38, 96, 19);
		frmTool.getContentPane().add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(1158, 77, 96, 19);
		frmTool.getContentPane().add(nameField);
		
		amountField = new JTextField();
		amountField.setColumns(10);
		amountField.setBounds(1158, 108, 96, 19);
		frmTool.getContentPane().add(amountField);
		
		calculationField = new JTextField();
		calculationField.setColumns(10);
		calculationField.setBounds(1158, 138, 96, 19);
		frmTool.getContentPane().add(calculationField);
		
		supplierField = new JTextField();
		supplierField.setColumns(10);
		supplierField.setBounds(1158, 173, 96, 19);
		frmTool.getContentPane().add(supplierField);
		
		originalityField = new JTextField();
		originalityField.setColumns(10);
		originalityField.setBounds(1158, 204, 96, 19);
		frmTool.getContentPane().add(originalityField);
		
		material_field = new JTextField();
		material_field.setColumns(10);
		material_field.setBounds(1158, 239, 96, 19);
		frmTool.getContentPane().add(material_field);
		frmTool.setVisible(true);
	}

}
