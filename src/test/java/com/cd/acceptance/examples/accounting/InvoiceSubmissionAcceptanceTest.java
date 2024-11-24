package com.cd.acceptance.examples.accounting;

import com.cd.acceptance.examples.accounting.dsl.Dsl;
import junit.framework.AssertionFailedError;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InvoiceSubmissionAcceptanceTest extends Dsl {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldAcknowledgeInvoiceSubmission() {
        invoices.createAccount("name: InvoiceSubmitter1", "role: Submitter");
        invoices.submitInvoice(
                "name: InvoiceSubmitter1", "invoice: invoice1", "Item: Software License");
        invoices.confirmInvoiceSubmitted("name: InvoiceSubmitter1", "invoice: invoice1");
    }

    @Test
    public void shouldSendSubmittedInvoiceToSupervisor() {
        invoices.createAccount("name: InvoiceSubmitter1", "role: Submitter");
        invoices.createAccount("name: Supervisor1", "role: AccountingSupervisor");
        invoices.submitInvoice(
                "name: InvoiceSubmitter1", "invoice: invoice1", "Item: Software License");
        invoices.confirmPendingAuthorisations("name: Supervisor1", "pendingItem: invoice1");
    }

    @Test
    public void shouldAllowInvoiceSubmissionForApprovedSubmitterAccounts() {
        invoices.createAccount("name: InvoiceSubmitter1", "role: Submitter");
        invoices.submitInvoice("name: InvoiceSubmitter1", "invoice: invoice1", "Item: Software License");
        invoices.confirmInvoiceSubmitted("name: InvoiceSubmitter1", "invoice: invoice1");
    }

    @Test
    public void shouldRejectInvoiceSubmissionForNoneSubmitterAccounts() throws AssertionFailedError {
        invoices.createAccount("name: NoneSubmitter1");

        thrown.expect(AssertionFailedError.class);
        thrown.expectMessage("User 'NoneSubmitter11' does not have permission to submit invoices");

        invoices.submitInvoice("name: NoneSubmitter1", "invoice: invoice1", "Item: Software License");
    }

}

//Given an authorised account “invoice Submitter 1”
//Given an invoice for a software license, invoice1
//When “invoice Submitter 1” submits invoice1
//Then invoice1 is acknowledged to “invoice Submitter 1”.

//Given an authorised account “invoice Submitter 1”
//Given an authorised account “accounting Supervisor 1”
//Given an invoice for a software license, invoice1
//When “invoice Submitter 1” submits invoice1
//Then invoice1 is sent to “accounting supervisor1” as "pending Authorisation"
