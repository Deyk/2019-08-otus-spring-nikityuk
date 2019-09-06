package ru.otus.spring.homework0105.util;

public enum Result {
    A("Excellent"),
    B("Good"),
    C("Satisfactory"),
    D("Poor"),
    E("Awful"),
    X("No data");

    private String title;

    Result(String title) {
        this.title = title;
    }

    public static Result getResult(int score) {
        switch (score) {
            case 5:
                return A;
            case 4:
                return B;
            case 3:
                return C;
            case 2:
                return D;
            case 1:
                return E;
            default:
                return X;
        }
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Result: " + this.getTitle() + "\n";
    }
}
