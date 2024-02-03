package jp.comprehesion.report.jpcomprehensionreport.controller;

import jp.comprehesion.report.jpcomprehensionreport.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
class HomeController {

    private final CacheService cacheService;

    @GetMapping("/")
    public String main(Model model) {
        List<String> unknownWords = cacheService.getValues("unknownWords");
        List<String> knownWords = cacheService.getValues("knownWords");
        List<String> pageWords = cacheService.getValues("pageWords");

        model.addAttribute("unknownWords", unknownWords);
        model.addAttribute("knownWords", knownWords);
        model.addAttribute("pageWords", pageWords);
        model.addAttribute("knownPercentage",
                !pageWords.isEmpty()
                        ? String.format("%.0f", (double) knownWords.size() / (double) pageWords.size() * 100)
                        : "0");
        return "home";
    }
}