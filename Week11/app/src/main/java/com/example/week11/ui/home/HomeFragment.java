package com.example.week11.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.week11.R;
import com.example.week11.logic.SettingStorage;

public class HomeFragment extends Fragment {
    private SettingStorage settingStorage;
    private EditText editText;
    private TextView textViewOut;

    private View view;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingStorage = SettingStorage.getInstance();
        editText = view.findViewById(R.id.editTextInput);
        textViewOut = view.findViewById(R.id.textViewOutput);
    }

    @Override
    public void onStop() {
        super.onStop();

        settingStorage.setStoredString(editText.getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();

        editText.setEnabled(settingStorage.isInputEnabled());

        if (!settingStorage.isInputEnabled()){
            textViewOut.setText(settingStorage.getStoredString());
            textViewOut.append("\n\n"+settingStorage.getDisplayText());
        }
        textViewOut.setTextSize(settingStorage.getFontSize());
        textViewOut.setWidth(settingStorage.getInputWidth());
        textViewOut.setHeight(settingStorage.getInputHight());
        textViewOut.setLines(settingStorage.getInputRows());



    }
}