package com.example.week11.logic;

public enum Langs {
    ENGLISH,
    FINNISH,
    SWEDISH;

    public static Langs getLangById(int input){
        switch (input){
            default:
            case 0:
                return Langs.ENGLISH;
            case 1:
                return Langs.FINNISH;
            case 2:
                return Langs.SWEDISH;
        }
    }

    public static int LangToValue(Langs input){
        switch (input){
            default:
            case ENGLISH:
                return 0;
            case FINNISH:
                return 1;
            case SWEDISH:
                return 2;
        }
    }
}
