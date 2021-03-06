/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.eval;

import java.util.Collections;

import com.puppetlabs.geppetto.pp.DoubleQuotedString;
import com.puppetlabs.geppetto.pp.LiteralBoolean;
import com.puppetlabs.geppetto.pp.LiteralDefault;
import com.puppetlabs.geppetto.pp.LiteralName;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.LiteralUndef;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.util.TextExpressionHelper;
import org.eclipse.xtext.util.PolymorphicDispatcher;

/**
 * Evaluates constant string expressions. Returns null if expression was not a constant string.
 * 
 */
public class PPStringConstantEvaluator {
	private PolymorphicDispatcher<String> stringDispatcher = new PolymorphicDispatcher<String>(
		"_string", 1, 1, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<String> get()) {
		@Override
		protected String handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	protected String _string(DoubleQuotedString o) {
		return TextExpressionHelper.getNonInterpolated(o);
	}

	protected String _string(LiteralBoolean o) {
		return Boolean.toString(o.isValue());
	}

	protected String _string(LiteralDefault o) {
		return "default";
	}

	protected String _string(LiteralName o) {
		return o.getValue();
	}

	protected String _string(LiteralNameOrReference o) {
		return o.getValue();
	}

	protected String _string(LiteralUndef o) {
		return "undef";
	}

	protected String _string(Object o) {
		return null;
	}

	protected String _string(SingleQuotedString o) {
		// TODO: Should return a string where escape sequences are evaluated
		return o.getText();
	}

	public String doToString(Object o) {
		if(o == null)
			return null;
		return stringDispatcher.invoke(o);

	}
}
