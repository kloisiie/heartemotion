package com.brframework.commonsecurity.core;

/**
 * @author xu
 * @date 2020/12/16 14:41
 */
public interface SecurityUserDetailsConverter {

    SecurityUserDetails converter(SecuritySubject securitySubject);

}
