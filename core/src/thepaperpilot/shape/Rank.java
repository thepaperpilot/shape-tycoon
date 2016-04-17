package thepaperpilot.shape;

import java.math.BigDecimal;

public class Rank {

    public static Rank BASE_RANK;

    static {
        // TODO load in via properties file?
        Rank rank;
        BASE_RANK = new Rank("1", "Just You");
        //noinspection UnusedAssignment
        rank.next = rank = new Rank("7", "DnD group");
        rank.next = rank = new Rank("20", "Raid group");
        rank.next = rank = new Rank("30", "Classroom");
        rank.next = rank = new Rank("80", "Lecture Hall");
        rank.next = rank = new Rank("842", "Vatican City");
        rank.next = rank = new Rank("2870", "Ludum Dare (34)");
        rank.next = rank = new Rank("5000", "Local college");
        rank.next = rank = new Rank("8000", "New social media site");
        rank.next = rank = new Rank("12000", "Small country");
        rank.next = rank = new Rank("55519", "American Samoa");
        rank.next = rank = new Rank("167000", "Comet Con");
        rank.next = rank = new Rank("563767", "Wyoming");
        rank.next = rank = new Rank("6378000", "Average US State");
        rank.next = rank = new Rank("8200000", "Active Friendster Users");
        rank.next = rank = new Rank("36000000", "Active Reddit Users");
        rank.next = rank = new Rank("37254503", "California");
        rank.next = rank = new Rank("220000000", "Active Google+ Users");
        rank.next = rank = new Rank("318900000", "United States of America");
        rank.next = rank = new Rank("320000000", "Active Twitter Users");
        rank.next = rank = new Rank("555000000", "Active Tumblr Users");
        rank.next = rank = new Rank("1591000000", "Active Facebook Users");
        rank.next = rank = new Rank("7400000000", "World Population!!!");
    }

    public BigDecimal rank;
    public String string;

    public Rank next;

    public Rank(String rank, String string) {
        this.rank = new BigDecimal(rank);
        this.string = string;
    }
}
