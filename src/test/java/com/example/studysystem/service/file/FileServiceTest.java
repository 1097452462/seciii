package com.example.studysystem.service.file;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {
    private FileService mockFileService=mock(FileService.class);
    @Test
    void getFiles() {
        mockFileService.getFiles();
        verify(mockFileService,times(1)).getFiles();
    }

    @Test
    void addFile() {
        mockFileService.addFile(null);
        verify(mockFileService,times(1)).addFile(null);
    }

    @Test
    void updateDB() {
        mockFileService.updateDB();
        verify(mockFileService,times(1)).updateDB();
    }
}