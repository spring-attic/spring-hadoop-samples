/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.yarn.YarnSystemConstants;
import org.springframework.yarn.container.AbstractYarnContainer;
import org.springframework.yarn.container.YarnContainer;

/**
 * Simple {@link YarnContainer} example which is able
 * to exist with error status to demonstrate how it is
 * handled on Application Master.
 *
 * @author Janne Valkealahti
 *
 */
public class FailingContextContainer extends AbstractYarnContainer {

	private static final Log log = LogFactory.getLog(FailingContextContainer.class);

	@Override
	protected void runInternal() {
		log.info("Hello from FailingContextContainer");

		// just get the last number from "container_1377159599818_0001_01_000002"
		String containerIdString = getEnvironment(YarnSystemConstants.SYARN_CONTAINER_ID);
		int containerId = Integer.parseInt(containerIdString.substring(containerIdString.length()-1));

		// We just use the container id found from token variable
		// to fail first container (with id 2)
		if ((containerId == 2)) {
			log.info("Exiting with error");
			System.exit(1);
		} else {
			log.info("Exiting with ok");
			System.exit(0);
		}
	}

}
