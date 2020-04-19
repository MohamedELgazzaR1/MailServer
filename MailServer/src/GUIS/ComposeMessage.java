package GUIS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class ComposeMessage extends JFrame{

	private JPanel contentPane;
	private JLabel toLbl;
	private JTextPane toField;
	private JLabel subjectLbl;
	private JTextPane subjectField;
	private JTextPane messageField;
	private JButton sendBtn;
	private JButton addAttachBtn;
	private JButton discardBtn;
	private JButton draftBtn;
	private static String currentEmail = "";
	private Choice priority;
	private JLabel priorityLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComposeMessage frame = new ComposeMessage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		currentEmail = args[0];
	}

	/**
	 * Create the application.
	 */
	public ComposeMessage() {
		
		setBounds(100, 100, 1025, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		toLbl = new JLabel("To");
		toLbl.setHorizontalAlignment(SwingConstants.CENTER);
		toLbl.setFont(new Font("Tahoma", Font.BOLD, 21));
		toLbl.setBounds(62, 29, 40, 39);
		contentPane.add(toLbl);
		
		toField = new JTextPane();
		toField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		toField.setBounds(134, 29, 492, 39);
		contentPane.add(toField);
		
		subjectLbl = new JLabel("Subject");
		subjectLbl.setHorizontalAlignment(SwingConstants.CENTER);
		subjectLbl.setFont(new Font("Tahoma", Font.BOLD, 21));
		subjectLbl.setBounds(21, 79, 94, 39);
		contentPane.add(subjectLbl);
		
		subjectField = new JTextPane();
		subjectField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		subjectField.setBounds(134, 79, 492, 39);
		contentPane.add(subjectField);
		
		messageField = new JTextPane();
		messageField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		messageField.setBounds(31, 139, 863, 290);
		contentPane.add(messageField);
		
		sendBtn = new JButton("Send Message");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get list of recipients
				String[] emailList = new String[100];
				int recipientNum = 0;
				String toFieldInput = toField.getText();
				String hold = "";
				for (int i = 0; i < toFieldInput.length(); i++) {
					if (toFieldInput.charAt(i) != ',') {
						hold += toFieldInput.charAt(i);
					}
					else {
						if (i==0) {
							JOptionPane.showMessageDialog(null,"Invalid format for list of recipients!","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}
						else {
							emailList[recipientNum] = hold;
							hold = "";
							recipientNum++;
						}
					}
					
				}
				// Check email format
				String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
				for (int i = 0; i < recipientNum ; i++) {
					if(!emailList[i].matches(regex)){
						JOptionPane.showMessageDialog(null,"Invalid Email format!","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				
				// Prepare subject line and message contents
				String subject = subjectField.getText();
				String message = messageField.getText();
			}
		});
		sendBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		sendBtn.setBackground(Color.CYAN);
		sendBtn.setBounds(50, 492, 144, 42);
		contentPane.add(sendBtn);
		
		addAttachBtn = new JButton("Add Attachments");
		addAttachBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		addAttachBtn.setBackground(Color.CYAN);
		addAttachBtn.setBounds(719, 492, 144, 42);
		contentPane.add(addAttachBtn);
		
		discardBtn = new JButton("Discard");
		discardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Discard the message and close the window after confirmation.
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to want to discard this message?","Confirmation",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
			
		});
		discardBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		discardBtn.setBackground(Color.CYAN);
		discardBtn.setBounds(496, 492, 144, 42);
		contentPane.add(discardBtn);
		
		draftBtn = new JButton("Save Draft");
		draftBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get list of recipients
				String[] emailList = new String[100];
				int recipientNum = 0;
				String toFieldInput = toField.getText();
				String hold = "";
				for (int i = 0; i < toFieldInput.length(); i++) {
					if (toFieldInput.charAt(i) != ',') {
						hold += toFieldInput.charAt(i);
					}
					else {
						if (i==0) {
							JOptionPane.showMessageDialog(null,"Invalid format for list of recipients!","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}
						else {
							emailList[recipientNum] = hold;
							hold = "";
							recipientNum++;
						}
					}
					
				}
				// Check email format
				String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
				for (int i = 0; i < recipientNum ; i++) {
					if(!emailList[i].matches(regex)){
						JOptionPane.showMessageDialog(null,"Invalid Email format!","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				
				// Prepare subject line and message contents
				String subject = subjectField.getText();
				String message = messageField.getText();
				
				File mailfolder = new File("D:\\MailServerData\\" + currentEmail + "\\Draft"  , "1");
				mailfolder.mkdir();
				File mail = new File(mailfolder, "mailcontent.txt");
				try {
					mail.createNewFile();
				} catch (IOException ee) {
					
				}
				
			}
		});
		draftBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		draftBtn.setBackground(Color.CYAN);
		draftBtn.setBounds(273, 492, 144, 42);
		contentPane.add(draftBtn);
		
		priority = new Choice();
		priority.add("Normal");
		priority.add("Very High");
		priority.add("High");
		priority.add("Low");
		priority.add("Very Low");
		priority.setBounds(758, 56, 136, 20);
		contentPane.add(priority);
		
		priorityLbl = new JLabel("Priority");
		priorityLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		priorityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		priorityLbl.setBounds(649, 56, 104, 20);
		contentPane.add(priorityLbl);
		
	}
}
