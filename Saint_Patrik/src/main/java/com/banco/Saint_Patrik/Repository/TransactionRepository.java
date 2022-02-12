package com.banco.Saint_Patrik.Repository;

import com.banco.Saint_Patrik.Entity.TransactionEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    /**
     * BUSCAR TODAS LAS TRANSACCIONES ORDENADAS POR FECHA EN FORMA ASCENDENTE
     *
     * SEARCH ALL TRANSACTIONS SORTED BY DATE IN ASCENDING FORM
     *
     * @return
     */
    @Query("SELECT t FROM TransactionEntity t ORDER BY t.dateTransaction ASC")
    public List<TransactionEntity> searchAll();

    /**
     * BUSCAR LAS TRANSACCIONES POR ID
     *
     * SEARCH TRANSACTIONS BY ID
     *
     * @param id
     * @return
     */
    @Query("SELECT t FROM TransactionEntity t WHERE t.id = :id")
    public TransactionEntity searchById(@Param("id") String id);

    /**
     * BUSCAR LAS TRANSACCIONES POR FECHA
     *
     * SEARCH TRANSACTIONS BY DATE
     *
     * @param dateTransaction
     * @return
     */
    @Query("SELECT t FROM TransactionEntity t WHERE t.dateTransaction = :dateTransaction")
    public TransactionEntity searchByDate(@Param("dateTransaction") Date dateTransaction);

    /**
     * BUSCAR LAS TRANSACCIONES POR TIPO DE TRANSACCIÓN
     *
     * SEARCH TRANSACTIONS BY TRANSACTION TYPE
     *
     * @param type
     * @return
     */
    @Query("SELECT t FROM TransactionEntity t WHERE t.type = :type")
    public TransactionEntity searchByType(@Param("type") String type);

    /**
     * BUSCAR TRANSACCIONES POR USUARIO
     *
     * SEARCH TRANSACTIONS BY USER
     *
     * @param id
     * @return
     */
    @Query("SELECT t FROM  TransactionEntity t WHERE t.user.id = :id")
    public List<TransactionEntity> searchTransactionByUser(@Param("id") String id);

    /**
     * BUSCAR TRANSACCIONES POR ID TARJETA
     *
     * SEARCH TRANSACTIONS BY CARD ID
     *
     * @param id
     * @return
     */
    @Query("SELECT t FROM  TransactionEntity t WHERE t.card.id = :card_id")
    public List<TransactionEntity> searchTransactionByCardId(@Param("card_id") String id);

    /**
     * BUSCAR TRANSACCIONES HABILITADAS
     *
     * SEARCH FOR ENABLED TRANSACTIONS
     *
     * @return
     */
    @Query("SELECT t from TransactionEntity t WHERE t.enabled = true ORDER BY t.dateTransaction ASC")
    public List<TransactionEntity> searchTransactionByEnabled();

    /**
     * BUSCAR TRANSACCIONES DESHABILITADAS
     *
     * SEARCH FOR DISABLED TRANSACTIONS
     *
     * @return
     */
    @Query("SELECT t from TransactionEntity t WHERE t.enabled = false ORDER BY t.dateTransaction ASC")
    public List<TransactionEntity> searchTransactionByDisabled();

    /**
     * BUSCAR TRANSACCIONES DE LOS ÚLTIMOS 30 DÍAS
     *
     * SEARCH FOR TRANSACTIONS FROM THE LAST 30 DAYS
     *
     * @param id
     * @return
     */
    @Query(value = "SELECT t from TransactionEntity t WHERE t.dateTransaction BETWEEN  "
            + "DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() AND t.card.id = :card_id order by t.dateTransaction DESC", nativeQuery = true)
    public List<TransactionEntity> searchTransactionByLast30Days(@Param("card_id") String id);

}
