package pl.maciejdudek.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.maciejdudek.project.model.Note;
import pl.maciejdudek.project.model.NoteStatus;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.repositories.NoteRepository;
import pl.maciejdudek.project.repositories.UserRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BootstrapData {

    @Autowired
    public BootstrapData(UserRepository userRepository, NoteRepository noteRepository, PasswordEncoder passwordEncoder) {
        List<User> users = userRepository.saveAll(createTestUsers(passwordEncoder));
        noteRepository.saveAll(createTestNotes(users));
    }

    private List<User> createTestUsers(PasswordEncoder passwordEncoder) {
        List<User> users = new ArrayList<>();

        User kamil = new User();
        kamil.setEmail("kamil@mail.com");
        kamil.setUsername("Kamil");
        kamil.setPassword(passwordEncoder.encode("Kamil123"));
        kamil.setRole("ROLE_ADMIN");
        kamil.setEnabled(true);
        users.add(kamil);

        User marcin = new User();
        marcin.setEmail("marcin@mail.com");
        marcin.setUsername("Marcin");
        marcin.setPassword(passwordEncoder.encode("Marcin123"));
        marcin.setRole("ROLE_USER");
        marcin.setEnabled(true);
        users.add(marcin);

        User maciej = new User();
        maciej.setEmail("maciej@mail.com");
        maciej.setUsername("Maciej");
        maciej.setPassword(passwordEncoder.encode("Maciej123"));
        maciej.setRole("ROLE_USER");
        maciej.setEnabled(true);
        users.add(maciej);

        User dawid = new User();
        dawid.setEmail("dawid@mail.com");
        dawid.setUsername("Dawid");
        dawid.setPassword(passwordEncoder.encode("Dawid123"));
        dawid.setRole("ROLE_USER");
        dawid.setEnabled(true);
        users.add(dawid);

        return users;
    }

    private List<Note> createTestNotes(List<User> testUsers) {
        List<Note> notes = new ArrayList<>();

        Note note1 = new Note();
        note1.setTitle("Notatka 1");
        note1.setContent("Treść notatki pierwszej.");
        note1.setUser(testUsers.get(1));
        note1.setCreatedAt(OffsetDateTime.now());
        note1.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note1);

        Note note2 = new Note();
        note2.setTitle("Notatka 2");
        note2.setContent("Treść notatki drugiej.");
        note2.setUser(testUsers.get(1));
        note2.setCreatedAt(OffsetDateTime.now());
        note2.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note2);

        Note note3 = new Note();
        note3.setTitle("Notatka 3");
        note3.setContent("Treść notatki 3.");
        note3.setUser(testUsers.get(1));
        note3.setCreatedAt(OffsetDateTime.now());
        note3.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note3);

        Note note4 = new Note();
        note4.setTitle("Notatka 4 - test nowej linii");
        note4.setContent("Treść notatki wygląda dokładnie tak jak tutaj.\nNowy wiersz.");
        note4.setUser(testUsers.get(2));
        note4.setCreatedAt(OffsetDateTime.now());
        note4.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note4);

        Note note5 = new Note();
        note5.setTitle("Notatka 5 - test markdowna");
        note5.setContent("## Treść innej notatki  \n- test\n- działania składki\n- języka markdown");
        note5.setUser(testUsers.get(2));
        note5.setCreatedAt(OffsetDateTime.now());
        note5.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note5);

        Note note6 = new Note();
        note6.setTitle("Notatka 6");
        note6.setContent("Zobaczymy jak wygląda kod:\n\n\t\twcięcie");
        note6.setUser(testUsers.get(2));
        note6.setCreatedAt(OffsetDateTime.now());
        note6.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note6);

        Note note7 = new Note();
        note7.setTitle("Notatka 7");
        note7.setContent("Albo czy działa tabela:\n\n| 1 | 2 |\n|:---:|:---:|\n| pierwsza kolumna | drugia kolumna |\n\n_pogrubienie_ i *pogrubienie*");
        note7.setUser(testUsers.get(2));
        note7.setCreatedAt(OffsetDateTime.now());
        note7.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note7);

        Note note8 = new Note();
        note8.setTitle("Notatka 8");
        note8.setContent("Kod wygląda tak:\n```\n\tpublic static void main(String[] argv) {\n\t\tNote note = new Note();\n\t}\n```");
        note8.setUser(testUsers.get(2));
        note8.setCreatedAt(OffsetDateTime.now());
        note8.setNoteStatus(NoteStatus.ACTIVE);
        notes.add(note8);

        Note note9 = new Note();
        note9.setTitle("Notatka 9 - zarchiwizowana.");
        note9.setContent("## Treść usuniętej notatki.\n*pogrubiony tekst*");
        note9.setUser(testUsers.get(2));
        note9.setCreatedAt(OffsetDateTime.now());
        note9.setNoteStatus(NoteStatus.ARCHIVED);
        notes.add(note9);

        Note note10 = new Note();
        note10.setTitle("Notatka 10 - usunięta");
        note10.setContent("[ta notatka jest usunięta](https://google.pl)");
        note10.setUser(testUsers.get(2));
        note10.setCreatedAt(OffsetDateTime.now());
        note10.setNoteStatus(NoteStatus.DELETED);
        notes.add(note10);

        return notes;
    }
}
