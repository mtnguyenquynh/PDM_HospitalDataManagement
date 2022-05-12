package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class GlobalPool_Menu {

	private JFrame frmPool;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlobalPool_Menu window = new GlobalPool_Menu();
					window.frmPool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GlobalPool_Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPool = new JFrame();
		frmPool.setTitle("Pool");
		frmPool.setBounds(100, 100, 1294, 530);
		frmPool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPool.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tool");
		lblNewLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblNewLabel.setBounds(336, 191, 85, 45);
		frmPool.getContentPane().add(lblNewLabel);
		
		JLabel lblResources = new JLabel("Resources");
		lblResources.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblResources.setBounds(674, 191, 128, 45);
		frmPool.getContentPane().add(lblResources);
	}

}
