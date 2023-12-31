package tictactoe;

import java.security.Principal;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tictactoe.game.GameService;
import tictactoe.game.entity.Game;
import tictactoe.game.entity.Game.PlayerType;
import tictactoe.user.entity.AppUser;
import tictactoe.user.entity.AppUserRepository;

@Controller
public class TicTacToeController {

    private final GameService gameService;

    private final AppUserRepository appUserRepository;

    private static final int DEFAULT_NUMBER_OF_ROWS = 3;

    public TicTacToeController(GameService gameService, AppUserRepository appUserRepository) {
        this.gameService = gameService;
        this.appUserRepository = appUserRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal, Model model) {
        AppUser appUser = getAppUser(principal);

        Game game = gameService.getLastGame(appUser);
        if (game == null) {
            game = gameService.create(appUser, true, DEFAULT_NUMBER_OF_ROWS);
        }

        setModelGameAttributes(model, game);

        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String takeTurns(
            Model model,
            Principal principal,
            @RequestParam("tile_id") String tileId,
            @RequestParam(value = "new_game", required = false, defaultValue = "false") boolean newGame,
            @RequestParam(value = "number_of_rows", required = false) Integer numberOfRows,
            @RequestParam(value = "player_go_first", required = false, defaultValue = "false") boolean playerGoFirst) {
        AppUser appUser = getAppUser(principal);

        Game game;
        if (newGame) {
            if (numberOfRows == null || numberOfRows <= 0) {
                game = gameService.create(appUser, playerGoFirst, DEFAULT_NUMBER_OF_ROWS);
            } else {
                game = gameService.create(appUser, playerGoFirst, numberOfRows);
            }

            if (!playerGoFirst) {
                // give computer a small advantage by always placing X in the center as its
                // first move
                gameService.takeTurn(game, "1-1");
            }
        } else {
            game = gameService.getLastGame(appUser);
            gameService.takeTurn(game, tileId); // Player Turn
            gameService.takeTurnRandom(game); // Computer Turn
        }

        setModelGameAttributes(model, game);

        return "index";
    }

    private void setModelGameAttributes(Model model, Game game) {
        boolean playerGoFirst = game.getPlayer1Type() == PlayerType.HUMAN;

        String playerStatus;
        switch (game.getState()) {
            case PLAYER_1_WIN:
                playerStatus = playerGoFirst ? "WON" : "LOST";
                break;
            case PLAYER_2_WIN:
                playerStatus = playerGoFirst ? "LOST" : "WON";
                break;
            case DRAW:
                playerStatus = "DRAW";
                break;
            case IN_PROGRESS:
            default:
                playerStatus = "IN_PROGRESS";
                break;
        }

        model.addAttribute("playerGoFirst", playerGoFirst);
        model.addAttribute("numberOfRows", game.getNumberOfRows());
        model.addAttribute("playStatus", playerStatus);
        model.addAttribute("board", game.getRows());
    }

    private AppUser getAppUser(Principal principal) {
        AppUser appUser = appUserRepository.findByUsername(principal.getName());
        if (appUser == null) {
            throw new UsernameNotFoundException("Invalid username: " + principal.getName());
        }
        return appUser;
    }
}
