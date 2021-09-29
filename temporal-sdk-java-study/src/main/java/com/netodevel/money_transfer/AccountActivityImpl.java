package com.netodevel.money_transfer;

public class AccountActivityImpl implements AccountActivity {

    // implementações reais aqui!
    // aqui terá chamadas a outros serviços

    @Override
    public void deposit(String accountId, String referenceId, double amount) {
        System.out.printf(
                "\nDepositing $%f from account %s. ReferenceId: %s\n",
                amount, accountId, referenceId
        );
    }

    @Override
    public void withdraw(String accountId, String referenceId, double amount) {
        System.out.printf(
                "\nWithdrawing $%f from account %s. ReferenceId: %s\n",
                amount, accountId, referenceId
        );
    }
}
