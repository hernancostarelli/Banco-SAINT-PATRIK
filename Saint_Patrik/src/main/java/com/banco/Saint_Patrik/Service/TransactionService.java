package com.banco.Saint_Patrik.Service;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.TransactionEntity;
import com.banco.Saint_Patrik.Entity.UserEntity;
import com.banco.Saint_Patrik.Enum.Type_Transaction;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Repository.CardRepository;
import com.banco.Saint_Patrik.Repository.TransactionRepository;
import com.banco.Saint_Patrik.Repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    /**
     * MÉTODO PARA REALIZAR UNA NUEVA TRANSACCIÓN
     *
     * METHOD TO MAKE A NEW TRANSACTION
     *
     * @param cardOwn
     * @param cardDestiny
     * @param amount
     * @return
     * @throws ErrorService
     */
    @Transactional
    public TransactionEntity newTransaction(CardEntity cardOwn,
            CardEntity cardDestiny, Double amount) throws ErrorService {

        if (cardOwn == null) {
            throw new ErrorService("THE SOURCE CARD NUMBER CANNOT BE EMPTY");
        }
        if (cardDestiny == null) {
            throw new ErrorService("THE DESTINATION CARD NUMBER CANNOT BE EMPTY");
        }
        if (amount == null || String.valueOf(amount).isEmpty()) {
            throw new ErrorService("THE TRANSACTION AMOUNT CANNOT BE EMPTY");
        }
        if (amount < 1) {
            throw new ErrorService("THE TRANSACTION AMOUNT MUST BE GREATER THAN OR EQUAL TO ONE");
        }

        // TRANSACCIÓN ORIGEN
        // ORIGIN TRANSACTION
        TransactionEntity transaction1 = new TransactionEntity();

        Date dateTransaction1 = new Date();

        if (cardOwn.getCredit() >= amount) {
            transaction1.setUser(cardOwn.getUser());
            transaction1.setEnabled(true);
            transaction1.setCard(cardOwn);
            transaction1.setDateTransaction(dateTransaction1);
            transaction1.setAmount(amount);
            transaction1.setType(Type_Transaction.SEND);

            cardOwn.setCredit(cardOwn.getCredit() - amount);
            cardRepository.save(cardOwn);
            transactionRepository.save(transaction1);

            // TRANSACCIÓN DESTINO
            // DESTINATION TRANSACTION
            TransactionEntity transaction2 = new TransactionEntity();

            Date dateTransaction2 = new Date();

            transaction2.setUser(cardDestiny.getUser());
            transaction2.setEnabled(true);
            transaction2.setCard(cardDestiny);
            transaction2.setDateTransaction(dateTransaction2);
            transaction2.setAmount(amount);
            transaction2.setType(Type_Transaction.RECEIVED);

            cardDestiny.setCredit(cardDestiny.getCredit() + amount);
            cardRepository.save(cardDestiny);
            transactionRepository.save(transaction2);

            // ENVÍO DE MAIL CONFIRMANDO TRANSACCIÓN
            // SENDING MAIL CONFIRMING TRANSACTION
            String to_1 = cardDestiny.getUser().getMail();
            String to_2 = cardOwn.getUser().getMail();
            String decription1 = "TRIAL TRANSFER - RECEIPT OF MONEY";
            String decription2 = "TRIAL TRANSFER - SENDING MONEY";

            // ENVÍO DEL MAIL DE CONFIRMACIÓN A LA PERSONA DESTINO
            // SENDING THE CONFIRMATION EMAIL TO THE DESTINATION PERSON
            mailService.sendMail(to_1, cardOwn.getUser(), cardDestiny.getUser(), amount, decription1);

            // ENVÍO DEL MAIL DE CONFIRMACIÓN A LA PERSONA ORIGEN
            // SENDING THE CONFIRMATION EMAIL TO THE PERSON OF ORIGIN
            mailService.sendMail(to_2, cardOwn.getUser(), cardDestiny.getUser(), amount, decription2);

        } else {
            throw new ErrorService("THE AVAILABLE BALANCE IS NOT ENOUGH TO MAKE THE TRANSACTION");
        }
        return transaction1;
    }

    @Transactional
    public TransactionEntity newTransactionTwo(UserEntity user1, UserEntity user2, CardEntity cardOwn,
            CardEntity cardDestiny, Double amount) throws ErrorService {

        if (user1 == null) {
            throw new ErrorService("THE USER 1 CANNOT BE EMPTY");
        }
        if (user2 == null) {
            throw new ErrorService("THE USER 2 CANNOT BE EMPTY");
        }
        if (cardOwn == null) {
            throw new ErrorService("THE SOURCE CARD NUMBER CANNOT BE EMPTY");
        }
        if (cardDestiny == null) {
            throw new ErrorService("THE DESTINATION CARD NUMBER CANNOT BE EMPTY");
        }
        if (amount == null || String.valueOf(amount).isEmpty()) {
            throw new ErrorService("THE TRANSACTION AMOUNT CANNOT BE EMPTY");
        }
        if (amount < 1) {
            throw new ErrorService("THE TRANSACTION AMOUNT MUST BE GREATER THAN OR EQUAL TO ONE");
        }

        // TRANSACCIÓN ORIGEN
        // ORIGIN TRANSACTION
        TransactionEntity transaction1 = new TransactionEntity();

        Date dateTransaction1 = new Date();

        if (cardOwn.getCredit() >= amount) {
            transaction1.setUser(cardOwn.getUser());
            transaction1.setEnabled(true);
            transaction1.setCard(cardOwn);
            transaction1.setDateTransaction(dateTransaction1);
            transaction1.setAmount(amount);
            transaction1.setType(Type_Transaction.SEND);

            cardOwn.setCredit(cardOwn.getCredit() - amount);
            cardRepository.save(cardOwn);
            transactionRepository.save(transaction1);

            // TRANSACCIÓN DESTINO
            // DESTINATION TRANSACTION
            TransactionEntity transaction2 = new TransactionEntity();

            Date dateTransaction2 = new Date();

            transaction2.setUser(cardDestiny.getUser());
            transaction2.setEnabled(true);
            transaction2.setCard(cardDestiny);
            transaction2.setDateTransaction(dateTransaction2);
            transaction2.setAmount(amount);
            transaction2.setType(Type_Transaction.RECEIVED);

            cardDestiny.setCredit(cardDestiny.getCredit() + amount);
            cardRepository.save(cardDestiny);
            transactionRepository.save(transaction2);

            // ENVÍO DE MAIL CONFIRMANDO TRANSACCIÓN
            // SENDING MAIL CONFIRMING TRANSACTION
            String to_1 = cardDestiny.getUser().getMail();
            String to_2 = cardOwn.getUser().getMail();
            String decription1 = "TRIAL TRANSFER - RECEIPT OF MONEY";
            String decription2 = "TRIAL TRANSFER - SENDING MONEY";

            // ENVÍO DEL MAIL DE CONFIRMACIÓN A LA PERSONA DESTINO
            // SENDING THE CONFIRMATION EMAIL TO THE DESTINATION PERSON
            mailService.sendMail(to_1, cardOwn.getUser(), cardDestiny.getUser(), amount, decription1);

            // ENVÍO DEL MAIL DE CONFIRMACIÓN A LA PERSONA ORIGEN
            // SENDING THE CONFIRMATION EMAIL TO THE PERSON OF ORIGIN
            mailService.sendMail(to_2, cardOwn.getUser(), cardDestiny.getUser(), amount, decription2);

        } else {
            throw new ErrorService("THE AVAILABLE BALANCE IS NOT ENOUGH TO MAKE THE TRANSACTION");
        }
        return transaction1;
    }

    /**
     * MÉTODO PARA ELIMINAR UNA TRASACCIÓN
     *
     * METHOD FOR DELETING A TRASACTION
     *
     * @param id
     * @throws ErrorService
     */
    @Transactional
    public void deleteTransaction(String id) throws ErrorService {

        Optional<TransactionEntity> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            TransactionEntity transaction = respuesta.get();
            transactionRepository.delete(transaction);
        } else {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DEL BANCO
     *
     * METHOD THAT DISPLAYS A LIST OF ALL BANK TRANSACTIONS
     *
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public List<TransactionEntity> searchAll() throws ErrorService {
        try {
            List<TransactionEntity> listTransaction = transactionRepository.searchAll();
            return listTransaction;
        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR DISPLAYING ALL CUSTOMER TRANSACTIONS");
        }
    }

    /**
     * MÉTODO QUE MUETRA LAS TRANSACCIONES DEL CLIENTE EN LOS ÚLTIMOS 30 DÍAS
     *
     * METHOD SHOWING CUSTOMER TRANSACTIONS IN THE LAST 30 DAYS
     *
     * @param idCard
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public List<TransactionEntity> searchTransactionByLast30Days(String idCard) throws ErrorService {
        try {
            List<TransactionEntity> listTransaction = transactionRepository.searchTransactionByLast30Days(idCard);
            return listTransaction;
        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR DISPLAYING TRANSACTIONS FOR THE LAST 30 DAYS");
        }
    }

    /**
     * MÉTODO QUE MUETSRA UNA TRANSACCIÓN BUSCADA POR ID
     *
     * METHOD THAT MUETSRA A TRANSACTION SEARCHED BY ID
     *
     * @param id
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public TransactionEntity searchById(String id) throws ErrorService {
        Optional<TransactionEntity> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            TransactionEntity transaction = respuesta.get();
            return transaction;
        } else {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA TRANSACCIÓN POR FECHA
     *
     * METHOD THAT DISPLAYS A TRANSACTION BY DATE
     *
     * @param dateTransaction
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public TransactionEntity searchByDate(Date dateTransaction) throws ErrorService {
        try {
            return transactionRepository.searchByDate(dateTransaction);
        } catch (Exception e) {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA TRANSACCIÓN POR TIPO
     *
     * METHOD THAT DISPLAYS ONE TRANSACTION BY TYPE
     *
     * @param type
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public TransactionEntity searchByType(String type) throws ErrorService {
        try {
            return transactionRepository.searchByType(type);
        } catch (Exception e) {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DEL CLIENTE
     *
     * METHOD THAT DISPLAYS A LIST OF ALL CUSTOMER TRANSACTIONS
     *
     * @param id
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public List<TransactionEntity> searchTransactionByUser(String id) throws ErrorService {
        try {
            List<TransactionEntity> listTransaction = transactionRepository.searchTransactionByUser(id);
            return listTransaction;
        } catch (Exception e) {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TRANSACCIONES POR CADA TARJETA
     *
     * METHOD THAT DISPLAYS A LIST OF TRANSACTIONS FOR EACH CARD
     *
     * @param id
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public List<TransactionEntity> searchTransactionByCardId(String id) throws ErrorService {
        try {
            List<TransactionEntity> listTransaction = transactionRepository.searchTransactionByCardId(id);
            return listTransaction;
        } catch (Exception e) {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO PARA DAR DE ALTA UNA TRANSACCIÓN
     *
     * METHOD TO ENABLE A TRANSACTION
     *
     * @param id
     * @throws ErrorService
     */
    @Transactional
    public void enableTransaction(String id) throws ErrorService {
        Optional<TransactionEntity> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            TransactionEntity transaction = respuesta.get();
            transaction.setEnabled(true);
            transactionRepository.save(transaction);
        } else {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO PARA DAR DE BAJA UNA TRANSACCIÓN
     *
     * METHOD TO DISABLE A TRANSACTION
     *
     * @param id
     * @throws ErrorService
     */
    @Transactional
    public void disableTransaction(String id) throws ErrorService {
        Optional<TransactionEntity> respuesta = transactionRepository.findById(id);
        if (respuesta.isPresent()) {
            TransactionEntity transaction = respuesta.get();
            transaction.setEnabled(false);
            transactionRepository.save(transaction);
        } else {
            throw new ErrorService("THE SEARCHED TRANSACTION COULD NOT BE FOUND");
        }
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TRANSACCIONES POR ESTADO DE ALTAS
     *
     * METHOD FOR DISPLAYING ENABLED TRANSACTIONS
     *
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public List<TransactionEntity> searchTransactionByEnabled() throws ErrorService {
        try {
            List<TransactionEntity> listTransaction = transactionRepository.searchTransactionByEnabled();
            return listTransaction;
        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR DISPLAYING ALL TRANSACTIONS BY ENABLED");
        }
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TRANSACCIONES POR ESTADO DE BAJAS
     *
     * METHOD FOR DISPLAYING DISABLED TRANSACTIONS
     *
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public List<TransactionEntity> searchTransactionByDisbled() throws ErrorService {
        try {
            List<TransactionEntity> listTransaction = transactionRepository.searchTransactionByDisabled();
            return listTransaction;
        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR DISPLAYING ALL TRANSACTIONS BY DISABLED");
        }
    }

}
