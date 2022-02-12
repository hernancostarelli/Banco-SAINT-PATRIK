package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.TransactionEntity;
import com.banco.Saint_Patrik.Entity.UserEntity;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Service.CardService;
import com.banco.Saint_Patrik.Service.TransactionService;
import com.banco.Saint_Patrik.Service.UserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String goTransactions(HttpSession session, ModelMap model,
            //@PathVariable String idUser,
            RedirectAttributes redirectAttributes) throws ErrorService {
        try {
            CardEntity login = (CardEntity) session.getAttribute("cardSession");
            if (login == null) {
                return "redirect:/login";
            }

//            String idUser = login.getUser().getId();
            List<TransactionEntity> listTransaction = cardService.searchAllTransactions(login.getId());
//            List<TransactionEntity> listTransaction = transactionService.searchTransactionByLast30Days(login.getId());

            model.addAttribute("last30Days", listTransaction);

            return "transaction.html";

        } catch (ErrorService e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "THERE WAS AN ERROR DISPLAYING TRANSACTIONS FOR THE LAST 30 DAYS");

            return "redirect:/";
        }

    }

    /**
     * MÉTODO PARA REALIZAR UNA TRANSACCIÓN
     *
     * METHOD FOR MAKING A TRANSACTION
     *
     * @param session
     * @param model
     * @param redirectAttributes
     * @return
     * @throws ErrorService
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/newTransactiong")
    public String newTransaction(HttpSession session, ModelMap model, RedirectAttributes redirectAttributes) throws ErrorService {
        CardEntity login = (CardEntity) session.getAttribute("cardSession");
        if (login == null) {

            return "redirect:/login";
        }

        List<UserEntity> userList = userService.userListEnabled();
        model.addAttribute("userAlta", userList);

//         List<TransactionEntity> listTransaction = cardService.searchAllTransactions(login.getId());
        List<CardEntity> cardList = cardService.cardListEnabled();

        // ELIMINO DE LA LISTA LA TARJETA QUE INICIÓ SESIÓN
        // I REMOVE THE CARD THAT LOGGED IN FROM THE LIST
        CardEntity card = (CardEntity) cardService.searchByNumberCard(login.getNumberCard());
        cardList.remove(card);

        model.addAttribute("cardAlta", cardList);
//        model.addAttribute("last30Days", listTransaction);
        model.addAttribute("session", login);
        return "transfer.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/newTransactionp")
    public String newTransaction(HttpSession session, ModelMap model,
            @RequestParam(required = false) String numberCardDestiny,
            @RequestParam(required = false) Double amount,
            //            @RequestParam(required = false) String description,
            RedirectAttributes redirectAttributes) {
        try {
            CardEntity login = (CardEntity) session.getAttribute("cardSession");
            if (login == null) {

                return "redirect:/login";
            }

            CardEntity cardDestiny = cardService.searchByNumberCard(numberCardDestiny);
            transactionService.newTransaction(login, cardDestiny, amount);

            Date date = new Date();

            model.put("date", new SimpleDateFormat("dd-MM-yyyy").format(date));
            model.put("cardDestiny", numberCardDestiny);
            model.put("description", "SEND");
            model.put("amount", amount);
            model.addAttribute("success", "THE TRANSACTION WAS SUCCESSFULLY GENERATED");

            return "transferconfirm.html";

        } catch (ErrorService e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", e.getMessage());
            //redirectAttributes.addFlashAttribute("idUser", idUser);
//            model.addAttribute("idCardDestiy", cardDestiny);
            model.addAttribute("amount", amount);

            return "redirect:/transaction/newTransactiong";
        }
    }

    /**
     * MÉTODO QUE MUESTRA LAS TRANSACCIONES DE LOS ÚLTIMOS 30 DÍAS
     *
     * METHOD SHOWING TRANSACTIONS FROM THE LAST 30 DAYS
     *
     * @param session
     * @param model
     * @param redirectAttributes
     * @return
     * @throws ErrorService
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    //@GetMapping("/transactionByLast30Days/{idUser}")
    @GetMapping("/transactionByLast30Days")
    public String transactionByLast30Days(HttpSession session, ModelMap model,
            //@PathVariable String idUser,
            RedirectAttributes redirectAttributes) throws ErrorService {
        try {
            CardEntity login = (CardEntity) session.getAttribute("cardSession");
            if (login == null) {

                return "redirect:/login";
            }

            String idCard = login.getId();

            List<TransactionEntity> listTransaction = transactionService.searchTransactionByLast30Days(idCard);
            model.addAttribute("last30Days", listTransaction);

            return "redirect:/transactionByLast30Days";

        } catch (ErrorService e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "THERE WAS AN ERROR DISPLAYING TRANSACTIONS FOR THE LAST 30 DAYS");

            return "redirect:/";
        }
    }
}
