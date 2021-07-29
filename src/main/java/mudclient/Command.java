package mudclient;
public class Command {
    public static final int CL_TRADE_CONFIRM_ACCEPT = 104;
    public static final int CL_APPEARANCE = 235;
    public static final int CL_BANK_CLOSE = 212;
    public static final int CL_BANK_DEPOSIT = 23;
    public static final int CL_BANK_WITHDRAW = 22;
    public static final int CL_CAST_GROUND = 158;
    public static final int CL_CAST_INVITEM = 4;
    public static final int CL_CAST_NPC = 50;
    public static final int CL_CAST_OBJECT = 99;
    public static final int CL_CAST_PLAYER = 229;
    public static final int CL_CAST_SELF = 137;
    public static final int CL_CHAT = 216;
    public static final int CL_CHOOSE_OPTION = 116;
    public static final int CL_CLOSE_CONNECTION = 31;
    public static final int CL_COMBAT_STYLE = 29;
    public static final int CL_COMMAND = 38;
    public static final int CL_DUEL_ACCEPT = 176;
    public static final int CL_DUEL_CONFIRM_ACCEPT = 77;
    public static final int CL_DUEL_DECLINE = 197;
    public static final int CL_DUEL_ITEM_UPDATE = 33;
    public static final int CL_DUEL_SETTINGS = 8;
    public static final int CL_FRIEND_ADD = 195;
    public static final int CL_FRIEND_REMOVE = 167;
    public static final int CL_CAST_GROUNDITEM = 249;
    public static final int CL_GROUNDITEM_TAKE = 247;
    public static final int CL_IGNORE_ADD = 132;
    public static final int CL_IGNORE_REMOVE = 241;
    public static final int CL_INV_CMD = 90;
    public static final int CL_INV_DROP = 246;
    public static final int CL_INV_UNEQUIP = 170;
    public static final int CL_INV_WEAR = 169;
    public static final int CL_KNOWN_PLAYERS = 163;
    public static final int CL_LOGIN = 0;
    public static final int CL_LOGOUT = 102;
    public static final int CL_NPC_ATTACK = 190;
    public static final int CL_NPC_CMD = 202;
    public static final int CL_NPC_TALK = 153;
    public static final int CL_OBJECT_CMD1 = 136;
    public static final int CL_OBJECT_CMD2 = 79;
    public static final int CL_PACKET_EXCEPTION = 3;
    public static final int CL_PING = 67;
    public static final int CL_PLAYER_ATTACK = 171;
    public static final int CL_PLAYER_DUEL = 103;
    public static final int CL_PLAYER_FOLLOW = 165;
    public static final int CL_PLAYER_TRADE = 142;
    public static final int CL_PM = 218;
    public static final int CL_PRAYER_OFF = 254;
    public static final int CL_PRAYER_ON = 60;
    public static final int CL_REPORT_ABUSE = 206;
    public static final int CL_SESSION = 32;
    public static final int CL_SETTINGS_GAME = 111;
    public static final int CL_SETTINGS_PRIVACY = 64;
    public static final int CL_SHOP_BUY = 236;
    public static final int CL_SHOP_CLOSE = 166;
    public static final int CL_SHOP_SELL = 221;
    public static final int CL_SLEEP_WORD = 45;
    public static final int CL_TRADE_ACCEPT = 55;
    public static final int CL_TRADE_DECLINE = 230;
    public static final int CL_TRADE_ITEM_UPDATE = 46;
    public static final int CL_USEWITH_GROUNDITEM = 53;
    public static final int CL_USEWITH_INVITEM = 91;
    public static final int CL_USEWITH_NPC = 135;
    public static final int CL_USEWITH_OBJECT = 115;
    public static final int CL_USEWITH_PLAYER = 113;
    public static final int CL_WALK = 187;
    public static final int CL_WALK_ACTION = 16;
    public static final int CL_WALL_OBJECT_COMMAND1 = 14;
    public static final int CL_WALL_OBJECT_COMMAND2 = 127;
    public static final int CL_CAST_WALLOBJECT = 180;
    public static final int CL_USEWITH_WALLOBJECT = 161;

