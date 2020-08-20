package pl.maciejdudek.project.model;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name="created_at", columnDefinition = "TIMESTAMP")
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private NoteStatus noteStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
