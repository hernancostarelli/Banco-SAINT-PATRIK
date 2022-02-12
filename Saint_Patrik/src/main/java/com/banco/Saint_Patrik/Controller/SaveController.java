package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.TransactionEntity;
import com.banco.Saint_Patrik.Entity.UserEntity;
import com.banco.Saint_Patrik.Enum.Role;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Repository.CardRepository;
import com.banco.Saint_Patrik.Repository.TransactionRepository;
import com.banco.Saint_Patrik.Repository.UserRepository;
import com.banco.Saint_Patrik.Service.SaveService;
import com.banco.Saint_Patrik.Service.TransactionService;
import com.banco.Saint_Patrik.Service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("permitAll()")
@RequestMapping("/database")
public class SaveController {

    @Autowired
    private SaveService saveService;

    @Autowired
    private UserService userService;

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/save3")
    public String putData3(ModelMap model) throws ErrorService {

        List<TransactionEntity> transactions = new ArrayList();
        List<TransactionEntity> transactions2 = new ArrayList();

        UserEntity user1 = new UserEntity();
        user1.setDocument("12345678");
        user1.setEnabled(true);
        user1.setMail("correo.na.de.prueba1@gmail.com");
        user1.setName("Nombre1");
        user1.setSurname("Apellido1");
        user1.setTypeRole(Role.USER);
        userRepo.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setDocument("23456789");
        user2.setEnabled(true);
        user2.setMail("correo.na.de.prueba2@gmail.com");
        user2.setName("Nombre2");
        user2.setSurname("Apellido2");
        user2.setTypeRole(Role.USER);
        userRepo.save(user2);

        UserEntity user3 = new UserEntity();
        user3.setDocument("34567890");
        user3.setEnabled(true);
        user3.setMail("correo.na.de.prueba3@gmail.com");
        user3.setName("Nombre3");
        user3.setSurname("Apellido3");
        user3.setTypeRole(Role.USER);
        userRepo.save(user3);

        CardEntity card1 = new CardEntity();
        card1.setNumberCard("4546857418565565");
        card1.setCredit(40555.0);
        card1.setEnabled(true);
        String encrypted = new BCryptPasswordEncoder().encode("4345");
        card1.setPin(encrypted);
        card1.setUser(user1);
        cardRepo.save(card1);

        CardEntity card2 = new CardEntity();
        card2.setNumberCard("5595345899897125");
        card2.setCredit(3566.0);
        card2.setEnabled(true);
        String encrypted2 = new BCryptPasswordEncoder().encode("1595");
        card2.setPin(encrypted2);
        card2.setUser(user2);
        cardRepo.save(card2);

        CardEntity card3 = new CardEntity();
        card3.setNumberCard("4858669658871578");
        card3.setCredit(23.0);
        card3.setEnabled(true);
        String encrypted3 = new BCryptPasswordEncoder().encode("1234");
        card3.setPin(encrypted3);
        card3.setUser(user3);
        cardRepo.save(card3);

        CardEntity card4 = new CardEntity();
        card4.setNumberCard("5854665625871547");
        card4.setCredit(300.0);
        card4.setEnabled(true);
        String encrypted4 = new BCryptPasswordEncoder().encode("4345");
        card4.setPin(encrypted4);
        card4.setUser(user2);
        cardRepo.save(card4);

        CardEntity card5 = new CardEntity();
        card5.setNumberCard("4546989623571478");
        card5.setCredit(35621.0);
        card5.setEnabled(true);
        String encrypted5 = new BCryptPasswordEncoder().encode("0023");
        card5.setPin(encrypted5);
        card5.setUser(user3);
        cardRepo.save(card5);

        Double amount1 = 100.0;
        Double amount2 = 2345.45;
        Double amount3 = 248.60;
        Double amount4 = 10.53;
        Double amount5 = 1500.0;
        Double amount6 = 14.09;
        Double amount7 = 30.90;
        Double amount8 = 249.10;
        Double amount9 = 198.75;
        Double amount10 = 163.07;

        try {
            
            transactions.add(transactionService.newTransactionTwo(user1, user2, card1, card2, amount1));
            transactions.add(transactionService.newTransactionTwo(user1, user3, card1, card3, amount2));
            transactions.add(transactionService.newTransactionTwo(user2, user1, card2, card1, amount4));
            transactions.add(transactionService.newTransactionTwo(user2, user3, card4, card3, amount6));
            transactions.add(transactionService.newTransactionTwo(user3, user1, card5, card1, amount7));
            transactions.add(transactionService.newTransactionTwo(user3, user2, card5, card4, amount5));           

        } catch (ErrorService e) {
            System.err.println(e.getMessage());
            System.out.println("larga error la nueva transaction");
        }
        System.err.println("sale del metodo");
        card1.setTransaction(transactions);
        return "login.html";
    }
}
