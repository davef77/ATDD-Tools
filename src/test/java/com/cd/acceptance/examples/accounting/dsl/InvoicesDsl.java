package com.cd.acceptance.examples.accounting.dsl;

import com.cd.acceptance.examples.accounting.dsl.drivers.AccountingSystemProtocolDriver;
import com.cd.acceptance.dsl.Params;

import java.util.List;

public class InvoicesDsl {
    private final Params.DslContext context;
    private final AccountingSystemProtocolDriver driver;
    private final KycDsl kyc;

    protected InvoicesDsl(Params.DslContext context, AccountingSystemProtocolDriver driver, KycDsl kyc) {
        this.context = context;
        this.driver = driver;
        this.kyc = kyc;
    }

    public void createAccount(String... args) {
        Params params = new Params(context, args);

        String name = params.alias("name");
        String role = params.optional("role", "Accountant");
        String kycCheck = params.optional("kyc", "Approved");
        String password = params.optional("password", "password123");

        if ("Approved".equals(kycCheck))
            kyc.approveAccount(args);
        else
            kyc.rejectAccount(args);

        driver.createAccount(name, password, role);
    }

    public void submitInvoice(String... args) {
        Params params = new Params(context, args);

        String userName = params.alias("name");
        String invoice = params.alias("invoice", "anInvoice");
        String purchaseOrder = params.optional("po", "po1");
        String invoiceNumber = params.optionalSequence("InvoiceNo", 1);
        String total = params.optional("total", "0");
        List<String> items = params.optionalList("item", new String[] {"item1"});

        driver.submitInvoice(userName, invoice, purchaseOrder, invoiceNumber, items, total);
    }

    public void confirmInvoiceSubmitted(String... args) {
        Params params = new Params(context, args);

        String userName = params.alias("name");
        String invoice = params.optional("invoice", "anInvoice");

        driver.confirmInvoiceSubmitted(userName, this.context.alias(invoice));
    }

    public void confirmPendingAuthorisations(String... args) {
        Params params = new Params(context, args);

        String userName = params.alias("name");
        String pendingItem = params.optional("pendingItem", "someItem");

        driver.confirmPendingAuthorisations(userName, context.alias(pendingItem));
    }

    public void confirmInvoiceRejected(String... args) {
    }
}
