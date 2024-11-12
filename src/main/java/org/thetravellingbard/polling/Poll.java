package org.thetravellingbard.polling;

import java.util.List;

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

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Option> optionList;

    public Poll() { }

    public Poll(String question, String email) {
        this.question = question;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getQuestion() {return question; }
    public void setQuestion(String name) { this.question = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Option> getOptionList() { return optionList; }
    public void setOptionList(List<Option> optionList) { this.optionList = optionList; }

    @Override
    public String toString() {
        String options ="";
        for (Option option: getOptionList()) {
            options += option.toString();
        }
        return "{" + "\"id\"=" + id + ", \"question\"=\"" + question + "\"" +  ", \"options\": [" + options + "]" + "}";
    }
}