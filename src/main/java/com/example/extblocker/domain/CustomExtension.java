package com.example.extblocker.domain;
import jakarta.persistence.*;

@Entity
@Table(name = "custom_extension", uniqueConstraints = {
        @UniqueConstraint(name = "uk_custom_ext", columnNames = {"ext"})
})
public class CustomExtension {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // "sh" 같은 값만 저장(점(.) 없이, 소문자)
    @Column(nullable = false, length = 20)
    private String ext;

    protected CustomExtension() {}

    public CustomExtension(String ext) {
        this.ext = ext;
    }

    public Long getId() { return id; }
    public String getExt() { return ext; }
}
