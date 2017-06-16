package me.messageofdeath.lib;

public enum LanguageSettings {

    /**-------------------------------------- General --------------------------------------**/
    General_ErrorTag("&0[&cError&0] &c"),
     /** -------------------------------------- General Commands --------------------------------------**/
    Commands_NoPermission("You do not have permission for this!"),
    Commands_WrongArgs("Wrong amount of arguments inputted!"),
    Commands_NotEnoughArgs("There aren't enough arguments!"),
    Commands_TooManyArgs("There are too many arguments!"),
    Commands_InvalidModifier("There is an invalid modifier in the command!"),
    Commands_MustBeIngame("You are required to be in-game to use this command!"),;

    private String setting;
    private final String defaultSetting;

    LanguageSettings(String defaultSetting) {
        this.defaultSetting = defaultSetting;
    }

    public String getName() {
        return toString();
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public void setDefaultSetting() {
        this.setting = this.defaultSetting;
    }

    public String getSetting() {
        return this.setting;
    }

    public String getDefaultSetting() {
        return this.defaultSetting;
    }
}