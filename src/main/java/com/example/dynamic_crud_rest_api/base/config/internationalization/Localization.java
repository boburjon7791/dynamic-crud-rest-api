package com.example.dynamic_crud_rest_api.base.config.internationalization;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Localization {
    private final MessageSource messageSource;
    public String getMessage(String code){
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Locale locale){
        return messageSource.getMessage(code, null, locale);
    }
}
