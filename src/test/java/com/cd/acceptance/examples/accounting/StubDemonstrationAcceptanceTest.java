package com.cd.acceptance.examples.accounting;

import com.cd.acceptance.examples.accounting.dsl.Dsl;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StubDemonstrationAcceptanceTest extends Dsl {
    @Test
    public void shouldAllowInvoiceSubmissionForApprovedAccounts() {
        kyc.approveAccount("name: InvoiceSubmitter1");
        invoices.createAccount("name: InvoiceSubmitter1", "role: Submitter", "kyc: Approved");
        invoices.submitInvoice("name: InvoiceSubmitter1", "invoice: invoice1", "Item: Software License");

        invoices.confirmInvoiceSubmitted("name: InvoiceSubmitter1", "invoice: invoice1");
    }

    @Test
    public void shouldRejectAccountCreationForAccountsRejectedByKyc() throws AssertionFailedError {
        AssertionFailedError e = assertThrows(AssertionFailedError.class, () ->
                invoices.createAccount("name: InvoiceSubmitter1", "role: Submitter", "kyc: Denied")) ;
        assertEquals("Unable to create authorised account for: InvoiceSubmitter1", e.getMessage());
    }
}


