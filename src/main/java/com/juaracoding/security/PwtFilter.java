//package com.juaracoding.security;
//
//import com.juaracoding.config.OtherConfig;
//import com.juaracoding.service.AppUserDetailService;
//import com.juaracoding.util.LoggingFile;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class PwtFilter extends OncePerRequestFilter {
//    @Autowired
//    private AppUserDetailService appUserDetailService;
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//        try{
//            String userName = Crypto.performDecrypt(request.getHeader("X-AUTH-USERNAME"));
//            final UserDetails userDetails = appUserDetailService.loadUserByUsername(userName);
//            final UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(userDetails, null,
//                            userDetails.getAuthorities());//USER,AKSES,GROUP-MENU,MENU,ARTIKEL-1,ARTIKEL-2
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        catch (Exception ex)
//        {
//            LoggingFile.exceptionStringz("PwtFilter","doFilterInternal", ex, OtherConfig.getFlagLogging());
//        }
//
//        filterChain.doFilter(request,response);
//    }
//}