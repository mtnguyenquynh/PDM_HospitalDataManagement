package UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frmPool = new JFrame();
		frmPool.getContentPane().setBackground(new Color(135, 206, 250));
		frmPool.setTitle("Pool");
		frmPool.setBounds(0, 0, width, height);
		frmPool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPool.getContentPane().setLayout(null);
		frmPool.setVisible(true);
		
		JButton btnTool = new JButton("Tool");
		btnTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				Tool_Page tool = new Tool_Page();
				tool.getClass();
			}
		});
		btnTool.setIcon(null);
		btnTool.setBackground(new Color(135, 206, 235));
		btnTool.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnTool.setBounds(362, 405, 222, 95);
		frmPool.getContentPane().add(btnTool);
		
		JButton btnResource = new JButton("Resource");
		btnResource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Resource_Page resource = new Resource_Page();
				resource.getClass();
			}
		});
		btnResource.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnResource.setBackground(new Color(135, 206, 235));
		btnResource.setBounds(795, 405, 239, 95);
		frmPool.getContentPane().add(btnResource);
		
		JButton btnBack = new JButton("Log out");
		btnBack.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnBack.setBounds(1308, 10, 112, 52);
		frmPool.getContentPane().add(btnBack);
		
		JLabel lblResource = new JLabel("");
		lblResource.setIcon(new ImageIcon(GlobalPool_Menu.class.getResource("/image/Resource.png")));
		lblResource.setBounds(755, 26, 327, 316);
		frmPool.getContentPane().add(lblResource);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(GlobalPool_Menu.class.getResource("/image/Tool.png")));
		lblNewLabel.setBounds(362, 26, 248, 316);
		frmPool.getContentPane().add(lblNewLabel);
	}
}
