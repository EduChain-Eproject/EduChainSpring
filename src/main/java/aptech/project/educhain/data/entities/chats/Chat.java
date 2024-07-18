package aptech.project.educhain.data.entities.chats;

import java.util.List;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
