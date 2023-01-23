package com.game.entity;

import com.game.controller.PlayersController;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "title")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "race")
    private Race race;
    @Enumerated(EnumType.STRING)
    @Column(name = "profession")
    private Profession profession;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "level")
    private Integer level;
    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "banned")
    private Boolean banned;

    public Player(Long id, String name, String title, Race race, Profession profession, Integer experience, Integer level,Integer untilNextLevel, Date birthday, Boolean banned) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        calculateLevel();
        calculateExperienceUntilNextLevel();
        this.birthday = birthday;
        this.banned = banned;
    }
    public Player (){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getExperience() {
        return experience;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
    public void setExperience(Integer experience) {
        this.experience = experience;
        calculateLevel();
        calculateExperienceUntilNextLevel();
    }

    public void setLevel(Integer level) {}

    public void setUntilNextLevel(Integer untilNextLevel) {}

    public void calculateLevel(){
        level = (int) ((Math.sqrt(2500 + 200*experience) - 50) / 100);
    }

    public void calculateExperienceUntilNextLevel(){
        untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
    }
}
