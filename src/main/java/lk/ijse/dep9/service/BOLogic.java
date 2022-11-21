package lk.ijse.dep9.service;

import lk.ijse.dep9.dao.DataAccess;

public class BOLogic {
    public static boolean deleteMember(String memberId ){
        // Some Logic
        return DataAccess.deleteMember(memberId);

    }
}
