package com.cmps312.payroll.model

fun main() {

    // create payable array List
    val payables = arrayListOf<Payable>()

    // populate array with objects that implement Payable
    payables.add(Invoice("01234", "Textbook", 2, 375.00))
    payables.add(Invoice("56789", "USB Disk", 3, 179.95))
    payables.add(SalariedEmployee("Ahmed", "Ali", "111-11-1111", 15000.00))
    payables.add(HourlyEmployee("Fatima", "Saleh", "222-22-2222", 160.75, 40.0))
    payables.add(CommissionEmployee("Samir", "Sami", "333-33-3333", 100000.0, .06))

    println("Invoices and Employees processed philosophically:\n")

    // generically process each element in array payableObjects using foreach

    payables.forEach { payable ->
        // output currentPayable and its appropriate payment amount
        println("$payable\n")

        if (payable is SalariedEmployee) {
            val oldBaseSalary = payable.monthlySalary
            payable.monthlySalary = oldBaseSalary * 1.1
            println("New salary with 10%% increase is: QR ${payable.getPaymentAmount()}\n")
        }
    }
}