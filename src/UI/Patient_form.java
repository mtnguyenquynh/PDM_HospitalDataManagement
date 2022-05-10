package UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class Patient_form {

	private JFrame frmPatient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patient_form window = new Patient_form();
					window.frmPatient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Patient_form() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frmPatient = new JFrame();
		frmPatient.setTitle("Patient Data");
		frmPatient.setBounds(0, 0, width, height);
		frmPatient.setVisible(true);
		frmPatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPatient.getContentPane().setLayout(null);

		JLabel lblPatientForm = new JLabel("PATIENT FORM");
		lblPatientForm.setBounds(572, 0, 320, 173);
		lblPatientForm.setBackground(Color.CYAN);
		lblPatientForm.setFont(new Font("Segoe UI Symbol", Font.BOLD, 30));
		lblPatientForm.setHorizontalAlignment(SwingConstants.CENTER);
		frmPatient.getContentPane().add(lblPatientForm);

		// Name field
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(401, 217, 51, 37);
		lblName.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmPatient.getContentPane().add(lblName);

		JTextPane txtName = new JTextPane();
		txtName.setBounds(551, 233, 341, 19);
		frmPatient.getContentPane().add(txtName);

		// DoB field
		JLabel lblDayOfBirth = new JLabel("Day of birth");
		lblDayOfBirth.setBounds(401, 303, 115, 25);
		lblDayOfBirth.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmPatient.getContentPane().add(lblDayOfBirth);

		JTextPane txtDob = new JTextPane();
		txtDob.setBounds(551, 309, 341, 19);
		frmPatient.getContentPane().add(txtDob);

		// Phone field
		JLabel lblPhoneNumber = new JLabel("Phone number");
		lblPhoneNumber.setBounds(401, 338, 172, 37);
		lblPhoneNumber.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmPatient.getContentPane().add(lblPhoneNumber);

		JTextPane txtPhone = new JTextPane();
		txtPhone.setBounds(551, 349, 341, 19);
		frmPatient.getContentPane().add(txtPhone);
		


		// Department field
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(401, 458, 109, 49);
		lblDepartment.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmPatient.getContentPane().add(lblDepartment);

		String[] list = { "Outpatient Department", "Emergency Room", "Cardiology Department",
				"Endocrinology Department", "Pediatrics Department", "Respiratory Department", "Intensive Care Unit" };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbDepartment = new JComboBox(list);
		cmbDepartment.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		cmbDepartment.setBounds(551, 470, 177, 25);
		frmPatient.getContentPane().add(cmbDepartment);
		frmPatient.getContentPane().add(cmbDepartment);

		// ID field
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(401, 181, 33, 37);
		lblId.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmPatient.getContentPane().add(lblId);

		JTextPane txtID = new JTextPane();
		txtID.setBounds(551, 192, 341, 19);
		frmPatient.getContentPane().add(txtID);

		// Gender field
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(401, 252, 85, 49);
		lblGender.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmPatient.getContentPane().add(lblGender);

		JCheckBox chckbxMale = new JCheckBox("Male");
		chckbxMale.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		chckbxMale.setBounds(551, 270, 93, 21);
		frmPatient.getContentPane().add(chckbxMale);

		JCheckBox chckbxFemale = new JCheckBox("Female");
		chckbxFemale.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		chckbxFemale.setBounds(646, 270, 93, 21);
		frmPatient.getContentPane().add(chckbxFemale);

		// Job field
		JLabel lblJob = new JLabel("Job");
		lblJob.setBounds(401, 378, 33, 37);
		lblJob.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmPatient.getContentPane().add(lblJob);
		
		JTextPane txtJob = new JTextPane();
		txtJob.setBounds(551, 385, 341, 19);
		frmPatient.getContentPane().add(txtJob);

		// Address field

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblAddress.setBounds(401, 411, 85, 49);
		frmPatient.getContentPane().add(lblAddress);
		
		JTextPane txtAddress = new JTextPane();
		txtAddress.setBounds(551, 425, 341, 19);
		frmPatient.getContentPane().add(txtAddress);

		// Button to insert data
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInsert.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
		btnInsert.setBounds(696, 523, 85, 21);
		frmPatient.getContentPane().add(btnInsert);

		// Button to search data
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Input search info");
			}
		});
		btnSearch.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
		btnSearch.setBounds(807, 523, 85, 21);
		frmPatient.getContentPane().add(btnSearch);

		// Button to comeback Menu page
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu_Page menu = new Menu_Page();
				menu.getClass();
				frmPatient.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnBack.setBounds(38, 581, 94, 31);
		frmPatient.getContentPane().add(btnBack);
	
	}
}
