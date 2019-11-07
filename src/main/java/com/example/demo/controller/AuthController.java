package com.example.demo.controller;

import com.example.demo.authorization.JwtTokenUtil;
import com.example.demo.authorization.JwtUserFactory;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
//import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.type}")
    private String tokenType;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @Autowired
//    @Qualifier("JwtUserDetailsService")
//    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    /***
     *
     * @param user,
     * 接收键
     * userPhone
     * userName
     * userPassword
     * @return token
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) {

//        String userName = body.get("username");
//        String email = body.get("email");
//        String password = body.get("password");
//
//        User user = new User();
//        user.setUsername(userName);
//        user.setEmail(email);
//        user.setPassword(password);
        user.setDateOfRegister(Date.valueOf(LocalDate.now()));

        logger.info("user/register request received!", user.toString());

        List<User> matchUsers = userService.getUsers(user.getUsername());
        if (!matchUsers.isEmpty()) {
            logger.info("user/register User exists!", user.toString());
            return ResponseEntity.badRequest().body("User exists!");
        }
        int success = userService.addUser(user);
        logger.info("user/register adduser success status: " + success);
        if (success == 1) {
//            try {
//                /*
//                 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到
//                 * d:/files大家是否能实现呢？ 等等;
//                 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；
//                 * 3、文件格式; 4、文件大小的限制;
//                 */
//                File dir = new File("./avatar/"+userPhone);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                File newFile = new File(dir, file.getOriginalFilename());
//                if (!newFile.exists()) {
//                    newFile.createNewFile();
//                }
//                BufferedOutputStream out = new BufferedOutputStream(
//                        new FileOutputStream(newFile));
//                System.out.println(file.getOriginalFilename());
//                out.write(file.getBytes());
//                out.flush();
//                out.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                return ResponseEntity.badRequest().body(null);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ResponseEntity.badRequest().body(null);
//            }


            final UserDetails userDetails = JwtUserFactory.create(user);
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(jwtTokenUtil.getTokenResponse(token));
        } else {
            logger.info("user/register Register failed!", user.toString());
            return ResponseEntity.badRequest().body("Register failed!");
        }
    }


    /***
     *
     * @param user
     * 接收键
     * userPhone
     * userName
     * userPassword
     * @return token
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        @NotNull
        String userName = user.getUsername();
        @NotNull
        String userPassword = user.getPassword();


        List<User> matchUsers = userService.getUsers(userName);
        if (!matchUsers.isEmpty() && matchUsers.get(0).getPassword().equals(userPassword)) {
            final UserDetails userDetails = JwtUserFactory.create(matchUsers.get(0));
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(jwtTokenUtil.getTokenResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials!");
        }
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(@RequestHeader String Authorization, @RequestParam Map<String, String> body) {
        final String token = Authorization.substring(7);
        String refreshedToken = jwtTokenUtil.refreshToken(token);
        return ResponseEntity.ok(jwtTokenUtil.getTokenResponse(refreshedToken));
    }
}
