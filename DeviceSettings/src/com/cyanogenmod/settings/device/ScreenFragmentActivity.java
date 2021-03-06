/*
 * Copyright (C) 2012 The CyanogenMod Project
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

package com.cyanogenmod.settings.device;

import android.content.Context;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.cyanogenmod.settings.device.R;

public class ScreenFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "DeviceSettings_Screen";
    private CABC mCABC;
    private mDNIeScenario mmDNIeScenario;
    private mDNIeMode mmDNIeMode;
    private mDNIeNegative mmDNIeNegative;
    private LedFade mLedFade;

    private static boolean sSPenSupported;
    private static boolean sTouchkeySupport;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.screen_preferences);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Resources res = getResources();

        /* CABC */
        mCABC = (CABC) findPreference(DeviceSettings.KEY_CABC);
        mCABC.setEnabled(CABC.isSupported(res.getString(R.string.mdnie_cabc_sysfs_file)));

        /* mDNIe */
        mmDNIeScenario = (mDNIeScenario) findPreference(DeviceSettings.KEY_MDNIE_SCENARIO);
        mmDNIeScenario.setEnabled(mDNIeScenario.isSupported(res.getString(R.string.mdnie_scenario_sysfs_file)));

        mmDNIeMode = (mDNIeMode) findPreference(DeviceSettings.KEY_MDNIE_MODE);
        mmDNIeMode.setEnabled(mDNIeMode.isSupported(res.getString(R.string.mdnie_mode_sysfs_file)));

        mmDNIeNegative = (mDNIeNegative) findPreference(DeviceSettings.KEY_MDNIE_NEGATIVE);
        mmDNIeNegative.setEnabled(mDNIeNegative.isSupported(res.getString(R.string.mdnie_negative_sysfs_file)));

        /* LED */
        mLedFade = (LedFade) findPreference(DeviceSettings.KEY_LED_FADE);
        mLedFade.setEnabled(LedFade.isSupported());

        /* S-Pen */
        String spenFilePath = res.getString(R.string.spen_sysfs_file);
        sSPenSupported = SPenPowerSavingMode.isSupported(spenFilePath);

        PreferenceCategory spenCategory = (PreferenceCategory) findPreference(DeviceSettings.KEY_CATEGORY_SPEN);
        if (!sSPenSupported) {
            preferenceScreen.removePreference(spenCategory);
        }
    }

    public static boolean isSupported(String FILE) {
        return Utils.fileExists(FILE);
    }

}
