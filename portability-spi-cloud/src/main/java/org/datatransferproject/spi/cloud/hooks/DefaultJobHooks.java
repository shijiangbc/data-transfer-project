/*
 * Copyright 2018 The Data Transfer Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.datatransferproject.spi.cloud.hooks;

import java.io.IOException;
import java.util.UUID;
import com.google.inject.Inject;
import org.datatransferproject.spi.cloud.storage.JobStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A default implementation of the job hooks. */
public class DefaultJobHooks implements JobHooks {

  private static final Logger logger = LoggerFactory.getLogger(DefaultJobHooks.class);

  private final JobStore store;

  @Inject
  DefaultJobHooks(JobStore store) {
    this.store = store;
  }

  @Override
  public void jobStarted(UUID jobId) {
    logger.debug("Begin processing jobId: {}", jobId);
  }

  @Override
  public void jobFinished(UUID jobId, boolean success) {
    logger.debug("Finished processing jobId: {}", jobId);
    try {
      store.remove(jobId);
    } catch (IOException e) {
      logger.error("Error removing jobId: " + jobId, e);
    }
  }
}
