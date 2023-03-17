package com.ll.basic.service;

import com.ll.basic.domain.Users;
import com.ll.basic.repository.UserRepository;
import com.ll.basic.response.StatusResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
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

    public StatusResponse login(Users user, HttpServletResponse res) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            resultCode = "F-2";
            msg = user.getUsername() + "(은)는 존재하지 않는 회원입니다.";
        } else if (userRepository.findByPassword(user.getPassword()) == null) {
            resultCode = "F-1";
            msg = "비밀번가 일치하지 않습니다.";
        } else {
            resultCode = "S-1";
            msg = user.getUsername() + "님 환영합니다.";
        }
        return new StatusResponse(resultCode, msg);
    }

    public StatusResponse loginWithCookie(HttpServletRequest req, Users user) {
        if (req.getCookies() != null) {
            String username = getCookie(req, user).getValue();
            return new StatusResponse("S-1", "당신의 username(은)는 " + username);
        }
        return new StatusResponse("F-1", "로그인 후 이용해주세요.");
    }

    public StatusResponse logout(HttpServletRequest request, Users user) {
        if (request.getCookies() != null) {
            getCookie(request, user).setMaxAge(0);
        }
        return new StatusResponse("S-1", "정상적으로 로그아웃 되었습니다.");
    }

    public Cookie getCookie(HttpServletRequest req, Users user) {
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("loginUserName") & cookie.getValue().equals(user.getUsername()))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
