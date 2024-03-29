/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.utils4j.filter;

import org.fuin.utils4j.Utils4J;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Defines a filter on a Comparable value.
 */
public abstract class ComparableFilter implements Filter {

    private final Operator operator;

    private final Comparable constValue;

    /**
     * Constructor with a property and an operator.
     * 
     * @param newOperator
     *            Operator to use.
     * @param constValue
     *            Value to compare with.
     */
    public ComparableFilter(final Operator newOperator, final Comparable constValue) {
        super();
        this.operator = newOperator;
        this.constValue = constValue;
    }

    @Override
    public final boolean complies(final Object value) {
        if (value == null) {
            return (constValue == null);
        } else {
            return simpleCompareTo((Comparable) value);
        }
    }

    /**
     * Returns the operator for compare operations.
     * 
     * @return Operator.
     */
    public final Operator getOperator() {
        return operator;
    }

    /**
     * Returns the operator for compare operations.
     * 
     * @return Operator.
     */
    public final String getOperatorName() {
        return "" + operator;
    }

    /**
     * Returns the constant value any string is compared with.
     * 
     * @return String value.
     */
    protected final Comparable getConstValueIntern() {
        return constValue;
    }

    private boolean simpleCompareTo(final Comparable propValue) {
        final int result = propValue.compareTo(constValue);
        if (operator == Operator.GT) {
            return (result > 0);
        } else if (operator == Operator.GTE) {
            return (result >= 0);
        } else if (operator == Operator.EQ) {
            return (result == 0);
        } else if (operator == Operator.LT) {
            return (result < 0);
        } else if (operator == Operator.LTE) {
            return (result <= 0);
        } else {
            throw new IllegalStateException("Unknown operator '" + operator + "'!");
        }
    }

    @Override
    public final String toString() {
        return " " + operator + " " + constValue;
    }

    /** Operators for compare operations. */
    public static final class Operator {

        /** Less than. */
        public static final Operator LT = new Operator("LT", "<");

        /** Less than or equal. */
        public static final Operator LTE = new Operator("LTE", "<=");

        /** Equal. */
        public static final Operator EQ = new Operator("EQ", "=");

        /** Greater than. */
        public static final Operator GT = new Operator("GT", ">");

        /** Greater than or equal. */
        public static final Operator GTE = new Operator("GTE", ">=");

        /** List of all known instances. */
        public static final List<Operator> INSTANCES = asList(LT, LTE, EQ, GT, GTE); // NOSONAR

        private final String id;

        private final String sign;

        private Operator(final String id, final String sign) {
            Utils4J.checkNotNull("id", id);
            Utils4J.checkNotNull("sign", sign);
            this.id = id;
            this.sign = sign;
        }

        /**
         * Returns the unique name of the operator.
         * 
         * @return ID.
         */
        public final String getId() {
            return id;
        }

        /**
         * Returns the symbol for the operator.
         * 
         * @return Sign.
         */
        public final String getSign() {
            return sign;
        }

        /**
         * Determines if a given id is valid.
         * 
         * @param id
         *            Name to check - Cannot be <code>null</code>.
         * 
         * @return If the name is valid <code>true</code> else <code>false</code>.
         */
        public static boolean isValid(final String id) {
            Utils4J.checkNotNull("id", id);
            for (final Operator op : INSTANCES) {
                if (op.getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns an instance for a given id.
         * 
         * @param id
         *            Name to return an instance for - Cannot be <code>null</code> and must be a valid name.
         * 
         * @return Instance.
         */
        public static Operator getInstance(final String id) {
            Utils4J.checkNotNull("id", id);
            for (final Operator op : INSTANCES) {
                if (op.getId().equals(id)) {
                    return op;
                }
            }
            throw new IllegalArgumentException("The id '" + id + "' is unknown!");
        }

        @Override
        public final String toString() {
            return sign;
        }

        private static List<Operator> asList(final Operator... operators) {
            final List<Operator> list = new ArrayList<>(operators.length);
            for (final Operator operator : operators) {
                list.add(operator);
            }
            return Collections.unmodifiableList(list);
        }

    }

}
