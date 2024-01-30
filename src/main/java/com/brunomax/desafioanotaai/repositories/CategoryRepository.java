package com.brunomax.desafioanotaai.repositories;

import com.brunomax.desafioanotaai.domain.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}
