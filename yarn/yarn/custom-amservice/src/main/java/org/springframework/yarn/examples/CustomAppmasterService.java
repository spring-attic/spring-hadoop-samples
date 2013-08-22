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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.yarn.integration.ip.mind.MindAppmasterService;
import org.springframework.yarn.integration.ip.mind.MindRpcMessageHolder;
import org.springframework.yarn.integration.ip.mind.binding.BaseObject;

/**
 * Custom application master service handling communication
 * from running containers. For simplicity expects message exchange
 * to happen using {@link JobRequest} and {@link JobResponse}.
 * <p>
 * Communicates with application master which keeps state of
 * current jobs. Application master is autowired into this
 * instance.
 *
 * @author Janne Valkealahti
 *
 */
public class CustomAppmasterService extends MindAppmasterService {

	private static final Log log = LogFactory.getLog(CustomAppmasterService.class);

	@Autowired
	private CustomAppmaster customAppmaster;

	@Override
	protected MindRpcMessageHolder handleMindMessageInternal(MindRpcMessageHolder message) {
		BaseObject request = getConversionService().convert(message, BaseObject.class);
		JobResponse response = handleJob((JobRequest)request);
		return getConversionService().convert(response, MindRpcMessageHolder.class);
	}

	/**
	 * Handles logic between {@link JobRequest} and {@link JobResponse}.
	 *
	 * @param request the request
	 * @return the response
	 */
	private JobResponse handleJob(JobRequest request) {
		JobResponse response = new JobResponse(JobResponse.State.STANDBY, null);

		if (request.getState().equals(JobRequest.State.JOBDONE)) {
			customAppmaster.reportJobStatus(request.getJob(), true);
		} else if (request.getState().equals(JobRequest.State.JOBFAILED)) {
			customAppmaster.reportJobStatus(request.getJob(), false);
		}

		Long job = customAppmaster.getJob();
		response.setJob(job);
		if (job != null) {
			response.setState(JobResponse.State.RUNJOB);
		} else if (!customAppmaster.hasJobs()) {
			response.setState(JobResponse.State.DIE);
		}

		log.info("Response: state=" + response.getState() + " job=" + response.getJob());

		return response;
	}


}
