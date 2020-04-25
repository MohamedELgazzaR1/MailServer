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



public class Contacts extends JFrame {

	private JPanel contentPane;
	private JButton btnAddContact;
	private JTextField ContactName;
	private static DefaultListModel DLM=new DefaultListModel();
	private static JList Contacts=new JList();
	private static String accountName;
	private JTextField textField;
	private JTextField textField_1;
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
	accountName=args[1];
	DLM.clear();
	File myobj =new File("D:\\MailServerData\\"+accountName+"\\contacts.txt");
	try {
		Scanner myreader= new Scanner(myobj);
		while(myreader.hasNextLine()) {
			String data=myreader.nextLine();
			DLM.addElement(data);
			Contacts.setModel(DLM);
			
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
		RemoveContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(Contacts.getSelectedValue()==null)) {
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+(String) Contacts.getSelectedValue()+ " Contact","Confirmation",JOptionPane.YES_NO_OPTION);
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
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Email");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1_1.setBounds(579, 425 ,147, 43);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("User Name");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(414, 1254,147, 43);
		contentPane.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(648, 346, 194, 48);
		contentPane.add(textField_1);
		
		JButton btnAddExtraEamil = new JButton("Add Extra Email");
		btnAddExtraEamil.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddExtraEamil.setBounds(414, 346, 194, 48);
		contentPane.add(btnAddExtraEamil);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(648, 164, 194, 44);
		contentPane.add(textField);
		
		JList Contacts_1 = new JList();
		Contacts_1.setBounds(12, 128, 166, 266);
		contentPane.add(Contacts_1);
		
		
		Contacts.setBounds(199, 128, 166, 266);
		contentPane.add(Contacts);
		
		JButton SendMassege = new JButton("Send Messege");
		SendMassege.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] currentEmail = new String[2];
				currentEmail[0] = accountName;
				currentEmail[1]=(String) Contacts.getSelectedValue();
				ComposeMessage.main(currentEmail);
				
			}
		});
		SendMassege.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SendMassege.setBounds(532, 426, 194, 44);
		contentPane.add(SendMassege);
		
		ContactName = new JTextField();
		ContactName.setBounds(414, 87, 194, 44);
		contentPane.add(ContactName);
		ContactName.setColumns(10);
		RemoveContact.setBounds(105, 426, 194, 44);
		contentPane.add(RemoveContact);
		
		btnAddContact = new JButton("Add Contact");
		btnAddContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName=ContactName.getText();
				if(Mail.checkmail(newName)) {
					if(Contact.existContact(newName)) {
						if(Contact.addContact(accountName,newName)) {
							DLM.addElement(newName);
							Contacts.setModel(DLM);
							JOptionPane.showMessageDialog(null,"Contact added Successfully !");
							ContactName.setText("");
				}else {
					JOptionPane.showMessageDialog(null,"The Contact Is Already Exist","Error",JOptionPane.ERROR_MESSAGE);
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"There is No Such a Contact Name !","Error",JOptionPane.ERROR_MESSAGE); 	
				}
				}
				else {
					 JOptionPane.showMessageDialog(null,"Invalid Email format.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAddContact.setBounds(532, 240, 194, 44);
		contentPane.add(btnAddContact);
		
		JLabel lblNewLabel_1 = new JLabel("My Contacts");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(24, 9, 147, 43);
		contentPane.add(lblNewLabel_1);
		
	
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Contacts.class.getResource("/Images/123.jpg")));
		lblNewLabel.setBounds(0, 0, 852, 502);
		contentPane.add(lblNewLabel);
	
	}
}
