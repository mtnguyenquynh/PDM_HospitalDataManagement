import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class Staff_form {

	private JFrame frmStaffData;
	private JTextField idField;
	private JTextField nameField;
	private JTextField dobField;
	private JTextField phoneField;
	private JTextField mailField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Staff_form window = new Staff_form();
					window.frmStaffData.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Staff_form() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		 Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		 int width = (int) size.getWidth();
		 int height = (int) size.getHeight();
		frmStaffData = new JFrame();
		frmStaffData.setTitle("Staff Data");
		frmStaffData.setBounds(0, 0, width, height);
		frmStaffData.setVisible(true);
		frmStaffData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStaffData.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STAFF FORM");
		lblNewLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 30));
		lblNewLabel.setBounds(604, 27, 256, 144);
		frmStaffData.getContentPane().add(lblNewLabel);
		
		//Name field
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblName.setBounds(155, 234, 86, 30);
		frmStaffData.getContentPane().add(lblName);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(464, 242, 465, 30);
		frmStaffData.getContentPane().add(nameField);
		
		//DoB field
		JLabel lblDoB = new JLabel("Day Of Birth");
		lblDoB.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblDoB.setBounds(155, 283, 127, 30);
		frmStaffData.getContentPane().add(lblDoB);

		dobField = new JTextField();
		dobField.setColumns(10);
		dobField.setBounds(464, 288, 465, 30);
		frmStaffData.getContentPane().add(dobField);
		
		//Phone field
		JLabel lblPhone = new JLabel("Phone number");
		lblPhone.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblPhone.setBounds(155, 332, 127, 30);
		frmStaffData.getContentPane().add(lblPhone);
		
		phoneField = new JTextField();
		phoneField.setColumns(10);
		phoneField.setBounds(464, 332, 465, 30);
		frmStaffData.getContentPane().add(phoneField);
		
		//ID field
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblID.setBounds(155, 200, 60, 24);
		frmStaffData.getContentPane().add(lblID);
		
		idField = new JTextField();
		idField.setBounds(464, 202, 465, 30);
		frmStaffData.getContentPane().add(idField);
		idField.setColumns(10);
		
		//Mail Field
		JLabel lblMail = new JLabel("Email");
		lblMail.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblMail.setBounds(155, 374, 109, 30);
		frmStaffData.getContentPane().add(lblMail);
		
		mailField = new JTextField();
		mailField.setColumns(10);
		mailField.setBounds(464, 374, 465, 30);
		frmStaffData.getContentPane().add(mailField);
		
		//Occupation Field
		JLabel lblOccupation = new JLabel("Occupation");
		lblOccupation.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblOccupation.setBounds(155, 414, 127, 41);
		frmStaffData.getContentPane().add(lblOccupation);
		
		String[] list = {"Doctor", "Nurse", "Cashier"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox btnOccupation = new JComboBox(list);
		btnOccupation.setBounds(464, 418, 133, 41);
		frmStaffData.getContentPane().add(btnOccupation);
		
		//Button to insert data
		JButton btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText();
				String name = nameField.getText();
				String dob = dobField.getText();
				String phone = phoneField.getText();
				String mail = mailField.getText();
				if(mail.matches("^(.+)@gmail.com")) {
				JOptionPane.showMessageDialog(lblNewLabel, "Patient ID: "+ id+ "\nPatient Name: "+name+"\nDay of birth: "+dob+"\nPhone number: "+phone+"\nEmail: "+mail+"\nSuccessful Submit!!");
				}
				else JOptionPane.showMessageDialog(lblNewLabel, "Wrong syntax for email, it should be @gmail.com");}
		});
		btnInsert.setBounds(832, 414, 97, 30);
		frmStaffData.getContentPane().add(btnInsert);
	}
}
