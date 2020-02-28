package com.sky.base.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sky.base.validation.metadata.OptionalStringValue;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-27
 */
public class OptionalStringValueValidator implements ConstraintValidator<OptionalStringValue, String> {

	private String[] optionalValues;

	@Override
	public void initialize(OptionalStringValue constraintAnnotation) {
		optionalValues = constraintAnnotation.values();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		for (String each : optionalValues) {
			if (each.equals(value)) {
				return true;
			}
		}
		return false;
	}

}
