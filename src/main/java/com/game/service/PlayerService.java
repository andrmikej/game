package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class PlayerService {
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final PlayersRepository playersRepository;

    @Autowired
    public PlayerService(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    @Transactional(readOnly = true)
    public List<Player> findAll(String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned,
                                Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder order,
                                Integer pageNumber, Integer pageSize){
        return playersRepository.findPlayersByValues(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel,
                PageRequest.of(pageNumber,pageSize, Sort.by(order.getFieldName()))).getContent();
    }

//    @Transactional(readOnly = true)
//    public Integer getInt (Date date){
//        return playersRepository.getInt(date);
//    }
    @Transactional(readOnly = true)
    public Integer getCount (String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned,
                             Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel){
        return playersRepository.getCount(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
    }

    @Transactional(readOnly = true)
    public Player findPlayer(long id){
        Optional<Player> optionalPlayer= playersRepository.findById(id);
        return  optionalPlayer.orElse(null);
    }
    @Transactional
    public Player save(Player player){
        if (player.getBanned() == null){
            player.setBanned(false);
        }
        return playersRepository.save(player);
    }

    @Transactional
    public Player update(long id, Player player){
        Player existing = playersRepository.findById(id).get();
        if (player.getName() != null && !player.getName().equals("") && player.getName().length() <= 12){
            existing.setName(player.getName());
        }
        if (player.getTitle() != null && player.getTitle().length() < 30){
            existing.setTitle(player.getTitle());
        }
        if (player.getRace() != null){
            existing.setRace(player.getRace());
        }
        if (player.getProfession() != null){
            existing.setProfession(player.getProfession());
        }
        if (player.getBirthday() != null && player.getBirthday().getTime() >= 0 &&
                (player.getBirthday().getYear()+1900) >= 2000 && (player.getBirthday().getYear()+1900) <= 3000){
            existing.setBirthday(player.getBirthday());
        }
        if (player.getBanned() != null){
            existing.setBanned(player.getBanned());
        }
        if (player.getExperience() != null && player.getExperience() >=0 && player.getExperience() <=10000000){
            existing.setExperience(player.getExperience());
        }
        return playersRepository.save(existing);
    }

    @Transactional
    public void delete(long id){
        playersRepository.deleteById(id);
    }

    public boolean exists(long id){
        return  playersRepository.existsById(id);
    }
}
