package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Entity.CardEntity;
import com.banco.Saint_Patrik.Entity.TransactionEntity;
import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Service.CardService;
import com.banco.Saint_Patrik.Service.TransactionService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private CardService cardService;
    
    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/main")
    public String main(HttpSession session, ModelMap model, @RequestParam(required = false) String cardId) throws ErrorService {
        CardEntity login = (CardEntity) session.getAttribute("cardSession"); //con esto si el id de login logueado viene nulo, no ejecuta el metodo
        if (login == null) {

            return "redirect:/login";
        }
        List<TransactionEntity> allTransactions = cardService.searchAllTransactions(login.getId());
        model.addAttribute("transactions", allTransactions);

        return "index.html";

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
            @RequestParam(required = false) String logout, ModelMap model) {
        
        if (error != null) {
            model.put("error", "INCORRECT USERNAME OR PASSWORD"); //hay que mostrarlo en la vista login.html                       
        }
        if (logout != null) {
            model.put("logout", "SUCCESSFULLY EXITED THE PLATFORM"); //hay que mostrarlo en la vista login.html                 
        }
        return "login";
    }
//    @GetMapping("/login")
//    public String logout(@RequestParam(required = false) String error,
//            @RequestParam(required = false) String logout, ModelMap model) {
//
//        if (error != null) {
//            model.put("error", "INCORRECT USERNAME OR PASSWORD"); //hay que mostrarlo en la vista login.html
//        }
//        if (logout != null) {
//            model.put("logout", "SUCCESSFULLY EXITED THE PLATFORM"); //hay que mostrarlo en la vista login.html
//        }
//
//        return "login.html";
//    }

    @GetMapping("/")
    public String home() {
        return "home.html";
    }
    
    
    @GetMapping("/expired")
    public String expired(ModelMap model) {
        
        return "expired.html";
    }
}
