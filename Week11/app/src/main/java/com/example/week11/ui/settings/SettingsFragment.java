package com.example.week11.ui.settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.week11.MainActivity;
import com.example.week11.R;
import com.example.week11.logic.Langs;
import com.example.week11.logic.SettingStorage;

import java.util.Locale;

public class SettingsFragment extends Fragment {
    private SettingStorage settingStorage;
    private EditText editTextFontSize;
    private EditText editTextWidth;
    private EditText editTextHigth;
    private EditText editTextRows;
    private EditText editTextDisplayText;
    private Switch switchEnableInput;
    private Spinner spinnerLanguage;
    private int iCurrentSelection;

    private View view;
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingStorage = SettingStorage.getInstance();
        switchEnableInput = view.findViewById(R.id.switchEnableMainInputEditText);
        editTextFontSize = view.findViewById(R.id.editTextFontSizeNumber);
        editTextWidth = view.findViewById(R.id.editTextWidthNumber);
        editTextHigth = view.findViewById(R.id.editTextHightNumber);
        editTextRows = view.findViewById(R.id.editTextRowsNumber);
        editTextDisplayText = view.findViewById(R.id.editTextDisplayText);
        spinnerLanguage = view.findViewById(R.id.spinnerLanguage);

        switchEnableInput.setChecked(true);
        switchEnableInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingStorage.setInputEnabled(isChecked);
            }
        });
        editTextFontSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0) {
                    settingStorage.setFontSize(Integer.parseInt(s.toString()));
                }
            }
        });
        editTextWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                settingStorage.setInputWidth(Integer.parseInt(s.toString()));
                }
            }
        });
        editTextHigth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0) {
                    settingStorage.setInputHight(Integer.parseInt(s.toString()));
                }
            }
        });
        editTextRows.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    settingStorage.setInputRows(Integer.parseInt(s.toString()));
                }
            }
        });
        editTextDisplayText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                settingStorage.setDisplayText(s.toString());
            }
        });

        iCurrentSelection =spinnerLanguage.getSelectedItemPosition();

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (iCurrentSelection != position) {
                    settingStorage.setSelectedLang(Langs.getLangById(position));
                    Intent i = new Intent(getActivity().getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        switchEnableInput.setChecked(settingStorage.isInputEnabled());
        editTextFontSize.setText(String.valueOf(settingStorage.getFontSize()));
        editTextWidth.setText(String.valueOf(settingStorage.getInputWidth()));
        editTextHigth.setText(String.valueOf(settingStorage.getInputHight()));
        editTextRows.setText(String.valueOf(settingStorage.getInputRows()));
        editTextDisplayText.setText(String.valueOf(settingStorage.getDisplayText()));
        iCurrentSelection = Langs.LangToValue(settingStorage.getSelectedLang());
        spinnerLanguage.setSelection(Langs.LangToValue(settingStorage.getSelectedLang()));
    }
}