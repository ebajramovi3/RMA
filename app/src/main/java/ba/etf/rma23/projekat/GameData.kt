package ba.etf.rma23.projekat

class GameData{
    companion object{
        fun getAll(): List<Game> {
            return listOf(
               /* Game(
                    "Call of Duty: Modern Warfare II",
                    "PlayStation 5, Xbox One",
                    "October 28, 2022",
                    3.9,
                    "cod",
                    "This is a first-person shooter in which players assume the roles of special forces units pursuing terrorist threats.",
                    "Infinity Ward, Activision Shanghai",
                    "Activision",
                    "Action",
                    "Call of Duty: Modern Warfare II is a 2022 first-person shooter game developed by Infinity Ward and published by Activision. It is a sequel to the 2019 reboot, and serves as the nineteenth installment in the overall Call of Duty series.",
                    listOf(
                        UserReview("GamersRD", System.currentTimeMillis(), "Call of Duty: Modern Warfare II is an exceptional game and a worthy successor. The game exhibits extraordinary graphics and realism that many fans seek."),
                        UserReview("Meristation", System.currentTimeMillis(), "Call of Duty: Modern Warfare 2 continues the great efforts from the 2019 installment by improving every aspect without taking too many risks."),
                        UserRating("GamingBolt", System.currentTimeMillis(), 3.5),
                        UserReview("anzbert", System.currentTimeMillis(), "It's the best shooter of this year for me. Infinity ward just nailed the crack addictive gameplay loop of running, shooting, dying and repeat."),
                        UserRating("NN010", System.currentTimeMillis(), 3.2)
                    )
                ),
                Game(
                    "Minecraft",
                    "Windows, macOS, Linux",
                    "November 18, 2011",
                    4.0,
                    "minecraft",
                    "From a first-person perspective, players traverse an “open-world” environment, avoid hazards, build new structures, and craft weapons to occasionally defend against monsters.",
                    "Mojang Studios",
                    "Xbox Game Studios, Sony Interactive Entertainment",
                    "Sandbox",
                    "In Minecraft, players explore a blocky, procedurally generated, three-dimensional world with virtually infinite terrain and may discover and extract raw materials, craft tools and items, and build structures, earthworks, and machines.",
                    listOf(
                        UserReview("ProjectAnomaly", System.currentTimeMillis(), "A wonderfully nostalgic game that boosts creativity and really soothes a bad day."),
                        UserRating("wego-82510", System.currentTimeMillis(), 3.5),
                        UserReview("iamcarterpeterson", System.currentTimeMillis(), "How this game or virtual hash is still relevant is horrible to digest. The graphics look bad. There really isn't anything to do on here except waste life."),
                        UserRating("neutredlum", System.currentTimeMillis(), 3.0),
                        UserRating("projectsgamer", System.currentTimeMillis(), 4.5),
                        UserRating("myyyyy1", System.currentTimeMillis(), 5.0)
                    )
                ),
                Game(
                    "Grand Theft Auto: Vice City",
                    "PlayStation 2, Windows, Xbox, Mac OS X",
                    "29 October, 2002",
                    3.7,
                    "gta",
                    "This is an action-adventure game in which players assume the role of an ex-convict (Tommy Vercetti) sent to establish a drug trade in the fictional Vice City.",
                    "Rockstar North",
                    "Rockstar Games",
                    "Action",
                    "Grand Theft Auto: Vice City is a 2002 action-adventure game developed by Rockstar North and published by Rockstar Games. It is the fourth main entry in the Grand Theft Auto series, following 2001's Grand Theft Auto III, and the sixth instalment overall.",
                    listOf()
                ),
                Game(
                    "Fortnite",
                    "Windows PC, PlayStation 4, Xbox One",
                    "July 21, 2017",
                    3.4,
                    "fortnite",
                    "This is an action game in which players build forts, gather resources, craft weapons, and battle hordes of monsters in frenetic combat. ",
                    "Epic Games",
                    "Epic Games, Warner Bros",
                    "Action",
                    "Fortnite is an online video game developed by Epic Games and released in 2017. It is available in three distinct game mode versions that otherwise share the same general gameplay and game engine.",
                    listOf()
                ),
                Game(
                    "Atomic Heart",
                    "PlayStation 4",
                    "February 21, 2023",
                    4.0,
                    "atomic_heart",
                    "Players battle robots and mutant creatures while trying to uncover conspiracies. Players use machine guns, shotguns, axes, and special skills to kill various enemies.",
                    "Mundfish",
                    "Mundfish",
                    "Action",
                    "Atomic Heart is a single-player first-person shooter developed by Mundfish and published by Focus Entertainment and 4Divinity.",
                    listOf()
                ),
                Game(
                    "Rocket League",
                    "Nintendo Switch",
                    "July 7, 2015",
                    4.0,
                    "rocket_league",
                    "This is an action-racing game in which players drive futuristic cars to play games of soccer. ",
                    "Psyonix",
                    "Psyonix",
                    "Action",
                    "Rocket League is a vehicular soccer video game developed and published by Psyonix.",
                    listOf()
                ),
                Game(
                    "PlayerUnknown's Battlegrounds",
                    "Stadia",
                    "March 23, 2017",
                    3.0,
                    "pubg",
                    "This is a multiplayer action game in which players can participate in \"last-man-standing\"-style shootouts while collecting supplies/weapons/armor around an island.",
                    "PUBG Corporation",
                    "KRAFTON",
                    " Action-adventure",
                    "The game, which was inspired by the Japanese film Battle Royale, is based on previous mods created by Brendan \"PlayerUnknown\" Greene for other games, and expanded into a standalone game under Greene's creative direction.",
                    listOf()
                ),
                Game(
                    "The Sims 4",
                    "Macintosh, Windows PC, PlayStation 4",
                    "September 2, 2014",
                    4.5,
                    "sims_4",
                    "/",
                    "Maxis, The Sims Studio",
                    "Electronic Arts",
                    "Simulation Game, Adventure",
                    "The Sims 4 is a social simulation game developed by Maxis and published by Electronic Arts.",
                    listOf()
                ),
                Game(
                    "League of Legends",
                    "Windows PC",
                    "October 27, 2009",
                    3.2,
                    "lol",
                    "This is an adventure game in which players assume the role of a child (alongside a Yeti) on a quest to find his mother in an icy land. From a third-person perspective, players traverse the icy landscapes, solve puzzles, and battle the forces of an ice witch.",
                    "Riot Games",
                    "Riot Games",
                    "Action role-playing game",
                    "League of Legends, commonly referred to as League, is a 2009 multiplayer online battle arena video game developed and published by Riot Games. ",
                    listOf()
                ),
                Game(
                    "Valorant",
                    "Windows PC",
                    "June 2, 2020",
                    3.8,
                    "valorant",
                    "This is a first-person shooter in which players compete in team-based multiplayer matches. Players use an assortment of firearms and special abilities to kill opponents to complete mission objectives.",
                    "Riot Games",
                    "Riot Games",
                    "Action",
                    "Valorant is a free-to-play first-person tactical hero shooter developed and published by Riot Games, for Windows. Teased under the codename Project A in October 2019, the game began a closed beta period with limited access on April 7, 2020, followed by a release on June 2, 2020.",
                    listOf()
                )*/
            )
        }

        fun getDetails(title: String) : Game {
            var allGames = getAll()
            var game = allGames.find {
                it.title == title
            }
            if(game != null) return game
            return Game(1,"Test", "Test", "Test", 0.0, "Test", "Test", "Test", "Test","Test","Test",
                listOf())
        }
    }

}