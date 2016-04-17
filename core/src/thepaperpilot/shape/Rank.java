package thepaperpilot.shape;

import java.math.BigDecimal;

public class Rank {

    public static Rank BASE_RANK;

    static {
        // TODO load in via properties file?
        Rank rank;
        // no... GWT why did you make me do this?
        BASE_RANK = rank = new Rank("1", "Just You");
        rank.next = new Rank("7", "DnD group");
        rank = rank.next;
        rank.next = new Rank("20", "Raid group");
        rank = rank.next;
        rank.next = new Rank("30", "Classroom");
        rank = rank.next;
        rank.next = new Rank("80", "Lecture Hall");
        rank = rank.next;
        rank.next = new Rank("842", "Vatican City");
        rank = rank.next;
        rank.next = new Rank("2870", "Ludum Dare (34)");
        rank = rank.next;
        rank.next = new Rank("5000", "Local college");
        rank = rank.next;
        rank.next = new Rank("8000", "New social media site");
        rank = rank.next;
        rank.next = new Rank("12000", "Small country");
        rank = rank.next;
        rank.next = new Rank("55519", "American Samoa");
        rank = rank.next;
        rank.next = new Rank("167000", "Comet Con");
        rank = rank.next;
        rank.next = new Rank("563767", "Wyoming");
        rank = rank.next;
        rank.next = new Rank("6378000", "Average US State");
        rank = rank.next;
        rank.next = new Rank("8200000", "Active Friendster Users");
        rank = rank.next;
        rank.next = new Rank("36000000", "Active Reddit Users");
        rank = rank.next;
        rank.next = new Rank("37254503", "California");
        rank = rank.next;
        rank.next = new Rank("220000000", "Active Google+ Users");
        rank = rank.next;
        rank.next = new Rank("318900000", "United States of America");
        rank = rank.next;
        rank.next = new Rank("320000000", "Active Twitter Users");
        rank = rank.next;
        rank.next = new Rank("555000000", "Active Tumblr Users");
        rank = rank.next;
        rank.next = new Rank("1591000000", "Active Facebook Users");
        rank = rank.next;
        rank.next = new Rank("7400000000", "World Population!!!");
    }

    public BigDecimal rank;
    public String string;

    public Rank next;

    public Rank(String rank, String string) {
        this.rank = new BigDecimal(rank);
        this.string = string;
    }
}
