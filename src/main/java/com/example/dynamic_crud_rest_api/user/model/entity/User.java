package com.example.dynamic_crud_rest_api.user.model.entity;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseEntityLong;
import com.example.dynamic_crud_rest_api.user.model.enums.Permission;
import com.example.dynamic_crud_rest_api.user.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntityLong {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String phone;

    @Column(columnDefinition = "text", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @JoinTable(name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            indexes = {
                @Index(name = "idx_user_permissions_user_id", columnList = "user_id")
            }
    )
    private Set<Permission> permissions=new HashSet<>();

    // field names list
    public static final String _permissions="permissions";
    public static final String _role="role";
    public static final String _password="password";

    public static User of(String firstName, String lastName, String fatherName, String phone, String password, Role role, Set<Permission> permissions) {
        return new User(firstName, lastName, fatherName, phone, password, role, permissions);
    }
}