    public static final int SV_APPEARANCE = 59;
    public static final int SV_BANK_CLOSE = 203;
    public static final int SV_BANK_OPEN = 42;
    public static final int SV_BANK_UPDATE = 249;
    public static final int SV_CLOSE_CONNECTION = 4;
    public static final int SV_DUEL_ACCEPTED = 210;
    public static final int SV_DUEL_CLOSE = 225;
    public static final int SV_DUEL_CONFIRM_OPEN = 172;
    public static final int SV_DUEL_OPEN = 176;
    public static final int SV_DUEL_OPPONENT_ACCEPTED = 253;
    public static final int SV_DUEL_SETTINGS = 30;
    public static final int SV_DUEL_UPDATE = 6;
    public static final int SV_FRIEND_LIST = 71;
    public static final int SV_FRIEND_MESSAGE = 120;
    public static final int SV_FRIEND_STATUS_CHANGE = 149;
    public static final int SV_GAME_SETTINGS = 240;
    public static final int SV_IGNORE_LIST = 109;
    public static final int SV_INVENTORY_ITEMS = 53;
    public static final int SV_INVENTORY_ITEM_REMOVE = 123;
    public static final int SV_INVENTORY_ITEM_UPDATE = 90;
    public static final int SV_LOGOUT_DENY = 183;
    public static final int SV_MESSAGE = 131;
    public static final int SV_OPTION_LIST = 245;
    public static final int SV_OPTION_LIST_CLOSE = 252;
    public static final int SV_PLAYER_DIED = 83;
    public static final int SV_PLAYER_QUEST_LIST = 5;
    public static final int SV_PLAYER_STAT_EQUIPMENT_BONUS = 153;
    public static final int SV_PLAYER_STAT_EXPERIENCE_UPDATE = 33;
    public static final int SV_PLAYER_STAT_FATIGUE = 114;
    public static final int SV_PLAYER_STAT_FATIGUE_ASLEEP = 244;
    public static final int SV_PLAYER_STAT_LIST = 156;
    public static final int SV_PLAYER_STAT_UPDATE = 159;
    public static final int SV_PRAYER_STATUS = 206;
    public static final int SV_PRIVACY_SETTINGS = 51;
    public static final int SV_REGION_ENTITY_UPDATE = 211;
    public static final int SV_REGION_GROUND_ITEMS = 99;
    public static final int SV_REGION_NPCS = 79;
    public static final int SV_REGION_NPC_UPDATE = 104;
    public static final int SV_REGION_OBJECTS = 48;
    public static final int SV_REGION_PLAYERS = 191;
    public static final int SV_REGION_PLAYER_UPDATE = 234;
    public static final int SV_REGION_WALL_OBJECTS = 91;
    public static final int SV_SERVER_MESSAGE = 89;
    public static final int SV_SERVER_MESSAGE_ONTOP = 222;
    public static final int SV_SHOP_CLOSE = 137;
    public static final int SV_SHOP_OPEN = 101;
    public static final int SV_SLEEP_CLOSE = 84;
    public static final int SV_SLEEP_INCORRECT = 194;
    public static final int SV_SLEEP_OPEN = 117;
    public static final int SV_SOUND = 204;
    public static final int SV_SYSTEM_UPDATE = 52;
    public static final int SV_TELEPORT_BUBBLE = 36;
    public static final int SV_TRADE_CLOSE = 128;
    public static final int SV_TRADE_CONFIRM_OPEN = 20;
    public static final int SV_TRADE_ITEMS = 97;
    public static final int SV_TRADE_OPEN = 92;
    public static final int SV_TRADE_RECIPIENT_STATUS = 162;
    public static final int SV_TRADE_STATUS = 15;
    public static final int SV_WELCOME = 182;
    public static final int SV_WORLD_INFO = 25;
}