package com.banco.Saint_Patrik.Controller;

import com.banco.Saint_Patrik.Error.ErrorService;
import com.banco.Saint_Patrik.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * TODAS LAS TARJETAS QUE POSEE UN USUARIO
     *
     * ALL CARDS OWNED BY A USER
     *
     * @param model
     * @param idUser
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/cards")
    public String cards(ModelMap model, @Param("idUser") String idUser) {
        model.addAttribute("cards", service.cardsFromUser(idUser));
        return "html";
    }

    /**
     * LISTA DE USUARIOS HABILITADOS
     *
     * LIST OF ENABLED USERS
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/userList")
    public String usersEnabled(ModelMap model) {
        model.addAttribute("users", service.userListEnabled());
        return "html";
    }

    /**
     * LISTA DE USUARIOS DE BAJA
     *
     * LIST OF DISABLED USERS
     *
     * @param model
     * @return
     * @throws ErrorService
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/usersDisabled")
    public String usersDisabled(ModelMap model) throws ErrorService {
        try {
            model.addAttribute("usersDisabled", service.userListDisabled());
        } catch (ErrorService e) {
            model.addAttribute("error", e.getMessage());
        }
        return "html";
    }

    /**
     * BUSCA UN USUARIO ESPECIFICO
     *
     * FIND A SPECIFIC USER
     *
     * @param model
     * @param idUser
     * @return
     * @throws ErrorService
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/usersSearch")
    public String user(ModelMap model, @RequestParam String idUser) throws ErrorService {
        try {
            model.addAttribute("user", service.user(idUser));
        } catch (ErrorService e) {
            model.addAttribute("error", e.getMessage());
        }
        return "html";
    }
}
