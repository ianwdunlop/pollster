package org.thetravellingbard.polling;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "option", cascade = CascadeType.ALL)
    private List<Vote> voteList;

    public Option() {
    }

    public Option(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String title) {
        this.text = title;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll owner) {
        this.poll = owner;
    }

    public List<Vote> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }

    @Override
    public String toString() {
        String votes = Integer.toString(getVoteList().size());
        return "{" + "\"id\":" + id + ", \"text\":\"" + text + "\"" + "\"votes\":" + votes + "}";
    }
}
