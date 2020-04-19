package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.App;
import Classes.Contact;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField upusername;
	private JButton signup;
	private JTextField upemail;
	private JPasswordField uppassword;
	private JPasswordField uprepassword;
	private JTextField InputEmail;
	private JPasswordField inputPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					//frame.setUndecorated(true);
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
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1025, 621);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		signup = new JButton("Sign Up");
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String repass=uprepassword.getText();	
				
				
				// EDIT IS Blank
				
				if(App.isBlankString(upusername.getText())) {
					JOptionPane.showMessageDialog(null,"Please Fill Username Field !","Error !",JOptionPane.ERROR_MESSAGE);
				}
				else if(App.isBlankString(upemail.getText())) {
					JOptionPane.showMessageDialog(null,"Please Fill Email Field !","Error !",JOptionPane.ERROR_MESSAGE);
				}
				else if(App.isBlankString(uppassword.getText())) {
					JOptionPane.showMessageDialog(null,"Please Fill Password Field !","Error !",JOptionPane.ERROR_MESSAGE);
				}
				
				else if(App.isBlankString(uprepassword.getText())) {
					JOptionPane.showMessageDialog(null,"Please Fill RePassword Field !","Error !",JOptionPane.ERROR_MESSAGE);
				}
				else if(repass.compareTo(uppassword.getText())!=0) {
					System.out.println(repass);
					System.out.println(uppassword.getText());
					JOptionPane.showMessageDialog(null,"Passwords Don't Match !","Error !",JOptionPane.ERROR_MESSAGE);
	
				}
				else {
					String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
				    if(!upemail.getText().matches(regex)) {
				   	  JOptionPane.showMessageDialog(null,"Invalid Email Format !","Error !",JOptionPane.ERROR_MESSAGE);
				    } 
				    else {
				    	Contact input=new Contact();
						input.setname(upusername.getText());
						input.setpassword(uppassword.getText());
						input.setemail(upemail.getText());
						App test=new App();
						if(test.signup(input)) {
							JOptionPane.showMessageDialog(null,"Account Created Successfully !");
						}
						else {
							JOptionPane.showMessageDialog(null,"Email Already Exist !","Error !",JOptionPane.ERROR_MESSAGE);
						}
				    }
				}
			}
		});
		signup.setForeground(Color.WHITE);
		signup.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		signup.setBackground(SystemColor.textHighlight);
		signup.setBounds(295, 515, 172, 49);
		contentPane.add(signup);
		
		upusername = new JTextField();
		upusername.setBounds(226, 295, 305, 42);
		contentPane.add(upusername);
		upusername.setColumns(10);
		
		upemail = new JTextField();
		upemail.setColumns(10);
		upemail.setBounds(226, 350, 305, 42);
		contentPane.add(upemail);
		
		JLabel lblNewLabel = new JLabel("USERNAME");
		lblNewLabel.setForeground(new Color(230, 230, 250));
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblNewLabel.setBounds(34, 299, 112, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassowrd = new JLabel("EMAIL");
		lblPassowrd.setForeground(new Color(230, 230, 250));
		lblPassowrd.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblPassowrd.setBounds(34, 354, 112, 32);
		contentPane.add(lblPassowrd);
		
		JLabel lblPassowrd_2 = new JLabel("PASSWORD");
		lblPassowrd_2.setForeground(new Color(230, 230, 250));
		lblPassowrd_2.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblPassowrd_2.setBounds(34, 409, 112, 32);
		contentPane.add(lblPassowrd_2);
		
		JLabel lblPassowrd_2_1 = new JLabel("RE PASSWORD");
		lblPassowrd_2_1.setForeground(new Color(230, 230, 250));
		lblPassowrd_2_1.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblPassowrd_2_1.setBounds(34, 470, 112, 32);
		contentPane.add(lblPassowrd_2_1);
		
		uppassword = new JPasswordField();
		uppassword.setBounds(226, 405, 305, 42);
		contentPane.add(uppassword);
		
		uprepassword = new JPasswordField();
		uprepassword.setBounds(226, 460, 305, 42);
		contentPane.add(uprepassword);
		
		InputEmail = new JTextField();
		InputEmail.setColumns(10);
		InputEmail.setBounds(226, 105, 305, 42);
		contentPane.add(InputEmail);
		
		inputPassword = new JPasswordField();
		inputPassword.setBounds(226, 154, 305, 42);
		contentPane.add(inputPassword);
		
		JButton signin = new JButton("Sign In");
		signin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
				if(App.isBlankString(InputEmail.getText())) {
					JOptionPane.showMessageDialog(null,"Please Fill Email Field !","Error !",JOptionPane.ERROR_MESSAGE);
				}
				else if(App.isBlankString(inputPassword.getText())) {
					JOptionPane.showMessageDialog(null,"Please Fill Password Field !","Error !",JOptionPane.ERROR_MESSAGE);
				}
				else if(!InputEmail.getText().matches(regex)){
				   	  JOptionPane.showMessageDialog(null,"Invalid Email Format !","Error !",JOptionPane.ERROR_MESSAGE);
				}
				else {
		
					App test=new App();
					if(test.signin(InputEmail.getText(), inputPassword.getText())) {
						JOptionPane.showMessageDialog(null,"Successfully Logged in !");
						ContactWindow Contact=new ContactWindow();
						Contact.setVisible(true);
						
						
					}else {
						JOptionPane.showMessageDialog(null,"Invaild Email or password","Error !",JOptionPane.ERROR_MESSAGE);
					}
				}
			
			}
		});
		signin.setForeground(Color.WHITE);
		signin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		signin.setBackground(SystemColor.textHighlight);
		signin.setBounds(546, 126, 160, 49);
		contentPane.add(signin);
		
		JLabel lblNewLabel_2 = new JLabel("EMAIL");
		lblNewLabel_2.setForeground(new Color(230, 230, 250));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblNewLabel_2.setBounds(34, 109, 112, 32);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblPassowrd_2_2 = new JLabel("PASSWORD");
		lblPassowrd_2_2.setForeground(new Color(230, 230, 250));
		lblPassowrd_2_2.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblPassowrd_2_2.setBounds(34, 164, 112, 32);
		contentPane.add(lblPassowrd_2_2);
		
		JLabel lblNewLabel_3 = new JLabel("Registration");
		lblNewLabel_3.setForeground(new Color(240, 255, 255));
		lblNewLabel_3.setFont(new Font("Andalus", Font.ITALIC, 40));
		lblNewLabel_3.setBounds(12, 229, 253, 42);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Login");
		lblNewLabel_3_1.setForeground(new Color(240, 255, 255));
		lblNewLabel_3_1.setFont(new Font("Andalus", Font.ITALIC, 40));
		lblNewLabel_3_1.setBounds(12, 27, 253, 44);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Home.class.getResource("/images/5195ebb8c5f9772deda82aa2937134d3 (4).jpg")));
		lblNewLabel_1.setBounds(0, -8, 1007, 592);
		contentPane.add(lblNewLabel_1);
	}
}
