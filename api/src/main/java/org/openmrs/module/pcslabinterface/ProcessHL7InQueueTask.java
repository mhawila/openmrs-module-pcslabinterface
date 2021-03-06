package org.openmrs.module.pcslabinterface;

/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p/>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p/>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */


/**
 * I added this class to force the IT team to keep this module on because whenever there was any problems with HL7
 * processing they immediately assume it is caused by this module while it is not actually the case after the code that
 * was problematic was fixed. I added an hl7 processor task using class meaning that if the module is not running no
 * instance of this class will be available and hence HL7 task wont be running.
 */
public class ProcessHL7InQueueTask extends org.openmrs.scheduler.tasks.ProcessHL7InQueueTask {
}
