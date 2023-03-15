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

    @GetMapping("/memeber/addPerson")
    public String addPerson(@RequestParam String name, int age) {
        Users user = createUser("user" + (cnt++), "123" + (passCnt++), name, age);
        userService.addUser(user);

        return user.getId() + "번 사람이 추가되었습니다.";
    }

    @GetMapping("/")
    public void initUsers() {
        userService.addUsers();
    }

    @GetMapping("/members")
    public List<Users> getList() {
        return userService.getList();
    }

    @GetMapping("/cookie/increase")
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

    @GetMapping("/login")
    public ResponseLoginUser login(@RequestParam String username, String password, HttpServletResponse res) {
        Users user = createUser(username, password, "name", 20);
        ResponseLoginUser result = userService.login(user, res);

        if (result.getResultCode().equals("S-1")) {
            res.addCookie(new Cookie("loginUserName", user.getUsername()));
        }

        return result;
    }

    @GetMapping("/login/cookie")
    public ResponseLoginUser loginWithCookie(@RequestParam String username, String password, HttpServletRequest request) {
        Users user = createUser(username, password, "name", 20);
        return userService.loginWithCookie(request, user);
    }

    private Users createUser(@RequestParam String username, String password, String name, int i) {
        return Users.builder()
                .name(name)
                .age(i)
                .username(username)
                .password(password)
                .build();
    }

    @GetMapping("/logout")
    public ResponseLoginUser logout(@RequestParam String username, String password, HttpServletRequest request) {
        Users user = createUser(username, password, "name", 20);
        return userService.logout(request, user);

    }
}
