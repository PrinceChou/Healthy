package android.microanswer.healthy.fragment;

import android.microanswer.healthy.R;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

/**
 * 由 Micro 创建于 2016/7/25.
 */

public class MainSetFragment extends PreferenceFragment {
    public static final String PREFERENCE_FILE_NAME = MPreferenceFragment.PREFERENCE_FILE_NAME;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.perference_main_set);
        getPreferenceManager().setSharedPreferencesName(PREFERENCE_FILE_NAME);
    }
}
