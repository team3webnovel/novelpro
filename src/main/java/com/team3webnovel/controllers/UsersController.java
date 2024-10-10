package com.team3webnovel.controllers;

import com.team3webnovel.services.GoogleOAuthService;
import com.team3webnovel.services.UserService;
import com.team3webnovel.vo.UserVo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

    private static final String CLIENT_ID = "79063217086-lsnrlcthi1q4tkqg9cd713qa3eg6qodc.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8080/team3webnovel/callback";
	
    @Autowired
    private UserService userService;

    @Autowired
    private GoogleOAuthService googleOAuthService;

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // 회원가입 페이지 보여주기
    @GetMapping("/register")
    public String showRegisterPage() {
        return "users/register"; // /WEB-INF/views/users/register.jsp로 이동
    }

    // 이메일 인증 토큰 생성 및 전송
    @PostMapping("/send-email-token")
    @ResponseBody
    public String sendEmailToken(@RequestBody Map<String, String> emailJson, HttpSession session) {
        try {
            String email = emailJson.get("email").trim(); // 이메일을 JSON에서 추출하고 공백 제거
            
            // 이메일 형식 검증
            if (!EmailValidator.getInstance().isValid(email)) {
                throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
            }

            // 인증 토큰 생성
            String token = String.format("%06d", new Random().nextInt(999999));
            
            // 세션에 토큰 저장
            session.setAttribute("emailAuthToken", token);
            session.setAttribute("authEmail", email);

            // 이메일 전송
            userService.sendEmailToken(email, token);
            return "{\"success\": true}";
        } catch (Exception e) {
            logger.error("이메일 인증 토큰 전송 중 오류 발생", e);
            return "{\"success\": false}";
        }
    }


    // 인증번호 확인 처리
    @PostMapping("/verify-email-token")
    @ResponseBody
    public String verifyEmailToken(@RequestBody Map<String, String> tokenJson, HttpSession session) {
        // 사용자가 보낸 토큰을 JSON에서 추출
        String inputToken = tokenJson.get("token").trim();
        
        // 세션에 저장된 인증 토큰 가져오기
        String sessionToken = (String) session.getAttribute("emailAuthToken");
        String sessionEmail = (String) session.getAttribute("authEmail");

        // 사용자가 입력한 토큰과 세션에 저장된 토큰 비교
        logger.debug("세션 인증 토큰: {}", sessionToken);
        logger.debug("세션 이메일: {}", sessionEmail);
        logger.debug("입력된 토큰: {}", inputToken);

        if (sessionToken != null && sessionToken.equals(inputToken)) {
            session.setAttribute("isEmailVerified", true);
            logger.debug("이메일 인증 성공: {}", sessionEmail);
            return "{\"success\": true}";
        } else {
            return "{\"success\": false}";
        }
    }


    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("confirmpassword") String confirmpassword,
                               HttpSession session,
                               Model model) {

        // 빈칸이 있는지 확인
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("message", "아이디를 입력하세요.");
            return "users/register";
        }

        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("message", "이메일을 입력하세요.");
            return "users/register";
        }

        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("message", "비밀번호를 입력하세요.");
            return "users/register";
        }

        if (confirmpassword == null || confirmpassword.trim().isEmpty()) {
            model.addAttribute("message", "비밀번호 확인을 입력하세요.");
            return "users/register";
        }

        // 이메일 인증 여부 확인
        Boolean isEmailVerified = (Boolean) session.getAttribute("isEmailVerified");
        if (isEmailVerified == null || !isEmailVerified) {
            model.addAttribute("message", "이메일 인증이 필요합니다.");
            return "users/register";
        }

        // 중복된 아이디 및 이메일 체크
        if (userService.findUserByUsername(username) != null) {
            model.addAttribute("message", "이미 존재하는 사용자 이름입니다.");
            return "users/register";
        }

        if (userService.findUserByEmail(email) != null) {
            model.addAttribute("message", "이미 존재하는 이메일입니다.");
            return "users/register";
        }

        // 비밀번호 일치 여부 확인
        if (!password.equals(confirmpassword)) {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "users/register";
        }

        try {
            // 사용자 등록 로직 실행
            UserVo newUser = new UserVo();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);

            userService.registerUser(newUser);

            model.addAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("message", "회원가입 중 오류가 발생했습니다.");
            return "users/register";
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
        System.err.println(user);
        
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

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("로그아웃 요청: 세션 ID = {}", session.getId());

        // 세션에 저장된 모든 속성 출력
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            logger.debug("세션 속성: 이름 = {}, 값 = {}", attributeName, attributeValue);
        }

        // 모든 쿠키 출력 및 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.debug("쿠키 이름: {}, 값: {}", cookie.getName(), cookie.getValue());

                // 쿠키 삭제 (쿠키의 유효 기간을 0으로 설정하고 경로를 지정)
                cookie.setMaxAge(0);
                cookie.setPath("/");  // 애플리케이션의 모든 경로에서 쿠키가 삭제되도록 설정
                response.addCookie(cookie);
                logger.debug("쿠키 삭제됨: {}", cookie.getName());
            }
        }

        // 세션 무효화
        session.invalidate();
        logger.debug("세션 무효화 완료");

        return "redirect:/login?logout=true";  // 로그아웃 후 로그인 페이지로 리다이렉트
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
    public String googleLogin(HttpSession session) {
        logger.debug("Google 로그인 요청.");

        try {
            // 세션에 저장된 사용자 정보 확인
            UserVo loggedInUser = (UserVo) session.getAttribute("user");
            System.err.println(loggedInUser);

            if (loggedInUser != null) {
                logger.debug("이미 로그인된 사용자: {}", loggedInUser.getEmail());
                return "redirect:/";  // 이미 로그인된 사용자는 홈으로 리다이렉트
            }

            // Google Authorization URL 가져오기
            String authorizationUrl = googleOAuthService.getAuthorizationUrl();

            // prompt=select_account 파라미터를 추가하여 구글 계정 선택을 강제
            authorizationUrl += "&prompt=select_account";

            logger.debug("Authorization URL: {}", authorizationUrl);
            return "redirect:" + authorizationUrl;  // 구글 로그인 페이지로 리다이렉트

        } catch (Exception e) {
            logger.error("Google OAuth 로그인 중 오류 발생: ", e);
            // 오류 발생 시 처리할 페이지로 리다이렉트, 예: 에러 페이지
            return "redirect:/error";
        }
    }



    @GetMapping("/callback")
    public String googleCallback(@RequestParam("code") String code, HttpSession session, Model model) {
        try {
            // Google OAuth 서비스에서 사용자 정보 가져오기
            UserVo googleUser = googleOAuthService.getUserInfo(code);  // 이 시점에서 사용자 이메일과 이름을 가져옴
            logger.debug("Google OAuth에서 사용자 정보 가져옴: user_id = {}, username = {}, email = {}", 
                         googleUser.getUserId(), googleUser.getUsername(), googleUser.getEmail());

            // DB에서 해당 이메일로 사용자가 있는지 확인
            UserVo existingUser = userService.findUserByEmail(googleUser.getEmail());
            // 로그인 성공 시 세션에 사용자 정보 저장

            if (existingUser != null) {
            	int clientId = googleUser.getUserId();
            	session.setAttribute("clientId", clientId);
                // 사용자가 이미 DB에 있는 경우 세션에 기존 사용자 정보 저장
                session.setAttribute("user", existingUser);
                logger.debug("기존 사용자 세션에 저장: {}", existingUser);
            } else {
                // 사용자가 DB에 없는 경우 새로 등록 (랜덤 패스워드 설정)
                String randomPassword = UUID.randomUUID().toString();  // 임의의 패스워드 생성
                googleUser.setPassword(randomPassword);  // 임의로 생성된 패스워드 설정
                userService.registerUser(googleUser);  // DB에 사용자 정보 저장
                session.setAttribute("user", googleUser);
                logger.debug("새로운 사용자 등록 후 세션에 저장: {}", googleUser);
            }

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
    
    // 비밀번호 재설정 페이지 표시
    @GetMapping("/reset-password")
    public String showResetPasswordPage() {
        return "users/resetPassword";
    }

    // 비밀번호 재설정 요청 처리
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email, Model model) {
        try {
            // 입력된 이메일로 사용자를 검색
            UserVo user = userService.findUserByEmail(email);

            if (user == null) {
                // 사용자가 존재하지 않는 경우
                model.addAttribute("message", "해당 이메일로 등록된 사용자가 없습니다.");
                return "users/resetPassword";
            }

            // 무작위 숫자 코드로 임시 비밀번호 생성 (6자리 숫자)
            String temporaryPassword = generateTemporaryCode();

            // 임시 비밀번호를 암호화하여 DB에 저장
            String encryptedPassword = passwordEncoder.encode(temporaryPassword);
            user.setPassword(encryptedPassword);
            userService.updateUserPassword(user);

            // 임시 비밀번호를 이메일로 전송
            userService.sendTemporaryPasswordEmail(user.getEmail(), temporaryPassword);

            model.addAttribute("successMessage", "임시 비밀번호가 이메일로 전송되었습니다.");
            return "users/resetPassword";
        } catch (Exception e) {
            logger.error("비밀번호 재설정 중 오류 발생", e);
            model.addAttribute("message", "비밀번호 재설정 중 오류가 발생했습니다.");
            return "users/resetPassword";
        }
    }

    // 무작위 숫자 생성 메서드 (6자리)
    private String generateTemporaryCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 100000 ~ 999999 사이의 숫자 생성
        return String.valueOf(code);
    }
    
    // 비밀번호 변경 페이지 보여주기 (마이페이지에서 접근)
    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "users/changePassword"; // /WEB-INF/views/users/change-password.jsp로 이동
    }
    
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session, Model model) {

        try {
            // 세션에서 현재 로그인된 사용자 정보 가져오기
            UserVo user = (UserVo) session.getAttribute("user");

            if (user == null) {
                model.addAttribute("message", "로그인이 필요합니다.");
                return "redirect:/login"; // 사용자가 로그아웃된 경우 로그인 페이지로 리다이렉트
            }

            // 새로운 비밀번호와 확인 비밀번호 일치 여부 확인
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("message", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                return "users/changePassword"; // 비밀번호 변경 페이지로 다시 이동
            }

            // 현재 비밀번호 확인
            if (!userService.isPasswordMatch(currentPassword, user.getPassword())) {
                model.addAttribute("message", "현재 비밀번호가 일치하지 않습니다.");
                return "users/changePassword"; // 비밀번호 변경 페이지로 다시 이동
            }

            // 새로운 비밀번호 암호화 후 사용자 객체의 비밀번호 업데이트
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);

            // DB에 사용자 비밀번호 업데이트
            userService.updateUserPassword(user);

            // 비밀번호 변경 성공 메시지
            model.addAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");
            return "users/changePassword"; // 비밀번호 변경 완료 후 같은 페이지로 리다이렉트

        } catch (Exception e) {
            // 예외 발생 시 에러 메시지 처리
            model.addAttribute("message", "비밀번호 변경 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "users/changePassword"; // 예외 발생 시 다시 비밀번호 변경 페이지로 이동
        }
    }



}