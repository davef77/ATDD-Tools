package com.cd.acceptance.examples.accounting.dsl;

import com.cd.acceptance.dsl.Params;
import com.cd.acceptance.examples.accounting.dsl.drivers.DummyAccountingSystemProtocolDriver;
import com.cd.acceptance.examples.accounting.DummyAccountingSUT;
import com.cd.acceptance.examples.accounting.dsl.drivers.KYCCheckProtocolDriver;
import com.cd.acceptance.examples.accounting.dsl.drivers.StubExternalKYCCheck;
import org.junit.jupiter.api.BeforeEach;

public class Dsl {
    private final Params.DslContext context = new Params.DslContext();

    protected InvoicesDsl invoices;
    protected KycDsl kyc;

    @BeforeEach
    public void setUp() {
//        In reality the Protocol Driver would connect to the real "System Under Test"
//        For the purpose of this example, we use a simple class to represent the SUT here.
//        A real Protocol Driver would do whatever it takes to translate from DSL to interactions with the SUT
//        For example, we may use something like Selenium to interact with a web page, or a REST client to interact with a web service
//        and this code would only exist in the Protocol Driver.

        StubExternalKYCCheck kycCheck = new StubExternalKYCCheck();
        DummyAccountingSUT sut = new DummyAccountingSUT(kycCheck);

        kyc = new KycDsl(context, new KYCCheckProtocolDriver(kycCheck));
        invoices = new InvoicesDsl(context, new DummyAccountingSystemProtocolDriver(context, sut,kycCheck), kyc);
    }
}
