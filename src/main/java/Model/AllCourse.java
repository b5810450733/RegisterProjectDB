package Model;

import Database.DBConnect;
import Database.DBControl;

import java.util.ArrayList;
import java.util.Arrays;

public class AllCourse {
//    public String[] year11 = new String[] {"01417111","01418112","01418114","01418131","01999111"};
//    public String[] year12 = new String[] {"01417112","01418113","01418132"};
//    public String[] year21 = new String[] {"01417322","01418211","01418231","01422111"};
//    public String[] year22 = new String[] {"01418221","01418232","01418233"};
//    public String[] year31 = new String[] {"01418321","01418331","01418341","01418497"};
//    public String[] year32 = new String[] {"01418332","01418333","01418334","01418351","01418390"};
//    public String[] year41 = new String[] {"01418490"};
//    public String[] year42 = new String[] {"01418499"};

//    public ArrayList<String>[] findSubject(Subject subject){
//        ArrayList[] name = new ArrayList[2] ;
//        ArrayList<String> base = new ArrayList<>();
//        ArrayList<String> continueSubject = new ArrayList<>();
//        try {
//            String[] moreBase = subject.getBaseSubject().split("#");
//            String[] moreCon = subject.getContSubject().split("#");
//            DBControl dbControl = DBConnect.openDB();
//            for (Subject subjectDB:dbControl.readSubject()) {
//                for (int i = 0; i < moreBase.length; i++) {
//                    if (subjectDB.getBaseSubject().equals(moreBase[i])){
//                        base.add(subjectDB.getSubName());
//                    }
//                }
////                for (int i = 0; i < moreCon.length; i++) {
////                    if (subjectDB.getBaseSubject().equals(moreCon[i])){
////                        continueSubject.add(subjectDB.getSubName());
////                    }
////                }
//            }
//
//        }catch (NullPointerException e){
//            base.add("No Base Subject");
//            continueSubject.add("No Continue Subject");
//            name[1] = base;
////            name[2] = continueSubject;
//            return name;
//        }
//        name[1] = base;
//        return name;
//    }
//
//    public String baseSubject(Subject subject){
//        String showing ="";
//        try {
//            String[] base = subject.getBaseSubject().split("#");
//            DBControl dbControl = DBConnect.openDB();
//            for (Subject check: dbControl.readSubject()) {
//                for (int i = 0; i < base.length; i++) {
//                    if (check.getSubCode().equals(base[i])){
//                        showing += check.getSubName()+"|";
//                    }
//                }
//            }
//        }catch (NullPointerException e){
//            showing = "No Base Subject";
//            return showing;
//        }
//        return showing;
//    }








//    public String[] getYear11() {
//        return year11;
//    }
//
//    public void setYear11(String[] year11) {
//        this.year11 = year11;
//    }
//
//    public String[] getYear12() {
//        return year12;
//    }
//
//    public void setYear12(String[] year12) {
//        this.year12 = year12;
//    }
//
//    public String[] getYear21() {
//        return year21;
//    }
//
//    public void setYear21(String[] year21) {
//        this.year21 = year21;
//    }
//
//    public String[] getYear22() {
//        return year22;
//    }
//
//    public void setYear22(String[] year22) {
//        this.year22 = year22;
//    }
//
//    public String[] getYear31() {
//        return year31;
//    }
//
//    public void setYear31(String[] year31) {
//        this.year31 = year31;
//    }
//
//    public String[] getYear32() {
//        return year32;
//    }
//
//    public void setYear32(String[] year32) {
//        this.year32 = year32;
//    }
//
//    public String[] getYear41() {
//        return year41;
//    }
//
//    public void setYear41(String[] year41) {
//        this.year41 = year41;
//    }
//
//    public String[] getYear42() {
//        return year42;
//    }
//
//    public void setYear42(String[] year42) {
//        this.year42 = year42;
//    }
}
