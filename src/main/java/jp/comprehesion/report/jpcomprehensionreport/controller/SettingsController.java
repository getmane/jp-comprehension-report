package jp.comprehesion.report.jpcomprehensionreport.controller;

import jp.comprehesion.report.jpcomprehensionreport.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("settings")
    public String settings(){
        return "settings";
    }

    @PostMapping("settings/upload-jpdb") public String uploadImage(
            Model model,
            @RequestParam("jpdb-file") MultipartFile file
    ) throws IOException {
        settingsService.saveJpdbFile(file);

        model.addAttribute("uploadedMessage", "Uploaded jpdb file: " + file.getName());
        return "settings";
    }
}