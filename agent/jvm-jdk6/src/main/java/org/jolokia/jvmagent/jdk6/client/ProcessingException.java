package org.jolokia.jvmagent.jdk6.client;

/*
 * Copyright 2009-2011 Roland Huss
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * @author roland
 * @since 12.08.11
 */
public class ProcessingException extends RuntimeException {
    private boolean quiet;
    private boolean verbose;

    public ProcessingException(String pErrMsg, Exception pStoredExp, OptionsAndArgs pOptions) {
        super(pErrMsg,pStoredExp);
        quiet = pOptions.isQuiet();
        verbose = pOptions.isVerbose();
    }

    @SuppressWarnings({"PMD.SystemPrintln"})
    public void printErrorMessage() {
        if (!quiet) {
            System.err.println(getMessage() + ": " + getCause());
        }
        if (verbose) {
            getCause().printStackTrace(System.err);
        }
    }
}