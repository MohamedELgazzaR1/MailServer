package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.Contact;
import Classes.Folder;
import Classes.Mail;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import java.awt.SystemColor;



public class Contacts extends JFrame {

	private JPanel contentPane;
	private JButton btnAddContact;
	private JTextField name;
	private static DefaultListModel DLM=new DefaultListModel();
	private static JList contactEmails=new JList();
	private static String accountName;
	private JTextField email;
	private JTextField extraEmail;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contacts frame = new Contacts();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	accountName=args[0];
	DLM.clear();
	File myobj =new File("D:\\MailServerData\\"+accountName+"\\contacts.txt");
	try {
		Scanner myreader= new Scanner(myobj);
		while(myreader.hasNextLine()) {
			String data=myreader.nextLine();
			DLM.addElement(data);
			contactEmails.setModel(DLM);
			
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

	/**
	 * Create the frame.
	 */
	public Contacts() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 856, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton RemoveContact = new JButton("Remove Selected");
		RemoveContact.setForeground(Color.WHITE);
		RemoveContact.setBackground(SystemColor.textHighlight);
		RemoveContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(contactEmails.getSelectedValue()==null)) {
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+(String) contactEmails.getSelectedValue()+ " Contact","Confirmation",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					File temp=new File("D:\\MailServerData\\"+accountName+"\\contacts.txt");
					//Folder.deleteData(temp,(String) Contacts.getSelectedValue(),temp., destBool, dest)
					
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"Please Select a Contact !");
				}
				
			}
		});
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setBounds(648, 110 ,147, 43);
		contentPane.add(lblEmail);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUserName.setBounds(416, 110,147, 43);
		contentPane.add(lblUserName);
		
		extraEmail = new JTextField();
		extraEmail.setColumns(10);
		extraEmail.setBounds(416, 346, 194, 48);
		contentPane.add(extraEmail);
		
		JButton btnAddExtraEamil = new JButton("Add Extra Email");
		btnAddExtraEamil.setForeground(Color.WHITE);
		btnAddExtraEamil.setBackground(SystemColor.textHighlight);
		btnAddExtraEamil.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddExtraEamil.setBounds(648, 343, 194, 48);
		contentPane.add(btnAddExtraEamil);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(648, 164, 194, 44);
		contentPane.add(email);
		
		JList contactNames = new JList();
		contactNames.setBounds(12, 128, 166, 266);
		contentPane.add(contactNames);
		
		
		contactEmails.setBounds(199, 128, 166, 266);
		contentPane.add(contactEmails);
		
		JButton SendMessage = new JButton("Send Message");
		SendMessage.setForeground(Color.WHITE);
		SendMessage.setBackground(SystemColor.textHighlight);
		SendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] currentEmail = new String[2];
				currentEmail[0] = accountName;
				currentEmail[1]=(String) contactEmails.getSelectedValue();
				ComposeMessage.main(currentEmail);
				
			}
		});
		SendMessage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SendMessage.setBounds(532, 426, 194, 44);
		contentPane.add(SendMessage);
		
		name = new JTextField();
		name.setBounds(414, 164, 194, 44);
		contentPane.add(name);
		name.setColumns(10);
		RemoveContact.setBounds(105, 426, 194, 44);
		contentPane.add(RemoveContact);
		
		btnAddContact = new JButton("Add Contact");
		btnAddContact.setForeground(Color.WHITE);
		btnAddContact.setBackground(SystemColor.textHighlight);
		btnAddContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName=name.getText();
				if(Mail.checkmail(newName)) {
					if(Contact.existContact(newName)) {
						if(Contact.addContact(accountName,newName)) {
							DLM.addElement(newName);
							contactEmails.setModel(DLM);
							JOptionPane.showMessageDialog(null,"Contact added successfully.");
							name.setText("");
				}else {
					JOptionPane.showMessageDialog(null,"This contact has already been added to the contact list.","Error",JOptionPane.ERROR_MESSAGE);
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"This email does not exist on this mail server.","Error",JOptionPane.ERROR_MESSAGE); 	
				}
				}
				else {
					 JOptionPane.showMessageDialog(null,"Invalid Email format.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAddContact.setBounds(532, 241, 194, 44);
		contentPane.add(btnAddContact);
		
		JLabel lblContacts = new JLabel("My Contacts");
		lblContacts.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblContacts.setForeground(Color.WHITE);
		lblContacts.setBounds(24, 9, 147, 43);
		contentPane.add(lblContacts);
		
	
		JLabel bgImage = new JLabel("");
		bgImage.setIcon(new ImageIcon(Contacts.class.getResource("/Images/123.jpg")));
		bgImage.setBounds(0, 0, 852, 502);
		contentPane.add(bgImage);
	
	}
}
