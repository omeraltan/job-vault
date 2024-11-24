package com.omer.candidate.service;

import org.springframework.core.io.Resource;

public interface IFileService {

    Resource loadFileAsResource(String fileName);

}
