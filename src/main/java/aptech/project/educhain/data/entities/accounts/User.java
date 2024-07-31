package aptech.project.educhain.data.entities.accounts;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.entities.chats.Message;
import aptech.project.educhain.data.entities.chats.UserChat;
import aptech.project.educhain.data.entities.courses.Award;
import aptech.project.educhain.data.entities.courses.Comment;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.UserAnswer;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.data.entities.courses.UserHomework;
import aptech.project.educhain.data.entities.others.Coupon;
import aptech.project.educhain.data.entities.others.CourseFeedback;
import aptech.project.educhain.data.entities.others.Notification;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel implements UserDetails {
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "isVerify")
    private Boolean isVerify;

    @Column(name = "avatarPath")
    private String avatarPath;


    @JsonIgnoreProperties("user")
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private RefreshToken refreshToken;

    @JsonIgnoreProperties("user")
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private EmailToken emailToken;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ResetPasswordToken> resetPasswordTokens;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Homework> homeworks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Award> userAwards;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserCourse> coursesParticipated;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserHomework> userHomeworks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserInterest> userInterests;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserChat> userChats;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupon> coupons;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CourseFeedback> courseFeedbacks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Blog> blogs;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlogComment> blogComments;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> receivedNotifications;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> sentNotifications;

    // Using UserDetail interface for config spring security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
