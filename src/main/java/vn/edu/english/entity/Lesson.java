//package vn.edu.english.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Table(name = "lesson")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Lesson {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "lesson_id")
//    private Long lessonId;
//
//    private String title;
//    private String content;
//    private String videoLink;
//    private String lessonObject;
//    private String lessonType;
//    private String packageRequired;
//
//    @ManyToMany
//    @JoinTable(
//            name = "lesson_topic",
//            joinColumns = @JoinColumn(name = "lesson_id"),
//            inverseJoinColumns = @JoinColumn(name = "topic_id")
//    )
//    private List<Topic> topics;
//
//}
