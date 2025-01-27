package com.example.joined_table_inheritance.config.utils;

import com.example.joined_table_inheritance.config.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public interface BaseUtils {
    static CustomUserDetails customUserDetails(){
        return (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
