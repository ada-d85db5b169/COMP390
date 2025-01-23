package com.yyz.comp390.service.impl;

import com.yyz.comp390.controller.FileController;
import com.yyz.comp390.entity.File;
import com.yyz.comp390.entity.dto.EditFileDTO;
import com.yyz.comp390.entity.dto.GetFileDTO;
import com.yyz.comp390.entity.vo.GetFileVO;
import com.yyz.comp390.exception.FileException;
import com.yyz.comp390.mapper.AdminMapper;
import com.yyz.comp390.mapper.FileMapper;
import com.yyz.comp390.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    AdminMapper adminMapper;

    @Resource
    FileMapper fileMapper;

    @Override
    public List<GetFileVO> getFiles(GetFileDTO getFileDTO) {
        List<Long> idList = adminMapper.getUserIdsByUserName(getFileDTO.getCreator());
        if(idList.isEmpty()){
            idList = null;
        }
        List<GetFileVO> getFileVOs = fileMapper.getFiles(idList, getFileDTO);
        return getFileVOs;
    }

    @Override
    public void editFiles(EditFileDTO editFileDTO) {
        fileMapper.editFile(editFileDTO);
    }

    @Override
    public void deleteFiles(List<Long> ids) {
        String dPath = FileController.downloadPath;
        List<String> fileAliases = fileMapper.getFileAliasByIds(ids);
        for(String fileAlias : fileAliases){
            Path path = Paths.get(dPath, fileAlias);
            java.io.File file = path.toFile();

            try{
                if (file.exists()) {
                    if(!file.delete()){
                        throw new FileException("Error occured while deleting file.");
                    }
                }
            } catch (Exception e) {
                throw new FileException("Error occured while deleting file.");
            }
        }
        fileMapper.deleteBatchIds(ids);
    }

    @Override
    public void uploadFile(File file) {
        fileMapper.insert(file);
    }

    @Override
    public GetFileVO getFileById(Long id) {
        return fileMapper.getFileById(id);
    }


}
