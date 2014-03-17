package com.biol498.sequencejoiner;

import java.util.Collection;
import java.util.Set;

import com.clcbio.api.base.algorithm.ParameterValidationHandler;
import com.clcbio.api.base.algorithm.parameter.AlgoInputSelection;
import com.clcbio.api.base.algorithm.parameter.AlgoParameters;
import com.clcbio.api.base.algorithm.parameter.AlgoParametersInterpreter;
import com.clcbio.api.base.algorithm.parameter.ParameterGroup;
import com.clcbio.api.base.algorithm.parameter.keys.CachingKeyChecker;
import com.clcbio.api.base.algorithm.parameter.keys.Key;
import com.clcbio.api.base.algorithm.parameter.keys.KeyChecker;
import com.clcbio.api.base.algorithm.parameter.keys.KeyContainer;
import com.clcbio.api.base.algorithm.parameter.keys.Keys;
import com.clcbio.api.base.algorithm.parameter.keys.StringKey;
import com.clcbio.api.base.process.Activity;
import com.clcbio.api.base.session.ApplicationContext;

public class JoinerParameters extends AlgoParametersInterpreter {
	public final ParameterGroup topGroup = ParameterGroup.topLevel("delimiter", "Set join parameters");
	public final ParameterGroup mainGroup = ParameterGroup.childOf(topGroup, "Delimiter settings");
	public final StringKey delimiter = Keys.newStringKey(this, "delimiter")
			.inGroup(mainGroup)
			.describedAs("Sequence Delimiter")
			.labelled("Delimiter")
			.defaultsTo("NNNNNNNNNN")
			.done();
	private final KeyContainer keys;

	public JoinerParameters(AlgoParameters aps) {
		super(aps);
		this.keys = new KeyContainer(delimiter);
	}

	@Override
	public String getClassKey() {
		return "com.biol498.sequencejoiner.JoinerParameters";
	}

	@Override
	public Set<String> getKeys() {
		return keys.getKeySet();
	}

	@Override
	public void setToDefault() {
		keys.setToDefault();
	}
	
	@Override
	public Collection<Key<?>> getKeyObjects() {
		return keys.getKeys();
	}
	
	@Override
	public KeyChecker createKeyChecker(final ApplicationContext applicationContext) {
		return new CachingKeyChecker(getAlgoParameters()) {

			@Override
			public boolean isEnabled(Key<?> arg0, AlgoInputSelection arg1,
					Activity arg2) throws InterruptedException {
				return true;
			}

			@Override
			public void validate(Collection<? extends Key<?>> keys,
					AlgoInputSelection input, ParameterValidationHandler handler,
					Activity activity) throws InterruptedException {
				for (Key<?> key : keys) {
					key.validateCurrent(handler, applicationContext);
				}
			}
		};
	}
}
