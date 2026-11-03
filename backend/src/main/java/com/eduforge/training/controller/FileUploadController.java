package com.eduforge.training.controller;

import com.eduforge.training.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Tag(name = "文件上传", description = "文件上传相关接口")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${file.upload.path:./uploads}")
    private String uploadPath;

    @Value("${file.upload.max-size:5242880}")
    private long maxSize;

    @PostMapping("/image")
    @Operation(summary = "上传图片", description = "上传课程封面等图片文件")
    public Result<String> uploadImage(
            @Parameter(description = "图片文件", required = true)
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return Result.badRequest("请选择要上传的文件");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isValidImageExtension(originalFilename)) {
            return Result.badRequest("只支持JPG、PNG、GIF格式的图片");
        }

        if (file.getSize() > maxSize) {
            return Result.badRequest("文件大小不能超过5MB");
        }

        try {
            String folder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            Path uploadDir = Paths.get(uploadPath, folder);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString().replace("-", "") + extension;
            Path filePath = uploadDir.resolve(newFilename);

            Files.copy(file.getInputStream(), filePath);

            String fileUrl = "/uploads/" + folder + "/" + newFilename;
            log.info("图片上传成功: {}", fileUrl);
            return Result.success(fileUrl);

        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    @PostMapping("/video")
    @Operation(summary = "上传视频", description = "上传课程视频文件")
    public Result<String> uploadVideo(
            @Parameter(description = "视频文件", required = true)
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return Result.badRequest("请选择要上传的文件");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isValidVideoExtension(originalFilename)) {
            return Result.badRequest("只支持MP4、AVI、FLV、MOV格式的视频");
        }

        try {
            String folder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            Path uploadDir = Paths.get(uploadPath, "videos", folder);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString().replace("-", "") + extension;
            Path filePath = uploadDir.resolve(newFilename);

            Files.copy(file.getInputStream(), filePath);

            String fileUrl = "/uploads/videos/" + folder + "/" + newFilename;
            log.info("视频上传成功: {}", fileUrl);
            return Result.success(fileUrl);

        } catch (IOException e) {
            log.error("视频上传失败", e);
            return Result.error("视频上传失败: " + e.getMessage());
        }
    }

    private boolean isValidImageExtension(String filename) {
        String lowerName = filename.toLowerCase();
        return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") ||
               lowerName.endsWith(".png") || lowerName.endsWith(".gif");
    }

    private boolean isValidVideoExtension(String filename) {
        String lowerName = filename.toLowerCase();
        return lowerName.endsWith(".mp4") || lowerName.endsWith(".avi") ||
               lowerName.endsWith(".flv") || lowerName.endsWith(".mov");
    }
}