package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;

@Repository
public interface PlayersRepository extends JpaRepository<Player, Long> {

//    @Query(value = "SELECT * FROM player WHERE (:name is null or name like '%:name%') and (:title is null or title = '%:title%') and (:race is null or race = :race)"
//            + "and (:profession is null or profession = :profession) and (:after is null or birthday >= :after) and (:before is null or birthday <= :before)" +
//            "and  (:banned is null or banned = :banned) and (:minExperience is null or experience >= :minExperience) and (:maxExperience is null or experience <= :maxExperience)" +
//            "and (:minLevel is null or level >= :minLevel) and (:maxLevel is null or level <= :maxLevel)",
//            countQuery = "SELECT count(*) FROM player WHERE (:name is null or name like '%:name%') and (:title is null or title = '%:title%') and (:race is null or race = :race)"
//                    + "and (:profession is null or profession = :profession) and (:after is null or birthday >= :after) and (:before is null or birthday <= :before)" +
//                    "and  (:banned is null or banned = :banned) and (:minExperience is null or experience >= :minExperience) and (:maxExperience is null or experience <= :maxExperience)" +
//                    "and (:minLevel is null or level >= :minLevel) and (:maxLevel is null or level <= :maxLevel)",
//            nativeQuery = true)
@Query("SELECT p FROM Player p WHERE (:name is null or p.name like %:name%) and (:title is null or p.title like %:title%) and (:race is null or p.race = :race)"
        + "and (:profession is null or p.profession = :profession) and (:after is null or p.birthday > :after) and (:before is null or p.birthday < :before)" +
        "and  (:banned is null or p.banned = :banned) and (:minExperience is null or p.experience >= :minExperience) and (:maxExperience is null or p.experience <= :maxExperience)" +
        "and (:minLevel is null or p.level >= :minLevel) and (:maxLevel is null or p.level <= :maxLevel)")
    Page<Player> findPlayersByValues(@Param("name") String name,
                                     @Param("title") String title,
                                     @Param("race") Race race,
                                     @Param("profession") Profession profession,
                                     @Param("after") Date after,
                                     @Param("before") Date before,
                                     @Param("banned") Boolean banned,
                                     @Param("minExperience") Integer minExperience,
                                     @Param("maxExperience") Integer maxExperience,
                                     @Param("minLevel") Integer minLevel,
                                     @Param("maxLevel")Integer maxLevel, Pageable pageable);


    @Query("SELECT count(p) FROM Player p WHERE (:name is null or p.name like %:name%) and (:title is null or p.title like %:title%) and (:race is null or p.race = :race)"
            + "and (:profession is null or p.profession = :profession) and (:after is null or p.birthday >= :after) and (:before is null or p.birthday <= :before)" +
            "and  (:banned is null or p.banned = :banned) and (:minExperience is null or p.experience >= :minExperience) and (:maxExperience is null or p.experience <= :maxExperience)" +
            "and (:minLevel is null or p.level >= :minLevel) and (:maxLevel is null or p.level <= :maxLevel)")
    Integer getCount(@Param("name") String name,
                                     @Param("title") String title,
                                     @Param("race") Race race,
                                     @Param("profession") Profession profession,
                                     @Param("after") Date after,
                                     @Param("before") Date before,
                                     @Param("banned") Boolean banned,
                                     @Param("minExperience") Integer minExperience,
                                     @Param("maxExperience") Integer maxExperience,
                                     @Param("minLevel") Integer minLevel,
                                     @Param("maxLevel")Integer maxLevel);

//    @Query(value = "SELECT count(*) FROM player WHERE birthday >= ?1",
//            nativeQuery = true)
//    Integer getInt(Date date);
}
