package com.team3webnovel.controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import com.team3webnovel.services.GoogleOAuthService;
import com.team3webnovel.services.UserService;
import com.team3webnovel.vo.UserVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoogleOAuthService googleOAuthService;

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    // 회원가입 페이지 보여주기
    @GetMapping("/register")
    public String showRegisterPage() {
        return "users/register"; // /WEB-INF/views/users/register.jsp로 이동
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("confirmpassword") String confirmpassword,
                               Model model) {
        // 비밀번호 일치 여부 확인
        if (!password.equals(confirmpassword)) {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "users/register";  // 비밀번호가 일치하지 않으면 다시 회원가입 페이지로
        }

        try {
            // 사용자 등록 로직 실행
            UserVo newUser = new UserVo();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            
            userService.registerUser(newUser);  // 서비스에서 비밀번호 암호화 처리
            
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/login";  // 회원가입 성공 시 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("message", "회원가입 중 오류가 발생했습니다.");
            return "users/register";  // 오류 발생 시 다시 회원가입 페이지로
        }
    }

    // 로그인 페이지 보여주기
    @GetMapping("/login")
    public String showLoginPage() {
        return "users/login"; // /WEB-INF/views/users/login.jsp로 이동
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session, Model model) {
        UserVo user = userService.findUserByUsername(username);
        
        // 사용자가 존재하지 않는 경우
        if (user == null) {
            model.addAttribute("message", "해당 아이디가 존재하지 않습니다.");
            return "users/login"; // 로그인 페이지로 다시 이동
        }

        // 비밀번호 검증 (암호화 적용 여부 확인)
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "users/login"; // 로그인 페이지로 다시 이동
        }

        // 로그인 성공 시 세션에 사용자 정보 저장
        session.setAttribute("user", user);

        // 각 필드별로 로그 출력
        logger.debug("로그인 성공: user_id = {}", user.getUserId());
        logger.debug("로그인 성공: username = {}", user.getUsername());
        logger.debug("로그인 성공: email = {}", user.getEmail());
        logger.debug("로그인 성공: password = {}", user.getPassword());  // 보안 상 실제로는 password는 로그에 찍지 않는 것이 좋습니다.
        logger.debug("로그인 성공: created_at = {}", user.getCreatedAt());

        // 혹은 한 번에 모든 정보를 출력
        logger.debug("로그인 성공: user_id = {}, username = {}, email = {}, created_at = {}", 
                      user.getUserId(), user.getUsername(), user.getEmail(), user.getCreatedAt());

        return "redirect:/"; // 로그인 성공 시 홈페이지로 리다이렉트
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.debug("로그아웃 요청: 세션 ID = {}", session.getId());
        session.invalidate(); // 세션 무효화
        return "redirect:/login?logout=true"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    // 마이페이지 보여주기 (로그인된 사용자만 접근 가능)
    @GetMapping("/mypage")
    public String showMyPage(HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            logger.debug("사용자가 로그인하지 않았습니다.");
            return "redirect:/login";
        } else {
            logger.debug("로그인된 사용자: user_id = {}", user.getUserId());
        }

        // 유저가 로그인한 경우 마이페이지로 이동
        model.addAttribute("user", user);  // 마이페이지에 사용자 정보 전달
        return "users/mypage";  // users/mypage.jsp로 이동
    }

    // Google OAuth 로그인 페이지로 이동
    @GetMapping("/google-login")
    public String googleLogin() {
        logger.debug("Google 로그인 요청.");
        String authorizationUrl = googleOAuthService.getAuthorizationUrl();
        logger.debug("Authorization URL: {}", authorizationUrl);
        return "redirect:" + authorizationUrl;  // 구글 로그인 페이지로 리다이렉트
    }

    // Google OAuth 콜백 처리
    @GetMapping("/callback")
    public String googleCallback(@RequestParam("code") String code, HttpSession session, Model model) {
        try {
            logger.debug("Google OAuth 콜백 처리 시작. Code: {}", code);
            System.err.println("콜백 진입!!!");
            // Google OAuth 서비스에서 사용자 정보 가져오기
            UserVo user = googleOAuthService.getUserInfo(code);

            logger.debug("Google OAuth에서 사용자 정보 가져옴: username = {}, email = {}", 
                         user.getUsername(), user.getEmail());

            // 세션에 사용자 정보 저장
            session.setAttribute("user", user);
            logger.debug("세션 ID: {}, 세션에 저장된 사용자: {}", session.getId(), session.getAttribute("user"));

            return "redirect:/";  // 로그인 성공 후 홈페이지로 리다이렉트
        } catch (Exception e) {
            logger.error("Google OAuth 로그인 실패", e);
            model.addAttribute("message", "Google 로그인 중 오류가 발생했습니다.");
            return "users/login";  // 로그인 페이지로 다시 이동
        }
    }
}
