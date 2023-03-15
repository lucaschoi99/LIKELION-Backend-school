package com.ll.basic.Login;


import com.ll.basic.domain.User;
import com.ll.basic.response.ResponseLoginUser;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
public class Login {

    private static final String username = "user1";
    private static final String password = "1234";

    private String resultCode;
    private String msg;

    public ResponseLoginUser validate(User user) {
        if (!user.getUsername().equals(username)) {
            resultCode = "F-2";
            msg = user.getUsername() + "(은)는 존재하지 않는 회원입니다.";
        } else if (!user.getPassword().equals(password)) {
            resultCode = "F-1";
            msg = "비밀번가 일치하지 않습니다.";
        } else {
            resultCode = "S-1";
            msg = user.getUsername() + "님 환영합니다.";
        }
        return new ResponseLoginUser(resultCode, msg);
    }

}
