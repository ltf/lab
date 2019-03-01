/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.authenticator;

import android.os.Handler;

import java.util.concurrent.Executor;

/**
 * {@link Executor} that invokes {@link Runnable} instances on the thread on which it was created.
 * The assumption is that the thread has a {@link android.os.Looper} associated with it.
 *
 * @author klyubin@google.com (Alex Klyubin)
 */
public class RunOnThisLooperThreadExecutor implements Executor {

  private final Handler mHandler = new Handler();

  @Override
  public void execute(Runnable command) {
    if (Thread.currentThread() == mHandler.getLooper().getThread()) {
      // The calling thread is the target thread of the Handler -- invoke immediately, blocking
      // the calling thread.
      command.run();
    } else {
      // The calling thread is not the same as the thread with which the Handler is associated --
      // post to the Handler for later execution.
      mHandler.post(command);
    }
  }
}
