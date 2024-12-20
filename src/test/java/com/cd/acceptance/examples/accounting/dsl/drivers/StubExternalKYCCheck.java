package com.cd.acceptance.examples.accounting.dsl.drivers;

import com.cd.acceptance.examples.accounting.ExternalKYCCheck;

import java.util.HashSet;
import java.util.Set;

public class StubExternalKYCCheck implements ExternalKYCCheck {
    private final Set<String> validUsers = new HashSet<>();

    void onVerifyApproveAccount(String account) {
        validUsers.add(account);
    }

    void onVerifyRejectAccount(String account) {
        validUsers.remove(account);
    }

    @Override
    public boolean verifyAccount(String name) {
        return validUsers.contains(name);
    }
}


