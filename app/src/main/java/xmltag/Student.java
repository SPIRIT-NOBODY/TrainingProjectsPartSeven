package xmltag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student {
	private String firstName = "";
	private String lastName = "";
	private String group = "";
	private String phone = "";
	private String id = "";
	private String gsm = "";
	private float progress = 0.0f;
	private List<String> errors = new ArrayList<>();
	// private String[] gsmMap = new String[] { "025", "033", "044", "029", };
	private List<String> gsmMap = Arrays.asList(new String[] { "025", "033", "044", "029", });

	public void checkErrors() {

		if (id.isEmpty()) {
			errors.add("id is empty");
		}
		if (firstName.isEmpty()) {
			errors.add("firstName is empty");
		}
		if (lastName.isEmpty()) {
			errors.add("lastName is empty");
		}
		if (group.isEmpty()) {
			errors.add("group is empty");
		}
		if (!gsmMap.contains(gsm)) {
			errors.add("gsm must 025 or 033 or 044 or 029");
		}
		if (phone.isEmpty()) {
			errors.add("phone is empty");
		}
		if (progress <= 0.0f || progress > 10f) {
			errors.add("progress must be more then 0.0 and less or equals 10");
		}
		System.out.println(id);
	}

	public String getErrorsString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (String error : errors) {
			stringBuilder.append(error + "<br>");
		}
		return stringBuilder.toString();
	}

	public String getGsm() {
		return gsm;
	}

	public void setGsm(String gsm) {
		this.gsm = gsm;
	}

	public Student() {
	}

	public Student(String firstName, String lastName, String group, String phone, String id, float progress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.group = group;
		this.phone = phone;
		this.id = id.replace("id_", "");
		this.progress = progress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.replace("id_", "");
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public void setProgress(String progress) {
		try {
			this.progress = Float.parseFloat(progress);
		} catch (Exception e) {
		}
	}

}
