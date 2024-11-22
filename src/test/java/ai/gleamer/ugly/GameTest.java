package ai.gleamer.ugly;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static ai.gleamer.ugly.QuestionCategory.*;
import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Nested
    class Roll {
        @Test
        void shouldAskQuestionWhenTheCurrentPlayerIsNotInPenaltyBox() {
            // given
            var game = new Game();
            game.addNewPlayer("Player 1");
            game.addNewPlayer("Player 2");
            // when
            game.roll(7);
            // then
            assertThat(game.getPlayerCursor()).isEqualTo(0);
            assertThat(game.getPlayer(0).getRandomQuestionCursor()).isEqualTo(7);
            assertThat(game.getPlayer(0).isInPenaltyBox()).isFalse();
        }

        @Test
        void shouldAskQuestionWhenTheCurrentPlayerIsInPenaltyBoxAndTheRollNumberIsOdd() {
            // given
            var game = new Game();
            game.addNewPlayer("Player 1");
            game.addNewPlayer("Player 2");
            game.getPlayer(0).setInPenaltyBox(true);
            // when
            game.roll(5);
            // then
            assertThat(game.getPlayer(0).isInPenaltyBox()).isEqualTo(false);
            assertThat(game.getPlayer(0).getRandomQuestionCursor()).isEqualTo(5);
            assertThat(game.getQuestions().countQuestions(SCIENCE)).isEqualTo(49);
        }

        @Test
        void shouldNotAskQuestionWhenTheCurrentPlayerIsInPenaltyBoxAndTheRollNumberIsEven() {
            // given
            var game = new Game();
            game.addNewPlayer("Player 1");
            game.addNewPlayer("Player 2");
            game.getPlayer(0).setInPenaltyBox(true);
            // when
            game.roll(4);
            // then
            assertThat(game.getPlayer(0).isInPenaltyBox()).isEqualTo(true);
            assertThat(game.getPlayer(0).getRandomQuestionCursor()).isEqualTo(0);
        }
    }

    @Nested
    class RegisterCorrectAnswer {

        @Test
        void shouldRewardTheCurrentPlayerWhenHeIsNotInPenaltyBox(){
            // given
            var game = new Game();
            game.addNewPlayer("Player 1");
            game.addNewPlayer("Player 2");
            // when
            game.registerCorrectAnswer();
            // then
            assertThat(game.getPlayer(0).getCoins()).isEqualTo(1);
            assertThat(game.getPlayerCursor()).isEqualTo(1);
        }

        @Test
        void shouldRewardTheCurrentPlayerWhenHeIsInPenaltyBoxAndGettingOutFromIt(){
            // given
            var game = new Game();
            game.addNewPlayer("Player 1");
            game.addNewPlayer("Player 2");
            game.getPlayer(0).setInPenaltyBox(true);
            // when
            game.registerCorrectAnswer();
            // then
            assertThat(game.getPlayer(0).getCoins()).isEqualTo(0);
            assertThat(game.getPlayerCursor()).isEqualTo(1);

        }

        @Test
        void shouldNotRewardTheCurrentPlayerWhenHeIsInPenaltyBoxAndNotGettingOutFromIt(){
            // given
            var game = new Game();
            game.addNewPlayer("Player 1");
            game.addNewPlayer("Player 2");
            game.getPlayer(0).setInPenaltyBox(true);
            // when
            game.registerCorrectAnswer();
            // then
            assertThat(game.getPlayer(0).getCoins()).isEqualTo(0);
            assertThat(game.getPlayerCursor()).isEqualTo(1);
        }
    }

    @Nested
    class RegisterWrongAnswer {

        @Test
        void shouldMoveTheCurrentPlayerInPenaltyBoxAndSwitchToNext() {
            // given
            var game = new Game();
            game.addNewPlayer("Player 1");
            game.addNewPlayer("Player 2");
            // when
            game.registerWrongAnswer();
            // then
            assertThat(game.getPlayer(0).isInPenaltyBox()).isTrue();
            assertThat(game.getPlayerCursor()).isEqualTo(1);
        }
    }

}