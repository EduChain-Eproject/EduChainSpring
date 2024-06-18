package aptech.project.educhain.data.entities.chats;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.UserAnswer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_chats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends BaseModel {
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserChat> userChats;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;
}
