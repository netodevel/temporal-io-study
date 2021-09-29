package com.netodevel;

import com.netodevel.money_transfer.AccountActivityImpl;
import com.netodevel.money_transfer.MoneyTransferWorkflow;
import com.netodevel.money_transfer.MoneyTransferWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.WorkerFactory;

import java.util.Scanner;
import java.util.UUID;

public class TemporalApplication {

    public static void main(String[] args) {

        // objetos de configuração
        // esse cara é responsável por fazer a comunicação com o temporal Server via GRPC
        var service = WorkflowServiceStubs.newInstance();
        // esse cara é responsável por gerenciar programaticamente workflows
        var client = WorkflowClient.newInstance(service);

        var factory = WorkerFactory.newInstance(client);
        var worker = factory.newWorker("money-transfer-task-queue");

        worker.registerWorkflowImplementationTypes(MoneyTransferWorkflowImpl.class);
        worker.registerActivitiesImplementations(new AccountActivityImpl());

        factory.start();

        // criação de workflow
        var workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue("money-transfer-task-queue")
                .setWorkflowId("money-transfer-workflow") // prevent duplicate instances of workflows
                .build();
        var moneyTransferWorkflow = client.newWorkflowStub(MoneyTransferWorkflow.class, workflowOptions);

        var referenceId = UUID.randomUUID().toString();
        var fromAccount = "001-001";
        var toAccount = "001-001";
        var amount = 2.50;

        // executor de workflows

        var scanner = new Scanner(System.in);
        var start = scanner.next();
        if (start.equals("1")) {
            var we = WorkflowClient.start(moneyTransferWorkflow::transfer, fromAccount, toAccount, referenceId, amount);
            System.out.printf("\nTransfer of $%f from account %s to account %s is processing\n", amount, fromAccount, toAccount);
            System.out.printf("\nWorkflowID: %s RunID: %s", we.getWorkflowId(), we.getRunId());
        }
    }

}
