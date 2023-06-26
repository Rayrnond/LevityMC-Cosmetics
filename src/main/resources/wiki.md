## Levity Cosmetics
Made for Purpur / Paper 1.19.4 by Rayrnond and Alex.dev.

### Features
- Backpack GUI
- Crown (symbol next to username)
- Glowing Effect
- Titles and Title Paint
- Nickname and Nickname Paint
- Tablist color
- Chat color
- Search Function
- Drop Function
- Async Database Connection


### Configuration
Each cosmetic has it's own configuration file (except title and title paints, which are combined in titles.yml), located in the data directory of your server. E.g. `plugins/LevityCosmetics/cosmetics/chatcolors.yml`

Configuration files contain the cosmetics for each server. Every cosmetic has a name, which acts as a unique identifier for the database. DO NOT change this value.

Color codes are managed by Minimessage. See documentation at [https://docs.advntr.dev/minimessage/format.html](https://docs.advntr.dev/minimessage/format.html)



### Commands

| Command                                        | Description                                                     | Permission                         |
|------------------------------------------------|-----------------------------------------------------------------|------------------------------------|
| `/backpack`                                    | Opens the backpack GUI                                          | `levitycosmetics.backpack`         |
| `/cosmetic give [player] [cosmeticName]`       | Gives the player a cosmetic                                     | `levitycosmetics.admin.give`       |
| `/cosmetic remove [player] [cosmeticName]`     | Removed a cosmetic from a player                                | `levitycosmetics.admin.remove`     |
| `/cosmetic reset [player]`                     | Reset player's cosmetics                                        | `levitycosmetics.admin.reset`      |
| `/cosmetic list [player]`                      | List all cosmetics                                              | `levitycosmetics.admin.list`       |
| `/nicknameticket [player]`                     | Get a nickname ticket                                           | `levitycosmetics.admin.ticket`     |
| `/search [query]`                              | Search for a cosmetic                                           | `levitycosmetics.search`           |
| `/drop [cosmeticName] [amount] [startingCost]` | Start a cosmetic, or crate drop. [cosmeticName] can be a crate. | `levitycosmetics.admin.drop`       |
| `/crate list`                                  | List all crates                                                 | `levitycosmetics.admin.crate.list` |
| `/crate get [crateName] [player]`              | Get a crate                                                     | `levitycosmetics.admin.crate.get`  |

[//]: # (| `/tradeban [player]`                           | Toggle trade ban status &#40;not implemented, missing instruction&#41;  | `levitycosmetics.admin.tradeban`   |)

### Placeholders

| Placeholder                                                                                    | Example                         | Description                                                                                      |
|------------------------------------------------------------------------------------------------|---------------------------------|--------------------------------------------------------------------------------------------------|
| %levitycosmetics_title%                                                                        | Lava                            | The player's current title                                                                       |
| %levitycosmetics_titlepaint%                                                                   | Lava                            | The player's current title with applied paints showing                                           |
| %levitycosmetics_tabcolor%                                                                     | WarWhale (username)             | The player's username with applied current tabcolor cosmetic showing                             |
| %levitycosmetics_chatcolor%                                                                    | %message%                       | The player's chat message with applied chat color cosmetic, replace %message% with your message. |
| %levitycosmetics_crown%                                                                        | $                               | The player's current crown with applied colors                                                   |
| %levitycosmetics_glow%                                                                         | §c                              | The player's glow. Some tab plugins require you set the nametag color for glow to work properly. |
| **NOTE**                                                                                       |                                 |                                                                                                  |
| Some placeholders support ending with `_spaced` which adds one space if the cosmetic is valid. | $<playername> to $ <playername> |                                                                                                  |

Here is an example placeholder setup I used for testing crowns, titles, and tab colors, for tablist, using placeholderapi.
`%levitycosmetics_crown_spaced%%levitycosmetics_titlepaint_spaced%%levitycosmetics_tabcolor%`

For chat, the plugin will automatically override chat messages with their chatcolor.

### Crates

Crates are implemented as right-clickable items that unlock one random cosmetic. This system was never intended to be complex, and I recommend you switch to a different crate plugin (CrazyCrates, Crates+, e.t.c.). You can use any of the cosmetic commands through console to give players' cosmetics.

### API

#### Get a cosmetic by ID
```java
Cosmetic.getCosmetic("<cosmetic id>");
```

#### Get all cosmetics
```java
Cosmetic.getAllCosmetics();
```

#### Get a user's data profile
```java
UserDataService.shared.retrieveUserFromCache(<UUID>); // only works for players online, save using shared instance of UserDataService
```

#### Database Notes

The database uses MySQL. Three tables exist: 1) userdata 2) titles 3) nicknames. User data has columns for owned cosmetics, and selected cosmetics. If there is an unknown cosmetic in their owned cosmetics column, it will still persist through unknownCosmetics list in UserData.class. 
It's not recommended to manually mess with the database. Ever. I will not provide support for any issues that arise from messing with the database.

Also, nicknames and titles have a unique cosmetic id which starts with up to 5 random numbers, then 10 characters based on the user's uuid (e.g. 12345ugbsbfkghd). Don't change this. Ever.

### Support

Minor modifications such as language changes, bug fixes, minor logic changes, e.t.c. will be provided for free until 30 (thirty) days after project completion. After this time, any modifications will be subject to charge at the discretion of the developer. Major modifications such as complete logic overhauls, will be subject to charges, at the discretion of the developer.

#### Contact  
`Rayrnond` on Discord or raymond@reflexian.com through email.