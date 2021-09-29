package com.netodevel.subscription;


import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SubscriptionWorkflow {

    @WorkflowMethod
    void startSubscription();

    @SignalMethod
    void cancelSubscription();

    @SignalMethod
    void updateBillingPeriodChargeAmount();

    @QueryMethod
    String queryCustomerId();

    @QueryMethod
    int queryBillingPeriodNumber();

    @QueryMethod
    int queryBillingPeriodChargeAmount();
}
