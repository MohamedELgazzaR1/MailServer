package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.SwingConstants;



public class Contacts extends JFrame {

	private JPanel contentPane;
	private JButton btnAddContact;
	private JTextField name;
	private static DefaultListModel DLM=new DefaultListModel();
	private static DefaultListModel DLM2=new DefaultListModel();
	private static JList Emails=new JList();
	private static JList Names=new JList();
	private static String accountName;
	private JTextField email;
	private JTextField extraEmail;
	private JLabel showcontact;
	private JLabel showmail;
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
	DLM2.clear();
	File myobj =new File("D:\\MailServerData\\"+accountName+"\\contacts");
	String[] names=myobj.list();
	
	for(String name : names) {
		String filename = name.split("\\.")[0];
	//	filename=Contact.toDate(filename);
		DLM.addElement(filename);	
		}
	Names.setModel(DLM);
	}

	/**
	 * Create the frame.
	 */
	public Contacts() {
		
		
	    ListSelectionListener listSelectionListener1 = new ListSelectionListener() {
		       
			@Override
			public void valueChanged(ListSelectionEvent e) {
			if(Emails.getSelectedValue()!=null) {	
				showmail.setText((String)Emails.getSelectedValue());
			}}
	    };
			Emails.addListSelectionListener(listSelectionListener1);
		
		
	    ListSelectionListener listSelectionListener = new ListSelectionListener() {
	       
			@Override
			public void valueChanged(ListSelectionEvent e) {
			if(Names.getSelectedValue()!=null) {	
				try {
					String[] con =((String) Names.getSelectedValue()).split("\\s+");
					showcontact.setText(con[0]);
					DLM2.clear();	
					String currSelection=(String)Names.getSelectedValue();
					File myobj =new File("D:\\MailServerData\\"+accountName+"\\Contacts\\"+currSelection+".txt");
					Scanner myreader;
					myreader = new Scanner(myobj);
					while(myreader.hasNextLine()) {
						String data=myreader.nextLine();
						DLM2.addElement(data);		
					}
					Emails.setModel(DLM2);
					myreader.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			
			}};
			Names.addListSelectionListener(listSelectionListener);
		
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 856, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton RemoveContact = new JButton("Remove Email");
		RemoveContact.setForeground(Color.WHITE);
		RemoveContact.setBackground(SystemColor.textHighlight);
		RemoveContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		RemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(Emails.getSelectedValue()==null)) {
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+(String) Emails.getSelectedValue()+ " Contact","Confirmation",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					File f = new File("D:\\MailServerData\\"+accountName+"\\Contacts\\"+(String)Names.getSelectedValue()+".txt");
					Folder.deleteData(f,(String)Emails.getSelectedValue(),0,false,null);
					DLM2.removeElement((String) Emails.getSelectedValue());
					showmail.setText("");
					JOptionPane.showMessageDialog(null,"Email Deleted Successfully !");
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"Please Select a Contact !");
				}
				
			}
		});
		
		JLabel lblExtraEmailName = new JLabel("Extra Email Name");
		lblExtraEmailName.setForeground(Color.WHITE);
		lblExtraEmailName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblExtraEmailName.setBounds(387, 354, 147, 43);
		contentPane.add(lblExtraEmailName);
		
		JLabel lblCurrentSelectedContact_1_1_1 = new JLabel("To");
		lblCurrentSelectedContact_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentSelectedContact_1_1_1.setForeground(Color.YELLOW);
		lblCurrentSelectedContact_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCurrentSelectedContact_1_1_1.setBounds(575, 286, 35, 43);
		contentPane.add(lblCurrentSelectedContact_1_1_1);
		
		JLabel lblCurrentSelectedContact_1_1 = new JLabel("To");
		lblCurrentSelectedContact_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentSelectedContact_1_1.setForeground(Color.YELLOW);
		lblCurrentSelectedContact_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCurrentSelectedContact_1_1.setBounds(575, 424, 35, 43);
		contentPane.add(lblCurrentSelectedContact_1_1);
		
		showmail = new JLabel("");
		showmail.setForeground(Color.YELLOW);
		showmail.setFont(new Font("Tahoma", Font.BOLD, 20));
		showmail.setBounds(612, 424, 230, 43);
		contentPane.add(showmail);
		
		showcontact = new JLabel("");
		showcontact.setForeground(Color.YELLOW);
		showcontact.setFont(new Font("Tahoma", Font.BOLD, 20));
		showcontact.setBounds(612, 286, 162, 43);
		contentPane.add(showcontact);
		
		JButton btnRemoveContact = new JButton("Remove Contact");
		btnRemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(Names.getSelectedValue()==null)) {
				String[] con =((String) Names.getSelectedValue()).split("\\s+");
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+con[0]+ " Contact","Confirmation",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					
					File f = new File("D:\\MailServerData\\"+accountName+"\\Contacts\\"+(String)Names.getSelectedValue()+".txt");
					f.delete();
					DLM.removeElement((String)Names.getSelectedValue());
					DLM2.clear();
					showcontact.setText("");
					JOptionPane.showMessageDialog(null,"Contact Deleted Succesfully !");
					
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"Please Select a Contact !");
				}
			}
		});
		btnRemoveContact.setForeground(Color.WHITE);
		btnRemoveContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRemoveContact.setBackground(SystemColor.textHighlight);
		btnRemoveContact.setBounds(10, 424, 171, 44);
		contentPane.add(btnRemoveContact);
		Names.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		Names.setBounds(10, 130, 171, 284);
		contentPane.add(Names);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setBounds(648, 93 ,147, 43);
		contentPane.add(lblEmail);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUserName.setBounds(416, 93,147, 43);
		contentPane.add(lblUserName);
		
		extraEmail = new JTextField();
		extraEmail.setColumns(10);
		extraEmail.setBounds(544, 354, 251, 48);
		contentPane.add(extraEmail);
		
		JButton btnAddExtraEamil = new JButton("Add Extra Email");
		btnAddExtraEamil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String extramail=extraEmail.getText();
			if(!(extramail.isBlank())) {
				if(Mail.checkmail(extramail)) {
					if(Contact.existEmail(extramail)) {
						if(!(Contact.existExtraEmail(accountName,(String)Names.getSelectedValue(),extramail))) {
							try {
								DLM2.addElement(extramail);
								File f=new File("D:\\MailServerData\\"+accountName+"\\Contacts\\"+(String)Names.getSelectedValue()+".txt");
								FileWriter wrt = new FileWriter(f, true);
								wrt.write(extramail+"\n");
								wrt.close();
								JOptionPane.showMessageDialog(null,"Email added Successfully !");
							} catch (FileNotFoundException e1) {
								
							} catch (IOException e1) {
								
							}
							
						}else {
							 JOptionPane.showMessageDialog(null,"Email is Already Exist ! ","Error",JOptionPane.ERROR_MESSAGE);
						}	
					}else {
						JOptionPane.showMessageDialog(null,"This email does not exist on this mail server !","Error",JOptionPane.ERROR_MESSAGE);
					}
					}else {
						JOptionPane.showMessageDialog(null,"Invalid Email format !","Error",JOptionPane.ERROR_MESSAGE);
					}
				
			}else {
				JOptionPane.showMessageDialog(null,"Please Fill Extra Email Field !","Error",JOptionPane.ERROR_MESSAGE);
			}
			}
			
		});
		btnAddExtraEamil.setForeground(Color.WHITE);
		btnAddExtraEamil.setBackground(SystemColor.textHighlight);
		btnAddExtraEamil.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddExtraEamil.setBounds(388, 284, 178, 48);
		contentPane.add(btnAddExtraEamil);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(648, 146, 194, 44);
		contentPane.add(email);
		
		
		Emails.setBounds(212, 130, 166, 284);
		contentPane.add(Emails);
		
		JButton SendMessage = new JButton("Send Message");
		SendMessage.setForeground(Color.WHITE);
		SendMessage.setBackground(SystemColor.textHighlight);
		SendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] currentEmail = new String[2];
				currentEmail[0] = accountName;
				currentEmail[1]=(String) Emails.getSelectedValue();
				ComposeMessage.main(currentEmail);
				
			}
		});
		SendMessage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SendMessage.setBounds(388, 424, 178, 44);
		contentPane.add(SendMessage);
		
		name = new JTextField();
		name.setBounds(416, 146, 194, 44);
		contentPane.add(name);
		name.setColumns(10);
		RemoveContact.setBounds(212, 424, 166, 44);
		contentPane.add(RemoveContact);
		
		btnAddContact = new JButton("Add Contact");
		btnAddContact.setForeground(Color.WHITE);
		btnAddContact.setBackground(SystemColor.textHighlight);
		btnAddContact.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date=new Date();
				long curr=date.getTime();
				String newName=name.getText()+"                                    "+String.valueOf(curr);
				String newEmail=email.getText();
				if(!(name.getText().isBlank()) && !(email.getText().isBlank())) {
				if(Mail.checkmail(newEmail)) {
					if(Contact.existEmail(newEmail)) {
						if(Contact.addContact(accountName,newName,newEmail)) {
							
						//	newName=Contact.toDate(newName);
							DLM.addElement(newName);
							Names.setModel(DLM);
							JOptionPane.showMessageDialog(null,"Contact added successfully.");
							name.setText("");
							email.setText("");
				}else {
					JOptionPane.showMessageDialog(null,"Error Adding Contact","Error",JOptionPane.ERROR_MESSAGE);
				}
				}
				else {
					JOptionPane.showMessageDialog(null,"This email does not exist on this mail server !","Error",JOptionPane.ERROR_MESSAGE); 	
				}
				}
				else {
					 JOptionPane.showMessageDialog(null,"Invalid Email format !","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				 JOptionPane.showMessageDialog(null,"Please Fill Both Username and Email Field !","Error",JOptionPane.ERROR_MESSAGE);
			}
			}
		});
		btnAddContact.setBounds(533, 204, 194, 44);
		contentPane.add(btnAddContact);
		
		JLabel lblContacts = new JLabel("My Contacts");
		lblContacts.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblContacts.setForeground(Color.WHITE);
		lblContacts.setBounds(24, 9, 147, 43);
		contentPane.add(lblContacts);
		
	
		JLabel LblImage = new JLabel("");
		LblImage.setForeground(Color.WHITE);
		LblImage.setIcon(new ImageIcon(Contacts.class.getResource("/Images/123.jpg")));
		LblImage.setBounds(0, 0, 852, 502);
		contentPane.add(LblImage);
	
		contentPane.add(LblImage);
	
		contentPane.add(LblImage);
	
		
	
	
	}
}
