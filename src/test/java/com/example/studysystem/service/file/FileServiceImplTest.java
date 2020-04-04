package com.example.studysystem.service.file;

import com.example.studysystem.db.InsertDB;
import com.example.studysystem.db.Insert_field;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceImplTest {
    private FileServiceImpl fileService=new FileServiceImpl();
    private FileServiceImpl mockFileService=mock(FileServiceImpl.class);
    private InsertDB mockInserDB=mock(InsertDB.class);
    private Insert_field mockInsert_field=mock(Insert_field.class);

    public void prepare(){fileService.set(mockInserDB,mockInsert_field);}
    @Test
    void getFiles() {
        prepare();
        mockFileService.getFiles();
        verify(mockFileService,times(1)).getFiles();
        assertNotNull(fileService.getFiles());
    }

    @Test
    void addFile() {
        prepare();
        mockFileService.addFile(null);
        verify(mockFileService,times(1)).addFile(null);
        assertEquals("失败",fileService.addFile(null).getMessage());
    }

    @Test
    void updateDB() {
        prepare();
        mockFileService.updateDB();
        verify(mockFileService,times(1)).updateDB();
        when(mockInserDB.tranfData()).thenThrow(new RuntimeException());
        assertEquals("失败",fileService.updateDB().getMessage());
        verify(mockInserDB,times(1)).tranfData();
    }
}