package com.xiaofeng.androidlibs;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.xiaofeng.androidlibs.util.ArrayUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 */
public class GeneralPreferenceFragment extends PreferenceFragment {
	public static class SummaryMapInfo {
		public final String summaryTemplate;
		public final String[] values;
		public final String[] entries;

		public SummaryMapInfo(String summaryTemplate, String[] values, String[] entries) {
			this.summaryTemplate = summaryTemplate;
			this.values = values;
			this.entries = entries;
		}
	}
	private final Map<String, SummaryMapInfo> keySummaryInfoMap = new HashMap<>();

	private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			if (keySummaryInfoMap.containsKey(preference.getKey())) {
				SummaryMapInfo summaryMapInfo = keySummaryInfoMap.get(preference.getKey());
				if (preference instanceof ListPreference) {
					int index = ArrayUtil.indexOf(summaryMapInfo.values, value.toString());
					if (index != ArrayUtil.NOT_FOUND) {
						preference.setSummary(String.format(summaryMapInfo.summaryTemplate, summaryMapInfo.entries[index]));
					}
				} else if (preference instanceof EditTextPreference) {
					preference.setSummary(String.format(summaryMapInfo.summaryTemplate, value));
				}
			}
			return true;
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_general);

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		keySummaryInfoMap.put(getString(R.string.pref_key_max_items_per_line), new SummaryMapInfo(getString(R.string.pref_max_items_per_line_summary_template), getResources().getStringArray(R.array.pref_max_items_per_line_values), getResources().getStringArray(R.array.pref_max_items_per_line_entries)));
		keySummaryInfoMap.put(getString(R.string.pref_key_alignment), new SummaryMapInfo(getString(R.string.pref_alignment_summary_template), getResources().getStringArray(R.array.pref_alignment_values), getResources().getStringArray(R.array.pref_alignment_entries)));
		keySummaryInfoMap.put(getString(R.string.pref_key_max_lines_per_item), new SummaryMapInfo(getString(R.string.pref_max_lines_per_item_summary_template), null, null));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_alignment)));
		bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_max_items_per_line)));
		bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_max_lines_per_item)));
	}

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 *
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
				PreferenceManager
						.getDefaultSharedPreferences(preference.getContext())
						.getString(preference.getKey(), ""));
	}
}
