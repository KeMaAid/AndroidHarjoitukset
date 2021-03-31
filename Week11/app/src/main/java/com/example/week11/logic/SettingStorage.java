package com.example.week11.logic;

public class SettingStorage {
    private static SettingStorage settingStorage = new SettingStorage();

    boolean isInputEnabled;
    Langs selectedLang;
    int fontSize;
    int inputWidth;
    int inputHight;
    int inputRows;
    String displayText;
    String storedString;


    private SettingStorage(){
        isInputEnabled=true;
        selectedLang=Langs.ENGLISH;
        fontSize=12;
        inputWidth=300;
        inputHight=300;
        inputRows=6;
        displayText="";
        storedString="";
    }

    public static SettingStorage getInstance() {
        return settingStorage;
    }

    public boolean isInputEnabled() {
        return isInputEnabled;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getInputHight() {
        return inputHight;
    }

    public int getInputRows() {
        return inputRows;
    }

    public int getInputWidth() {
        return inputWidth;
    }

    public Langs getSelectedLang() {
        return selectedLang;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getStoredString() {
        return storedString;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setInputEnabled(boolean inputEnabled) {
        this.isInputEnabled = inputEnabled;
    }

    public void setInputHight(int inputHight) {
        this.inputHight = inputHight;
    }

    public void setInputRows(int inputRows) {
        this.inputRows = inputRows;
    }

    public void setInputWidth(int inputWidth) {
        this.inputWidth = inputWidth;
    }

    public void setSelectedLang(Langs selectedLang) {
        this.selectedLang = selectedLang;
    }

    public void setStoredString(String storedString) {
        this.storedString = storedString;
    }
}

