package br.com.foton.course.model;

import br.com.foton.course.model.Lesson;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 200)
    @Column(length = 200, nullable = false)
    private String name;


    @NotNull
    @Column(length = 10, nullable = false)
    private String category;

    @NotNull
    @Column(length = 8, nullable = false)
    private String status;

    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private Set<Lesson> lessons = new HashSet<>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        if (lessons == null) {
            throw new IllegalArgumentException("Lessons cannot be null.");
        }
        this.lessons.clear();
        this.lessons.addAll(lessons);
    }

    public void addLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null.");
        }
        lesson.setCourse(this);
        this.lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null.");
        }
        lesson.setCourse(null);
        this.lessons.remove(lesson);
    }

}
