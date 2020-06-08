import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginCheck extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginCheck frame = new LoginCheck();
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
	public LoginCheck() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(79, 72, 57, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(79, 126, 57, 13);
		contentPane.add(lblPassword);
		
		JLabel lbl_username = new JLabel("");
		lbl_username.setBounds(168, 100, 119, 13);
		contentPane.add(lbl_username);
		
		JLabel lbl_password = new JLabel("");
		lbl_password.setBounds(168, 152, 119, 13);
		contentPane.add(lbl_password);
		
		username = new JTextField();
		username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				lbl_username.setText("");
			}
		});
		username.setBounds(168, 69, 172, 19);
		contentPane.add(username);
		username.setColumns(10);
		
		password = new JTextField();
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lbl_password.setText("");
			}
		});
		password.setColumns(10);
		password.setBounds(168, 123, 172, 19);
		contentPane.add(password);		
		
		JButton signin = new JButton("Signin");
		signin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				if (username.getText().trim().isEmpty()) {
					lbl_username.setText("Username is empty");
				}
				if (password.getText().trim().isEmpty()) {
					lbl_password.setText("Password is empty");
				}				
				else if (!username.getText().trim().isEmpty() && !password.getText().trim().isEmpty()) {					
					try {
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						String url = "jdbc:sqlserver://localhost:1433;Database=TestDB;user=sa;password=ujglqlvkekyy;";
						Connection con = DriverManager.getConnection(url);
						String sql = "Select * from Users where username = ? and password = ?";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, username.getText());
						ps.setString(2, password.getText());
						ResultSet rs = ps.executeQuery();
						if (rs.next()) {
							JOptionPane.showMessageDialog(null, "Username and password matched");
							Succes field = new Succes();
							field.setVisible(true);
							setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "Username and password not correct");
							username.setText("");
							password.setText("");
						}
						con.close();
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});
		signin.setBounds(79, 178, 85, 21);
		contentPane.add(signin);
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				username.setText("");
				password.setText("");			}
		});
		reset.setBounds(223, 178, 85, 21);
		contentPane.add(reset);
		
		
		
		java.awt.Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2-getHeight()/2);
	}
}
