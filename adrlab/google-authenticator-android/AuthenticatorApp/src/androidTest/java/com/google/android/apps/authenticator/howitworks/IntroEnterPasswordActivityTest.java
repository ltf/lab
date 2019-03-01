/*
 * Copyright 2011 Google Inc. All Rights Reserved.
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

package com.google.android.apps.authenticator.howitworks;

import com.google.android.apps.authenticator.wizard.WizardPageActivityTestBase;

import android.content.Intent;

import java.io.Serializable;

/**
 * Unit tests for {@link IntroEnterPasswordActivity}.
 *
 * @author klyubin@google.com (Alex Klyubin)
 */
public class IntroEnterPasswordActivityTest
    extends WizardPageActivityTestBase<IntroEnterPasswordActivity, Serializable> {

  public IntroEnterPasswordActivityTest() {
    super(IntroEnterPasswordActivity.class);
  }

  public void testBackKeyFinishesActivity() throws Exception {
    assertBackKeyFinishesActivity();
  }

  public void testLeftButtonFinishesActivity() throws Exception {
    assertLeftButtonPressFinishesActivity();
  }

  public void testRightButtonStartsNextPage() throws Exception {
    Intent intent = pressRightButtonAndCaptureActivityStartIntent();
    assertIntentForClassInTargetPackage(IntroEnterCodeActivity.class, intent);
    assertFalse(getActivity().isFinishing());
  }
}
