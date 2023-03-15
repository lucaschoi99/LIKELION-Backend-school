package com.ll.basic.controller;

import com.ll.basic.domain.Person;
import com.ll.basic.domain.User;
import com.ll.basic.repository.BasicRepository;
import com.ll.basic.Login.Login;
import com.ll.basic.response.ResponseLoginUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class BasicController {

    @Autowired
    private BasicRepository basicRepository;

    @GetMapping("/home/addPerson")
    public String addPerson(@RequestParam String name, int age) {
        Person person = new Person(name, age);
        basicRepository.save(person);
        return person.getId() + "번 사람이 추가되었습니다.";
    }

    @GetMapping("/home/people")
    public List<Person> getList() {
        return basicRepository.findAll();
    }

    @GetMapping("/home/cookie/increase")
    public long addCookie(HttpServletRequest req, HttpServletResponse res) {

        long cookieCnt = 0;

        if (req.getCookies() != null) {
            cookieCnt = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .mapToLong(Cookie -> Long.parseLong(Cookie.getValue()))
                    .findFirst()
                    .orElse(0);
        }

        long newCount = cookieCnt + 1;

        res.addCookie(new Cookie("count", Long.toString(newCount)));

        return newCount;
    }

    @GetMapping("/member/login")
    public ResponseLoginUser login(@RequestParam String username, String password) {
        User user = new User(username, password);

        Login login = new Login();
        return login.validate(user);
    }
}
