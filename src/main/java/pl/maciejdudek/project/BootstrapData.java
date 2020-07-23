package pl.maciejdudek.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.repositories.NoteRepository;
import pl.maciejdudek.project.repositories.UserRepository;

import java.time.LocalDateTime;

@Configuration
public class BootstrapData {

    @Autowired
    public BootstrapData(UserRepository userRepository, NoteRepository noteRepository, PasswordEncoder passwordEncoder) {
        User kamil = new User();
        kamil.setEmail("kamil@mail.com");
        kamil.setUsername("Kamil");
        kamil.setPassword(passwordEncoder.encode("Kamil123"));
        kamil.setRole("ROLE_ADMIN");
        kamil.setEnabled(true);

        User marcin = new User();
        marcin.setEmail("marcin@mail.com");
        marcin.setUsername("Marcin");
        marcin.setPassword(passwordEncoder.encode("Marcin123"));
        marcin.setRole("ROLE_USER");
        marcin.setEnabled(true);

        User maciej = new User();
        maciej.setEmail("maciej@mail.com");
        maciej.setUsername("Maciej");
        maciej.setPassword(passwordEncoder.encode("Maciej123"));
        maciej.setRole("ROLE_USER");
        maciej.setEnabled(true);

        User dawid = new User();
        dawid.setEmail("dawid@mail.com");
        dawid.setUsername("Dawid");
        dawid.setPassword(passwordEncoder.encode("Dawid123"));
        dawid.setRole("ROLE_USER");
        dawid.setEnabled(true);

        userRepository.save(kamil);
        userRepository.save(marcin);
        userRepository.save(maciej);
        userRepository.save(dawid);

        Note note1 = new Note();
        note1.setTitle("Notatka 1");
        note1.setContent("Treść notatki pierwszej.");
        note1.setUser(marcin);
        note1.setCreatedAt(LocalDateTime.now());
        note1.setNoteStatus(NoteStatus.ACTIVE);

        Note note2 = new Note();
        note2.setTitle("Notatka 2");
        note2.setContent("Treść notatki drugiej.");
        note2.setUser(marcin);
        note2.setCreatedAt(LocalDateTime.now());
        note2.setNoteStatus(NoteStatus.ACTIVE);

        Note note3 = new Note();
        note3.setTitle("Notatka 3");
        note3.setContent("Treść notatki 3.");
        note3.setUser(marcin);
        note3.setCreatedAt(LocalDateTime.now());
        note3.setNoteStatus(NoteStatus.ACTIVE);

        Note note4 = new Note();
        note4.setTitle("Notatka 4 - test nowej linii");
        note4.setContent("Treść notatki wygląda dokładnie tak jak tutaj.\nNowy wiersz.");
        note4.setUser(maciej);
        note4.setCreatedAt(LocalDateTime.now());
        note4.setNoteStatus(NoteStatus.ACTIVE);

        Note note5 = new Note();
        note5.setTitle("Notatka 5 - inna");
        note5.setContent("## Treść innej notatki  \n- test\n- działania składki\n- języka markdown");
        note5.setUser(maciej);
        note5.setCreatedAt(LocalDateTime.now());
        note5.setNoteStatus(NoteStatus.ACTIVE);

        Note note6 = new Note();
        note6.setTitle("Notatka 6");
        note6.setContent("Treść notatki 6.");
        note6.setUser(dawid);
        note6.setCreatedAt(LocalDateTime.now());
        note6.setNoteStatus(NoteStatus.ACTIVE);

        Note note7 = new Note();
        note7.setTitle("Notatka 7");
        note7.setContent("Treść notatki 7.");
        note7.setUser(marcin);
        note7.setCreatedAt(LocalDateTime.now());
        note7.setNoteStatus(NoteStatus.ACTIVE);

        noteRepository.save(note1);
        noteRepository.save(note2);
        noteRepository.save(note3);
        noteRepository.save(note4);
        noteRepository.save(note5);
        noteRepository.save(note6);
        noteRepository.save(note7);
    }
}
