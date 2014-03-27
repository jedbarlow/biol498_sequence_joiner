/*
 * Copyright 2014 E. Jed Barlow <ejbarlow@ualberta.ca>
 *
 * This file is part of Delimited Sequence Joiner.
 *
 * SeqPartitioner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SeqPartitioner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SeqPartitioner.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.biol498.sequencejoiner;

import java.util.Collections;

import com.clcbio.api.base.algorithm.Algo;
import com.clcbio.api.base.algorithm.AlgoException;
import com.clcbio.api.base.algorithm.CallableExecutor;
import com.clcbio.api.base.algorithm.OutputHandler;
import com.clcbio.api.base.algorithm.parameter.AlgoParameters;
import com.clcbio.api.base.algorithm.parameter.AlgoParametersInterpreter;
import com.clcbio.api.base.session.ApplicationContext;
import com.clcbio.api.base.session.FactoryManager;
import com.clcbio.api.base.util.iterator.MovableSequenceIterator;
import com.clcbio.api.free.datatypes.ClcObject;
import com.clcbio.api.free.datatypes.bioinformatics.sequence.Sequence;
import com.clcbio.api.free.datatypes.bioinformatics.sequence.SequenceBuilder;
import com.clcbio.api.free.datatypes.bioinformatics.sequence.alphabet.AlphabetTools;
import com.clcbio.api.free.datatypes.bioinformatics.sequence.list.SequenceList;
import com.clcbio.api.free.datatypes.framework.history.HistoryEntry;

public class JoinerAlgo extends Algo {

	public JoinerAlgo(ApplicationContext ac) {
		super(ac);
	}
	
	@Override
	public void calculate(OutputHandler handler, CallableExecutor objectModificationExecutor) throws AlgoException, InterruptedException {
		JoinerParameters jp = new JoinerParameters(getParameters());
		String delimiter = jp.delimiter.get();

		SequenceBuilder builder = FactoryManager.getInstance().getSequenceFactory()
				.createBuilder();
		String firstname = "";
		builder.setAlphabet(AlphabetTools.getAlphabet(AlphabetTools.DNATYPE));
		
		for (ClcObject o : getInputObjectsIteratorProvider()) {
			SequenceList sl = (SequenceList) o;
			if (firstname == "") firstname = o.getName();

			MovableSequenceIterator msi = sl.getSequenceIterator();
			while(msi.hasNext()) {
				Sequence s = msi.next();
				builder.addSequenceData(s.getString());
				builder.addSequenceData(delimiter);
			}
		}
		
		builder.setName(firstname + " delimited join");
		builder.setDescription("Join of " + firstname + " with delimiter '" + delimiter + "'.");

		Sequence output = builder.finish();
		output.startNoUndoBlock();
		output.addHistory(new HistoryEntry("Created Hello World sequence", getApplicationContext()));
		output.endNoUndoBlock();
		handler.postOutputObjects(Collections.singletonList(output),  this);
	}
	
	@Override
	protected AlgoParametersInterpreter getInterpreter(AlgoParameters aps) {
		return new JoinerParameters(aps);
	}
	
	@Override
	public String getClassKey() {
		return "com.biol498.sequencejoiner.JoinerAlgo";
	}

	@Override
	public String getName() {
		return "Delimited Sequence Join";
	}

	@Override
	public double getVersion() {
		return 0.1;
	}
}
