package Classes;

public class IndexMail {
	
	String mailName;
	int priority;
	String from;
	String to;
	String subject;
	
	
	public String getMailName() {
		return mailName;
	}

	public void setMailName (String mailName) {
		this.mailName = mailName;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom (String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}

	public void setTo (String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}

	public void setSubject (String subject) {
		this.subject = subject;
	}
	public int getPriority() {
		return priority;
	}

	public void setPriority (int priority) {
		this.priority = priority;
	}
}
