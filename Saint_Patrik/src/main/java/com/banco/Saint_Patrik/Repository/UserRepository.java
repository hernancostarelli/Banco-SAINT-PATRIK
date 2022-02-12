package com.banco.Saint_Patrik.Repository;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * BUSCAR TODOS LOS USUARIOS ORDENADOS POR APELLIDO EN FORMA DESCENDENTE
     *
     * FIND ALL USERS SORTED BY LAST NAME IN DESCENDING FORM
     *
     * @return
     */
    @Query("SELECT u FROM UserEntity u ORDER BY u.surname DESC")
    public List<UserEntity> searchAll();

    /**
     * BUSCAR LOS USUARIOS POR ID
     *
     * SEARCH USERS BY ID
     *
     * @param id
     * @return
     */
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    public UserEntity searchById(@Param("id") String id);

    /**
     * BUSCAR LOS USUARIOS POR NOMBRE
     *
     * SEARCH USERS BY NAME
     *
     * @param name
     * @return
     */
    @Query("SELECT u FROM UserEntity u WHERE u.name = :name")
    public UserEntity searchByName(@Param("name") String name);

    /**
     * BUSCAR LOS USUARIOS POR APELLIDO
     *
     * SEARCH USERS BY LAST NAME
     *
     * @param surname
     * @return
     */
    @Query("SELECT u FROM UserEntity u WHERE u.surname = :surname")
    public UserEntity searchBySurname(@Param("surname") String surname);

    /**
     * BUSCAR LOS USUARIOS POR NÚMERO DE DOCUMENTO
     *
     * FIND USERS BY DOCUMENT NUMBER
     *
     * @param document
     * @return
     */
    @Query("SELECT u FROM UserEntity u WHERE u.document = :document")
    public UserEntity searchByDocument(@Param("document") String document);

    /**
     * BUSCAR LAS TARJETAS DEL USUARIO FIND THE USER'S CARDS
     *
     * @param idUser
     * @return
     */
    @Query("SELECT u.card FROM UserEntity u WHERE u.id = :idUser")
    public List<CardEntity> searchCardsFromUser(@Param("idUser") String idUser);

    /**
     * BUSCAR USUARIOS HABILITADOS
     *
     * FIND ENABLED USERS
     *
     * @return
     */
    @Query("SELECT u from UserEntity u WHERE u.enabled = true ORDER BY u.surname ASC")
    public List<UserEntity> searchUserByEnabled();

    /**
     * BUSCAR USUARIOS DESHABILITADOS
     *
     * FIND DISABLED USERS
     *
     * @return
     */
    @Query("SELECT u from UserEntity u WHERE u.enabled = false ORDER BY u.surname ASC")
    public List<UserEntity> searchUserByDisabled();

    /**
     * BUSCAR UN USUARIO POR ID DE TARJETA
     *
     * SEARCH FOR A USER BY CARD ID
     *
     * @param id
     * @return
     */
    @Query("SELECT u.card FROM UserEntity u WHERE u.id = :id")
    public UserEntity searchUserByIdCard(@Param("id") String id);

    /**
     * BUSCAR UNA TARJETA POR ID DE USUARIO
     *
     * SEARCH FOR A CARD BY USER ID
     *
     * @param id
     * @return
     */
    @Query("SELECT c.user from CardEntity c WHERE c.id = :id ")
    public UserEntity searchCardByUser(@Param("id") String id);

}
