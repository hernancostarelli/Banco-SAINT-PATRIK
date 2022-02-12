package com.banco.Saint_Patrik.Service;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.UserEntity;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Repository.CardRepository;
import com.banco.Saint_Patrik.Repository.UserRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    /**
     * MÉTODO PARA MOSTRAR LAS TARJETAS DE UN USUARIOS
     *
     * METHOD FOR DISPLAYING A USER'S CARDS
     *
     * @param idUser
     * @return
     */
    @Transactional(readOnly = true)
    public List<CardEntity> cardsFromUser(String idUser) {
        return userRepository.searchCardsFromUser(idUser);
    }

    /**
     * MÉTODO PARA MOSTRAR LOS USUARIOS POR ESTADO DE ALTAS
     *
     * METHOD FOR DISPLAYING ENABLED USERS
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserEntity> userListEnabled() {
        return userRepository.searchUserByEnabled();
    }

    /**
     * MÉTODO PARA MOSTRAR LOS USUARIOS POR ESTADO DE BAJAS
     *
     * METHOD FOR DISPLAYING DISABLED USERS
     *
     * @return
     * @throws com.banco.Saint_Patrik.Error.ErrorService
     */
    @Transactional(readOnly = true)
    public List<UserEntity> userListDisabled() throws ErrorService {
        if (userRepository.searchUserByDisabled() == null) {
            throw new ErrorService("no hay usuarios deshabilitados");
        }
        return userRepository.searchUserByDisabled();
    }

    /**
     * METODO PARA TRAER UN USUARIO ESPECIFICO
     *
     * METHOD TO BRING A SPECIFIC USER
     *
     * @param idUser
     * @throws com.banco.Saint_Patrik.Error.ErrorService
     * @Param idUser
     * @return User
     */
    public UserEntity user(String idUser) throws ErrorService {
        if (userRepository.searchById(idUser) == null) {
            throw new ErrorService("el usuario no existe");
        }
        return userRepository.searchById(idUser);
    }
}
