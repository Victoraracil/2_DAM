package com.dam;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        //TreeMap ordered by student's grade
        TreeMap<Student, HashSet<Teacher>> school = new TreeMap<>();

        //Create Students (not in order by grade)
        Student s1 = new Student("Alice", 8.5);
        Student s2 = new Student("Bob", 6.9);
        Student s3 = new Student("Charlie", 9.2);

        //Create Teachers
        Teacher t1 = new Teacher("Mr. Smith", "Math");
        Teacher t2 = new Teacher("Ms. Johnson", "History");
        Teacher t3 = new Teacher("Mrs. Lee", "Science");
        Teacher t4 = new Teacher("Mr. Brown", "English");
        Teacher t5 = new Teacher("Ms. Green", "Physics");
        Teacher t6 = new Teacher("Mr. White", "Music");

        //Assign Teachers to Students (unordered sets)
        HashSet<Teacher> teachers1 = new HashSet<>();
        teachers1.add(t1);
        teachers1.add(t2);

        HashSet<Teacher> teachers2 = new HashSet<>();
        teachers2.add(t3);
        teachers2.add(t4);

        HashSet<Teacher> teachers3 = new HashSet<>();
        teachers3.add(t5);
        teachers3.add(t6);

        //Populate TreeMap (not adding students in grade order)
        school.put(s2, teachers2); // Bob 6.9
        school.put(s1, teachers1); // Alice 8.5
        school.put(s3, teachers3); // Charlie 9.2

        //Iterate through TreeMap entries (ordered by grade)
        System.out.println("=== Students and their Teachers (ordered by grade) ===");
        for (Map.Entry<Student, HashSet<Teacher>> entry : school.entrySet()) {
            Student student = entry.getKey();
            Set<Teacher> teachers = entry.getValue();

            System.out.println("\n" + student + ":");
            for (Teacher t : teachers) {
                System.out.println("   - " + t);
            }
        }
    }
}

