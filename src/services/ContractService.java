package services;

import Entities.Contract;
import Entities.Installment;

import java.time.LocalDate;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }


    public void processContract(Contract contract, int months){
        double basiQuota = contract.getTotalValue() / months;
        for (int i = 1; i <= months ; i++) {
            LocalDate dueDate = contract.getDate().plusMonths(i);

            double interest = onlinePaymentService.interest(basiQuota, i);
            double fee = onlinePaymentService.paymentFee(basiQuota + interest);
            double quota = basiQuota + interest + fee;


            contract.getInstallments().add(new Installment(dueDate, quota));


        }
    }
}
