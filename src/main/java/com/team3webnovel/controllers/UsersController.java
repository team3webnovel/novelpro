package com.team3webnovel.controllers;

import com.team3webnovel.services.UserService;
import com.team3webnovel.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

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
        return "redirect:/"; // 로그인 성공 시 홈페이지로 리다이렉트
        
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/login?logout=true"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }
    
    // 마이페이지 보여주기 (로그인된 사용자만 접근 가능)
    @GetMapping("/mypage")
    public String showMyPage(HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("user");
        
        if (user == null) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        
        // 로그인한 사용자의 정보를 모델에 추가
        model.addAttribute("user", user);
        return "users/mypage";  // /WEB-INF/views/users/mypage.jsp로 이동
    }
}
