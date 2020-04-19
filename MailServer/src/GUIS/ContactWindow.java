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

public class ContactWindow extends JFrame {

	private JPanel contentPane;
	private JPanel tabsPanel;
	private JButton inboxBtn;
	private JButton draftBtn;
	private JButton sentBtn;
	private JButton trashBtn;
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
		
		tabsPanel = new JPanel();
		tabsPanel.setBounds(30, 190, 100, 165);
		tabsPanel.setBackground(Color.WHITE);
		contentPane.add(tabsPanel);
		tabsPanel.setLayout(null);
		
		inboxBtn = new JButton("Inbox");
		inboxBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		inboxBtn.setBackground(Color.WHITE);
		inboxBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Highlight button of folder selected
				inboxBtn.setBackground(new Color(173, 216, 230));
				draftBtn.setBackground(Color.WHITE);
				sentBtn.setBackground(Color.WHITE);
				trashBtn.setBackground(Color.WHITE);
				
				inboxBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
				draftBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				sentBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				trashBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));

			}
		});
		inboxBtn.setBounds(0, 0, 102, 41);
		tabsPanel.add(inboxBtn);
		
		draftBtn = new JButton("Draft");
		draftBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		draftBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Highlight button of folder selected
				inboxBtn.setBackground(Color.WHITE);
				draftBtn.setBackground(new Color(173, 216, 230));
				sentBtn.setBackground(Color.WHITE);
				trashBtn.setBackground(Color.WHITE);
				
				inboxBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				draftBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
				sentBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				trashBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
		});
		draftBtn.setBackground(Color.WHITE);
		draftBtn.setBounds(0, 41, 102, 41);
		tabsPanel.add(draftBtn);
		
		sentBtn = new JButton("Sent");
		sentBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Highlight button of folder selected
				inboxBtn.setBackground(Color.WHITE);
				draftBtn.setBackground(Color.WHITE);
				sentBtn.setBackground(new Color(173, 216, 230));
				trashBtn.setBackground(Color.WHITE);
				
				inboxBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				draftBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				sentBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
				trashBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
		});
		sentBtn.setBackground(Color.WHITE);
		sentBtn.setBounds(0, 82, 102, 41);
		tabsPanel.add(sentBtn);
		
		trashBtn = new JButton("Trash");
		trashBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		trashBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Highlight button of folder selected
				inboxBtn.setBackground(Color.WHITE);
				draftBtn.setBackground(Color.WHITE);
				sentBtn.setBackground(Color.WHITE);
				trashBtn.setBackground(new Color(173, 216, 230));
				
				inboxBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				draftBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				sentBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				trashBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
			}
		});
		trashBtn.setBackground(Color.WHITE);
		trashBtn.setBounds(0, 123, 102, 41);
		tabsPanel.add(trashBtn);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(162, 115, 711, 315);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		composeBtn = new JButton("New Message");
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
		composeBtn.setBackground(Color.CYAN);
		composeBtn.setBounds(8, 34, 144, 42);
		contentPane.add(composeBtn);
		
		nextBtn = new JButton("Next Page");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if less than last page number (after loading emails)
				currentPage ++;
				pageLbl.setText("Current Page: " + currentPage);
			}
		});
		nextBtn.setBackground(Color.LIGHT_GRAY);
		nextBtn.setBounds(599, 522, 131, 23);
		contentPane.add(nextBtn);
		
		prevBtn = new JButton("Previous Page");
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
		prevBtn.setBackground(Color.LIGHT_GRAY);
		prevBtn.setBounds(211, 522, 131, 23);
		contentPane.add(prevBtn);
		
		pageLbl = new JLabel("Current Page: " + currentPage);
		pageLbl.setForeground(Color.WHITE);
		pageLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		pageLbl.setBackground(Color.WHITE);
		pageLbl.setBounds(390, 520, 154, 23);
		contentPane.add(pageLbl);
		
		lblSearch = new JLabel("Search");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSearch.setBackground(Color.WHITE);
		lblSearch.setBounds(197, 25, 154, 23);
		contentPane.add(lblSearch);
		
		textField = new JTextField();
		textField.setBounds(372, 14, 391, 42);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblSort = new JLabel("Sort According To");
		lblSort.setHorizontalAlignment(SwingConstants.CENTER);
		lblSort.setForeground(Color.WHITE);
		lblSort.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSort.setBackground(Color.WHITE);
		lblSort.setBounds(197, 75, 154, 23);
		contentPane.add(lblSort);
		
		sort = new Choice();
		sort.add("Date & Time Ascendingly");
		sort.add("Date & Time Descendingly");
		sort.add("Alphabetical Order");
		sort.add("Reverse Alphabetical Order");
		sort.setBounds(375, 75, 267, 40);
		contentPane.add(sort);
		
		refreshBtn = new JButton("");
		refreshBtn.setIcon(new ImageIcon(ContactWindow.class.getResource("/Images/refresh.jpg")));
		refreshBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		refreshBtn.setBackground(Color.WHITE);
		refreshBtn.setBounds(889, 16, 54, 40);
		contentPane.add(refreshBtn);
		
		contactBtn = new JButton("Contacts");
		contactBtn.setBounds(8, 115, 144, 42);
		contentPane.add(contactBtn);
		contactBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		contactBtn.setBackground(Color.CYAN);
		
		addFolderBtn = new JButton("Add Folder");
		addFolderBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		addFolderBtn.setBackground(Color.CYAN);
		addFolderBtn.setBounds(31, 467, 144, 42);
		contentPane.add(addFolderBtn);
		
		JLabel bgImage = new JLabel("");
		bgImage.setIcon(new ImageIcon(ContactWindow.class.getResource("/Images/5195ebb8c5f9772deda82aa2937134d3 (4).jpg")));
		bgImage.setBounds(0, -8, 1007, 592);
		contentPane.add(bgImage);
	}
}
