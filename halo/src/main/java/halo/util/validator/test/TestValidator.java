package halo.util.validator.test;

import halo.util.P;
import halo.util.validator.ErrResult;
import halo.util.validator.ObjectValidator;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class TestValidator {

	@Test
	public void validate() {
		User user = new User();
		user.setAge(10);
		user.setName("akweiweiwei");
		user.setBirthday(new Date());
		user.setCreatetime(new Date());
		user.setGender((byte) 0);
		// user.setStatus("");
		user.setUserid(190);
		// ObjectValidator validator = new ObjectValidator(user,
		// validatorCreator);
		// validator.addExprFromFile(TestValidator.class.getResource("").getPath()
		// + "user.v");
		ObjectValidator validator = new ObjectValidator(user,
				"classpath:halo/util/validator/test/user.v");
		ErrResult errResult = validator.exec();
		if (errResult != null) {
			P.println(errResult.getMsg());
		}
		List<ErrResult> list = validator.execBatch();
		for (ErrResult o : list) {
			P.println(o.getName() + " | " + o.getMsg());
		}
	}

	public static void main(String[] args) {
		P.println(TestValidator.class.getClassLoader().getResource("")
				.getPath());
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