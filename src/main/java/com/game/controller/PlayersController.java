package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest/players")
public class PlayersController {

    private final PlayerService playerService;

    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers (@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "title", required = false) String title,
                                                       @RequestParam(value = "race", required = false) Race race,
                                                       @RequestParam(value = "profession", required = false) Profession profession,
                                                       @RequestParam(value = "after", required = false) Long after,
                                                       @RequestParam(value = "before", required = false) Long before,
                                                       @RequestParam(value = "banned", required = false) Boolean banned,
                                                       @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                       @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                       @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                       @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                       @RequestParam(value = "order", required = false) PlayerOrder order,
                                                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize){
        List<Player> players = playerService.findAll(name, title, race, profession,
                (after != null? new Date(after) : null), (before != null? new Date(before) : null), banned,
                minExperience, maxExperience, minLevel, maxLevel,
                order == null? PlayerOrder.ID : order,
                pageNumber == null? 0 : pageNumber, pageSize == null? 3 : pageSize);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "race", required = false) Race race,
                                            @RequestParam(value = "profession", required = false) Profession profession,
                                            @RequestParam(value = "after", required = false) Long after,
                                            @RequestParam(value = "before", required = false) Long before,
                                            @RequestParam(value = "banned", required = false) Boolean banned,
                                            @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                            @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                            @RequestParam(value = "maxLevel", required = false) Integer maxLevel){
        return new ResponseEntity<>(playerService.getCount(name, title, race, profession, (after != null? new Date(after) : null),
                (before != null? new Date(before) : null), banned,
                minExperience, maxExperience, minLevel, maxLevel), HttpStatus.OK);
    }
//    @GetMapping("/count2")
//    public ResponseEntity<Integer> getInt(@RequestParam(value = "date", required = false) Long date){
//        System.out.println(date);
//        return new ResponseEntity<>(playerService.getInt(new Date(date)), HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<Player> create(@RequestBody Player player){
        if (player.getName() == null || player.getTitle() == null || player.getRace() == null || player.getProfession() == null || player.getExperience() == null ||
                player.getBirthday() == null || player.getName().equals("") || player.getName().length() > 12 || player.getBirthday().getTime() < 0 ||
                (player.getBirthday().getYear() + 1900) < 2000 || (player.getBirthday().getYear() + 1900) > 3000 || player.getTitle().length() > 30 || player.getExperience() < 0 || player.getExperience() > 10000000 ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        player.calculateLevel();
        player.calculateExperienceUntilNextLevel();
        return new ResponseEntity<>(playerService.save(player), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") long id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!playerService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(playerService.findPlayer(id), HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id, @RequestBody Player player){
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!playerService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if((player.getExperience() != null&&(player.getExperience() < 0 || player.getExperience() > 10000000))){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (player.getBirthday() != null && (player.getBirthday().getTime() < 0 ||
                (player.getBirthday().getYear()+1900) <= 2000 || (player.getBirthday().getYear()+1900) >= 3000)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(playerService.update(id, player), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("id") long id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!playerService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
