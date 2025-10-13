package academyawards2;

import academyawards2.data.FileHandler;

import java.io.File;

public class Main {

    public static void main(String[] args){
        FileHandler.readNominationFile("the_oscar_award.tsv");
    }
}


