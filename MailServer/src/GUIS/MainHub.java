package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.App;
import Classes.Contact;
import Classes.Folder;
import Classes.IndexMail;
import Classes.Mail;
import classes.DList;
import classes.SinglyLinkedList;
import interfaces.ILinkedList;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.ListSelectionModel;

import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainHub extends JFrame {

	private JPanel contentPane;
	private JButton composeBtn;
	private JButton nextBtn;
	private JButton prevBtn;
	private JLabel pageLbl;
	private static int currentPage=1;
	private static Contact currentUser = new Contact();
	private JLabel lblSearch;
	private JTextField searchField;
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
	private JTable table;
	private static ILinkedList folderIndex = new DList();
	private static String loadedFolder;
	private DefaultTableModel modelShowEmail;
	private final int mailsPerPage = 15;
	private int pagesPerFolder;
	private int pageStart;
	private int pageEnd;
	private int lastPageMails;
	private IndexMail[] pageArray;
	private JButton btnMoveSelectedEmails;
	private JButton btnDeleteSelectedEmails;
	private JButton btnRestoreSelectedEmails;
	private static ILinkedList selectedEmails = new SinglyLinkedList();
	private String sortType = "Newest first";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainHub frame = new MainHub();
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
		currentPage = Integer.parseInt(args[4]);
		folderList.clear();
		//Delete trash after 30 days after logging in
		File trash = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\Trash");
		Mail.deleteFromTrash(trash, null, true);
		//Load folders
		File mailFolder = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders");
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
		File currentFile = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\"+ loadedFolder);
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
				folderIndex.add(0, load);		
			}
			myreader.close();
		}		
		catch (FileNotFoundException eee) {
		
		}
		
	}

	/**
	 * Create the frame.
	 */
	public MainHub() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1025, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(259, 200, 708, 264);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelShowEmail = new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
				},
				new String[] {
					"Select", "Priority", "Subject", "From", "To", "Date"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, Integer.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
						true, false, false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
			
		};
		table.setModel(modelShowEmail);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(0).setPreferredWidth(47);
		table.getColumnModel().getColumn(1).setPreferredWidth(47);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(140);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		//Open double clicked email
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		           int index = table.getSelectedRow();
		           File email = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\"+ loadedFolder,pageArray[index].getMailName());
		           File contents = new File(email,"indexfile.txt");
		           File attachments = new File(email,"Attachments");
		           Scanner myreader;
		   		
		   		try {	
		   			myreader = new Scanner(contents);
		   			String body = "";
		   			
		   				String data=myreader.nextLine();
		   				String[] composeData = new String[7];
		   				composeData[0] = currentUser.getemail();
		   				//from
		   				composeData[2] = myreader.nextLine();
		   				//to
		   				composeData[1] = myreader.nextLine();
		   				//priority
		   				composeData[3] = myreader.nextLine();
		   				//subject
		   				composeData[4] = myreader.nextLine();
		   				//skip attachments line
		   				myreader.nextLine();
		   				//body
		   				String add = "";
		   				while(myreader.hasNextLine()) {
		   					 
		   					body += myreader.nextLine();
		   					body += "\n";
		   					
		   				}
		   				composeData[5] = body;
		   				composeData[6] = attachments.getAbsolutePath();
		   			myreader.close();
		   			ComposeMessage.main(composeData);
		   		}		
		   		catch (FileNotFoundException eeee) {
		   			JOptionPane.showMessageDialog(null,"Error.","Error",JOptionPane.ERROR_MESSAGE);
		   		}
		        }
		    }
		});

		scrollPane.setViewportView(table);
		
		//Show emails for the first time
		pagesPerFolder = (folderIndex.size( )/ mailsPerPage);
		lastPageMails = folderIndex.size() % mailsPerPage;
		if (lastPageMails > 0) {
			pagesPerFolder++;
		}
		pageStart = (currentPage - 1)*mailsPerPage;
		if (lastPageMails > 0 && currentPage == pagesPerFolder) {
			pageEnd = (pageStart + lastPageMails) - 1 ;
		}
		else {
			pageEnd = (currentPage * mailsPerPage) - 1;
		}
		
		pageArray = new IndexMail[mailsPerPage];
		if(!folderIndex.isEmpty()) {
			for(int i = pageStart , j = 0 ; i <= pageEnd && j < mailsPerPage ; i++ , j++) {
				pageArray[j] = (IndexMail) folderIndex.get(i);
			}
		}
		
		for (int i = 0 ; i < mailsPerPage ; i++) {
			for (int j = 1; j < 6 ; j++) {
				if (pageArray[i]!=null) {
					String add = "";
					if (j==1) {
						add = "" + pageArray[i].getPriority();

					}
					else if (j==2) {
						add = pageArray[i].getSubject();

					}
					else if (j==3) {
						add = pageArray[i].getFrom();

					}
					else if (j==4) {
						add = pageArray[i].getTo() ;

					}
					else {
						long temp = Long.parseLong(pageArray[i].getMailName());
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
						Date resultdate = new Date(temp);
						add = sdf.format(resultdate);
					}
					modelShowEmail.setValueAt(add, i, j);
				}
			}
		}
		
		
		
		lblFolderName = new JLabel("Folder Name");
		lblFolderName.setHorizontalAlignment(SwingConstants.LEFT);
		lblFolderName.setForeground(Color.WHITE);
		lblFolderName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFolderName.setBackground(Color.WHITE);
		lblFolderName.setBounds(12, 412, 83, 31);
		contentPane.add(lblFolderName);
		
		addFolderTxt = new JTextField();
		addFolderTxt.setColumns(10);
		addFolderTxt.setBounds(104, 410, 145, 33);
		contentPane.add(addFolderTxt);
		
		lblSelectFolder = new JLabel("Select Folder");
		lblSelectFolder.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectFolder.setForeground(Color.WHITE);
		lblSelectFolder.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSelectFolder.setBackground(Color.WHITE);
		lblSelectFolder.setBounds(12, 456, 113, 23);
		contentPane.add(lblSelectFolder);
		
		folderSelect = new Choice();
		folderSelect.setBounds(30, 485, 219, 40);
		contentPane.add(folderSelect);
		
		for (int i = 1; i <= folderList.size() ; i++) {
			folderSelect.add((String)folderList.get(i));
		}
		folderSelect.select(loadedFolder);
		ItemListener updateTable = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// clear table
				for (int i = 0 ; i < table.getRowCount() ; i++) {
					for (int j = 0 ; j < table.getColumnCount() ; j++) {
						modelShowEmail.setValueAt(null, i, j);
					}
				}
				// Reset page if selection changed
				if (loadedFolder.compareTo(folderSelect.getSelectedItem())!=0) {
					currentPage = 1;
					pageLbl.setText("Current Page: " + currentPage);
				}
				
				folderIndex = new DList();
				loadedFolder = folderSelect.getSelectedItem();
				
				//Show restore button if loaded folder is Trash folder and hide it otherwise
				if (loadedFolder.compareTo("Trash")==0) {
					btnRestoreSelectedEmails.setVisible(true);
				}
				else {
					btnRestoreSelectedEmails.setVisible(false);
				}
				
				
				File currentFile = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\"+ loadedFolder);
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
						folderIndex.add(0, load);		
					}
					myreader.close();
				}		
				catch (FileNotFoundException ee) {
				
				}
				pagesPerFolder = (folderIndex.size( )/ mailsPerPage);
				lastPageMails = folderIndex.size() % mailsPerPage;
				if (lastPageMails > 0) {
					pagesPerFolder++;
				}
				pageStart = (currentPage - 1)*mailsPerPage;
				if (lastPageMails > 0 && currentPage == pagesPerFolder) {
					pageEnd = (pageStart + lastPageMails) - 1 ;
				}
				else {
					pageEnd = (currentPage * mailsPerPage) - 1;
				}
				
				pageArray = new IndexMail[mailsPerPage];
				if(!folderIndex.isEmpty()) {
					for(int i = pageStart , j = 0 ; i <= pageEnd && j < mailsPerPage ; i++ , j++) {
						pageArray[j] = (IndexMail) folderIndex.get(i);
					}
				}
				
				for (int i = 0 ; i < mailsPerPage ; i++) {
					for (int j = 1; j < 6 ; j++) {
						if (pageArray[i]!=null) {
							String add = "";
							if (j==1) {
								add = "" + pageArray[i].getPriority();

							}
							else if (j==2) {
								add = pageArray[i].getSubject();

							}
							else if (j==3) {
								add = pageArray[i].getFrom();

							}
							else if (j==4) {
								add = pageArray[i].getTo() ;

							}
							else {
								long temp = Long.parseLong(pageArray[i].getMailName());
								SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
								Date resultdate = new Date(temp);
								add = sdf.format(resultdate);
							}
							modelShowEmail.setValueAt(add, i, j);
						}
					}
				}
				
					
			}};
			folderSelect.addItemListener(updateTable);
		
		Username = new JLabel("Username");
		Username.setHorizontalAlignment(SwingConstants.LEFT);
		Username.setForeground(Color.WHITE);
		Username.setFont(new Font("Tahoma", Font.BOLD, 20));
		Username.setBackground(Color.WHITE);
		Username.setBounds(12, 16, 121, 23);
		contentPane.add(Username);
		
		
		
		composeBtn = new JButton("New Message");
		composeBtn.setForeground(Color.WHITE);
		composeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data = new String[2];
				data[0] = currentUser.getemail();
				data[1] = null;
				ComposeMessage.main(data);
				
			}
		});
		composeBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		composeBtn.setBackground(SystemColor.textHighlight);
		composeBtn.setBounds(30, 248, 144, 42);
		contentPane.add(composeBtn);
		
		nextBtn = new JButton("Next Page");
		nextBtn.setForeground(Color.WHITE);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage < pagesPerFolder) {
					currentPage ++;
					pageLbl.setText("Current Page: " + currentPage);
					updateTable.itemStateChanged(null);
				}
			}
		});
		nextBtn.setBackground(SystemColor.textHighlight);
		nextBtn.setBounds(731, 538, 131, 23);
		contentPane.add(nextBtn);
		
		prevBtn = new JButton("Previous Page");
		prevBtn.setForeground(Color.WHITE);
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage>1) {
					currentPage--;
					pageLbl.setText("Current Page: " + currentPage);
					updateTable.itemStateChanged(null);
				}
				
			}
		});
		prevBtn.setBackground(SystemColor.textHighlight);
		prevBtn.setBounds(292, 538, 131, 23);
		contentPane.add(prevBtn);
		
		pageLbl = new JLabel("Current Page: " + currentPage);
		pageLbl.setForeground(Color.WHITE);
		pageLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		pageLbl.setBackground(Color.WHITE);
		pageLbl.setBounds(507, 537, 154, 23);
		contentPane.add(pageLbl);
		
		lblSearch = new JLabel("Search");
		lblSearch.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSearch.setBackground(Color.WHITE);
		lblSearch.setBounds(263, 59, 70, 23);
		contentPane.add(lblSearch);
		
		searchField = new JTextField();
		searchField.setBounds(372, 40, 334, 42);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		lblSort = new JLabel("Sort According To");
		lblSort.setHorizontalAlignment(SwingConstants.CENTER);
		lblSort.setForeground(Color.WHITE);
		lblSort.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSort.setBackground(Color.WHITE);
		lblSort.setBounds(263, 129, 154, 23);
		contentPane.add(lblSort);
		
		sort = new Choice();
		sort.add("Newest first");
		sort.add("Oldest first");
		sort.add("Alphabetical Order");
		sort.add("Reverse Alphabetical Order");
		sort.setBounds(436, 130, 267, 40);
		ItemListener updateSort = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//Sort 
				sortType = sort.getSelectedItem();
				updateTable.itemStateChanged(null);
					
			}};
			sort.addItemListener(updateSort);
		contentPane.add(sort);
		
		refreshBtn = new JButton("");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//dispose();
				//String[] user = new String[6];
				//user[0] = currentUser.getname();
				//user[1] = currentUser.getemail();
				//user[2] = currentUser.getpassword();
				//user[3] = loadedFolder;
				//user[4] = "" + currentPage;
				updateTable.itemStateChanged(null);
				//MainHub.main(user);
			}
		});
		refreshBtn.setIcon(new ImageIcon(MainHub.class.getResource("/Images/refresh.jpg")));
		refreshBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		refreshBtn.setBackground(Color.WHITE);
		refreshBtn.setBounds(889, 16, 54, 40);
		contentPane.add(refreshBtn);
		
		contactBtn = new JButton("Contacts");
		contactBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data = new String[1];
				data[0] = currentUser.getemail();
				Contacts.main(data);
				
			}
		});
		contactBtn.setForeground(Color.WHITE);
		contactBtn.setBounds(30, 193, 144, 42);
		contentPane.add(contactBtn);
		contactBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		contactBtn.setBackground(SystemColor.textHighlight);
		
		lblName = new JLabel(currentUser.getname());
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Agency FB", Font.BOLD, 28));
		lblName.setForeground(new Color(240, 255, 255));
		lblName.setBounds(22, 40, 227, 49);
		contentPane.add(lblName);
		
		
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
						File newFolder = new File("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders" , folderName);
						newFolder.mkdir();
						File mailsFile = new File(newFolder, "mailsfile.txt");
						try {
							mailsFile.createNewFile();
						} catch (IOException ex) {
							
						}
						folderSelect.add(folderName);
						folderSelect.select(folderName);
						addFolderTxt.setText(null);
						updateTable.itemStateChanged(null);
					}
				}
			}
				
		});
		btnAddFolder.setForeground(Color.WHITE);
		btnAddFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnAddFolder.setBackground(SystemColor.textHighlight);
		btnAddFolder.setBounds(30, 303, 144, 42);
		contentPane.add(btnAddFolder);
		
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
						File deleted = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\" + selectedFolder);
						Folder.deleteFolder(deleted);
						folderSelect.select("Inbox");
						updateTable.itemStateChanged(null);
					}
					
				}
			}
		});
		btnDeleteSelectedFolder.setForeground(Color.WHITE);
		btnDeleteSelectedFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnDeleteSelectedFolder.setBackground(SystemColor.textHighlight);
		btnDeleteSelectedFolder.setBounds(31, 519, 218, 42);
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
									File old = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\" + selectedFolder);
									File renamed = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\" + folderName);
									old.renameTo(renamed);
								}
								addFolderTxt.setText(null);
								folderSelect.select(folderName);
								updateTable.itemStateChanged(null);
					}
				}
			}
				

		});
		btnRenameFolder.setForeground(Color.WHITE);
		btnRenameFolder.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnRenameFolder.setBackground(SystemColor.textHighlight);
		btnRenameFolder.setBounds(30, 356, 194, 42);
		contentPane.add(btnRenameFolder);
		
		btnMoveSelectedEmails = new JButton("Move Selected Emails");
		btnMoveSelectedEmails.setForeground(Color.WHITE);
		btnMoveSelectedEmails.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnMoveSelectedEmails.setBackground(SystemColor.textHighlight);
		btnMoveSelectedEmails.setBounds(283, 475, 174, 42);
		btnMoveSelectedEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectedEmails.clear();
				for (int i = 0 ; i < mailsPerPage ; i++) {
					Object tmp = modelShowEmail.getValueAt(i, 0);
					try {
						boolean select = (boolean)tmp;
						File selectedFile = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\"+ loadedFolder +"\\" + pageArray[i].getMailName() );
						selectedEmails.add(selectedFile);
					}
					catch (Exception eeeeee) {
					
					}
				}
				if(!selectedEmails.isEmpty()) {
					int acceptableMoves = folderSelect.countItems()-1;
					String[] folders = new String[acceptableMoves];
					for (int i = 0, j = 0 ; i < acceptableMoves && j < folderSelect.countItems() ; j++) {
						String name = (String)folderSelect.getItem(j);
						if (name.compareTo(loadedFolder)!=0) {
							folders[i] = name;
							i++;
						}
					}
					String dest = (String)JOptionPane.showInputDialog(null,"Move the selected email(s) to", "Select Folder",JOptionPane.INFORMATION_MESSAGE,null, folders, folders[0]);

					
					if ((dest != null) && (dest.length() > 0)) {
					    Folder destinationFolder = new Folder("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\"+ dest);
					    App move = new App();
					    move.moveEmails(selectedEmails, destinationFolder);
					}
					updateTable.itemStateChanged(null);
					
				}
				else {
					JOptionPane.showMessageDialog(null,"No emails selected.","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		contentPane.add(btnMoveSelectedEmails);
		
		btnDeleteSelectedEmails = new JButton("Delete Selected Emails");
		btnDeleteSelectedEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					selectedEmails.clear();
					for (int i = 0 ; i < mailsPerPage ; i++) {
						Object tmp = modelShowEmail.getValueAt(i, 0);
						try {
							boolean select = (boolean)tmp;
							File selectedFile = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\"+ loadedFolder +"\\" + pageArray[i].getMailName() );
							selectedEmails.add(selectedFile);
						}
						catch (Exception eeeee) {
						
						}
					}
					if(!selectedEmails.isEmpty()) {
						int dialogResult;
						if (loadedFolder.compareTo("Trash")!=0){
							dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the selected email(s)?","Confirmation",JOptionPane.YES_NO_OPTION);
							
						}
						else {
							dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to permanently delete the selected email(s)?","Confirmation",JOptionPane.YES_NO_OPTION);
						}
						if(dialogResult == JOptionPane.YES_OPTION) {
							App deletion = new App();
							deletion.deleteEmails(selectedEmails);
							updateTable.itemStateChanged(null);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"No emails selected.","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			
		});
		btnDeleteSelectedEmails.setForeground(Color.WHITE);
		btnDeleteSelectedEmails.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnDeleteSelectedEmails.setBackground(SystemColor.textHighlight);
		btnDeleteSelectedEmails.setBounds(716, 475, 174, 42);
		contentPane.add(btnDeleteSelectedEmails);
		
		btnRestoreSelectedEmails = new JButton("Restore Selected Emails");
		btnRestoreSelectedEmails.setVisible(false);
		btnRestoreSelectedEmails.setForeground(Color.WHITE);
		btnRestoreSelectedEmails.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnRestoreSelectedEmails.setBackground(SystemColor.textHighlight);
		btnRestoreSelectedEmails.setBounds(492, 475, 194, 42);
		btnRestoreSelectedEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectedEmails.clear();
				for (int i = 0 ; i < mailsPerPage ; i++) {
					Object tmp = modelShowEmail.getValueAt(i, 0);
					try {
						boolean select = (boolean)tmp;
						File selectedFile = new File ("D:\\MailServerData\\" + currentUser.getemail() + "\\Mail Folders\\"+ loadedFolder +"\\" + pageArray[i].getMailName() );
						selectedEmails.add(selectedFile);
					}
					catch (Exception eeeee) {
					
					}
				}
				if(!selectedEmails.isEmpty()) {
					int dialogResult;
					dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to restore the selected email(s)?","Confirmation",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION) {
						App restoration = new App();
						restoration.restoreEmails(selectedEmails);
						updateTable.itemStateChanged(null);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"No emails selected.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
				
		});
		contentPane.add(btnRestoreSelectedEmails);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchText = searchField.getText();
				// Search here using linear search and return all hits.
			}
		});
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnSearch.setBackground(SystemColor.textHighlight);
		btnSearch.setBounds(718, 40, 144, 42);
		contentPane.add(btnSearch);
		
		bgImage = new JLabel("");
		bgImage.setIcon(new ImageIcon(MainHub.class.getResource("/Images/opening-email-ss-1920.jpg")));
		bgImage.setBounds(0, -8, 1007, 592);
		contentPane.add(bgImage);
	}
}
