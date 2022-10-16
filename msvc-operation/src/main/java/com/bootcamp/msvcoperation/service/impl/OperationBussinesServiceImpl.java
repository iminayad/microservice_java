package com.bootcamp.msvcoperation.service.impl;

import com.bootcamp.msvcoperation.model.document.Operation;
import com.bootcamp.msvcoperation.model.mapper.OperationMapper;
import com.bootcamp.msvcoperation.repository.OperationRepository;
import com.bootcamp.msvcoperation.webclient.MsvcAccountWebClient;
import com.specification.api.dto.AccountDto;
import com.specification.api.dto.BalanceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@Slf4j
public class OperationBussinesServiceImpl {



    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private MsvcAccountWebClient accountWebClient;

    @Autowired
    private OperationMapper mapper;


    @Value("${spring.typeOne}")
    public String typeOne;

    @Value("${spring.typeTwo}")
    private String typeTwo;

    @Value("${spring.typeThree}")
    private String typeThree;

    @Value("${spring.typeFour}")
    private String typeFour;


    @Value("${product.typeOne}")
    public String productTypeOne;

    @Value("${product.typeTwo}")
    private String productTypeTwo;

    @Value("${product.typeThree}")
    private String productTypeThree;

    @Value("${product.typeFour}")
    public String productTypeFour;

    @Value("${product.typeFive}")
    private String productTypeFive;

    @Value("${product.typeSix}")
    private String productTypeSix;

    @Value("${product.typeSeven}")
    private String productTypeSeven;





    public Mono<Operation> saveOperation( Operation operation) {

        Mono<Operation> result = new Mono<Operation>() {
            @Override
            public void subscribe(CoreSubscriber<? super Operation> actual) {

            }
        };

            if(operation.getType().equals(typeOne)){
                result=  accountWebClient.getAccountById(operation.getIdAccount())
                        .switchIfEmpty(Mono.error(new Throwable("Cuenta no encontrada")))
                        .flatMap(account ->
                                Mono.just(account.getProductList().stream().map(product -> {
                                    log.info(" operation"+operation.toString());
                                    BalanceDto balance = new BalanceDto();
                                    if (product.getId().equals(operation.getIdProductOne())) {
                                        product.setBalance(product.getBalance() - operation.getBalanceOperation());
                                        if(product.getId().equals(productTypeFour) || product.getId().equals(productTypeFive))
                                           product.setBalancespent((product.getBalancespent()==null?0:product.getBalancespent()) + operation.getBalanceOperation());


                                    } else if  (product.getId().equals(operation.getIdProductTwo())) {


                                        if (product.getId().equals(operation.getIdProductOne()) && (product.getId().equals(productTypeFour) || product.getId().equals(productTypeFive))) {


                                            Double spent= ((product.getBalancespent()==null?0:product.getBalancespent()) - operation.getBalanceOperation());
                                            if(spent<0){
                                                product.setBalancespent(0.0);
                                                product.setBalance( product.getBalance()+spent);
                                            }
                                            else{
                                                product.setBalancespent(spent);
                                                product.setBalance( product.getBalance()+spent);
                                            }
                                        }
                                        else{
                                            product.setBalance(product.getBalance() + operation.getBalanceOperation());
                                        }
                                    }

                                    balance.setBalance(product.getBalance());
                                    balance.setBalancespent(product.getBalancespent());

                                    return accountWebClient.updateAccount(account.getId(),product.getId(), Mono.just(balance));
                                }).collect(Collectors.toList()))

                        )
                        .flatMap(operationDB->operationRepository.save(operation));
            }
            else if(operation.getType().equals(typeTwo)){
                result=   accountWebClient.getAccountById(operation.getIdAccount())
                    .switchIfEmpty(Mono.error(new Throwable("Cuenta no encontrada")))
                    .flatMap(account ->
                            Mono.just(account.getProductList().stream().map(product -> {
                                if (product.getId().equals(operation.getIdProductOne()) && (product.getId().equals(productTypeFour) || product.getId().equals(productTypeFive))) {
                                    product.setBalance(product.getBalance() + operation.getBalanceOperation());

                                  Double spent= ((product.getBalancespent()==null?0:product.getBalancespent()) - operation.getBalanceOperation());
                                  if(spent<0){
                                      product.setBalancespent(0.0);
                                      product.setBalance( product.getBalance()+spent);
                                  }
                                  else{
                                      product.setBalancespent(spent);
                                      product.setBalance( product.getBalance()+spent);
                                  }
                                }
                                else if (product.getId().equals(operation.getIdProductOne()) && !product.getId().equals(productTypeFour) && !product.getId().equals(productTypeFive)) {
                                    product.setBalance(product.getBalance() + operation.getBalanceOperation());

                                }
                                BalanceDto balance = new BalanceDto();
                                balance.setBalance(product.getBalance());
                                balance.setBalancespent(product.getBalancespent());
                                return accountWebClient.updateAccount(account.getId(),product.getId(), Mono.just(balance));
                            }).collect(Collectors.toList()))

                    )
                    .flatMap(operationDB->operationRepository.save(operation));
        }

            else if(operation.getType().equals(typeThree)){
                result=   accountWebClient.getAccountById(operation.getIdAccount())
                        .switchIfEmpty(Mono.error(new Throwable("Cuenta no encontrada")))
                        .flatMap(account ->
                                Mono.just(account.getProductList().stream().map(product -> {
                                    if (product.getId().equals(operation.getIdProductOne()) && (product.getId().equals(productTypeFour) || product.getId().equals(productTypeFive))) {
                                        product.setBalance(product.getBalance() + operation.getBalanceOperation());

                                            Double spent= ((product.getBalancespent()==null?0:product.getBalancespent()) + operation.getBalanceOperation());


                                            product.setBalancespent(spent);
                                            product.setBalance( product.getBalance()- operation.getBalanceSpentOperation());

                                    }
                                    else if (product.getId().equals(operation.getIdProductOne()) && !product.getId().equals(productTypeFour) && !product.getId().equals(productTypeFive)) {
                                       Double saldo = product.getBalance() - operation.getBalanceOperation();
                                       product.setBalance(saldo);
                                    }
                                    BalanceDto balance = new BalanceDto();
                                    balance.setBalance(product.getBalance());

                                    return accountWebClient.updateAccount(account.getId(),product.getId(), Mono.just(balance));
                                }).collect(Collectors.toList()))

                        )
                        .flatMap(operationDB->operationRepository.save(operation));
            }


          return result;


    }
}
