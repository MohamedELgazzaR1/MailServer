package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.Contact;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.ScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Choice;
import javax.swing.JToggleButton;
import java.awt.SystemColor;

public class ContactWindow extends JFrame {

	private JPanel contentPane;
	private JPanel panel_1;
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
	private JButton addFolderBtn;
	private JLabel Username;
	private JLabel lblSelectFolder;
	

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
	}

	/**
	 * Create the frame.
	 */
	public ContactWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1025, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblSelectFolder = new JLabel("Select Folder");
		lblSelectFolder.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectFolder.setForeground(Color.WHITE);
		lblSelectFolder.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSelectFolder.setBackground(Color.WHITE);
		lblSelectFolder.setBounds(31, 358, 113, 23);
		contentPane.add(lblSelectFolder);
		
		Choice FolderSelect = new Choice();
		FolderSelect.setBounds(31, 411, 219, 40);
		contentPane.add(FolderSelect);
		FolderSelect.add("Inbox");
		FolderSelect.add("Trash");
		FolderSelect.add("");
		
		Username = new JLabel("UserName");
		Username.setHorizontalAlignment(SwingConstants.LEFT);
		Username.setForeground(Color.WHITE);
		Username.setFont(new Font("Tahoma", Font.BOLD, 20));
		Username.setBackground(Color.WHITE);
		Username.setBounds(12, 16, 121, 23);
		contentPane.add(Username);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(256, 205, 711, 315);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		composeBtn = new JButton("New Message");
		composeBtn.setForeground(Color.WHITE);
		composeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ComposeMessage compose = new ComposeMessage();
				String[] currentEmail = new String[1];
				currentEmail[0] = currentUser.getemail();
				compose.main(currentEmail);
				//compose.setVisible(true);
				
			}
		});
		composeBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		composeBtn.setBackground(SystemColor.textHighlight);
		composeBtn.setBounds(31, 281, 144, 42);
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
		nextBtn.setBounds(731, 538, 131, 23);
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
		
		textField = new JTextField();
		textField.setBounds(372, 40, 391, 42);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblSort = new JLabel("Sort According To");
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
		refreshBtn.setIcon(new ImageIcon(ContactWindow.class.getResource("/Images/refresh.jpg")));
		refreshBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		refreshBtn.setBackground(Color.WHITE);
		refreshBtn.setBounds(889, 16, 54, 40);
		contentPane.add(refreshBtn);
		
		contactBtn = new JButton("Contacts");
		contactBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contacts contacts=new Contacts();
				contacts.setVisible(true);
				
			}
		});
		contactBtn.setForeground(Color.WHITE);
		contactBtn.setBounds(31, 205, 144, 42);
		contentPane.add(contactBtn);
		contactBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		contactBtn.setBackground(SystemColor.textHighlight);
		
		addFolderBtn = new JButton("Add Folder");
		addFolderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		addFolderBtn.setForeground(Color.WHITE);
		addFolderBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		addFolderBtn.setBackground(SystemColor.textHighlight);
		addFolderBtn.setBounds(31, 455, 144, 42);
		contentPane.add(addFolderBtn);
		
		JLabel lblName = new JLabel(currentUser.getname());
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Agency FB", Font.BOLD, 28));
		lblName.setForeground(new Color(240, 255, 255));
		lblName.setBounds(22, 40, 227, 49);
		contentPane.add(lblName);
		
		JLabel bgImage = new JLabel("");
		bgImage.setIcon(new ImageIcon(ContactWindow.class.getResource("/Images/opening-email-ss-1920.jpg")));
		bgImage.setBounds(0, -8, 1007, 592);
		contentPane.add(bgImage);
	}
}
