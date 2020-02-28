package com.sky.base.validation.bval;

import static org.junit.Assert.*;

import javax.validation.Validator;

import org.junit.Test;

import com.sky.base.validation.bval.SimpleValidatorFactory;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-27
 */
public class SimpleValidatorFactoryTest {

	@Test
	public void testGetValidator() {
		Validator validator = SimpleValidatorFactory.BVAL.getValidator();
		assertNotNull(validator);
	}

}
