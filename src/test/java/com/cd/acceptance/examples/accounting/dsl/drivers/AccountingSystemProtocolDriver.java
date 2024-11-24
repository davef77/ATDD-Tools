package com.cd.acceptance.examples.accounting.dsl.drivers;

import java.util.List;

public interface AccountingSystemProtocolDriver {
    void createAccount(String name, String password, String role);

    void submitInvoice(String userName, String invoiceName, String purchaseOrder,
                       String invoiceNumber, List<String> items, String total);

    void confirmInvoiceSubmitted(String userName, String invoice);

    void confirmPendingAuthorisations(String userName, String pendingItem);
}
