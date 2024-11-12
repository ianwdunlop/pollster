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

    @Column(name = "text")
    private String text;

    @Column(name = "category")
    private String category;

    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public Option() { }

    public Option(String text) {
        this.text = text;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String title) { this.text = title; }

    public Poll getPoll() { return poll; }
    public void setPoll(Poll owner) { this.poll = owner; }
    
    @Override
    public String toString() {
        return "{" + "\"id\"=" + id + ", \"text\"=\"" + text + "\"" + "}";
    }
}
