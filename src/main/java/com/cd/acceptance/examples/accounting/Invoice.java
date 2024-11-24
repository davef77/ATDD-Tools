package com.cd.acceptance.examples.accounting;

import java.util.List;

public class Invoice {
    private final String invoiceName;
    private final String purchaseOrder;
    private final String invoiceNumber;
    private final List<String> items;
    private String total;

    public Invoice(String invoiceName, String purchaseOrder, String invoiceNumber, List<String> items, String total) {

        this.invoiceName = invoiceName;
        this.purchaseOrder = purchaseOrder;
        this.invoiceNumber = invoiceNumber;
        this.items = items;
        this.total = total;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public List<String> getItems() {
        return items;
    }

    public String getTotal() {
        return total;
    }

}
