package shtanko.type;

public enum Sex {
    MALE("Мужчина"),
    FEMALE("Женщина");

    private final String nameGender;

    Sex(String nameGender) {
        this.nameGender = nameGender;
    }

    public String getNameGender() {
        return nameGender;
    }
}
