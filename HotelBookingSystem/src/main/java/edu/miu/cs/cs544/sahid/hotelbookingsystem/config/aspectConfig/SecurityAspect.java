package edu.miu.cs.cs544.sahid.hotelbookingsystem.config.aspectConfig;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Before("execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.HotelService.updateHotel(..)) || execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.RoomService.updateRoom(..))")
    public void checkAdminAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            logger.warn("Unauthorized access attempt.");
            throw new AccessDeniedException("You do not have permission to perform this action.");
        }
    }
}

