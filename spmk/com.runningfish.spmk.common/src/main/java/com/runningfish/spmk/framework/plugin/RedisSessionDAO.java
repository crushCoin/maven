package com.runningfish.spmk.framework.plugin;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    /**
     * shiro-redis的session对象前缀
     */
    private final String SHIRO_REDIS_SESSION_PRE = "shiro_redis_session:";
    /**
     * 存放uid的对象前缀
     */
    //private final String SHIRO_SHESSIONID_PRE = "shiro_sessionid:";

    private ICache cache;

    private long expire = 180000;

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        session.setTimeout(expire);
        Long redisExpire = expire / 1000;
        int timeout = redisExpire.intValue();
        try {
            //保存用户会话
            //CacheHandler.getCacheHanlder().setCache(this.getByteKey(this.SHIRO_REDIS_SESSION_PRE,session.getId()),
            //		session, timeout);
            String key = cache.getKey(this.SHIRO_REDIS_SESSION_PRE, session.getId());
            cache.setCache(key, session, timeout);

            //保存用户会话对应的UID
//			UserSubjectInfo userSubjectInfo = this.getUserSubjectInfo(session);
//			if (null != userSubjectInfo){
//				CacheHandler.getCacheHanlder().setCache(this.getByteKey(this.SHIRO_SHESSIONID_PRE,session.getId()),
//						userSubjectInfo.getAccountId(), timeout);
//			}
        } catch (Exception ex) {
            logger.error("error execute redissessiondao.saveSession", ex);
        }
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        try {
            //删除用户会话
            //CacheHandler.getCacheHanlder().delCache(this.getByteKey(this.SHIRO_REDIS_SESSION_PRE,session.getId()));
            String key = cache.getKey(this.SHIRO_REDIS_SESSION_PRE, session.getId());
            cache.delCache(key);
//            if( null != session.getAttribute(SecurityHelper.USER_KEY)){
//            RedisHandler.getRedisHandler().decr(UserSession.ONLINE_SESSION_NUM);
//            }
            //删除用户会话对应的UID
//			CacheHandler.getCacheHanlder().delCache(this.getByteKey(this.SHIRO_SHESSIONID_PRE,session.getId()));
//			UserSubjectInfo userSubjectInfo = getUserSubjectInfo(session);
//			if(userSubjectInfo!=null){
//				//删除用户资源
//				CacheHandler.getCacheHanlder().delCache(String.valueOf(SecurityHelper.USER_RESOURCES_KEY+"_"+userSubjectInfo.getIdentification()));
//				//删除用户权限
//				CacheHandler.getCacheHanlder().delCache(String.valueOf(SecurityHelper.USER_PERMISSION_KEY+"_"+userSubjectInfo.getIdentification()));
//				//删除用户角色
//				CacheHandler.getCacheHanlder().delCache(String.valueOf(SecurityHelper.USER_ROLE_KEY+"_"+userSubjectInfo.getIdentification()));
//			}
        } catch (Exception ex) {
            logger.error("error execute redissessiondao.delete", ex);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        try {
            //Set<String> keys = CacheHandler.getCacheHanlder().getKeys(this.SHIRO_REDIS_SESSION_PRE + "*");
            Set<String> keys = cache.getKeys(cache.getKey(this.SHIRO_REDIS_SESSION_PRE + "*"));
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    //Session s = (Session) CacheHandler.getCacheHanlder().getCache(key);
                    Session s = (Session) cache.getCache(key);
                    sessions.add(s);
                }
            }
        } catch (Exception ex) {
            logger.error("error execute redissessiondao.getActiveSessions", ex);
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session s = null;
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }
        String key = cache.getKey(this.SHIRO_REDIS_SESSION_PRE, sessionId);
        //logger.debug("#####Redis.SessionId=" + new String(getByteKey(this.SHIRO_REDIS_SESSION_PRE,sessionId)));
        logger.debug("#####Redis.SessionId=" + key);
        try {
            //s = (Session) CacheHandler.getCacheHanlder().getCache(this.getByteKey(this.SHIRO_REDIS_SESSION_PRE,sessionId));
            s = (Session) cache.getCache(key);
        } catch (Exception ex) {
            logger.error("error execute redissessiondao.doReadSession", ex);
        }
        return s;
    }

    /**
     * 获得String型的key
     *
     * @param key
     * @return
     */
    //private String getByteKey(String preKey,Serializable sessionId) {
    //	String key = preKey + sessionId;
    //	return key;
    //}
//	/**
//	 * 获取用户唯一标识
//	 * @param session
//	 * @return
//	 */
//	private UserSubjectInfo getUserSubjectInfo(Session session){
//		DefaultAccountSubject accountSubject =  (DefaultAccountSubject) session.getAttribute("_Security_USER_KEY");
//		if(accountSubject!=null){
//			UserSubjectInfo userSubjectInfo = (UserSubjectInfo) accountSubject.getSubjectInfo();
//			return userSubjectInfo;
//		}
//		return null;
//	}
    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }
}
