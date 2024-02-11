package com.hyperfoods.userMicroService.service;


import com.hyperfoods.userMicroService.entity.Address;
import com.hyperfoods.userMicroService.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address save(Address address) {
        return repository.save(address);
    }

    public Address findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Page findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Address update(Address address) {
        Address existingAddress = repository.findById(address.getId()).orElse(null);
        if (address.getStreet() != null){
            existingAddress.setStreet(address.getStreet());
        }
        if (address.getStreet() != null){
            existingAddress.setStreet(address.getStreet());
        }
        if (address.getCity() != null){
            existingAddress.setCity(address.getCity());
        }
        if (address.getNumber() != null){
            existingAddress.setNumber(address.getNumber());
        }
        if (address.getProvince() != null){
            existingAddress.setProvince(address.getProvince());
        }
        if (address.getZipCode() != null){
            existingAddress.setZipCode(address.getZipCode());
        }
        if (address.getPhoneNumber() != null){
            existingAddress.setPhoneNumber((address.getPhoneNumber()));
        }
        return repository.save(existingAddress);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void deactivateById(Long id) {
        Address existingAddress = repository.findById(id).orElse(null);
        existingAddress.setActive(false);
        repository.save(existingAddress);
    }
}
