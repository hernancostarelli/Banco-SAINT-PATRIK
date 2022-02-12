package com.banco.Saint_Patrik.Service;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.TransactionEntity;
import com.banco.Saint_Patrik.Entity.UserEntity;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Repository.CardRepository;
import com.banco.Saint_Patrik.Repository.TransactionRepository;
import com.banco.Saint_Patrik.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository tranRepository;

    @Transactional
    public void registrar(UserEntity user, CardEntity card, TransactionEntity transaction) throws ErrorService {

        userRepository.save(user);
        cardRepository.save(card);
        tranRepository.save(transaction);

    }

    @Transactional
    public void registrar2(CardEntity card) throws ErrorService {

        cardRepository.save(card);
    }
}
