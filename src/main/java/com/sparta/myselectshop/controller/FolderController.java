package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.FolderRequestDto;
import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/folders")
    public void addFolders(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody FolderRequestDto folderRequestDto) {
        List<String> folderNames = folderRequestDto.getFolderNames();
        folderService.addFolders(folderNames, userDetails.getUser());
    }

    @GetMapping("/folders")
    public List<FolderResponseDto> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser());
    }
}
