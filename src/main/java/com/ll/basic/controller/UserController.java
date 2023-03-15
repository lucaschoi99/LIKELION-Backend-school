package com.ll.basic.controller;

import com.ll.basic.domain.Users;
import com.ll.basic.response.ResponseLoginUser;
import com.ll.basic.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private int cnt = 0;
    private int passCnt = 0;

    @GetMapping("/home/addPerson")
    public String addPerson(@RequestParam String name, int age) {
        Users user = Users.builder()
                .name(name)
                .age(age)
                .username("user" + (cnt++))
                .password("123" + (passCnt++))
                .build();
        userService.addUser(user);

        return user.getId() + "번 사람이 추가되었습니다.";
    }

    @GetMapping("/home/addUserList")
    public void addUsers() {
        userService.addUsers();
    }

    @GetMapping("/home/people")
    public List<Users> getList() {
        return userService.getList();
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
    public ResponseLoginUser login(@RequestParam String username, String password, HttpServletResponse res) {
        Users user = Users.builder()
                .name("name")
                .age(20)
                .username(username)
                .password(password)
                .build();

        return userService.login(user, res);
    }

    @GetMapping("/member/me")
    public ResponseLoginUser loginWithCookie(HttpServletRequest request) {
        return userService.loginWithCookie(request);
    }
}
