package com.example.dynamic_crud_rest_api.post.model.entity;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseEntityLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntityLong {
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String body;

    public static Post of(String title, String body) {
        return new Post(title, body);
    }
}
