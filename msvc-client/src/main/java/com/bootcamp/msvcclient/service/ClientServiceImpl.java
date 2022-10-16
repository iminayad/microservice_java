package com.bootcamp.msvcclient.service;

import com.bootcamp.msvcclient.model.document.Client;
import com.bootcamp.msvcclient.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ClientServiceImpl implements ClientService {


    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Flux<Client> findAll() {
        return clientRepository.findAll()
                .switchIfEmpty(Mono.error(
                        new Throwable("No hay clientes registrados")));
    }

    @Override
    public Mono<Void> delete(Client client) {
        logger.info("ClientServiceImpl: delete");
        return clientRepository.delete(client);
    }


    @Override
    public Mono<Client> save(Client client) {
        return clientRepository.findAll()
                .filter(clientDB ->
                        clientDB.getIdentityNumber().equals(client.getIdentityNumber())
                )
                .collectList()
                .flatMap(clients -> {
                    if (clients.size() > 0) {
                        return Mono.error(new Throwable("El cliente ya existe"));
                    }
                    return clientRepository.save(client);
                });
    }

    @Override
    public Mono<Client> updateByIdentityNumber(Client client, String identityNumber) {

        return clientRepository.findByIdentityNumber(identityNumber)
                .flatMap(clientDB -> {
                    clientDB.setAddress(client.getAddress());
                    if (client.getName() != null) clientDB.setName(client.getName());
                    if (client.getAddress() != null) clientDB.setName(client.getAddress());
                    if (client.getPhoneNumber() != null) clientDB.setName(client.getPhoneNumber());
                    if (client.getSegmentType() != null) clientDB.setSegmentType(client.getSegmentType());
                    return clientRepository.save(clientDB);
                });

    }

//    @Cacheable(value = "clientIdCache")
    @Override
    public Mono<Client> findById(String id) {
        logger.info("ClientServiceImpl: findById");
        return clientRepository.findById(id);
    }

}
