LevityMC - Cosmetics
Game Version: 1.19.4

%abysseconomy_<currency>_raw% - The raw amount of currency the player has
Cosmetics:

Tabcolor, Crown, Glow, Hat, ChatColor, Nickname Ticket, Nickname Paint, Title, Title Paint, 




Hats:
 - Tabcolor
 - Crown (icon in front of name)
 - Glow Effect
 - Helmet Item

Chat Colors:
 - Solid Color
 - Gradient

Nickname Ticket:
 - Custom Nickname

Nickname Paint:
 - Applied to nickname tickets

Titles:
  - Prefix

Backpack:
  - 3 base pages, buy more with vault currency

Search:
  - /search (thing) 

Crates:
  - Rotate a random selection of cosmetics every hour, with config limits on how many of which type of cosmetic

Drop Command:
  - /drop start Winter 25 3000
  - /drop start {cosmetic} {amount} {credits}

- Upon opening a case and getting a hat, you will have set % chances to obtain variables
  on the hat; tabcolor (https://imgur.com/smJo1fE), crown; the icon in front of the players
  name (https://imgur.com/kodGOwj), a glow (https://i.imgur.com/WqrAtU0.png).
  Remember that a hat can have more than 1 variable (e.g: a tab color and a crown)
- Hats will also be the only thing a player can wear on their head, blocking users from
  equipping helmets would be great.
  Chat Colors:
- Chat colors can either be a single solid color (https://prnt.sc/hUS90oJFXLDu) or it can be
  a gradient (https://prnt.sc/s0fFhBc3T9Gr).
  Nickname Ticket:
- A nickname ticket is a consumable item that can be used to have a custom nickname.
  (https://imgur.com/jEHRE1D).
  Nickname Paint:
- Nickname paint follows the same color guidelines as stated in Chat Colors, but they can
  only be applied to a nickname ticket.
  Titles:
- Titles, either you or we can create, but they will be predetermined titles a player can
  equip as a {prefix}, an example is shown below in the example.
  Title Paint:
- Title paint follows the same color guidelines as stated in Chat Colors, but they can only
  be applied to a title.
  How it may potentially look in LuckPermsChat:
  {title-color}, {nickname-color}, and {chat-color} would all be placeholders that are assigned by
  what the player has both unlocked and selected.
  "{title-color} {title}&r {nickname-color} {name}&r &8≫{chat-color} {message}"
  Database colors (basic example):
  Chat colors:
  Red - chatcolor.red #example permission (player loses permission if item is
  traded)
  Blue - select -> {chat-color} #sets chat to blue
  Title colors:
  green - select -> {title-color} #sets title to yellow
  Nickname colors:
  Orange - select -> {nickname-color} #sets name to orange
  Final example output
  [Gamer] [Countdown] >> This is my example message
  Cosmetic Storage:
  Every player’s cosmetics will be stored in their /backpack. Your backpack is where you can
  equip/unequip certain cosmetics. Here is how the backpack should look
  (https://imgur.com/RtqyRox). As you can see in the reference image, there is some cosmetics in
  the backpack. By default, your backpack has 3 pages, you can purchase more pages with
  credits (premium currency).
  /search Feature:
  Upon using the command /search, a menu will open with the cosmetic you are searching. For
  example: /search chat color. When using that command, every cosmetic with “chat color” in their
  name will be shown in the menu (https://imgur.com/46AIdo4). Another example would be:
  /search purple (https://imgur.com/8UeCPyM). As you can also see from the /search feature, it
  lists the owner of the item.
  Crates:
  These cosmetics will be obtainable thru custom crates (read Dependencies). Every crate will be
  on a 1 hour rotation with different items that will be randomly chosen from the cosmetic bank (all
  the cosmetics). In the config there should be a part with the crates where you can choose how
  many chat colors, titles, nickname paints, title paints should be allowed in the crate, for
  example:
  crates:
  tier1:
  Chatcolors: 3
  Titles: 4
  Nickpaints: 8
  Titlepaints: 12
  tier2:
  Chatcolors: 5
  Titles: 6
  Nickpaints: 4
  Titlepaints: 8
  /drop:
  Upon using this command, a small UI will open with the selected cosmetic from the drop. When
  starting a /drop, you can choose the amount of credits the drop should start at (e.g: /drop start
  {item} {cosmetic} {credits}. Every second, the price will go down from the starting price, when a
  player buys the item, it announces in chat and the drop will be closed until the next one.
  Another usage of /drop would be on special events/few seasons. How the crate drop works
  would be that there is a set price for the crate and a limited amount of them. (e.g: /drop start
  {crate} {amount} {credits}). For example here the crate would be set as “Winter Crate”, there
  would only be 25 allowed to be purchased and the amount to purchase it would be of 3,000
  credits. The command would go as follows: /drop start Winter 25 3000.
  Config:
  The config for chat colors & other items should go as follow:
  chatcolors:
  Winter: <#084CFB> {text} </#ADF3FD> (2 colors gradient)
  CrimsonRed: <#FB0000> {text} </#3B5AFD to #E00000> (3 colors gradient)
  Gradients should be able to go up to the desired amount of colors to use as a gradient (no limit)
  Nickname paints and Title paints would be the same format.