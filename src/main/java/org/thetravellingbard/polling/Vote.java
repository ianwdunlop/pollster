package org.thetravellingbard.polling;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "VOTE_DETAILS")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "option_id")
    private Option option;

    public Vote() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Option getOption() { return option; }
    public void setOption(Option option) { this.option = option; }
    
    @Override
    public String toString() {
        return "{" + "\"id\":" + id + "\"created\":" +  "\"" + created_at + "}";
    }
}

