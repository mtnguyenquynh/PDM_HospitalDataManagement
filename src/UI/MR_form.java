import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class MR_form {

	private JFrame frmMR;
	private JTextField txtDate;
	private JTextField txtDoctorId;
	private JTextField txtPatientId;
	private JTextField txtIllness;
	private JTextField txtTreatment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MR_form window = new MR_form();
					window.frmMR.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MR_form() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		 Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int) size.getWidth();
			int height = (int) size.getHeight();
		frmMR = new JFrame();
		frmMR.setBounds(0, 0, width, height);
		frmMR.setVisible(true);
		frmMR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMR.getContentPane().setLayout(null);
		
		JLabel lblMedicalRecord = new JLabel("MEDICAL RECORD FORM");
		lblMedicalRecord.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		lblMedicalRecord.setBounds(546, 60, 322, 59);
		frmMR.getContentPane().add(lblMedicalRecord);
		
		//Date field
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblDate.setBounds(218, 165, 86, 36);
		frmMR.getContentPane().add(lblDate);
		
		txtDate = new JTextField();
		txtDate.setBounds(451, 174, 438, 28);
		frmMR.getContentPane().add(txtDate);
		txtDate.setColumns(10);
		
		//Doctor ID field
		JLabel lblDoctorId = new JLabel("Doctor ID");
		lblDoctorId.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblDoctorId.setBounds(218, 209, 86, 36);
		frmMR.getContentPane().add(lblDoctorId);
		
		txtDoctorId = new JTextField();
		txtDoctorId.setColumns(10);
		txtDoctorId.setBounds(451, 218, 438, 28);
		frmMR.getContentPane().add(txtDoctorId);
		
		//PatientId
		JLabel lblPatientId = new JLabel("Patient ID");
		lblPatientId.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblPatientId.setBounds(218, 253, 86, 36);
		frmMR.getContentPane().add(lblPatientId);
		
		txtPatientId = new JTextField();
		txtPatientId.setColumns(10);
		txtPatientId.setBounds(451, 262, 438, 28);
		frmMR.getContentPane().add(txtPatientId);
		
		//Illness field
		JLabel lblIllness = new JLabel("Illness");
		lblIllness.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblIllness.setBounds(218, 299, 86, 36);
		frmMR.getContentPane().add(lblIllness);
		
		txtIllness = new JTextField();
		txtIllness.setColumns(10);
		txtIllness.setBounds(451, 308, 438, 28);
		frmMR.getContentPane().add(txtIllness);
		
		
		JLabel lblTreatment = new JLabel("Treatment");
		lblTreatment.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblTreatment.setBounds(218, 348, 114, 36);
		frmMR.getContentPane().add(lblTreatment);
		
		txtTreatment = new JTextField();
		txtTreatment.setColumns(10);
		txtTreatment.setBounds(451, 357, 438, 28);
		frmMR.getContentPane().add(txtTreatment);
		
		//Button to insert data
		JButton btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnInsert.setBounds(625, 429, 93, 36);
		frmMR.getContentPane().add(btnInsert);
		
		//Button to find data
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Input the Medico Record ID you want to search");
			}
		});
		btnFind.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnFind.setBounds(742, 429, 93, 36);
		frmMR.getContentPane().add(btnFind);
		
		//Button to remove data
		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnRemove.setBounds(857, 429, 153, 36);
		frmMR.getContentPane().add(btnRemove);
	}
}
