package com.example.dynamic_crud_rest_api.base.repository;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseSuperEntity;
import com.example.dynamic_crud_rest_api.base.specification.UpdateSpecification;
import com.example.dynamic_crud_rest_api.base.utils.BaseUtils;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.repository.NoRepositoryBean;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface UpdateSpecificationExecutor<T extends BaseSuperEntity> {
    Set<String> NOT_UPDATING_FIELDS=Set.of(
            BaseSuperEntity._id,
            BaseSuperEntity._createdBy,
            BaseSuperEntity._createdAt,
            BaseSuperEntity._deleted,
            BaseSuperEntity._updatedBy,
            BaseSuperEntity._updatedAt
    );
    Set<String> ANNOTATIONS=Set.of(
            ManyToOne.class.getSimpleName(),
            OneToMany.class.getSimpleName(),
            OneToOne.class.getSimpleName(),
            ManyToMany.class.getSimpleName(),
            ElementCollection.class.getSimpleName(),
            JoinTable.class.getSimpleName(),
            JoinColumn.class.getSimpleName()
    );

    default int executeUpdate(UpdateSpecification<T> spec, Class<T> classType, EntityManager entityManager){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(classType);
        Root<T> root = criteriaUpdate.from(classType);

        // lampba expression da berilgan custom query ni yaratish
        Predicate predicate = spec.apply(criteriaBuilder, criteriaUpdate, root);
        criteriaUpdate.where(predicate);

        addAutomaticallyUpdateFields(criteriaUpdate, root);

        return entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    default int executeUpdate(UpdateSpecification<T> spec, Class<T> classType, Object updateModel, EntityManager entityManager){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(classType);
        Root<T> root = criteriaUpdate.from(classType);

        // lampba expression da berilgan custom query ni yaratish
        Predicate predicate = spec.apply(criteriaBuilder, criteriaUpdate, root);
        criteriaUpdate.where(predicate);

        AtomicInteger countOfUpdatingFields=new AtomicInteger(0);
        checkAndSetUpdatingFields(criteriaUpdate, root, updateModel, classType, countOfUpdatingFields);
        if (countOfUpdatingFields.get()==0) {
            return 1;
        }

        addAutomaticallyUpdateFields(criteriaUpdate, root);

        // ishga tushirish
        return entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    // check and set updating fields
    private static <T> void checkAndSetUpdatingFields(CriteriaUpdate<T> criteriaUpdate, Root<T> root, Object updateModel, Class<T> classType, AtomicInteger countOfUpdatingFields){
        Set<String> relationObjects = Arrays.stream(classType.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> Arrays.stream(field.getAnnotations())
                        .anyMatch(annotationName -> ANNOTATIONS.contains(annotationName.annotationType().getSimpleName())))
                .map(Field::getName)
                .collect(Collectors.toSet());

        // update bo'ladigon qiymatlarni tekshirib set qilish
        for (Field declaredField : updateModel.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                // umumiy holda update qilish cheklangan maydonlar berilmaganlgi tekshiriladi
                if (NOT_UPDATING_FIELDS.contains(declaredField.getName())) {
                    continue;
                }

                // relation bo'lgan obyektlar ignore qilinadi
                if (relationObjects.contains(declaredField.getName())) {
                    continue;
                }

                if (isNullOrStringIsBlank(declaredField, updateModel)) {
                    continue;
                }

                // if shartlaridan birontasiga tushmasa, qiymat set qilinadi
                criteriaUpdate.set(root.get(declaredField.getName()), declaredField.get(updateModel));
                countOfUpdatingFields.incrementAndGet();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean isNullOrStringIsBlank(Field declaredField, Object updateModel) throws IllegalAccessException {
        // null qiymatlar ham ignore qilinadi
        Object value = declaredField.get(updateModel);
        if (Objects.isNull(value)) {
            return true;
        }

        // string bo'lsa va isBlank bo'lsa ham ignore qilinadi
        return value instanceof String string && string.isBlank();
    }

    // add automatically updating fields()
    private static <T> void addAutomaticallyUpdateFields(CriteriaUpdate<T> criteriaUpdate, Root<T> root){
        // update ni monitoring qilish uchun bu maydonlar qo'lda beriladi
        criteriaUpdate.set(root.get(BaseSuperEntity._updatedBy), BaseUtils.currentAuthenticatedUser().getId());
        criteriaUpdate.set(root.get(BaseSuperEntity._updatedAt), LocalDateTime.now());
    }
}
