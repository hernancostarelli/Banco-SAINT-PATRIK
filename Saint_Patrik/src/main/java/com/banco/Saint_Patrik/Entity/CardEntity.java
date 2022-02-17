package com.banco.Saint_Patrik.Entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class CardEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;
    @Column(unique = true)
    private String numberCard;
    private String pin;
    private Double credit;
    private Boolean enabled;

    @OneToOne
    private UserEntity user;

    @OneToMany//(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true)
    private List<TransactionEntity> transaction;
}
