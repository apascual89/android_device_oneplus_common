/*
 * Copyright (C) 2017 The MoKee Open Source Project
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

package org.mokee.settings.device.slider;

import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import org.mokee.settings.device.SliderControllerBase;

public final class BrightnessController extends SliderControllerBase {

    public static final int ID = 3;

    private static final String TAG = "BrightnessController";

    private static final int BRIGHTNESS_AUTO = 30;
    private static final int BRIGHTNESS_BRIGHTEST = 31;
    private static final int BRIGHTNESS_DARKEST = 32;

    private static final int DARKEST = 0;
    private static final int BRIGHTEST = 255;

    public BrightnessController(Context context) {
        super(context);
    }

    @Override
    protected boolean processAction(int action) {
        Log.d(TAG, "slider action: " + action);
        switch (action) {
            case BRIGHTNESS_AUTO:
                return writeSettings(Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            case BRIGHTNESS_BRIGHTEST:
                return writeSettings(Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) &&
                    writeSettings(Settings.System.SCREEN_BRIGHTNESS, BRIGHTEST);
            case BRIGHTNESS_DARKEST:
                return writeSettings(Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) &&
                    writeSettings(Settings.System.SCREEN_BRIGHTNESS, DARKEST);
            default:
                return false;
        }
    }

    @Override
    public void reset() {
        writeSettings(Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    private boolean writeSettings(String key, int value) {
        return Settings.System.putIntForUser(mContext.getContentResolver(),
                    key, value, UserHandle.USER_CURRENT);
    }
}
