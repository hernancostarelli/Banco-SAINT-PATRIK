package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entity.TransactionEntity;
import com.banco.Saint_Patrik.Entity.UserEntity;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Service.CardService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * MÉTODO QUE MUESTRA EL SALDO DE UNA DE LAS TARJETAS DEL CLIENTE
     *
     * METHOD SHOWING THE BALANCE OF ONE OF THE CUSTOMER'S CARDS
     *
     * @param session
     * @param model
     * @param cardId
     * @return
     * @throws ErrorService
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/credit")
    public String cardCredit(HttpSession session, ModelMap model,
            @RequestParam(required = false) String cardId) throws ErrorService {

        UserEntity login = (UserEntity) session.getAttribute("cardSession");
        if (login == null) {
            return "redirect:/login";
        }
        Double creditCard = cardService.searchcardAmountByIdCard(cardId);
        model.addAttribute("credit", creditCard);

        return "index.html";
    }

    /**
     * MÉTODO QUE MUESTRA UNA LISTA DE TODAS LAS TRANSACCIONES DE UNA TARJETA
     * DEL CLIENTE
     *
     * METHOD THAT DISPLAYS A LIST OF ALL TRANSACTIONS ON A CUSTOMER CARD
     *
     * @param session
     * @param model
     * @param cardId
     * @return
     * @throws ErrorService
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/transactions")
    public String cardTransactions(HttpSession session, ModelMap model,
            @RequestParam(required = false) String cardId) throws ErrorService {

        UserEntity login = (UserEntity) session.getAttribute("usersession");
        if (login == null) {
            return "redirect:/login";
        }
        List<TransactionEntity> allTransactions = cardService.searchAllTransactions(cardId);
        model.addAttribute("transactions", allTransactions);

        return "html";
    }

    /**
     * MÉTODO PARA DAR DE BAJA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * METHOD TO DISABLE A CUSTOMER CARD (FOR ADMIN ROLE USERS)
     *
     * @param session
     * @param cardId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/disable")
    public String disableCard(HttpSession session, @RequestParam String cardId) {

        try {
            UserEntity login = (UserEntity) session.getAttribute("usersession");
            cardService.disable(login.getId(), cardId);
        } catch (ErrorService ex) {
            Logger.getLogger(CardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/cards/all";
    }

    /**
     * MÉTODO PARA DAR DE ALTA UNA TARJETA DEL CLIENTE (PARA USUARIOS ROL ADMIN)
     *
     * METHOD TO ENABLE A CUSTOMER CARD (FOR ADMIN ROLE USERS)
     *
     * @param session
     * @param cardId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/enable")
    public String enableCard(HttpSession session, @RequestParam String cardId) {

        try {
            UserEntity login = (UserEntity) session.getAttribute("usersession");
            cardService.enable(login.getId(), cardId);
        } catch (ErrorService ex) {
            Logger.getLogger(CardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/cards/all";
    }
}
