package se.experis.academy.sessions.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.sessions.Util.Password;
import se.experis.academy.sessions.Util.SessionKeeper;
import se.experis.academy.sessions.model.Notice;
import se.experis.academy.sessions.repository.NoticeRepository;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    NoticeRepository noticeRepository;

    final private int MaxAge = 600;

    @GetMapping("/")
    public String index(Model model) {
        List<Notice> notices = noticeRepository.findByActiveTrueOrderByDateDesc();
        model.addAttribute("notices", notices);
        return "viewonly";
    }

    @GetMapping(value = "/login")
    public String login(@CookieValue(value = "foreverCookie", defaultValue = "") String foreverCookie, HttpServletRequest request, HttpSession session, Model model) {
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("loginStatus", SessionKeeper.getInstance().CheckSession(session.getId()));
        model.addAttribute("Password", new Password());
        return "login";
    }


    @GetMapping(value = "/logout")
    public String special(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model) {

        model.addAttribute("sessionId", session.getId());

        model.addAttribute("loginStatus", SessionKeeper.getInstance().CheckSession(session.getId()));


        return "logout";
    }
    @GetMapping("/index")
    public String indes(Model model) {
        List<Notice> notices = noticeRepository.findByActiveTrueOrderByDateDesc();
        model.addAttribute("notices", notices);
        return "index";
    }

@PostMapping(value = "/saveSession")
public String add(@RequestParam(value="action", required=true) String action,
                  @ModelAttribute("Password") Password password,
                  HttpServletRequest request,
                  HttpSession session,
                  Model model) {
    if(action.equals("login")) {
        if (password.value.equals("cat")) {
            System.out.println("login successful");
            SessionKeeper.getInstance().AddSession(session.getId());
            session.setMaxInactiveInterval(MaxAge);
            return "redirect:/index";
        } else {
            System.out.println("login failed");
        }
        return "redirect:/login";
    }
    else if(action.equals("logout")) {
        SessionKeeper.getInstance().RemoveSession(session.getId());
        System.out.println("logged out");
        return "redirect:/logout";
    }
    return "redirect:/index";
}

    @GetMapping(value = "/secret")
    public String secret(@CookieValue(value = "secret", defaultValue = "") String secret,
                         HttpSession session) {

            return "redirect:/login";
        }
    }







