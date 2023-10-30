package com.amida.tacocloud.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService{
    UserDetails loadUserByUsername(String user)
            throws UsernameNotFoundException;
}
