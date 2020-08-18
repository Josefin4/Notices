package se.experis.academy.sessions.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "notices")
public class Notice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "active", nullable = false)
    private boolean active = true ;


    public Notice(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Notice() { }

    public long getId() { return id; }

    public String getText() { return text; }


    public String getDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }



    public void setInactive() { active = false;}

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", active=" + active +
                '}';
    }
}
