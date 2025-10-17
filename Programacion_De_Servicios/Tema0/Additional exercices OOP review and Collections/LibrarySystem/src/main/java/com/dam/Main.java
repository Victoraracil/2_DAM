package com.dam;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //Create HashMap: Key = Book, Value = LinkedList<Member>
        HashMap<Book, LinkedList<Member>> library = new HashMap<>();

        //Create Books (not ordered by title or author)
        Book b1 = new Book("The Great Gatsby", "F. Scott Fitzgerald");
        Book b2 = new Book("1984", "George Orwell");
        Book b3 = new Book("To Kill a Mockingbird", "Harper Lee");

        //Create Members
        Member m1 = new Member("Alice", "M001");
        Member m2 = new Member("Bob", "M002");
        Member m3 = new Member("Charlie", "M003");
        Member m4 = new Member("Diana", "M004");
        Member m5 = new Member("Edward", "M005");
        Member m6 = new Member("Fiona", "M006");

        //Create member lists (unordered)
        LinkedList<Member> list1 = new LinkedList<>();
        list1.add(m1);
        list1.add(m2);

        LinkedList<Member> list2 = new LinkedList<>();
        list2.add(m3);
        list2.add(m4);

        LinkedList<Member> list3 = new LinkedList<>();
        list3.add(m5);
        list3.add(m6);

        //Add to HashMap (unordered insertion)
        library.put(b2, list2); //1984
        library.put(b1, list1); //The Great Gatsby
        library.put(b3, list3); //To Kill a Mockingbird

        //Iterate using entrySet()
        System.out.println("=== Library Books and Members ===");

        for (Map.Entry<Book, LinkedList<Member>> entry : library.entrySet()) {
            Book book = entry.getKey();
            LinkedList<Member> members = entry.getValue();

            System.out.println("\n" + book + " borrowed by:");
            for (Member m : members) {
                System.out.println("   - " + m);
            }
        }
    }
}
