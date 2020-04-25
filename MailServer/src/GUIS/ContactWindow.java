package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.Contact;
import Classes.Folder;
import Classes.IndexMail;
import Classes.Mail;
import classes.DList;
import classes.SinglyLinkedList;
import interfaces.ILinkedList;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.ScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Choice;
import javax.swing.JToggleButton;
import java.awt.SystemColor;
import javax.swing.JList;

public class ContactWindow extends JFrame {

	private JPanel contentPane;
	private JPanel panelShowEmails;
	private JButton composeBtn;
	private JButton nextBtn;
	private JButton prevBtn;
	private JLabel pageLbl;
	private int currentPage=1;
	private static Contact currentUser = new Contact();
	private JLabel lblSearch;
	private JTextField textField;
	private Choice sort;
	private JButton refreshBtn;
	private JButton contactBtn;
	private JLabel Username;
	private JLabel lblSelectFolder;
	private JTextField addFolderTxt;
	private Choice folderSelect;
	private JLabel lblFolderName;
	private JButton btnAddFolder;
	private JButton btnDeleteSelectedFolder;
	private JLabel lblName;
	private JLabel bgImage;
	private JLabel lblSort;
	private static ILinkedList folderList = new SinglyLinkedList();
	private JButton btnRenameFolder;
	private static DefaultListModel modelShowEmail;
	private JList listEmail;
	private static ILinkedList folderIndex = new DList();
	private static String loadedFolder;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactWindow frame = new ContactWindow();
					frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		currentUser.setname(args[0]);
		currentUser.setemail(args[1]);
		currentUser.setpassword(args[2]);
		currentUser.setrepassword(args[2]);
		loadedFolder = args[3];
		folderList.clear();
		//Delete trash after 30 days after logging in
		File trash = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\Trash");
		Mail.deleteFromTrash(trash, null, true);
		//Load folders
		File mailFolder = new File ("D:\\MailServerData\\" + currentUser.getemail());
		String[] files = mailFolder.list();
		for (String filename : files) {
			File currentFile = new File(mailFolder, filename);
            if (currentFile.isDirectory()) {
            	folderList.add(filename);
            }
		}
		// Sort folders with original 4 at the top.
		for (int j = 1; j <= 4 ; j++) {
			String fName = "";
			if (j==1) {
				fName = "Inbox";
			}
			else if (j==2) {
				fName = "Draft";
			}
			else if (j==3) {
				fName = "Sent";
			}
			else {
				fName = "Trash";
			}
			for (int i = 1 ; i <= folderList.size() ; i++) {
				String folder = (String)folderList.get(i);
				if (fName.compareTo(folder)==0) {
					folderList.remove(i);
					folderList.add(j, fName);
					break;
				}
			}
		}
		//Load Emails for the Inbox (default selection)
		folderIndex = new DList();
		File currentFile = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\"+ loadedFolder);
		File currentIndex = new File(currentFile , "mailsfile.txt");
		
		Scanner myreader;
		
