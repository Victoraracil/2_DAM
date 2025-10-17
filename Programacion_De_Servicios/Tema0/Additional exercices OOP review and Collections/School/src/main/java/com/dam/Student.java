package com.dam;

public class Student implements Comparable<Student> {
    private String name;
    private double grade;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public int compareTo(Student other) {
        // Order by grade ascending
        return Double.compare(this.grade, other.grade);
    }

    @Override
    public String toString() {
        return String.format("%s (Grade: %.2f)", name, grade);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + (int) grade * 100;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return name.equals(other.name) && grade == other.grade;
    }
}
