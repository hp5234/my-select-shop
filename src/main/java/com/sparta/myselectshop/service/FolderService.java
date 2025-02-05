package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    @Transactional
    public void addFolders(List<String> folderNames, User user) {
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        ArrayList<Folder> newFolderList = new ArrayList<>();

        for (String folderName : folderNames) {
            if (isExistFolderName(folderName, existFolderList)) { // 이미 존재하는 폴더 명 검사
                throw new IllegalArgumentException("폴더명이 중복되었습니다.");
            }

            Folder newFolder = new Folder(folderName, user);
            newFolderList.add(newFolder);
        }

        folderRepository.saveAll(newFolderList);
    }

    public List<FolderResponseDto> getFolders(User user) {
        return folderRepository.findAllByUser(user)
                .stream()
                .map(FolderResponseDto::new)
                .toList();
    }

    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        for (Folder existFolder : existFolderList) {
            if (folderName.equals(existFolder.getName())) {
                return true;
            }
        }
        return false;
    }
}
