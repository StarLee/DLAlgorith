package cuc.digital.transform;

public class StaticInfoBean {
	private int userID;
	private double age;
	private double sex;
	private int administrator;
	private int artist;
	private int doctor;
	private int educator;
	private int engineer;
	private int entertainment;
	private int executive;
	private int healthcare;
	private int homemaker;
	private int lawyer;
	private int librarian;
	private int marketing;
	private int none;
	private int other;
	private int programmer;
	private int retired;
	private int salesman;
	private int scientist;
	private int student;
	private int technician;
	private int writer;

	public StaticInfoBean(int userID, double age, String sex, String occupation) {
		this.userID = userID;
		this.age = age;
		if ("M".equals(sex)) {
			this.sex = 1;
		} else
			this.sex = 0.5;
		if ("administrator".equals(occupation)) {
			this.administrator = 1;
		}
		if ("artist".equals(occupation)) {
			this.artist = 1;
		}
		if ("doctor".equals(occupation)) {
			this.doctor = 1;
		}
		if ("educator".equals(occupation)) {
			this.educator = 1;
		}
		if ("engineer".equals(occupation)) {
			this.engineer = 1;
		}
		if ("entertainment".equals(occupation)) {
			this.entertainment = 1;
		}
		if ("executive".equals(occupation)) {
			this.executive = 1;
		}
		if ("healthcare".equals(occupation)) {
			this.healthcare = 1;
		}
		if ("homemaker".equals(occupation)) {
			this.homemaker = 1;
		}
		if ("lawyer".equals(occupation)) {
			this.lawyer = 1;
		}
		if ("librarian".equals(occupation)) {
			this.librarian = 1;
		}
		if ("marketing".equals(occupation)) {
			this.marketing = 1;
		}
		if ("none".equals(occupation)) {
			this.none = 1;
		}
		if ("other".equals(occupation)) {
			this.other = 1;
		}
		if ("programmer".equals(occupation)) {
			this.programmer = 1;
		}
		if ("retired".equals(occupation)) {
			this.retired = 1;
		}
		if ("salesman".equals(occupation)) {
			this.salesman = 1;
		}
		if ("scientist".equals(occupation)) {
			this.scientist = 1;
		}
		if ("student".equals(occupation)) {
			this.student = 1;
		}
		if ("technician".equals(occupation)) {
			this.technician = 1;
		}
		if ("writer".equals(occupation)) {
			this.writer = 1;
		}
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	public double getSex() {
		return sex;
	}

	public void setSex(double sex) {
		this.sex = sex;
	}

	public int getAdministrator() {
		return administrator;
	}

	public void setAdministrator(int administrator) {
		this.administrator = administrator;
	}

	public int getArtist() {
		return artist;
	}

	public void setArtist(int artist) {
		this.artist = artist;
	}

	public int getDoctor() {
		return doctor;
	}

	public void setDoctor(int doctor) {
		this.doctor = doctor;
	}

	public int getEducator() {
		return educator;
	}

	public void setEducator(int educator) {
		this.educator = educator;
	}

	public int getEngineer() {
		return engineer;
	}

	public void setEngineer(int engineer) {
		this.engineer = engineer;
	}

	public int getEntertainment() {
		return entertainment;
	}

	public void setEntertainment(int entertainment) {
		this.entertainment = entertainment;
	}

	public int getExecutive() {
		return executive;
	}

	public void setExecutive(int executive) {
		this.executive = executive;
	}

	public int getHealthcare() {
		return healthcare;
	}

	public void setHealthcare(int healthcare) {
		this.healthcare = healthcare;
	}

	public int getHomemaker() {
		return homemaker;
	}

	public void setHomemaker(int homemaker) {
		this.homemaker = homemaker;
	}

	public int getLawyer() {
		return lawyer;
	}

	public void setLawyer(int lawyer) {
		this.lawyer = lawyer;
	}

	public int getLibrarian() {
		return librarian;
	}

	public void setLibrarian(int librarian) {
		this.librarian = librarian;
	}

	public int getMarketing() {
		return marketing;
	}

	public void setMarketing(int marketing) {
		this.marketing = marketing;
	}

	public int getNone() {
		return none;
	}

	public void setNone(int none) {
		this.none = none;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public int getProgrammer() {
		return programmer;
	}

	public void setProgrammer(int programmer) {
		this.programmer = programmer;
	}

	public int getRetired() {
		return retired;
	}

	public void setRetired(int retired) {
		this.retired = retired;
	}

	public int getSalesman() {
		return salesman;
	}

	public void setSalesman(int salesman) {
		this.salesman = salesman;
	}

	public int getScientist() {
		return scientist;
	}

	public void setScientist(int scientist) {
		this.scientist = scientist;
	}

	public int getStudent() {
		return student;
	}

	public void setStudent(int student) {
		this.student = student;
	}

	public int getTechnician() {
		return technician;
	}

	public void setTechnician(int technician) {
		this.technician = technician;
	}

	public int getWriter() {
		return writer;
	}

	public void setWriter(int writer) {
		this.writer = writer;
	}

	public String toString() {
		StringBuilder bui = new StringBuilder();
		bui.append(userID);
		bui.append(";");
		bui.append(this.age);
		bui.append(";");
		bui.append(this.sex);
		bui.append(";");
		bui.append(this.administrator);
		bui.append(";");
		bui.append(this.artist);
		bui.append(";");
		bui.append(this.doctor);
		bui.append(";");
		bui.append(this.educator);
		bui.append(";");
		bui.append(this.engineer);
		bui.append(";");
		bui.append(this.entertainment);
		bui.append(";");
		bui.append(this.executive);
		bui.append(";");
		bui.append(this.healthcare);
		bui.append(";");
		bui.append(this.homemaker);
		bui.append(";");
		bui.append(this.lawyer);
		bui.append(";");
		bui.append(this.librarian);
		bui.append(";");
		bui.append(this.marketing);
		bui.append(";");
		bui.append(this.none);
		bui.append(";");
		bui.append(this.other);
		bui.append(";");
		bui.append(this.programmer);
		bui.append(";");
		bui.append(this.retired);
		bui.append(";");
		bui.append(this.salesman);
		bui.append(";");
		bui.append(this.scientist);
		bui.append(";");
		bui.append(this.student);
		bui.append(";");
		bui.append(this.technician);
		bui.append(";");
		bui.append(this.writer);
		bui.append("\n");
		return bui.toString();
	}
}
