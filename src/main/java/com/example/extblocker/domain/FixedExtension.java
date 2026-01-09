package com.example.extblocker.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "fixed_extension", uniqueConstraints = {
        @UniqueConstraint(name = "uk_fixed_ext", columnNames = {"ext"})
})
public class FixedExtension {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예: "bat", "cmd" ...
    @Column(nullable = false, length = 20)
    private String ext;

    // 체크 여부 (기본 false = unCheck)
    @Column(nullable = false)
    private boolean blocked;

    protected FixedExtension() {}

    public FixedExtension(String ext, boolean blocked) {
        this.ext = ext;
        this.blocked = blocked;
    }

    public Long getId() { return id; }
    public String getExt() { return ext; }
    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }
}
