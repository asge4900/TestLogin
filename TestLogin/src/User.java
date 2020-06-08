
public class User {
	private int id;
	private String name, address, gender, knowledge, subject, date;
	private byte[] picture;
	
	public User(int id, String name, String address, String gender, String knowledge, String subject, byte[] image, String date) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.gender = gender;
		this.knowledge = knowledge;
		this.subject = subject;
		this.picture = image;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getGender() {
		return gender;
	}
	public String getKnowledge() {
		return knowledge;
	}
	public String getSubject() {
		return subject;
	}
	public byte[] getImage() {
		return picture;
	}
	public String getDate() {
		return date;
	}
}
