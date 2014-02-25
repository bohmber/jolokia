package org.jolokia.osgi.detector;

/*
 * Copyright 2009-2013 Roland Huss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashMap;
import java.util.Map;

import org.jolokia.core.service.ServerHandle;
import org.jolokia.core.util.jmx.MBeanServerExecutor;

/**
 * Detector for Eclipse Virgo
 *
 * @author roland
 * @since 02.12.10
 */
public class VirgoDetector extends AbstractOsgiServerDetector {

    /**
     * Create a server detector
     *
     * @param pOrder of the detector (within the list of detectors)
     */
    public VirgoDetector(int pOrder) {
        super(pOrder);
    }

    /** {@inheritDoc}
     * @param pMBeanServerExecutor*/
    public ServerHandle detect(MBeanServerExecutor pMBeanServerExecutor) {
        String version = getBundleVersion("org.eclipse.virgo.kernel.userregion");
        if (version != null) {
            String type = "kernel";
            if (getBundleVersion("org.eclipse.gemini.web.core") != null) {
                type = "tomcat";
            } else if (getBundleVersion("org.eclipse.jetty.osgi.boot") != null) {
                type = "jetty";
            }
            Map<String,String> extraInfo = new HashMap<String,String>();
            extraInfo.put("type",type);
            return new VirgoServerHandle(version,extraInfo);
        } else {
            return null;
        }
    }

    private static class VirgoServerHandle extends ServerHandle {

        private final Map<String, String> extraInfo;

        public VirgoServerHandle(String pVersion, Map<String, String> pExtraInfo) {
            super("Eclipse","Virgo",pVersion);
            extraInfo = pExtraInfo;
        }

        @Override
        public Map<String, String> getExtraInfo(MBeanServerExecutor pServerManager) {
            return extraInfo;
        }
    }
}
