package com.example.dynamic_crud_rest_api.base.utils;

import com.example.dynamic_crud_rest_api.base.config.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public interface BaseUtils {
    static CustomUserDetails currentAuthenticatedUser() {
        return  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new LinkedList<>();
        while (type != null) {
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
            type = type.getSuperclass();
        }
        return fields;
    }
}
