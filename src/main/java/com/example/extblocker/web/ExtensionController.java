package com.example.extblocker.web;

import com.example.extblocker.service.ExtensionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ext")
public class ExtensionController {

    private final ExtensionService service;

    public ExtensionController(ExtensionService service) {
        this.service = service;
    }

    // 🔹 고정 확장자 목록
    @GetMapping("/fixed")
    public Object fixedList() {
        return service.getFixed();
    }

    // 🔹 고정 확장자 체크/해제
    @PostMapping("/fixed")
    public void setFixed(@RequestParam String ext,
                         @RequestParam boolean blocked) {
        service.setFixedBlocked(ext, blocked);
    }

    // 🔹 커스텀 목록 조회
    @GetMapping("/custom")
    public List<String> customList() {
        return service.getCustomList();
    }

    // 🔹 커스텀 추가
    @PostMapping("/custom")
    public String addCustom(@RequestParam String ext) {
        return service.addCustom(ext);
    }

    // 🔹 커스텀 삭제
    @DeleteMapping("/custom")
    public void deleteCustom(@RequestParam String ext) {
        service.deleteCustom(ext);
    }
}
