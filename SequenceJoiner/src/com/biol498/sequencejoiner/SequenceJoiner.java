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

import com.clcbio.api.base.algorithm.Algo;
import com.clcbio.api.base.algorithm.parameter.AlgoParameters;
import com.clcbio.api.base.tools.SequenceTools;
import com.clcbio.api.clc.gui.wizard.WizardFacade;
import com.clcbio.api.free.actions.framework.StaticActionGroupDefinitions;
import com.clcbio.api.free.algorithm.AlgoAction;
import com.clcbio.api.free.algorithm.wizard.AlgoSaveWizardStepModel;
import com.clcbio.api.free.datatypes.ClcObject;
import com.clcbio.api.free.datatypes.bioinformatics.sequence.SequenceType;
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
            public boolean canAddObject(ClcObject[] currentSelection, ClcObject candidate) {
                if (!super.canAddObject(currentSelection, candidate))
                    return false;

                if (!SequenceTools.isNucleotide(candidate)) {
                    wr.showAddingWarning("Can only join nucleotide sequences");
                    return false;
                }

                return true;
            }

            @Override
            public boolean verifySelection(ClcObject[] objs) {
                return 1 <= SequenceTools.getSequenceCount(SequenceType.ALL, (Object[]) objs);
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
