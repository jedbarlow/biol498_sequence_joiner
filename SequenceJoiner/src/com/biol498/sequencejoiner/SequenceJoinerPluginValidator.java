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

import java.util.jar.JarFile;

import com.clcbio.api.base.plugin.PluginValidator;

public class SequenceJoinerPluginValidator implements PluginValidator {
    public void setJarFile(JarFile jarFile) {
    }

    public boolean validate() {
        return true;
    }
}
