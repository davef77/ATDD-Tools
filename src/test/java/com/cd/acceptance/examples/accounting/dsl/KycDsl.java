package com.cd.acceptance.examples.accounting.dsl;

import com.cd.acceptance.dsl.Params;
import com.cd.acceptance.examples.accounting.dsl.drivers.KYCCheckProtocolDriver;

public class KycDsl {
    private final Params.DslContext context;
    private final KYCCheckProtocolDriver kycCheckDriver;

    public KycDsl(Params.DslContext context, KYCCheckProtocolDriver kycCheckDriver) {
        this.context = context;
        this.kycCheckDriver = kycCheckDriver;
    }

    public void approveAccount(String... args) {
        Params params = new Params(context, args);
        String account = params.alias("name");

        kycCheckDriver.approveAccount(account);
    }

    public void rejectAccount(String... args) {
        Params params = new Params(context, args);
        String account = params.alias("name");

        kycCheckDriver.rejectAccount(account);
    }
}
