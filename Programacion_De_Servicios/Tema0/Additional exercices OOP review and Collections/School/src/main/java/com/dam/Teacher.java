package com.dam;

public class Teacher {
    private String name;
    private String subject;

    public Teacher(String name, String subject) {
        this.name = name;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, subject);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + subject.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Teacher)) return false;
        Teacher other = (Teacher) obj;
        return name.equals(other.name) && subject.equals(other.subject);
    }
}

