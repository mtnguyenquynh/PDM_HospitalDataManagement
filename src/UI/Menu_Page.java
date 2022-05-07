import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Menu_Page {

	private JFrame frmMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu_Page window = new Menu_Page();
					window.frmMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu_Page() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frmMenu = new JFrame();
		frmMenu.setTitle("MENU");
		frmMenu.getContentPane().setBackground(Color.WHITE);
		frmMenu.setBounds(0, 0, width, height);
		frmMenu.setVisible(true);
		frmMenu.setResizable(true);
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.getContentPane().setLayout(null);

		//Button to get MR_form
		JButton btnMedicalRecord = new JButton("MEDICAL RECORD");
		btnMedicalRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MR_form record = new MR_form();
				record.getClass();
			}
		});
		btnMedicalRecord.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnMedicalRecord.setBounds(1034, 369, 211, 58);
		frmMenu.getContentPane().add(btnMedicalRecord);
		
		//Button to get Staff_form
		JButton btnStaffForm = new JButton("STAFF FORM");
		btnStaffForm.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnStaffForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Staff_form staff = new Staff_form();
				staff.getClass();
			}
		});
		btnStaffForm.setBounds(673, 369, 144, 58);
		frmMenu.getContentPane().add(btnStaffForm);

		//Button to get Patient_form
		JButton btnPatientForm = new JButton("PATIENT FORM");
		btnPatientForm.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnPatientForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Patient_form patient = new Patient_form();
				patient.getClass();
			}
		});
		btnPatientForm.setBounds(270, 369, 184, 58);
		frmMenu.getContentPane().add(btnPatientForm);

		//Set image icon for patient, staff and MR page
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Menu_Page.class.getResource("/image/Patient.png")));
		lblNewLabel.setBounds(223, 116, 253, 209);
		frmMenu.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Menu_Page.class.getResource("/image/Staff.png")));
		lblNewLabel_1.setBounds(637, 116, 245, 209);
		frmMenu.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Menu_Page.class.getResource("/image/Record.png")));
		lblNewLabel_2.setBounds(1061, 133, 184, 192);
		frmMenu.getContentPane().add(lblNewLabel_2);

		//Button to comeback Login_Page
		JButton btnLogout = new JButton("Log Out");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login_Page login = new Login_Page();
				login.getClass();
			}
		});
		btnLogout.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnLogout.setBounds(1150, 10, 109, 33);
		frmMenu.getContentPane().add(btnLogout);
	}
}
