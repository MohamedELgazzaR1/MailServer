package GUIS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Classes.App;
import Classes.Contact;
import Classes.Mail;
import classes.*;
import interfaces.*;


import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import java.awt.SystemColor;
import javax.swing.ImageIcon;

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
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	
	ILinkedList attaches = new SinglyLinkedList();
	private JButton draftBtn_1;
	private JButton btnClear;
	private JButton btnViewAttachments;
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
		
		Choice priority_1 = new Choice();
		priority_1.setBounds(829, 138, 143, 22);
		contentPane.add(priority_1);
		
		btnViewAttachments = new JButton("View Attachments");
		btnViewAttachments.setForeground(Color.WHITE);
		btnViewAttachments.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnViewAttachments.setBackground(SystemColor.textHighlight);
		btnViewAttachments.setBounds(829, 90, 144, 42);
		contentPane.add(btnViewAttachments);
		
		btnClear = new JButton("Clear Text");
		btnClear.setForeground(Color.WHITE);
		btnClear.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnClear.setBackground(SystemColor.textHighlight);
		btnClear.setBounds(21, 166, 104, 42);
		contentPane.add(btnClear);
		
		
		toLbl = new JLabel("To");
		toLbl.setForeground(Color.WHITE);
		toLbl.setHorizontalAlignment(SwingConstants.CENTER);
		toLbl.setFont(new Font("Tahoma", Font.BOLD, 21));
		toLbl.setBounds(62, 29, 40, 39);
		contentPane.add(toLbl);
		
		toField = new JTextPane();
		toField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		toField.setBounds(134, 29, 492, 39);
		contentPane.add(toField);
		
		subjectLbl = new JLabel("Subject");
		subjectLbl.setForeground(Color.WHITE);
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
		messageField.setBounds(132, 166, 762, 290);
		contentPane.add(messageField);
		
		sendBtn = new JButton("Send Message");
		sendBtn.setForeground(Color.WHITE);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get list of recipients
				IQueue emailList = new LinkedBased();
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
							return;
						}
						else {
							if (!Contact.checkmail(hold)) {
								JOptionPane.showMessageDialog(null,"Invalid Email format!","Error",JOptionPane.ERROR_MESSAGE);
								return;
							} else {
								emailList.enqueue(hold);
								hold = "";
								recipientNum++;
							}
						}
					}
				}
				if (!App.isBlankString(hold)) {
					emailList.enqueue(hold);
					hold = "";
				}
				// Prepare subject line and message contents
				String subject = subjectField.getText();
				String message = messageField.getText();
				String prio = priority.getSelectedItem();
				int out;
				if (prio.compareTo("Normal") == 0) {
					out = 3;
				} else if (prio.compareTo("Very High") == 0) {
					out = 1;
				} else if (prio.compareTo("High") == 0) {
					out = 2;
				} else if (prio.compareTo("Low") == 0) {
					out = 4;
				} else {
					out = 5;
				}
				
				Mail.newEmail(false, out, emailList, subject , attaches, message, currentEmail);
			}
		});
		sendBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		sendBtn.setBackground(SystemColor.textHighlight);
		sendBtn.setBounds(132, 492, 144, 42);
		contentPane.add(sendBtn);
		
		addAttachBtn = new JButton("Add Attachments");
		addAttachBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf=new JFileChooser();
				if(jf.showOpenDialog(null)==jf.APPROVE_OPTION) {
					File file=jf.getSelectedFile();
					attaches.add(file);
				}
				
				
			}
		});
		addAttachBtn.setForeground(Color.WHITE);
		addAttachBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		addAttachBtn.setBackground(SystemColor.textHighlight);
		addAttachBtn.setBounds(829, 42, 144, 42);
		contentPane.add(addAttachBtn);
		
		discardBtn = new JButton("Discard");
		discardBtn.setForeground(Color.WHITE);
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
		discardBtn.setBackground(SystemColor.textHighlight);
		discardBtn.setBounds(750, 492, 144, 42);
		contentPane.add(discardBtn);
		
		draftBtn = new JButton("Save Draft");
		draftBtn.setForeground(Color.WHITE);
		draftBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get list of recipients
				IQueue emailList = new LinkedBased();
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
							return;
						}
						else {
							if (!Contact.checkmail(hold)) {
								JOptionPane.showMessageDialog(null,"Invalid Email format!","Error",JOptionPane.ERROR_MESSAGE);
								return;
							} else {
								emailList.enqueue(hold);
								hold = "";
								recipientNum++;
							}
						}
					}
				}
				if (!App.isBlankString(hold)) {
					emailList.enqueue(hold);
					hold = "";
				}
				
				// Prepare subject line and message contents
				String subject = subjectField.getText();
				String message = messageField.getText();
				String prio = priority.getSelectedItem();
				int out;
				if (prio.compareTo("Normal") == 0) {
					out = 3;
				} else if (prio.compareTo("Very High") == 0) {
					out = 1;
				} else if (prio.compareTo("High") == 0) {
					out = 2;
				} else if (prio.compareTo("Low") == 0) {
					out = 4;
				} else {
					out = 5;
				}
				
				Mail.newEmail(true, out, emailList, subject , attaches, message, currentEmail);
			}
		});
		draftBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		draftBtn.setBackground(SystemColor.textHighlight);
		draftBtn.setBounds(448, 492, 144, 42);
		contentPane.add(draftBtn);
		
		priority = new Choice();
		priority.add("Normal");
		priority.add("Very High");
		priority.add("High");
		priority.add("Low");
		priority.add("Very Low");
		priority.setBounds(134, 130, 136, 20);
		contentPane.add(priority);
		
		priorityLbl = new JLabel("Priority");
		priorityLbl.setForeground(Color.WHITE);
		priorityLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		priorityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		priorityLbl.setBounds(11, 128, 104, 20);
		contentPane.add(priorityLbl);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ComposeMessage.class.getResource("/Images/paperplanemaking.jpeg")));
		lblNewLabel.setBounds(0, -18, 1007, 592);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(693, 29, 124, 34);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(712, 42, 85, 21);
		contentPane.add(btnNewButton_1);
		
		draftBtn_1 = new JButton("Save Draft");
		draftBtn_1.setForeground(Color.WHITE);
		draftBtn_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		draftBtn_1.setBackground(SystemColor.textHighlight);
		draftBtn_1.setBounds(594, 492, 144, 42);
		contentPane.add(draftBtn_1);
		
	}
}
