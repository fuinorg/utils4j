/**
 * Copyright (C) 2009 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.utils4j.filter;

import org.fuin.utils4j.filter.StringFilter.Operator;

/**
 * Defines a filter on a String property.
 */
public class StringPropertyFilter extends PropertyFilter {

	private final StringFilter filter;

	/**
	 * Constructor with a property and an operator.
	 * 
	 * @param newPropertyName Name of the property.
	 * @param newOperator Operator to use.
	 * @param constValue Value to compare with.
	 */
	public StringPropertyFilter(final String newPropertyName,
			final StringFilter.Operator newOperator, final String constValue) {
		super(newPropertyName);
		this.filter = new StringFilter(newOperator, constValue);
	}

	/**
	 * {@inheritDoc}
	 */
	protected final String[] createGetterNames(final String property) {
		return new String[] {"get" + Character.toUpperCase(property.charAt(0))
				+ property.substring(1)};
	}
	
	/**
	 * Constructor with a property and an operator name.
	 * 
	 * @param newPropertyName Name of the property.
	 * @param newOperator Operator to use.
	 * @param constValue Value to compare with.
	 */
	public StringPropertyFilter(final String newPropertyName,
			final String newOperator, final String constValue) {
		super(newPropertyName);
		this.filter = new StringFilter(newOperator, constValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean complies(final Object obj) {
		final String value = (String) this.getProperty(obj, this
				.getPropertyName());
		return filter.complies(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public final String toString() {
		return getPropertyName() + filter.toString();
	}

	/**
	 * Returns the operator for compare operations.
	 * 
	 * @return Operator.
	 */
	public final Operator getOperator() {
		return filter.getOperator();
	}

	/**
	 * Returns the operator for compare operations.
	 * 
	 * @return Operator.
	 */
	public final String getOperatorName() {
		return filter.getOperatorName();
	}

	/**
	 * Returns the constant value any value is compared with.
	 * 
	 * @return String value.
	 */
	public final String getConstValue() {
		return filter.getConstValue();
	}

}
