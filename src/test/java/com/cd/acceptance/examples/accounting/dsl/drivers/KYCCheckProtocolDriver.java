package com.cd.acceptance.examples.accounting.dsl.drivers;

public class KYCCheckProtocolDriver {
    private final StubExternalKYCCheck kycCheck;

    public KYCCheckProtocolDriver(StubExternalKYCCheck kycCheck) {
        this.kycCheck = kycCheck;
    }

    public void approveAccount(String account) {
        kycCheck.onVerifyApproveAccount(account);
    }

    public void rejectAccount(String account) {
        kycCheck.onVerifyRejectAccount(account);
    }
}

