package com.sky.base.validation.bval;

import static com.sky.base.test.util.MatcherUtils.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.Before;
import org.junit.Test;

import com.sky.base.validation.bval.SimpleValidatorFactory;
import com.sky.base.validation.example.Address;
import com.sky.base.validation.example.Merchant;
import com.sky.base.validation.example.MockType;
import com.sky.base.validation.example.Person;
import com.sky.base.validation.example.User;
import com.sky.base.validation.example.UserNonValid;
import com.sky.base.validation.example.Person.Group1;
import com.sky.base.validation.example.Person.Group2;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-27
 */
public class BvalValidatorTest {

	private Validator validator;

	@Before
	public void setUp() {
		validator = SimpleValidatorFactory.BVAL.getValidator();
	}

	@Test
	public void testNotNull() {
		Merchant merchant = new Merchant();
		merchant.setId(null);
		merchant.setName("333");
		merchant.setType("01");
		Set<ConstraintViolation<Merchant>> constraintViolations = validator.validate(merchant);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("商户编号不能为空", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void testMin() {
		testMin(-934L);
		testMin(-1L);
		testMin(0L);
	}

	private void testMin(Long id) {
		Merchant merchant = new Merchant();
		merchant.setId(id);
		merchant.setName("333");
		merchant.setType("01");
		Set<ConstraintViolation<Merchant>> constraintViolations = validator.validate(merchant);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("商户编号不能小于等于0", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void testNotBlank() {
		Merchant merchant = new Merchant();
		merchant.setId(1L);
		merchant.setName(" ");
		merchant.setType("01");
		Set<ConstraintViolation<Merchant>> constraintViolations = validator.validate(merchant);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("商户名称不能为空白", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void testOptionalStringValue() {
		Merchant merchant = new Merchant();
		merchant.setId(1L);
		merchant.setName("123");
		merchant.setType("03");
		Set<ConstraintViolation<Merchant>> constraintViolations = validator.validate(merchant);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("商户类型取值无效", constraintViolations.iterator().next().getMessage());
	}

	@Test(expected = javax.validation.UnexpectedTypeException.class)
	public void testOptionalStringValueInOtherTypeField() {
		MockType mockType = new MockType();
		mockType.setType(1);
		validator.validate(mockType);
	}

	@Test
	public void testPattern() {
		User user = new User();
		user.setName("test");
		user.setEmail("13123");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("邮箱格式不正确", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void testValid() {
		Map<String, String> result = new HashMap<>();
		result.put("address.province", "所属省不能为空");
		result.put("address.city", "所属市不能为空");
		User user = new User();
		user.setName("test");
		user.setEmail("123@qq.com");
		user.setAddress(new Address());
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		assertThat(constraintViolations.size(), is(2));
		ConstraintViolation<User> v1 = constraintViolations.iterator().next();
		ConstraintViolation<User> v2 = constraintViolations.iterator().next();
		assertEquals(result.get(v1.getPropertyPath().toString()), v1.getMessage());
		assertEquals(result.get(v2.getPropertyPath().toString()), v2.getMessage());
	}

	@Test
	public void testNonValid() {
		Map<String, String> result = new HashMap<>();
		result.put("address.province", "所属省不能为空");
		result.put("address.city", "所属市不能为空");
		UserNonValid user = new UserNonValid();
		user.setName("test");
		user.setEmail("123@qq.com");
		user.setAddress(new Address());
		Set<ConstraintViolation<UserNonValid>> constraintViolations = validator.validate(user);
		assertThat(constraintViolations.size(), is(0));
	}

	@Test
	public void testGroup() {
		testNonGroup();
		testGroup1();
		testGroup2();
		testGroup1AndGroup2();
	}

	private void testGroup1AndGroup2() {
		Person person = new Person();
		person.setName("123");
		person.setRemark("");
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person, Group1.class, Group2.class);
		assertThat(constraintViolations.size(), is(4));
	}

	private void testGroup2() {
		Person person = new Person();
		person.setName("123");
		person.setRemark("一二");
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person, Group2.class);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("备注长度不符, 长度要求3~7", constraintViolations.iterator().next().getMessage());

		person.setRemark(null);
		constraintViolations = validator.validate(person, Group2.class);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("备注不能为空2", constraintViolations.iterator().next().getMessage());

		person.setRemark("一二三四五六七八九十");
		constraintViolations = validator.validate(person, Group2.class);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("备注长度不符, 长度要求3~7", constraintViolations.iterator().next().getMessage());

		person.setRemark("一二三四五六七");
		constraintViolations = validator.validate(person, Group2.class);
		assertThat(constraintViolations.size(), is(0));

		person.setRemark("一二三");
		constraintViolations = validator.validate(person, Group2.class);
		assertThat(constraintViolations.size(), is(0));
	}

	private void testGroup1() {
		Person person = new Person();
		person.setName("123");
		person.setRemark("一");
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person, Group1.class);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("备注长度不符, 长度要求2~10", constraintViolations.iterator().next().getMessage());

		person.setRemark(null);
		constraintViolations = validator.validate(person, Group1.class);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("备注不能为空", constraintViolations.iterator().next().getMessage());

		person.setRemark("一二三四五六七八九十一");
		constraintViolations = validator.validate(person, Group1.class);
		assertThat(constraintViolations.size(), is(1));
		assertEquals("备注长度不符, 长度要求2~10", constraintViolations.iterator().next().getMessage());

		person.setRemark("一二三四五六七八九十");
		constraintViolations = validator.validate(person, Group1.class);
		assertThat(constraintViolations.size(), is(0));

		person.setRemark("一2");
		constraintViolations = validator.validate(person, Group1.class);
		assertThat(constraintViolations.size(), is(0));
	}

	private void testNonGroup() {
		Person person = new Person();
		person.setName("123");
		person.setRemark("一");
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
		assertThat(constraintViolations.size(), is(0));
	}

}
