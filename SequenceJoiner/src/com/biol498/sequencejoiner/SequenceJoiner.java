package com.biol498.sequencejoiner;

import com.clcbio.api.base.algorithm.Algo;
import com.clcbio.api.base.algorithm.parameter.AlgoParameters;
import com.clcbio.api.clc.gui.wizard.WizardFacade;
import com.clcbio.api.free.actions.framework.StaticActionGroupDefinitions;
import com.clcbio.api.free.algorithm.AlgoAction;
import com.clcbio.api.free.algorithm.wizard.AlgoSaveWizardStepModel;
import com.clcbio.api.free.datatypes.ClcObject;
import com.clcbio.api.free.datatypes.bioinformatics.sequence.list.SequenceList;
import com.clcbio.api.free.gui.components.MultiSelectClassRestrictor;
import com.clcbio.api.free.gui.components.MultiSelectRestrictor;
import com.clcbio.api.free.wizard.dynamic.ClcWizardStepModel;

public class SequenceJoiner extends AlgoAction {
	private final static long serialVersionUID = 3584735260060536558L;
	public final static String PLUGIN_GROUP = "free";

	@Override
	public boolean appliesTo(Class<?>[] typeList) {
		return true;
	}
	
	@Override
	public String getName() {
		return "Delimited Sequence Join";
	}
	
	@Override
	public int getPreferredMenuLocation() {
		return 10;
	}
	
	@Override
	public boolean isInToolBar() {
		return true;
	}
	
	@Override
	public boolean isInActionTree() {
		return true;
	}
	
	@Override
	public boolean isInMenu() {
		return true;
	}
	
	@Override
	public double getVersion() {
		return 0.1;
	}
	
	@Override
	public String getClassKey() {
		return "com.biol498.sequencejoiner.SequenceJoiner";
	}
	
	@Override
	protected void addToActionGroup() {
		StaticActionGroupDefinitions.TOOLBOX_TOP_GROUP.addAction(this);
	}
	
	@Override
	public Algo createAlgo() {
		return new JoinerAlgo(getManager());
	}

	@Override
	public MultiSelectRestrictor createRestrictor(final WarningReceptor wr) {
		return new MultiSelectClassRestrictor(new Class<?>[] { SequenceList.class }, "Select a sequence list"){
			@Override
			public boolean canAddObject(ClcObject[] currentSelectedObjects, ClcObject candidateObject) {
				return true;
			}
			
			@Override
			public boolean verifySelection(ClcObject[] objs) {
				return true;//1 <= SequenceTools.getSequenceCount(SequenceType.ALL, (Object[]) objs);
			}
		};
	}
	
	@Override
	public ClcWizardStepModel getFirstStep(AlgoParameters aps, ClcWizardStepModel nextStep) {
		WizardFacade facade = WizardFacade.getInstance();
		JoinerParameters jp = new JoinerParameters(aps);
		return facade.createDefaultParameterSteps(jp.createKeyChecker(getManager()), jp.getKeyObjects(), nextStep);
	}
	
	@Override
	public AlgoSaveWizardStepModel getAlgoSaveWizardStepModel(AlgoParameters aps) {
		WizardFacade facade = WizardFacade.getInstance();
		JoinerParameters jp = new JoinerParameters(aps);		
		return facade.createDefaultSaveStepModel(jp.createKeyChecker(getManager()), jp.getKeyObjects());
	}
}