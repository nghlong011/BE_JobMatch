package com.example.jobmatch.domain.category;

import com.example.jobmatch.entity.CategoryEntity;
import com.example.jobmatch.entity.JobsEntity;
import com.example.jobmatch.respon.Respon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Respon createCategory(CategoryRequest categoryRequest) {
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryName(categoryRequest.getCategoryName());
            categoryRepository.save(categoryEntity);
            return new Respon<>("Tạo category thành công");
        } catch (Exception e) {
            return new Respon<>("Tạo category thất bại");
        }
    }

    public Respon deleteCategory(Integer categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return new Respon<>("Xoá category thành công");
        } catch (Exception e) {
            return new Respon<>("Xoá category thất bại " + e);
        }
    }

    public Respon updateCategory(Integer categoryId, CategoryRequest categoryRequest) {
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();
            categoryEntity.setCategoryName(categoryRequest.getCategoryName());
            categoryRepository.save(categoryEntity);
            return new Respon<>("Update category thành công");
        } catch (Exception e) {
            return new Respon<>("Update category thất bại " + e);
        }
    }


    public Respon getCategory() {
        try {
            List<CategoryEntity> listJob = categoryRepository.findAll();
            List<JobsEntity> jobsEntities = listJob.get(0).getJobsEntities();
            return new Respon<>("Lấy thông tin category thành công", jobsEntities);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin category thất bại");
        }
    }
}
