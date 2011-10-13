package halo.util.validator.test;

import halo.util.validator.ObjectValidator;
import halo.util.validator.ValidatorCreator;

import java.util.Date;

import org.junit.Test;

public class TestValidator {

	@Test
	public void validate() {
		User user = new User();
		user.setAge(10);
		user.setName("akweiwei");
		user.setBirthday(new Date());
		user.setCreatetime(new Date());
		user.setGender((byte) 0);
		user.setStatus("hahhahahah");
		user.setUserid(190);
		ValidatorCreator validatorCreator = ValidatorCreator
				.getDefaultValidatorCreator();
		ObjectValidator validator = new ObjectValidator(user, validatorCreator);
		validator.addExpr("age", "number{min=10;max=100}msgage");
		validator.exec();
	}
}

class User {

	private long userid;

	private int age;

	private byte gender;

	private String name;

	private String status;

	private Date birthday;

	private Date createtime;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}