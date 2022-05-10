import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tool_Page {

	private JFrame frmTool;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

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
		
		textField = new JTextField();
		textField.setBounds(1158, 38, 96, 19);
		frmTool.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(1158, 77, 96, 19);
		frmTool.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(1158, 108, 96, 19);
		frmTool.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(1158, 138, 96, 19);
		frmTool.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(1158, 173, 96, 19);
		frmTool.getContentPane().add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(1158, 204, 96, 19);
		frmTool.getContentPane().add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(1158, 239, 96, 19);
		frmTool.getContentPane().add(textField_6);
		frmTool.setVisible(true);
	}

}
