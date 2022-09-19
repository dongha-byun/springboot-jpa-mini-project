package project.notice.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import project.notice.constrants.SessionConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
public class SessionCheckInterceptor implements HandlerInterceptor {

    private static final String LOG_ID = "LOG_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);

        log.info("session : {}", session);
        log.info("request [{}][{}][{}][{}]", uuid, request, requestURI, handler);

        if(session == null || session.getAttribute(SessionConstants.LOGIN_USER) == null){
            response.sendRedirect("/login?redirectPath=" + requestURI);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("post handler : {}", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}][{}][{}]", uuid, request.getDispatcherType(), requestURI, handler);
        if(ex != null){
            log.error("afterCompletion error!! : {}", ex.getMessage());
        }
    }
}
