package com.team3webnovel.controllers;

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

        return "redirect:/"; // 로그인 성공 시 홈페이지로 리다이렉트
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.debug("로그아웃 요청: 세션 ID = {}", session.getId());
        session.invalidate(); // 세션 무효화
        return "redirect:/login?logout=true"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    // 마이페이지 보여주기
    @GetMapping("/mypage")
    public String showMyPage(HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("user");

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
            UserVo googleUser = googleOAuthService.getUserInfo(code);
            logger.debug("Google OAuth에서 사용자 정보 가져옴: username = {}, email = {}", 
                         googleUser.getUsername(), googleUser.getEmail());

            // DB에서 해당 이메일로 사용자가 있는지 확인
            UserVo existingUser = userService.findUserByEmail(googleUser.getEmail());

            if (existingUser != null) {
                // 사용자가 이미 DB에 있는 경우 세션에 기존 사용자 정보 저장
                session.setAttribute("user", existingUser);
                logger.debug("기존 사용자 세션에 저장: {}", existingUser);
            } else {
                // 사용자가 DB에 없는 경우 새로 등록 (패스워드는 null로 설정 가능)
                googleUser.setPassword(null); // 구글 로그인 시 패스워드는 사용하지 않음
                userService.registerUser(googleUser);
                session.setAttribute("user", googleUser);
                logger.debug("새로운 사용자 등록 후 세션에 저장: {}", googleUser);
            }

            logger.debug("세션 ID: {}, 세션에 저장된 사용자: {}", session.getId(), session.getAttribute("user"));
            return "redirect:/";  // 로그인 성공 후 홈페이지로 리다이렉트
        } catch (Exception e) {
            logger.error("Google OAuth 로그인 실패", e);
            model.addAttribute("message", "Google 로그인 중 오류가 발생했습니다.");
            return "users/login";  // 로그인 페이지로 다시 이동
        }
    }
    
    // 아이디 찾기 페이지 보여주기
    @GetMapping("/find-id")
    public String showFindIdPage() {
        return "users/findId"; // /WEB-INF/views/users/findId.jsp로 이동
    }
    // 아이디 찾기 처리
    @PostMapping("/find-id")
    public String findIdByEmail(@RequestParam("email") String email, Model model) {
        try {
            // 이메일로 아이디를 찾음
            UserVo user = userService.findUserByEmail(email);

            if (user != null) {
                model.addAttribute("message", "해당 이메일로 등록된 아이디는: " + user.getUsername() + " 입니다.");
            } else {
                model.addAttribute("errorMessage", "해당 이메일로 등록된 아이디가 없습니다.");
            }
        } catch (Exception e) {
            logger.error("아이디 찾기 중 오류 발생", e);
            model.addAttribute("errorMessage", "아이디 찾기 중 오류가 발생했습니다.");
        }

        return "users/findId"; // 다시 아이디 찾기 페이지로 이동
    }
}
