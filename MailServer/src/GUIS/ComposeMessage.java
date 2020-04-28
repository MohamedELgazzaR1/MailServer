package GUIS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.StyledDocument;

import Classes.App;
import Classes.Contact;
import Classes.Mail;
import InterFaces.IApp;
import InterFaces.IMail;
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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.Desktop;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class ComposeMessage extends JFrame{

	private JPanel contentPane;
	private JLabel toLbl;
	private static JTextArea toField;
	private JLabel subjectLbl;
	private JTextField subjectField;
	private JTextArea messageField;
	private JButton sendBtn;
	private JButton addAttachBtn;
	private JButton discardBtn;
	private JButton draftBtn;
	private static String currentEmail = "";
	private static String  sendTo= "";
	private Choice priority;
	private JLabel priorityLbl;
	private JLabel lblNewLabel;
	private Choice attachList;
	private JButton btnClear;
	private JButton btnViewAttachment;
	private JButton btnRemoveAttachment;
	private JLabel lblAttachments;
	private JScrollPane scrollPaneMessage;
	private JScrollPane scrollPaneTo;
	private AbstractDocument doc;
	private static final int MAX_CHARACTERS = 10;
	
	ILinkedList attaches = new SinglyLinkedList();
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
		sendTo = args[1];

	}

	/**
	 * Create the application.
	 */
	public ComposeMessage() {
		setResizable(false);
		
		setBounds(100, 100, 1025, 609);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		attachList = new Choice();
		attachList.setBounds(609, 128, 167, 20);
		contentPane.add(attachList);
		
		btnViewAttachment = new JButton("View Selected Attachment");
		btnViewAttachment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedAttach = attachList.getSelectedItem();
				if (selectedAttach!= null) {
					for (int i = 1 ; i <= attaches.size(); i++) {
						File attachment = (File)attaches.get(i);
						String temp = attachment.getName();
						if ( selectedAttach.compareTo(temp) == 0 ){
							Desktop desktop = Desktop.getDesktop();  
							if(attachment.exists())
								try {
									desktop.open(attachment);
								} catch (IOException e1) {
								}             
							}  
							break;
						}
					
				}
				else {
					JOptionPane.showMessageDialog(null,"Attachment list is empty.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnViewAttachment.setBounds(782, 113, 191, 42);
		btnViewAttachment.setForeground(Color.WHITE);
		btnViewAttachment.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 9));
		btnViewAttachment.setBackground(SystemColor.textHighlight);
		contentPane.add(btnViewAttachment);
		
		btnClear = new JButton("Clear Text");
		btnClear.setBounds(21, 166, 104, 42);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!messageField.getText().isBlank()) {
					int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to clear the message?","Confirmation",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION) {
						messageField.setText("");
					}
				}
			}
		});
		btnClear.setForeground(Color.WHITE);
		btnClear.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		btnClear.setBackground(SystemColor.textHighlight);
		contentPane.add(btnClear);
		
		
		toLbl = new JLabel("To");
		toLbl.setBounds(46, 29, 40, 39);
		toLbl.setForeground(Color.WHITE);
		toLbl.setHorizontalAlignment(SwingConstants.CENTER);
		toLbl.setFont(new Font("Tahoma", Font.BOLD, 21));
		contentPane.add(toLbl);
		
		toField = new JTextArea();
		toField.setBounds(0, 70, 492, 39);
		toField.setWrapStyleWord(true);
		toField.setLineWrap(true);
		toField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		if(sendTo!=null) {
			toField.setText(sendTo);
			toField.setEditable(false);
			sendTo=null;
		}
		
		subjectLbl = new JLabel("Subject");
		subjectLbl.setBounds(19, 69, 94, 39);
		subjectLbl.setForeground(Color.WHITE);
		subjectLbl.setHorizontalAlignment(SwingConstants.CENTER);
		subjectLbl.setFont(new Font("Tahoma", Font.BOLD, 21));
		contentPane.add(subjectLbl);
		
		subjectField = new JTextField();
		subjectField.setBounds(134, 77, 463, 23);
		subjectField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(subjectField);
		
		
		sendBtn = new JButton("Send Message");
		sendBtn.setBounds(132, 492, 144, 42);
		sendBtn.setForeground(Color.WHITE);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IMail cmps = new Mail();
				cmps.setCurrentMail(currentEmail);
				cmps.setfiles(attaches);
				cmps.setDraft(false);
					// Get list of recipients
					String toFieldInput = toField.getText();
					IQueue emailList =  cmps.checkEmailList(toFieldInput);
					if (emailList == null) {
						return;
					}
					
					// Prepare subject line and message contents
					String prio = priority.getSelectedItem();
					int out = Mail.choosePriorty(prio);
					
					cmps.setMails(emailList);
					cmps.setSubject(subjectField.getText());
					cmps.setBody(messageField.getText());
					cmps.setPriority(out);
					
					if (cmps.getSubject().isBlank()) {
						JOptionPane.showMessageDialog(null,"Cannot send an email without subject.","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					IApp C = new App();
					if (C.compose(cmps)) {
						JOptionPane.showMessageDialog(null,"Email sent successfully.","",JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,"Email could not be sent!","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
					dispose();
				}
			
		});
		sendBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		sendBtn.setBackground(SystemColor.textHighlight);
		contentPane.add(sendBtn);
		
		addAttachBtn = new JButton("Add Attachment");
		addAttachBtn.setBounds(782, 11, 191, 42);
		addAttachBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf=new JFileChooser();
				if(jf.showOpenDialog(null)==jf.APPROVE_OPTION) {
					File file=jf.getSelectedFile();
					attaches.add(file);
					attachList.add(file.getName());
					attachList.select(file.getName());
				}
				
				
			}
		});
		addAttachBtn.setForeground(Color.WHITE);
		addAttachBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		addAttachBtn.setBackground(SystemColor.textHighlight);
		contentPane.add(addAttachBtn);
		
		discardBtn = new JButton("Discard");
		discardBtn.setBounds(750, 492, 144, 42);
		discardBtn.setForeground(Color.WHITE);
		discardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Discard the message and close the window after confirmation.
				int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to discard this message?","Confirmation",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
			
		});
		discardBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		discardBtn.setBackground(SystemColor.textHighlight);
		contentPane.add(discardBtn);
		
		draftBtn = new JButton("Save Draft");
		draftBtn.setBounds(448, 492, 144, 42);
		draftBtn.setForeground(Color.WHITE);
		draftBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IMail cmps = new Mail();
				cmps.setCurrentMail(currentEmail);
				cmps.setfiles(attaches);
				cmps.setDraft(true);
					// Get list of recipients
					String toFieldInput = toField.getText();
					IQueue emailList =  cmps.checkEmailList(toFieldInput);
					if (emailList == null) {
						return;
					}
					
					// Prepare subject line and message contents
					String prio = priority.getSelectedItem();
					int out = Mail.choosePriorty(prio);
					
					cmps.setMails(emailList);
					cmps.setSubject(subjectField.getText());
					cmps.setBody(messageField.getText());
					cmps.setPriority(out);
					
					//
					if (cmps.getSubject().isBlank()) {
						JOptionPane.showMessageDialog(null,"Cannot save an email without Subject.","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
				
					IApp C = new App();
					if (C.compose(cmps)) {
						JOptionPane.showMessageDialog(null,"Email saved successfully.","",JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,"Email could not be saved!","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
					dispose();
				}
			
		});
		draftBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		draftBtn.setBackground(SystemColor.textHighlight);
		contentPane.add(draftBtn);
		
		priority = new Choice();
		priority.setBounds(134, 130, 136, 20);
		priority.add("Very High");
		priority.add("High");
		priority.add("Normal");
		priority.add("Low");
		priority.add("Very Low");
		priority.select("Normal");
		contentPane.add(priority);
		
		priorityLbl = new JLabel("Priority");
		priorityLbl.setBounds(11, 128, 104, 20);
		priorityLbl.setForeground(Color.WHITE);
		priorityLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		priorityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(priorityLbl);
		
		btnRemoveAttachment = new JButton("Remove Selected Attachment");
		btnRemoveAttachment.setBounds(782, 60, 191, 42);
		btnRemoveAttachment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedAttach = attachList.getSelectedItem();
				if (selectedAttach!= null) {
					for (int i = 1 ; i <= attaches.size(); i++) {
						File attachment = (File)attaches.get(i);
						String temp = attachment.getName();
						if ( selectedAttach.compareTo(temp) == 0 ){
							attaches.remove(i);
							break;
						}
					}
					attachList.remove(selectedAttach);
					if(attachList.countItems() > 0) {
						attachList.select(0);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Attachment list is empty.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRemoveAttachment.setForeground(Color.WHITE);
		btnRemoveAttachment.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 9));
		btnRemoveAttachment.setBackground(SystemColor.textHighlight);
		contentPane.add(btnRemoveAttachment);
		
		lblAttachments = new JLabel("Attachment List");
		lblAttachments.setBounds(597, 102, 183, 20);
		lblAttachments.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttachments.setForeground(Color.WHITE);
		lblAttachments.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		contentPane.add(lblAttachments);
		
		messageField = new JTextArea();
		messageField.setBounds(134, 166, 753, 313);
		messageField.setWrapStyleWord(true);
		messageField.setLineWrap(true);
		messageField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		scrollPaneMessage = new JScrollPane(messageField);
		scrollPaneMessage.setBounds(134, 166, 753, 313);
		contentPane.add(scrollPaneMessage);
		
		scrollPaneTo = new JScrollPane(toField);
		scrollPaneTo.setBounds(134, 29, 463, 39);
		contentPane.add(scrollPaneTo);
		
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, -18, 1007, 592);
		lblNewLabel.setIcon(new ImageIcon(ComposeMessage.class.getResource("/Images/paperplanemaking.jpeg")));
		contentPane.add(lblNewLabel);
		
	}
}
