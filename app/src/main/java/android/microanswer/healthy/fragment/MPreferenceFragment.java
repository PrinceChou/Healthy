package android.microanswer.healthy.fragment;

import android.microanswer.healthy.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by MicroAnswer on 2016/6/28.
 */
public class MPreferenceFragment extends PreferenceFragment {
    public static final String PREFERENCE_FILE_NAME = "mpreference";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.perference_main);
        getPreferenceManager().setSharedPreferencesName(PREFERENCE_FILE_NAME);
    }

}
