package foxcatcher.stats;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * DAO class for the {@link PlayerStats} entity.
 */
public class PlayerStatsDao extends GenericJpaDao<PlayerStats> {

    public PlayerStatsDao() {
        super(PlayerStats.class);
    }

    /**
     * Returns the list of {@code n} best results with respect to the time
     * spent for solving the puzzle.
     *
     * @param n the maximum number of results to be returned
     * @return the list of {@code n} best results with respect to the time
     * spent for solving the puzzle
     */
    @Transactional
    public List<PlayerStats> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM PlayerStats r  ORDER BY r.wins DESC, r.losses ASC, r.created DESC", PlayerStats.class)
                .setMaxResults(n)
                .getResultList();
    }


    @Transactional
    public void updatePlayerStats(String player,int win,int lost) {

        try {
            PlayerStats playerStats =  entityManager.createQuery("SELECT r FROM PlayerStats r WHERE r.player = :player", PlayerStats.class)
                    .setParameter("player", player)
                    .getSingleResult();

            playerStats.setWins(playerStats.getWins()+win);
            playerStats.setLosses(playerStats.getLosses()+lost);

        } catch (NoResultException e) {

            entityManager.persist(PlayerStats.builder()
                    .player(player)
                    .wins(win)
                    .losses(lost)
                    .build());
        }


    }

}
