package com.serafim.java_blog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageExtensionValidator implements ConstraintValidator<ValidImageExtension, MultipartFile[]> {

    private List<String> allowedExtensions;

    @Override
    public void initialize(ValidImageExtension constraintAnnotation) {
        this.allowedExtensions = Arrays.asList(constraintAnnotation.allowedExtensions());
    }

    @Override
    public boolean isValid(MultipartFile[] files, ConstraintValidatorContext constraintValidatorContext) {

        if (files == null || files.length == 0) {
            return true;
        }

        for (MultipartFile file : files) {

            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();

                if (originalFilename == null) {
                    return false;
                }

                int dotIndex = originalFilename.lastIndexOf(".");

                if (dotIndex > 0) {
                    String extenstion = originalFilename.substring(dotIndex + 1).toLowerCase();

                    if (!allowedExtensions.contains(extenstion)) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        return true;
    }
}
