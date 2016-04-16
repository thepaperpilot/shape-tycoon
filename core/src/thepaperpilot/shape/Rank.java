package thepaperpilot.shape;

import java.math.BigDecimal;

public class Rank {

    public static Rank BASE_RANK;

    static {
        // TODO load in via properties file?
        Rank rank;
        BASE_RANK = new Rank("1", "Just You");
        //noinspection UnusedAssignment
        BASE_RANK.next = rank = new Rank("2", "Family");
        rank.next = rank = new Rank("4", "Household");
        rank.next = rank = new Rank("7", "DnD group");
        rank.next = rank = new Rank("20", "Raid group");
        rank.next = rank = new Rank("30", "Classroom");
        rank.next = rank = new Rank("74", "Lecture Hall");
        rank.next = rank = new Rank("128", "Active friendster users");
        rank.next = rank = new Rank("200", "Active myspace users");
        rank.next = rank = new Rank("340", "Active google+ users");
        rank.next = rank = new Rank("640", "Small convention");
        rank.next = rank = new Rank("1000", "Small youtube channel");
        rank.next = rank = new Rank("2500", "Small city");
        rank.next = rank = new Rank("5000", "Local college");
        rank.next = rank = new Rank("8000", "New social media site");
        rank.next = rank = new Rank("12000", "Small country");
        rank.next = rank = new Rank("55519", "American Samoa");
        rank.next = rank = new Rank("159358", "U.S. Virgin Islands");
        rank.next = rank = new Rank("563767", "Wyoming");
    }

    public BigDecimal rank;
    public String string;

    public Rank next;

    public Rank(String rank, String string) {
        this.rank = new BigDecimal(rank);
        this.string = string;
    }
}
