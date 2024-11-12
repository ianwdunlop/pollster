package org.thetravellingbard.polling;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OPTION_DETAILS")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public Option() { }

    public Option(String title, String category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Poll getPoll() { return poll; }
    public void setPoll(Poll owner) { this.poll = owner; }
    
    @Override
    public String toString() {
        return "{" + "\"id\"=" + id + ", \"title\"=\"" + title + "\"" + ", \"category\"=\"" + category + "\"" +
                ", \"content\"=\"" + content + "\"" + "}";
    }
}
