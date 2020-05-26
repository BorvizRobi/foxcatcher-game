package foxcatcher.stats;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;

import javax.persistence.NoResultException;
import java.util.List;

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

    /**
     * Updates the given player's statistics in the database.
     *
     * @param player The player name.
     * @param winner For setting the winner of the game.

     */
    @Transactional
    public void updatePlayerStats(String player,boolean winner) {

        try {
            PlayerStats playerStats =  entityManager.createQuery("SELECT r FROM PlayerStats r WHERE r.player = :player", PlayerStats.class)
                    .setParameter("player", player)
                    .getSingleResult();

            if(winner){
            playerStats.setWins(playerStats.getWins()+1);
            }
            else {
                playerStats.setLosses(playerStats.getLosses() + 1);
            }

        } catch (NoResultException e) {

            if(winner) {
                entityManager.persist(PlayerStats.builder()
                        .player(player)
                        .wins(1)
                        .losses(0)
                        .build());
            }else{
                entityManager.persist(PlayerStats.builder()
                        .player(player)
                        .wins(0)
                        .losses(1)
                        .build());
            }
        }


    }

}