		try {	
			myreader = new Scanner(currentIndex);
			while(myreader.hasNextLine()) {
				String data=myreader.nextLine();
				if (data.isBlank()) {
					break;
				}
				IndexMail load = new IndexMail();
				load.setMailName(data);
				load.setPriority(Integer.parseInt(myreader.nextLine()));
				load.setFrom(myreader.nextLine());
				load.setTo(myreader.nextLine());
				load.setSubject(myreader.nextLine());
				folderIndex.add(load);		
			}
			myreader.close();
		}		
		catch (FileNotFoundException eee) {
		
		}
		
	}

	/**
	 * Create the frame.
	 */
	public ContactWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1025, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAddFolder = new JButton("Add Folder");
		btnAddFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean acceptableFolder = true;
				String folderName = addFolderTxt.getText();
				int folderNum = folderSelect.countItems();
				if(folderName.isBlank()) {
					JOptionPane.showMessageDialog(null,"The folder name field is blank.","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for (int i = 0 ; i < folderNum ; i++) {
					String folder = folderSelect.getItem(i);
					if (folderName.compareTo(folder)==0) {
						JOptionPane.showMessageDialog(null,"The specified folder name already exists.","Error",JOptionPane.ERROR_MESSAGE);
						acceptableFolder = false;
						break;
					}
				}
				if (acceptableFolder == true) {
					int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to create the selected folder\""+folderName+"\"?","Confirmation",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION) {
						File newFolder = new File("D:\\MailServerData\\" + currentUser.getemail() , folderName);
						newFolder.mkdir();
						File mailsFile = new File(newFolder, "mailsfile.txt");
						try {
							mailsFile.createNewFile();
						} catch (IOException ex) {
							
						}
						folderSelect.add(folderName);
					}
				}
			}
				
		});
		btnAddFolder.setForeground(Color.WHITE);
		btnAddFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnAddFolder.setBackground(SystemColor.textHighlight);
		btnAddFolder.setBounds(30, 273, 144, 42);
		contentPane.add(btnAddFolder);
		
		lblFolderName = new JLabel("Folder Name");
		lblFolderName.setHorizontalAlignment(SwingConstants.LEFT);
		lblFolderName.setForeground(Color.WHITE);
		lblFolderName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFolderName.setBackground(Color.WHITE);
		lblFolderName.setBounds(12, 343, 83, 31);
		contentPane.add(lblFolderName);
		
		addFolderTxt = new JTextField();
		addFolderTxt.setColumns(10);
		addFolderTxt.setBounds(104, 342, 145, 33);
		contentPane.add(addFolderTxt);
		
		lblSelectFolder = new JLabel("Select Folder");
		lblSelectFolder.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectFolder.setForeground(Color.WHITE);
		lblSelectFolder.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSelectFolder.setBackground(Color.WHITE);
		lblSelectFolder.setBounds(62, 497, 113, 23);
		contentPane.add(lblSelectFolder);
		
		folderSelect = new Choice();
		folderSelect.setBounds(30, 526, 219, 40);
		contentPane.add(folderSelect);
		
		for (int i = 1; i <= folderList.size() ; i++) {
			folderSelect.add((String)folderList.get(i));
		}
		folderSelect.select(loadedFolder);
		folderSelect.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modelShowEmail.clear();
				folderIndex = new DList();
				loadedFolder = folderSelect.getSelectedItem();
				File currentFile = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\"+ loadedFolder);
				File currentIndex = new File(currentFile , "mailsfile.txt");
				
				Scanner myreader;
				
				try {	
					myreader = new Scanner(currentIndex);
					while(myreader.hasNextLine()) {
						String data=myreader.nextLine();
						if (data.isBlank()) {
							break;
						}
						IndexMail load = new IndexMail();
						load.setMailName(data);
						load.setPriority(Integer.parseInt(myreader.nextLine()));
						load.setFrom(myreader.nextLine());
						load.setTo(myreader.nextLine());
						load.setSubject(myreader.nextLine());
						folderIndex.add(load);		
					}
					myreader.close();
				}		
				catch (FileNotFoundException ee) {
				
				}
				for (int i = 0 ; i < folderIndex.size() ; i++) {
					String add = ((IndexMail) folderIndex.get(i)).getPriority() + "   " + ((IndexMail) folderIndex.get(i)).getFrom() + "   " + ((IndexMail) folderIndex.get(i)).getTo() + "   " + ((IndexMail) folderIndex.get(i)).getSubject();
					modelShowEmail.add(i, add );
				}
				
					
			}});
		
		Username = new JLabel("Username");
		Username.setHorizontalAlignment(SwingConstants.LEFT);
		Username.setForeground(Color.WHITE);
		Username.setFont(new Font("Tahoma", Font.BOLD, 20));
		Username.setBackground(Color.WHITE);
		Username.setBounds(12, 16, 121, 23);
		contentPane.add(Username);
		
		panelShowEmails = new JPanel();
		panelShowEmails.setBackground(Color.WHITE);
		panelShowEmails.setBounds(306, 163, 620, 357);
		contentPane.add(panelShowEmails);
		panelShowEmails.setLayout(null);
		
		//Show emails here by modelShowEmail.add()
		modelShowEmail = new DefaultListModel();
	    listEmail = new JList(modelShowEmail);
		listEmail.setBounds(0, 0, 620, 357);
		listEmail.setFont(new Font("Tahoma",Font.BOLD, 15));
		for (int i = 0 ; i < folderIndex.size() ; i++) {
			String add = ((IndexMail) folderIndex.get(i)).getPriority() + "   " + ((IndexMail) folderIndex.get(i)).getFrom() + "   " + ((IndexMail) folderIndex.get(i)).getTo() + "   " + ((IndexMail) folderIndex.get(i)).getSubject();
			modelShowEmail.add(i, add );
		}
		panelShowEmails.add(listEmail);
		
		composeBtn = new JButton("New Message");
		composeBtn.setForeground(Color.WHITE);
		composeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] currentEmail = new String[1];
				currentEmail[0] = currentUser.getemail();
				ComposeMessage.main(currentEmail);
				
			}
		});
		composeBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		composeBtn.setBackground(SystemColor.textHighlight);
		composeBtn.setBounds(30, 218, 144, 42);
		contentPane.add(composeBtn);
		
		nextBtn = new JButton("Next Page");
		nextBtn.setForeground(Color.WHITE);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if less than last page number (after loading emails)
				currentPage ++;
				pageLbl.setText("Current Page: " + currentPage);
			}
		});
		nextBtn.setBackground(SystemColor.textHighlight);
		nextBtn.setBounds(852, 542, 131, 23);
		contentPane.add(nextBtn);
		
		prevBtn = new JButton("Previous Page");
		prevBtn.setForeground(Color.WHITE);
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage>1) {
					currentPage--;
					pageLbl.setText("Current Page: " + currentPage);
					// Go back a page here (once pages are developed)
				}
				else {
					// Error message or ignore ?
				}
			}
		});
		prevBtn.setBackground(SystemColor.textHighlight);
		prevBtn.setBounds(267, 542, 131, 23);
		contentPane.add(prevBtn);
		
		pageLbl = new JLabel("Current Page: " + currentPage);
		pageLbl.setForeground(Color.WHITE);
		pageLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		pageLbl.setBackground(Color.WHITE);
		pageLbl.setBounds(548, 542, 154, 23);
		contentPane.add(pageLbl);
		
		lblSearch = new JLabel("Search");
		lblSearch.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSearch.setBackground(Color.WHITE);
		lblSearch.setBounds(263, 59, 70, 23);
		contentPane.add(lblSearch);
		
		textField = new JTextField();
		textField.setBounds(372, 40, 391, 42);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblSort = new JLabel("Sort According To");
		lblSort.setHorizontalAlignment(SwingConstants.CENTER);
		lblSort.setForeground(Color.WHITE);
		lblSort.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSort.setBackground(Color.WHITE);
		lblSort.setBounds(263, 129, 154, 23);
		contentPane.add(lblSort);
		
		sort = new Choice();
		sort.add("Date & Time Ascendingly");
		sort.add("Date & Time Descendingly");
		sort.add("Alphabetical Order");
		sort.add("Reverse Alphabetical Order");
		sort.setBounds(436, 130, 267, 40);
		contentPane.add(sort);
		
		refreshBtn = new JButton("");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				String[] user = new String[4];
				user[0] = currentUser.getname();
				user[1] = currentUser.getemail();
				user[2] = currentUser.getpassword();
				user[3] = loadedFolder;
				modelShowEmail.clear();
				ContactWindow.main(user);
			}
		});
		refreshBtn.setIcon(new ImageIcon(ContactWindow.class.getResource("/Images/refresh.jpg")));
		refreshBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		refreshBtn.setBackground(Color.WHITE);
		refreshBtn.setBounds(889, 16, 54, 40);
		contentPane.add(refreshBtn);
		
		contactBtn = new JButton("Contacts");
		contactBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contacts.main(null);
				
			}
		});
		contactBtn.setForeground(Color.WHITE);
		contactBtn.setBounds(30, 163, 144, 42);
		contentPane.add(contactBtn);
		contactBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		contactBtn.setBackground(SystemColor.textHighlight);
		
		lblName = new JLabel(currentUser.getname());
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Agency FB", Font.BOLD, 28));
		lblName.setForeground(new Color(240, 255, 255));
		lblName.setBounds(22, 40, 227, 49);
		contentPane.add(lblName);
		
		btnDeleteSelectedFolder = new JButton("Delete Selected Folder");
		btnDeleteSelectedFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedFolder = folderSelect.getSelectedItem();
				if (selectedFolder.compareTo("Inbox")==0 || selectedFolder.compareTo("Draft")==0  || selectedFolder.compareTo("Sent")==0 || selectedFolder.compareTo("Trash")==0) {
					JOptionPane.showMessageDialog(null,"Cannot delete the essential folder \""+selectedFolder+"\".","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the selected folder\""+selectedFolder+"\"?","Confirmation",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION) {
						folderSelect.remove(selectedFolder);
						File deleted = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\" + selectedFolder);
						Folder.deleteFolder(deleted);
					}
					
				}
			}
		});
		btnDeleteSelectedFolder.setForeground(Color.WHITE);
		btnDeleteSelectedFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnDeleteSelectedFolder.setBackground(SystemColor.textHighlight);
		btnDeleteSelectedFolder.setBounds(12, 444, 218, 42);
		contentPane.add(btnDeleteSelectedFolder);
		
		btnRenameFolder = new JButton("Rename Selected Folder");
		btnRenameFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean acceptableFolder = true;
				String folderName = addFolderTxt.getText();
				String selectedFolder = folderSelect.getSelectedItem();
				if(folderName.isBlank()) {
					JOptionPane.showMessageDialog(null,"The folder name field is blank.","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (selectedFolder.compareTo("Inbox")==0 || selectedFolder.compareTo("Draft")==0  || selectedFolder.compareTo("Sent")==0 || selectedFolder.compareTo("Trash")==0) {
					JOptionPane.showMessageDialog(null,"Cannot rename the essential folder \""+selectedFolder+"\".","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					int folderNum = folderSelect.countItems();
					for (int i = 0 ; i < folderNum ; i++) {
						String folder = folderSelect.getItem(i);
						if (folderName.compareTo(folder)==0) {
							JOptionPane.showMessageDialog(null,"The specified folder name already exists.","Error",JOptionPane.ERROR_MESSAGE);
							acceptableFolder = false;
							break;
						}
					}
					if (acceptableFolder == true) {
						int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to rename the selected folder\""+selectedFolder+"\" to\""+folderName+"\"?","Confirmation",JOptionPane.YES_NO_OPTION);
								if(dialogResult == JOptionPane.YES_OPTION) {
									int index = folderSelect.getSelectedIndex();
									folderSelect.remove(index);
									folderSelect.insert(folderName, index);
									File old = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\" + selectedFolder);
									File renamed = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\" + folderName);
									old.renameTo(renamed);
								}
					}
				}
			}
				

		});
		btnRenameFolder.setForeground(Color.WHITE);
		btnRenameFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnRenameFolder.setBackground(SystemColor.textHighlight);
		btnRenameFolder.setBounds(12, 385, 219, 42);
		contentPane.add(btnRenameFolder);
		
		bgImage = new JLabel("");
		bgImage.setIcon(new ImageIcon(ContactWindow.class.getResource("/Images/opening-email-ss-1920.jpg")));
		bgImage.setBounds(0, -8, 1007, 592);
		contentPane.add(bgImage);
	}
}
