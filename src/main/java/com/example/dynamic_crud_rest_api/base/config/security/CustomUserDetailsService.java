package com.example.dynamic_crud_rest_api.base.config.security;

import com.example.dynamic_crud_rest_api.base.config.internationalization.Localization;
import com.example.dynamic_crud_rest_api.base.exception.ApiException;
import com.example.dynamic_crud_rest_api.user.model.entity.User;
import com.example.dynamic_crud_rest_api.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public record CustomUserDetailsService(UserRepository userRepository, Localization localization) implements UserDetailsService {
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByPhoneAndDeletedFalse(username)
                .orElseThrow(() -> {
                    String classNameFromLocalization = localization.getMessage(User.class.getSimpleName());
                    String notFoundMessage = localization.getMessage("not_found");
                    return new ApiException(classNameFromLocalization+" "+notFoundMessage);
                });
        return CustomUserDetails.of(user);
    }
}
