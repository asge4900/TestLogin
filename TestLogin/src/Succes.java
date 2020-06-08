import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Succes extends JFrame {
	String gender;
	String fileName = null;
	byte[] person_image;

	private JPanel contentPane;
	private JTextField name;
	private JTextField address;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable jTable_Display_Person;
	private JTextField searchData;
	private JTextField id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Succes frame = new Succes();
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
	public Succes() {
		this.person_image = null;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(44, 91, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(44, 126, 77, 13);
		contentPane.add(lblAddress);
		
		JLabel lblNewLabel_1_1 = new JLabel("Gender");
		lblNewLabel_1_1.setBounds(44, 167, 45, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Knowledge");
		lblNewLabel_1_1_1.setBounds(44, 211, 74, 13);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Subject");
		lblNewLabel_1_1_1_1.setBounds(44, 250, 45, 13);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		name = new JTextField();
		name.setBounds(131, 88, 144, 19);
		contentPane.add(name);
		name.setColumns(10);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(131, 123, 144, 19);
		contentPane.add(address);
		
		JRadioButton male = new JRadioButton("Male");
		buttonGroup.add(male);
		male.setBounds(124, 163, 56, 21);
		contentPane.add(male);
		
		JRadioButton female = new JRadioButton("Female");
		buttonGroup.add(female);
		female.setBounds(204, 163, 103, 21);
		contentPane.add(female);
		
		JCheckBox coreJava = new JCheckBox("Java");
		coreJava.setBounds(124, 207, 56, 21);
		contentPane.add(coreJava);
		
		JCheckBox python = new JCheckBox("Python");
		python.setBounds(204, 207, 93, 21);
		contentPane.add(python);
		
		JComboBox<String> subject = new JComboBox<String>();
		subject.setModel(new DefaultComboBoxModel<String>(new String[] {"Computer Science", "Management", "Humanities", "Arts", "Education"}));
		subject.setBounds(131, 246, 116, 21);
		contentPane.add(subject);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(131, 288, 144, 19);
		contentPane.add(dateChooser);
		
		JLabel lblNewLabel_1 = new JLabel("Date");
		lblNewLabel_1.setBounds(44, 294, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;Database=TestDB;user=sa;password=ujglqlvkekyy;";
					Connection con = DriverManager.getConnection(url);
					String sql = "insert into Persons (Name, Address, Gender, Knowledge, Subject, Image, Date) VALUES(?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, name.getText());
					ps.setString(2, address.getText());
					if (male.isSelected()) {
						gender = "Male";
					}
					if (female.isSelected()) {
						gender = "Female";
					}
					ps.setString(3, gender);
					String knowledgde = "";
					if (coreJava.isSelected()) {
						knowledgde += coreJava.getText() + " ";
					}
					if (python.isSelected()) {
						knowledgde += python.getText() + " ";
					}
					ps.setString(4, knowledgde);
					String course;
					course = subject.getSelectedItem().toString();
					ps.setString(5, course);
					ps.setBytes(6, person_image);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(dateChooser.getDate());
					ps.setString(7, date);
					ps.executeUpdate();
					DefaultTableModel model = (DefaultTableModel)jTable_Display_Person.getModel();
					model.setRowCount(0);
					show_user();
					JOptionPane.showMessageDialog(null, "Inserted successfully!");
					//con.close();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		
		saveBtn.setBounds(10, 333, 75, 21);
		contentPane.add(saveBtn);
		
		JLabel lbl_img = new JLabel("");
		lbl_img.setBounds(363, 140, 116, 128);
		contentPane.add(lbl_img);
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				name.setText("");
				address.setText("");
				buttonGroup.clearSelection();
				coreJava.setSelected(false);
				python.setSelected(false);
				subject.setSelectedIndex(0);
				lbl_img.setIcon(null);
			}
		});
		resetBtn.setBounds(274, 333, 75, 21);
		contentPane.add(resetBtn);	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(363, 0, 573, 112);
		contentPane.add(scrollPane);
		
		jTable_Display_Person = new JTable();
		jTable_Display_Person.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int i = jTable_Display_Person.getSelectedRow();
				TableModel model = jTable_Display_Person.getModel();
				id.setText(model.getValueAt(i, 0).toString());
				name.setText(model.getValueAt(i, 1).toString());
				address.setText(model.getValueAt(i, 2).toString());
				String sex = model.getValueAt(i, 3).toString();
				if (sex.equals("Male")) {
					male.setSelected(true);
				}
				else {
					female.setSelected(true);
				}
				String knowlegde = model.getValueAt(i, 4).toString();
				switch (knowlegde) {
				case "Java ": 
					coreJava.setSelected(true);
					python.setSelected(false);
					break;				
				case "Python ":
					coreJava.setSelected(false);
					python.setSelected(true);
					break;
				default:
					coreJava.setSelected(true);
					python.setSelected(true);
					break;
				}
				String subjectString = model.getValueAt(i, 5).toString();
				switch (subjectString) {
				case "Computer Science":
					subject.setSelectedIndex(0);
					break;
				case "Management":
					subject.setSelectedIndex(1);
					break;
				case "Humanities":
					subject.setSelectedIndex(2);
					break;
				case "Arts":
					subject.setSelectedIndex(3);
					break;
				case "Education":
					subject.setSelectedIndex(4);
					break;				
				}
				byte[] img = userList().get(i).getImage();
				if (img != null) {
					ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(lbl_img.getWidth(), lbl_img.getHeight(), Image.SCALE_SMOOTH));
					lbl_img.setIcon(imageIcon);
					person_image = img;
				}
				else {
					lbl_img.setIcon(null);
				}
				try {
					int srow = jTable_Display_Person.getSelectedRow();
					java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(srow, 6));
					dateChooser.setDate(date);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		scrollPane.setViewportView(jTable_Display_Person);
		jTable_Display_Person.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Name", "Address", "Gender", "Knowledge", "Subject", "Date"
			}
		));
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Connection con = getConnection();
					//int row = jTable_Display_Person.getSelectedRow();
					//String value = jTable_Display_Person.getModel().getValueAt(row, 0).toString();
					String value = id.getText();
					String sql = "UPDATE Persons SET Name = ?, Address = ?, Gender = ?, Knowledge = ?, Subject = ?, Image = ?, Date = ? WHERE Id = " + value;
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, name.getText());
					ps.setString(2, address.getText());
					if (male.isSelected()) {
						gender = "Male";
					}
					if (female.isSelected()) {
						gender = "Female";
					}
					ps.setString(3, gender);
					String knowledgde = "";
					if (coreJava.isSelected()) {
						knowledgde += coreJava.getText() + " ";
					}
					if (python.isSelected()) {
						knowledgde += python.getText() + " ";
					}
					ps.setString(4, knowledgde);
					String course;
					course = subject.getSelectedItem().toString();
					ps.setString(5, course);
					byte[] img = person_image;
					ps.setBytes(6, img);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(dateChooser.getDate());
					ps.setString(7, date);
					ps.executeUpdate();
					DefaultTableModel model = (DefaultTableModel)jTable_Display_Person.getModel();
					model.setRowCount(0);
					show_user();
					JOptionPane.showMessageDialog(null, "Update successfully!");
					//con.close();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		updateBtn.setBounds(101, 333, 75, 21);
		contentPane.add(updateBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int opt = JOptionPane.showConfirmDialog(null, "Are You Sure to Delete", "Delete", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {				
					try {
						Connection con = getConnection();
						int row = jTable_Display_Person.getSelectedRow();
						String value = jTable_Display_Person.getModel().getValueAt(row, 0).toString();
						String sql = "DELETE FROM dbo.Persons WHERE dbo.Persons.Id = " + value;
						PreparedStatement ps = con.prepareStatement(sql);					
						ps.executeUpdate();
						DefaultTableModel model = (DefaultTableModel)jTable_Display_Person.getModel();
						model.setRowCount(0);
						show_user();
						
						// TODO: Make clear Method
						lbl_img.setIcon(null);
						
						JOptionPane.showMessageDialog(null, "Deleted successfully!");
						//con.close();
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}				
				}
			}
		});
		deleteBtn.setBounds(189, 333, 75, 21);
		contentPane.add(deleteBtn);
		
		
		JButton btnImage = new JButton("Choose");
		btnImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				fileName = f.getAbsolutePath();
				ImageIcon imageIcon = new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(lbl_img.getWidth(), lbl_img.getHeight(), Image.SCALE_SMOOTH));
				lbl_img.setIcon(imageIcon);
				try {
					File image = new File(fileName);
					FileInputStream fStream = new FileInputStream(image);					
					ByteArrayOutputStream bStream = new ByteArrayOutputStream();					
					byte[] buf = new byte[1024];
					for (int readNum; (readNum = fStream.read(buf)) != -1;) {
						bStream.write(buf, 0, readNum);
					}
					person_image = bStream.toByteArray();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		btnImage.setBounds(489, 195, 85, 21);
		contentPane.add(btnImage);	
		
		searchData = new JTextField();
		searchData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				try {
					Connection con = getConnection();
					String sql = "SELECT * FROM Persons WHERE Id = ?";
					PreparedStatement pStatement = con.prepareStatement(sql);
					pStatement.setString(1, searchData.getText());
					ResultSet rSet = pStatement.executeQuery();
					if (rSet.next()) {
						String setId = rSet.getString("Id");
						id.setText(setId);
						
						String setName = rSet.getString("Name");
						name.setText(setName);
						
						String setAddress = rSet.getString("Address");
						address.setText(setAddress);
						
						
						String sex = rSet.getString("Gender");
						if (sex.equals("Male")) {
							male.setSelected(true);
						}
						else {
							female.setSelected(true);
						}
						String knowlegde = rSet.getString("Knowledge");
						switch (knowlegde) {
						case "Java ": 
							coreJava.setSelected(true);
							python.setSelected(false);
							break;				
						case "Python ":
							coreJava.setSelected(false);
							python.setSelected(true);
							break;
						default:
							coreJava.setSelected(true);
							python.setSelected(true);
							break;
						}
						String subjectString = rSet.getString("Subject");
						switch (subjectString) {
						case "Computer Science":
							subject.setSelectedIndex(0);
							break;
						case "Management":
							subject.setSelectedIndex(1);
							break;
						case "Humanities":
							subject.setSelectedIndex(2);
							break;
						case "Arts":
							subject.setSelectedIndex(3);
							break;
						case "Education":
							subject.setSelectedIndex(4);
							break;				
						}
						byte[] img = rSet.getBytes("Image");
						if (img != null) {
							ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(lbl_img.getWidth(), lbl_img.getHeight(), Image.SCALE_SMOOTH));
							lbl_img.setIcon(imageIcon);
							person_image = img;
						}
						else {
							lbl_img.setIcon(null);
						}
						Date date = rSet.getDate("Date");
						dateChooser.setDate(date);					
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		searchData.setBounds(131, 8, 144, 19);
		contentPane.add(searchData);
		searchData.setColumns(10);
		
		id = new JTextField();
		id.setBounds(131, 46, 144, 19);
		contentPane.add(id);
		id.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Search");
		lblNewLabel_2.setBounds(44, 7, 45, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Id");
		lblNewLabel_3.setBounds(44, 49, 45, 13);
		contentPane.add(lblNewLabel_3);
		
		show_user();
		
		java.awt.Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2-getHeight()/2);
	}
	
	public Connection getConnection() throws Exception {
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = "jdbc:sqlserver://localhost:1433;Database=TestDB;user=sa;password=ujglqlvkekyy;";
		Connection con = DriverManager.getConnection(url);
		
		return con;
	}
	
	public ArrayList<User> userList() {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;Database=TestDB;user=sa;password=ujglqlvkekyy;";
			Connection con = DriverManager.getConnection(url);
			String sql = "SELECT * FROM dbo.Persons p";
			Statement st = con.createStatement();				
			ResultSet rs = st.executeQuery(sql);
			User user;
			while(rs.next()) {
				user = new User(rs.getInt("Id"),
								rs.getString("Name"),
								rs.getString("Address"),
								rs.getString("Gender"),
								rs.getString("Knowledge"),
								rs.getString("Subject"),
								rs.getBytes("Image"),
								rs.getString("Date"));
				users.add(user);
			}				
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		return users;
	}
	
	public void show_user() {
		ArrayList<User> list = userList();
		DefaultTableModel model = (DefaultTableModel)jTable_Display_Person.getModel();
		Object[] row = new Object[7];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getId();
			row[1] = list.get(i).getName();
			row[2] = list.get(i).getAddress();
			row[3] = list.get(i).getGender();
			row[4] = list.get(i).getKnowledge();
			row[5] = list.get(i).getSubject();
			row[6] = list.get(i).getDate();
			model.addRow(row);
		}
	}
}
