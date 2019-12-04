package com.toklahBackend.security;

import static java.util.Collections.emptyList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toklahBackend.dao.AdminDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.model.Admin;
import com.toklahBackend.model.User;

@Service("customAdminDetailsService")
public class AdminDetailsServiceImp  implements UserDetailsService {
	
static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	
	@Autowired 
	private AdminDao adminDao;

	@Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String phoneOremail) throws UsernameNotFoundException {
        Admin account = adminDao.mobileOremail(phoneOremail);
        if (account == null) {
            throw new UsernameNotFoundException("Admin '" + phoneOremail + "' not found");
        }
        
       
        return org.springframework.security.core.userdetails.User
                .withUsername(phoneOremail)
                .password(account.getPassword())
                .authorities(emptyList())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

    }
	
}
