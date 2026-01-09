package com.example.extblocker.service;

import com.example.extblocker.domain.CustomExtension;
import com.example.extblocker.domain.FixedExtension;
import com.example.extblocker.repo.CustomExtensionRepo;
import com.example.extblocker.repo.FixedExtensionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExtensionService {

    // 과제에 나온 고정 확장자 목록
    private static final List<String> FIXED = List.of("bat", "cmd", "com", "cpl", "exe", "scr", "js");

    private final FixedExtensionRepo fixedRepo;
    private final CustomExtensionRepo customRepo;

    public ExtensionService(FixedExtensionRepo fixedRepo, CustomExtensionRepo customRepo) {
        this.fixedRepo = fixedRepo;
        this.customRepo = customRepo;

        // 서버 시작 시 고정 확장자 기본 row를 만들어둠(없으면 생성)
        // (default unCheck = false)
        initFixedIfNeeded();
    }

    @Transactional
    public void initFixedIfNeeded() {
        for (String ext : FIXED) {
            fixedRepo.findByExt(ext).orElseGet(() -> fixedRepo.save(new FixedExtension(ext, false)));
        }
    }

    // ✅ 입력을 “같은 형태”로 바꾸는 함수
    // ".SH " -> "sh"
    public String normalize(String raw) {
        if (raw == null) return "";
        String s = raw.trim().toLowerCase();

        // 앞에 점이 있으면 제거
        if (s.startsWith(".")) s = s.substring(1);

        // 중간 공백도 제거(예: "s h" 같은 꼼수 방지)
        s = s.replaceAll("\\s+", "");

        return s;
    }

    @Transactional(readOnly = true)
    public List<FixedExtension> getFixed() {
        // 고정 목록은 항상 7개가 있어야 함(안전)
        initFixedIfNeeded();
        return fixedRepo.findAll().stream()
                .sorted((a,b) -> FIXED.indexOf(a.getExt()) - FIXED.indexOf(b.getExt()))
                .toList();
    }

    @Transactional
    public void setFixedBlocked(String extRaw, boolean blocked) {
        String ext = normalize(extRaw);

        // 고정 목록에 없는 걸 바꾸려 하면 거절
        if (!FIXED.contains(ext)) {
            throw new IllegalArgumentException("고정 확장자 목록에 없는 값입니다: " + ext);
        }

        FixedExtension fe = fixedRepo.findByExt(ext).orElseThrow();
        fe.setBlocked(blocked);
    }

    @Transactional(readOnly = true)
    public List<String> getCustomList() {
        return customRepo.findAll().stream()
                .map(CustomExtension::getExt)
                .sorted()
                .toList();
    }

    @Transactional
    public String addCustom(String raw) {
        String ext = normalize(raw);

        // 2-1) 최대 20자
        if (ext.isBlank() || ext.length() > 20) {
            throw new IllegalArgumentException("확장자는 1~20자만 가능합니다.");
        }

        // (선택) 너무 이상한 문자는 막기 (영문/숫자만 허용)
        if (!ext.matches("[a-z0-9]+")) {
            throw new IllegalArgumentException("확장자는 영문 소문자/숫자만 허용합니다.");
        }

        // 3-1) 최대 200개
        if (customRepo.countBy() >= 200) {
            throw new IllegalStateException("커스텀 확장자는 최대 200개까지 가능합니다.");
        }

        // 고정 확장자는 커스텀으로 추가 못하게(혼동 방지)
        if (FIXED.contains(ext)) {
            throw new IllegalArgumentException("이 확장자는 고정 확장자 목록에 있습니다. 고정 체크박스를 사용하세요.");
        }

        // 중복 체크
        if (customRepo.existsByExt(ext)) {
            throw new IllegalArgumentException("이미 존재하는 확장자입니다: " + ext);
        }

        customRepo.save(new CustomExtension(ext));
        return ext;
    }

    @Transactional
    public void deleteCustom(String raw) {
        String ext = normalize(raw);
        customRepo.deleteByExt(ext);
    }

    public int getCustomCount() {
        return (int) customRepo.countBy();
    }

    public int getCustomLimit() {
        return 200;
    }
}
