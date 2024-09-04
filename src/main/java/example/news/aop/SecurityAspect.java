package example.news.aop;

import example.news.dto.CommentDto;
import example.news.dto.NewsDto;
import example.news.entity.CommentEntity;
import example.news.entity.NewsEntity;
import example.news.entity.OwnerInterface;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.error.exceptions.UnauthorizedException;
import example.news.repository.CommentRepository;
import example.news.repository.NewsRepository;
import example.news.service.CommentService;
import example.news.service.NewsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component

public class SecurityAspect {

    @Autowired
    private CommentService commentRepository;
    @Autowired
    private NewsRepository newsRepository;

    @Around("@annotation(SecureAccess)")
    public Object secureBefore(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[1];
        Long objectId = (Long) args[0];

        if (userId == null) {
            throw new UnauthorizedException("User is not authorized");
        }
            // Проверка доступа
            if (objectId != null) {
                OwnerInterface owner = null;

                if (signature.getDeclaringTypeName().equals(CommentService.class.getName())) {
                    owner = commentRepository.findByIdOrNotFoundThrow(objectId);
                } else if (signature.getDeclaringTypeName().equals(NewsService.class.getName())) {
                    owner = newsRepository.findById(objectId).orElse(null);
                }

                if (owner == null) {
                    throw new ObjectNotFoundException("Object not found");
                }

                if (owner.getUser().getId() != userId) {
                    throw new AccessDeniedException("Access denied");
                }
            }
            return joinPoint.proceed();
        }

    }










