package com.sky.base.validation.bval;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.bval.jsr.ApacheValidationProvider;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-27
 */
public enum SimpleValidatorFactory {
	/**
	 * apache bval
	 */
	BVAL {
		private static final long serialVersionUID = 1L;
		private final ValidatorFactory vf = Validation.byProvider(ApacheValidationProvider.class).configure()
		        .buildValidatorFactory();

		@Override
		public Validator getValidator() {
			return vf.getValidator();
		}
	};

	public abstract Validator getValidator();
}