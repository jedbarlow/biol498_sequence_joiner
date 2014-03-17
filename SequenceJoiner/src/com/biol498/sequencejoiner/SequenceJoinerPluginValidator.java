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
