package tictactoe.game.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tictactoe.user.entity.AppUser;

import java.util.List;

@Entity
@Convert(attributeName = "json", converter = JsonType.class)
public class Game {

    public enum PlayerType {
        HUMAN,
        COMPUTER;
    }

    public enum PlayerNumber {
        PLAYER_1,
        PLAYER_2
    }

    public enum GameState {
        IN_PROGRESS,
        PLAYER_1_WIN,
        PLAYER_2_WIN,
        DRAW
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private AppUser appUser;

    private PlayerType player1Type;

    private PlayerType player2Type;

    private PlayerNumber nextMove;

    private GameState state;

    private int numberOfRows;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<List<String>> rows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public PlayerType getPlayer1Type() {
        return player1Type;
    }

    public void setPlayer1Type(PlayerType player1Type) {
        this.player1Type = player1Type;
    }

    public PlayerType getPlayer2Type() {
        return player2Type;
    }

    public void setPlayer2Type(PlayerType player2Type) {
        this.player2Type = player2Type;
    }

    public PlayerNumber getNextMove() {
        return nextMove;
    }

    public void setNextMove(PlayerNumber nextMove) {
        this.nextMove = nextMove;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }
}
