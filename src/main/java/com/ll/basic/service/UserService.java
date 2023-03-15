package com.ll.basic.service;

import com.ll.basic.domain.Users;
import com.ll.basic.repository.UserRepository;
import com.ll.basic.response.ResponseLoginUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private String resultCode;
    private String msg;

    public void addUser(Users user) {
        userRepository.save(user);
    }

    public void addUsers() {
        userRepository.saveAll(IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Users.builder()
                        .name("name" + i)
                        .age(10 + i)
                        .username("user" + i)
                        .password("123" + i)
                        .build())
                .collect(Collectors.toList())
        );
    }

    public List<Users> getList() {
        return userRepository.findAll();
    }

    public ResponseLoginUser login(Users user, HttpServletResponse res) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            resultCode = "F-2";
            msg = user.getUsername() + "(은)는 존재하지 않는 회원입니다.";
        } else if (userRepository.findByPassword(user.getPassword()) == null) {
            resultCode = "F-1";
            msg = "비밀번가 일치하지 않습니다.";
        } else {
            resultCode = "S-1";
            msg = user.getUsername() + "님 환영합니다.";
            res.addCookie(new Cookie("username", user.getUsername()));
        }
        return new ResponseLoginUser(resultCode, msg);
    }

    public ResponseLoginUser loginWithCookie(HttpServletRequest req) {
        if (req.getCookies() != null) {
            String username = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("username"))
                    .map(cookie -> cookie.getValue().toString())
                    .findFirst()
                    .orElseThrow(IllegalAccessError::new);
            return new ResponseLoginUser("S-1", "당신의 username(은)는 " + username);
        }
        return new ResponseLoginUser("F-1", "로그인 후 이용해주세요.");
    }
}
