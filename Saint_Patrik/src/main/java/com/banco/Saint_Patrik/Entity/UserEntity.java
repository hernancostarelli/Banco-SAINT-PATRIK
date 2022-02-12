package com.banco.Saint_Patrik.Entity;

import com.banco.Saint_Patrik.Enum.Role;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;
    private String name;
    private String surname;

    @Column(unique = true)
    private String document;
    private String mail;
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role typeRole;

    @OneToMany//(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> card;
}
