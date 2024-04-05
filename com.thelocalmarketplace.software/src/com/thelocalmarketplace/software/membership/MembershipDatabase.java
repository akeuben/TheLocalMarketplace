/**
 * SENG 300 Project - Group 1:
 *
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - 30185321
 */
package com.thelocalmarketplace.software.membership;

import java.util.HashMap;

/**
 * Represents the membership database in lieu of real database software
 */
public class MembershipDatabase {

    private static MembershipDatabase instance;

    private HashMap<Long, Membership> memberDatabase;//Long represents user id
    public static MembershipDatabase getInstance() {
        return instance;
    }

    private MembershipDatabase(HashMap<Long, Membership> memberDatabase) {
        this.memberDatabase = memberDatabase;
    }

    public static void initialize(HashMap<Long, Membership> memberDatabase) throws RuntimeException {
        if(instance != null){
            throw new RuntimeException("Database already exists");
        }

        instance = new MembershipDatabase(memberDatabase);
    }

    public boolean validateMembershipNumber(long id){
        return memberDatabase.containsKey((Long) id);
    }

    public long getMemberPoints(long id){

        return validateMembershipNumber(id) ? memberDatabase.get(id).getMemberPoints() : 0;
        /*
        //In case membership doesn't get validated first
        if(!validateMembershipNumber(id)){
            return 0;
        }
        return memberDatabase.get(id).getMemberPoints();
        */
    }

}
