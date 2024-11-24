package com.cd.acceptance.examples.accounting;

import com.cd.acceptance.examples.accounting.dsl.Dsl;
import junit.framework.AssertionFailedError;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StubDemonstrationAcceptanceTest extends Dsl {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldAllowInvoiceSubmissionForApprovedAccounts() {
        kyc.approveAccount("name: InvoiceSubmitter1");
        invoices.createAccount("name: InvoiceSubmitter1", "role: Submitter", "kyc: Approved");
        invoices.submitInvoice("name: InvoiceSubmitter1", "invoice: invoice1", "Item: Software License");

        invoices.confirmInvoiceSubmitted("name: InvoiceSubmitter1", "invoice: invoice1");
    }

    @Test
    public void shouldRejectAccountCreationForAccountsRejectedByKyc() throws AssertionFailedError {
        thrown.expect(AssertionFailedError.class);
        thrown.expectMessage("Unable to create authorised account for: InvoiceSubmitter1");

        invoices.createAccount("name: InvoiceSubmitter1", "role: Submitter", "kyc: Denied");
    }
}


