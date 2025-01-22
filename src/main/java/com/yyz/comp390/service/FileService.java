package com.yyz.comp390.service;


import com.yyz.comp390.entity.File;
import com.yyz.comp390.entity.dto.EditFileDTO;
import com.yyz.comp390.entity.dto.GetFileDTO;
import com.yyz.comp390.entity.vo.GetFileVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileService {
    List<GetFileVO> getFiles(GetFileDTO getFileDTO);

    void editFiles(EditFileDTO editFileDTO);

    void deleteFiles(List<Long> ids);

    void uploadFile(File uploadFileDTO);

    GetFileVO getFileById(Long id);
}
