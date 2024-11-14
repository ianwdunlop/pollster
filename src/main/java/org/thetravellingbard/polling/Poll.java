package org.thetravellingbard.polling;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "POLL_DETAILS")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "question")
    private String question;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Option> optionList;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;

    public Poll() {
    }

    public Poll(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String name) {
        this.question = name;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }

    @Override
    public String toString() {
        String options = "";
        for (Option option : getOptionList()) {
            options += option.toString() + ",";
        }
        // remove trailing comma
        options = options.substring(0, options.length() - 1);
        return "{" + "\"id\":" + id + ", \"question\":\"" + question + "\"" + ", \"created\":" + "\"" +  created_at + "\"" + ", \"options\": [" + options + "]" + "}";
    }
}