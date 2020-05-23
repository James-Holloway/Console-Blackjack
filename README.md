# Console Blackjack

This was made in school while learning about object oriented programming with Java and therefore I suggest you don't expect too much.

This was made with NetBeans 8.2 and Java 1.8 (52.0)

This is a console version of Blackjack. You will be prompted with the number of NPCs and players you want to add. You will then get to name each Player. Players take turns, in hotseat mode. All players and NPCs start with £500. The player will then get to choose how much they bet. When a user's turn is reached, they will can choose what to do (see commands below).

When all players have gone bust or cashed out, the game continues until the AI loses all their money.

Note: If your console doesn't support Unicode characters then the suit will show as a question mark (?).

#### Commands
- Twist - pick up another card. Aliases: t, hit, hit me, +
- Stick - keep your current hand. Aliases: s, stay, keep, =
- View - view your current hand. Aliases: v, hand, -
- Double down - double the bet but forced to pick up one last card. Aliases: d, double, 2
- Surrender - keep half your bet but you're no longer playing. Aliases - sur, .
- Help - show this message. Aliases: h, help, ?
- Balance - get your balance and current bet. Aliases: bal, b, bet, money
- Cash Out - leave the game and take your current balance, essentially quit. Aliases: cash, c, quit, leave, exit, x


#### Notes

The builds can be run from the EXE or running the .bat file in the ZIP. The executables are packaged using Windows' MakeCab.exe.

The coloured version uses [ansicon](https://github.com/adoxa/ansicon). The coloured console EXE is packaged with it. 