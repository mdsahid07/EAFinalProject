package edu.miu.cs.cs544.sahid.hotelbookingsystem.config.aspectConfig;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Room;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoomStatusAspect {

    private static final Logger logger = LoggerFactory.getLogger(RoomStatusAspect.class);

    @After("execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.RoomService.updateRoom(..))")
    public void logRoomStatusChange(JoinPoint joinPoint) {
        Room room = (Room) joinPoint.getArgs()[1];
        logger.info("Room status updated. Room Number: {}, Availability: {}", room.getRoomNumber(), room.isAvailable());
    }
}

