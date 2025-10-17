package com.dam;

public class Member {
    private String name;
    private String memberID;

    public Member(String name, String memberID) {
        this.name = name;
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public String getMemberID() {
        return memberID;
    }

    @Override
    public String toString() {
        return name + " (ID: " + memberID + ")";
    }
}

