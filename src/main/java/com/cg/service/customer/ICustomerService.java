package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.model.dto.customer.CustomerCreReqDTO;
import com.cg.model.dto.customer.CustomerCreResDTO;
import com.cg.model.dto.customer.CustomerResDTO;
import com.cg.model.dto.customer.CustomerUpReqDTO;
import com.cg.model.dto.transfer.TransferCreReqDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    List<Customer> findAllByDeletedIsFalse();
    List<Customer> findAllByIdNot(Long id);
    Boolean existsByEmail(String email);
    Boolean existsByEmailAndIdIsNot(String email, Long id);
    void incrementBalance(Long id, BigDecimal amount);
    Customer deposit(Deposit deposit);
    Customer withdraw(Withdraw withdraw);
    void transfer(Transfer transfer);
    void transfer(TransferCreReqDTO transferCreReqDTO);
    List<CustomerResDTO> findAllRecipientsWithoutSenderId(@Param("senderId") Long senderId);
    List<CustomerResDTO> findAllCustomerResDTOS();
    CustomerCreResDTO create(CustomerCreReqDTO customerCreReqDTO);
    Customer update(Customer customer, CustomerUpReqDTO customerUpReqDTO);
}