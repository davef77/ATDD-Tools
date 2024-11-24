package com.cd.acceptance.examples.accounting;

public class SubmittedInvoice {
    private final User user;
    private final Invoice invoice;

    public SubmittedInvoice(User user, Invoice invoice) {

        this.user = user;
        this.invoice = invoice;
    }

    public User getUser() {
        return user;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
