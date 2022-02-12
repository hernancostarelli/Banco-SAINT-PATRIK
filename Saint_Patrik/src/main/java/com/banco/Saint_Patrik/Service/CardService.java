package com.banco.Saint_Patrik.Service;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.TransactionEntity;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Repository.CardRepository;
import com.banco.Saint_Patrik.Repository.TransactionRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CardService implements UserDetailsService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * MÉTODO QUE MUESTRA EL SALDO DE UNA DE LAS TARJETAS DEL CLIENTE
     *
     * METHOD SHOWING THE BALANCE OF ONE OF THE CUSTOMER'S CARDS
     *
     * @param id
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public Double searchcardAmountByIdCard(String id) throws ErrorService {
        try {
            CardEntity card = cardRepository.searchById(id);
            return card.getCredit();
        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR DISPLAYING THE CARD BALANCE");
        }
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DE UNA TARJETA
     * DEL CLIENTE
     *
     * METHOD THAT DISPLAYS A LIST OF ALL TRANSACTIONS ON A CUSTOMER CARD
     *
     * @param id
     * @return
     * @throws ErrorService
     */
    @Transactional(readOnly = true)
    public List<TransactionEntity> searchAllTransactions(String id) throws ErrorService {
        try {
            List<TransactionEntity> listTransactions = transactionRepository.searchTransactionByCardId(id);
            return listTransactions;
        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR DISPLAYING ALL TRANSACTIONS ON THIS CARD");
        }
    }

    /**
     * MÉTODO PARA DAR DE BAJA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * METHOD TO DISABLE A CLIENT CARD (FOR ADMIN ROLE USERS)
     *
     * @param idUser
     * @param cardId
     * @throws ErrorService
     */
    @Transactional
    public void disable(String idUser, String cardId) throws ErrorService {
        try {
            CardEntity card = cardRepository.searchById(cardId);

            card.setEnabled(Boolean.FALSE);
            cardRepository.save(card);

        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR DISABLING THE CARD");
        }
    }

    /**
     * MÉTODO PARA DAR DE ALTA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * METHOD TO ENABLE A CLIENT CARD (FOR ADMIN ROLE USERS)
     *
     * @param idUser
     * @param cardId
     * @throws ErrorService
     */
    @Transactional
    public void enable(String idUser, String cardId) throws ErrorService {
        try {
            CardEntity card = cardRepository.searchById(cardId);

            card.setEnabled(Boolean.TRUE);
            cardRepository.save(card);
        } catch (Exception e) {
            throw new ErrorService("THERE WAS AN ERROR ENABLING THE CARD");
        }
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TARJETAS POR ESTADO DE ALTAS
     *
     * METHOD FOR DISPLAYING ENABLED CARDS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<CardEntity> cardListEnabled() {
        return cardRepository.searchCardByEnabled();
    }

    /**
     * MÉTODO PARA MOSTRAR LAS TARJETAS POR ESTADO DE BAJAS
     *
     * METHOD FOR DISPLAYING DISABLED CARDS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<CardEntity> cardListDisabled() {
        return cardRepository.searchCardByDisabled();
    }
     @Transactional(readOnly = true)
    public CardEntity searchById(String id) {
        return cardRepository.getById(id);
    }
    
    public CardEntity searchByNumberCard(String numberCard){
       return cardRepository.searchCardByNumberCard(numberCard);
    }

    /**
     *
     * @param cardNumber
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {

        CardEntity card = cardRepository.searchCardByNumberCard(cardNumber);

        if (card != null) {

            List<GrantedAuthority> permissions = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + card.getUser().getTypeRole());
            permissions.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("cardSession", card);

            User user = new User(card.getNumberCard(), card.getPin(), permissions);

            return user;

        } else {
            return null;
        }
    }           
}