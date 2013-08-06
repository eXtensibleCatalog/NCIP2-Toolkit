package org.extensiblecatalog.ncip.v2.binding.ncipv2_02.jaxb.dozer;

import java.math.BigDecimal;

import org.dozer.CustomConverter;

public class MzkIntegerConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {

		if (sourceFieldValue instanceof BigDecimal) {
			if (existingDestinationFieldValue == null) {
				Integer result = new Integer(
						((BigDecimal) sourceFieldValue).intValue());
				return result;
			} else {
				return existingDestinationFieldValue;
			}
		}

		if (sourceFieldValue instanceof Integer) {
			if (destinationClass.getName().equals(Short.class.getName())) {

				if (existingDestinationFieldValue == null) {
					Short result = new Short(
							((Integer) sourceFieldValue).shortValue());
					return result;
				} else {
					return existingDestinationFieldValue;
				}
			}

			if (destinationClass.getName().equals(BigDecimal.class.getName())) {
				if (existingDestinationFieldValue == null) {
					BigDecimal result = new BigDecimal(
							((Integer) sourceFieldValue).intValue());
					return result;
				} else {
					return existingDestinationFieldValue;
				}
			}
		}

		if (sourceFieldValue instanceof Short) {
			if (existingDestinationFieldValue == null) {
				Integer result = new Integer(
						((Short) sourceFieldValue).intValue());
				return result;
			} else {
				return existingDestinationFieldValue;
			}
		}

		return null;
	}

}
