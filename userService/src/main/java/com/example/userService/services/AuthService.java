package com.example.userService.services;

import com.example.userService.dto.SendEmailMessageDto;
import com.example.userService.dto.UserDto;
import com.example.userService.models.Session;
import com.example.userService.models.SessionStatus;
import com.example.userService.models.User;
import com.example.userService.repositories.SessionRepository;
import com.example.userService.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey secretKey;
    private SessionRepository sessionRepository;

    @Autowired
    public AuthService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,SessionRepository sessionRepository){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.sessionRepository=sessionRepository;
        secretKey = Jwts.SIG.HS256.key().build();
    }


    public ResponseEntity<UserDto> login(String email, String password){
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return null;
        }
        User user= userOptional.get();
        System.err.println("hello world");
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            throw new RuntimeException("password/username does not exist");
        }

        Map<String,Object> jwtData=new HashMap<>();
        jwtData.put("email",email);
        jwtData.put("createdAt",new Date());
        jwtData.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token=Jwts.builder().claims(jwtData).signWith(secretKey).compact();
        System.out.println(secretKey);

        Session session=new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);

        sessionRepository.save(session);
        UserDto userDto=UserDto.from(user);

        MultiValueMap<String, String> header=new MultiValueMapAdapter<>(new HashMap<>());
        header.add(HttpHeaders.SET_COOKIE,"auth-token: "+token);
        return new ResponseEntity<UserDto>(userDto, header,HttpStatus.OK);
    }

    public UserDto signUp(String email,String password){
        User user=new User();
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User savedUser=userRepository.save(user);
        /*try{
            SendEmailMessageDto sendEmailMessageDto=new SendEmailMessageDto();
            sendEmailMessageDto.setTo(savedUser.getEmail());
            sendEmailMessageDto.setFrom("aditi1305sinha@gmail.com");
            sendEmailMessageDto.setSubject("Welcome");
            sendEmailMessageDto.setBody("Welcome to our application");

        }
        */
         /*
        catch(Exception e){
            System.out.println("Some exception occurred");
        }*/
        return UserDto.from(savedUser);
    }


    public SessionStatus validateSession(String token,Long userId) {
        Optional<Session> sessionOptional=sessionRepository.findByTokenAndUser_Id(token,userId);
        /*System.out.println("hello");
        System.out.println(sessionOptional.get());
        //JWTS=> header+payload+signature(a+b+(a+b+secretkey))
        */
        if(sessionOptional.isEmpty()){
            return SessionStatus.ENDED;
        }

        Session session=sessionOptional.get();

        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.ENDED;
        }

        Jws<Claims> claimsJws=Jwts
                                .parser()
                                .verifyWith(secretKey)
                                .build()
                                .parseSignedClaims(token);

        System.out.println(secretKey);
        String email=(String) claimsJws.getPayload().get("email");
        /*Long expiry=(Long)claimsJws.getPayload().get("expiryAt");
        Date expiryAt= new Date(expiry);
        Date date=new Date();
        int result=date.compareTo(expiryAt);
        if(result>0){
            return SessionStatus.ENDED;
        }*/
        System.out.println(email);
        System.out.println("hello");
        return SessionStatus.ACTIVE;
        /****
         static Key secret = MacProvider.generateKey(); will generate a new random key each time your server is reloaded because static variables are initialized when the class is loaded

         It means that if you issue a JWT, it is only valid as long as the server does not reboot. The SignatureException you got is because the signing key it is different

         You need to store the signing key secret.getEncoded() after first generation and load it when your module starts
         ***/
    }

    public void logout(String token,Long userId) {
        Optional<Session> sessionOptional=sessionRepository.findByTokenAndUser_Id(token,userId);

        if(sessionOptional.isEmpty()){
            return;
        }
        Session session=sessionOptional.get();
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

    }
}
