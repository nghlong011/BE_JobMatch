package com.example.jobmatch.domain.category;

import com.example.jobmatch.domain.category.dto.request.CategoryRequest;
import com.example.jobmatch.domain.company.CompanyRepo;
import com.example.jobmatch.domain.company.dto.response.CompanyResponse;
import com.example.jobmatch.domain.company.dto.response.Company_JobResponse;
import com.example.jobmatch.entity.*;
import com.example.jobmatch.respon.Respon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepo companyRepo;

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


    public Respon getCategory(Integer categoryId) {
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();
            List<CompanyEntity> listCompany = companyRepo.findAll();

            List<CompanyResponse> companyResponseList = new ArrayList<>();

            for (CompanyEntity company : listCompany) {
                List<Company_JobResponse> listCompany_JobResponse = new ArrayList<>();
                List<String> companyImageLinks = company.getCompanyImageEntityList()
                        .stream()
                        .map(CompanyImageEntity::getLink)
                        .collect(Collectors.toList());

                CompanyResponse companyResponse = new CompanyResponse(company.getCompanyId(), company.getCompanyName(), company.getDescription(),
                        company.getLocation(), company.getLogo(), null, companyImageLinks);
                for (JobsEntity jobsEntity : categoryEntity.getJobsEntities()) {
                    if (company.getCompanyId() == jobsEntity.getCompanyEntity().getCompanyId()) {
                        List<String> JobImageLinks = jobsEntity.getJobImageEntities()
                                .stream()
                                .map(JobImageEntity::getLink)
                                .collect(Collectors.toList());

                        Company_JobResponse company_jobResponse = new Company_JobResponse(jobsEntity.getJobId(), jobsEntity.getTitle(),
                                jobsEntity.getDescription(), jobsEntity.getSalary(), jobsEntity.getLocation(), jobsEntity.getAnnoucementDate(),
                                jobsEntity.getExpirationDate(), jobsEntity.getWorkExperience(), JobImageLinks);
                        listCompany_JobResponse.add(company_jobResponse);
                    }
                }

                companyResponse.setJobs(listCompany_JobResponse);
                if (companyResponse.getJobs().size() > 0) {
                    companyResponseList.add(companyResponse);
                }
            }

            return new Respon<>("Lấy thông tin category thành công", companyResponseList);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin category thất bại");
        }
    }
}
